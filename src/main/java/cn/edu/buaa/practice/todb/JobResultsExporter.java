/**
 * 
 */
package cn.edu.buaa.practice.todb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

import cn.edu.buaa.practice.todb.impl.BrowserJobResultExportHandler;
import cn.edu.buaa.practice.todb.impl.DeviceJobResultExportHandler;
import cn.edu.buaa.practice.todb.impl.PVJobResultExportHandler;
import cn.edu.buaa.practice.todb.impl.SourceJobResultExportHandler;
import cn.edu.buaa.practice.todb.impl.TimeJobResultExportHandler;
import cn.edu.buaa.practice.todb.impl.UVJobResultExportHandler;

/**
 * @author BurningIce
 *
 */
public class JobResultsExporter {
	private final static int SQL_BATCH_COUNT = 500;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Options opts = new Options();
		opts.addOption("help", false, "help");
		opts.addOption("hdfs", true, "hdfs location");
		opts.addOption("date", true, "date to export. format: yyyyMMdd");
		opts.addOption("h", true, "server host of database");
		opts.addOption("P", true, "server port of database");
		opts.addOption("u", true, "user name of database");
		opts.addOption("p", true, "password of database ");
		opts.addOption("D", true, "database schema to use");

		final Map<String, JobResultExportHandler> jobResultExportHandlersMap = new HashMap<String, JobResultExportHandler>();
		jobResultExportHandlersMap.put("pv", new PVJobResultExportHandler());		
		jobResultExportHandlersMap.put("uv", new UVJobResultExportHandler());
		jobResultExportHandlersMap.put("time", new TimeJobResultExportHandler());
		jobResultExportHandlersMap.put("source", new SourceJobResultExportHandler());
		jobResultExportHandlersMap.put("browser", new BrowserJobResultExportHandler());
		jobResultExportHandlersMap.put("device", new DeviceJobResultExportHandler());

		CommandLineParser parser = new PosixParser();
		try {
			CommandLine cmd = parser.parse(opts, args);
			if(cmd.hasOption("help")) {
				printHelp(opts);
				return;
			}
			
			String hdfs = cmd.getOptionValue("hdfs");
			if(hdfs == null || hdfs.length() == 0) {
				System.out.println("hdfs location is required.");
				printHelp(opts);
				return;
			}
			if(hdfs.endsWith("/")) {
				hdfs = hdfs.substring(0, hdfs.length() - 1);
			}
			
			String date = cmd.getOptionValue("date");
			if(date == null || date.length() < 0) {
				// use yesterday for default
				date = formatDate(new Date(System.currentTimeMillis() - 86400000L));
			}
			
			String host = cmd.getOptionValue("h", "localhost");
			String port = cmd.getOptionValue("P", "3306");
			String user = cmd.getOptionValue("u", "root");
			String password;
			if(cmd.hasOption("p")) {
				password = cmd.getOptionValue("p");
				if(password == null || password.length() == 0) {
					System.out.print("Enter password: ");
					password = readLine();
				}
			} else {
				password = null;
			}
			
			String databaseName = cmd.getOptionValue("D");
			if(databaseName == null || databaseName.length() == 0) {
				System.out.println("database name not provided.");
				printHelp(opts);
				return;
			}
			String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException ex2) {
				System.out.println("no driver found for oracle or mysql.");
				return;
			}
			
			Configuration conf = new Configuration();
			String kpiDataDir = hdfs + "/data/output/kpidata/" + date;
			FileSystem fs = FileSystem.get(URI.create(hdfs), conf);
			FileStatus[] dataDirs = fs.listStatus(new Path(kpiDataDir));
			Path[] paths = new Path[dataDirs.length];
			for(int i = 0; i < dataDirs.length; ++i) {
				paths[i] = dataDirs[i].getPath();
			}
			
			FileStatus[] dataFileStatuses = fs.listStatus(paths, new PathFilter() {
				@Override
				public boolean accept(Path path) {
					return path.getName().startsWith("part-");
				}				
			});
			
			for(FileStatus dataFile : dataFileStatuses) {
				System.out.println("exporting data file: " + dataFile.getPath().toString() + "...");
				String statName = dataFile.getPath().getParent().getName();
				JobResultExportHandler jobResultExportHandler = jobResultExportHandlersMap.get(statName);
				if(jobResultExportHandler == null) {
					// ignore
					continue;
				}
				
				Date statDate = parseDateTime(date);
				
				Connection connection = null;
				try {
					connection = DriverManager.getConnection(jdbcUrl, user, password);
					boolean autoCommit0 = connection.getAutoCommit();
					if(autoCommit0) {
						connection.setAutoCommit(false);
					}
					
					PreparedStatement pstmt = connection.prepareStatement(jobResultExportHandler.sqlInsert());
					int batchedCount = 0;
					int totalCount = 0;
					
					FSDataInputStream input = fs.open(dataFile.getPath());
					BufferedReader reader = null;
					try {
						reader = new BufferedReader(new InputStreamReader(input));						
						String line;
						for( ; (line = reader.readLine()) != null; ) {
							// 格式： key value 或 "key with space" value
							if(line != null && line.length() > 0) {
								int delimiter = line.lastIndexOf('\t');
								if(delimiter == -1) {
									// invalid data
									System.out.println("invalid data: " + line);
									continue;
								}
							
								String key = line.substring(0, delimiter);
								String value = line.substring(delimiter + 1);
								if(key.length() > 1 && key.charAt(0) == '"' && key.charAt(key.length() - 1) == '"') {
									key = key.substring(1, key.length() - 1);
								}
								
								jobResultExportHandler.setParameterForPS(pstmt, statDate, key, value);
								pstmt.addBatch();
								++batchedCount;
								if(batchedCount >= SQL_BATCH_COUNT) {
									// commit in batch
									pstmt.executeBatch();
									connection.commit();
									pstmt = connection.prepareStatement(jobResultExportHandler.sqlInsert());
									batchedCount = 0;
								}
								
								++totalCount;
							}
						}
						
						if(batchedCount > 0) {
							pstmt.executeBatch();
							connection.commit();
						}
						
						System.out.println("finished export data file " + dataFile.getPath().toString() + " of " + totalCount + " items.");
					} catch(IOException ex) {
						ex.printStackTrace();
					} finally {
						if(reader != null) {
							try {
								reader.close();
							} catch(IOException ex2) {
								
							}
						}
					}
				} catch(SQLException sqlex) {
					System.out.println("SQLException: " + sqlex.getMessage());
				} finally {
					if(connection != null) {
						try {
							connection.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (ParseException e) {
			System.out.println("cannot parse command line: " + e.getMessage());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String readLine() {
		try {
			int c;
			StringBuilder sb = new StringBuilder();
			for( ;(c = System.in.read()) != '\n'; ) {
				if(c != '\r' && c != '\n') {
					sb.append((char)c);
				}
			}
			
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static void printHelp(Options opts) {
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.printHelp("JobResultsExporter", opts);
	}
	
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
	static {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	}
	
	private static String formatDate(Date date) {
		if(date == null)
			return null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return dateFormat.format(date);
	}	
	
	private static Date parseDateTime(String dateText) {
		if(dateText == null || dateText.length() == 0)
			return null;
		
		try {
			return DATE_FORMAT.parse(dateText);
		} catch (java.text.ParseException e) {
			System.out.println("failed to parse date: " + dateText);
			return null;
		}
	}
}
