/**
 * 
 */
package cn.edu.buaa.practice.simulate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * 自动生成access_log模拟数据
 * 
 * @author BurningIce
 *
 */
public class AccessLogGenerator {
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
	private static long MIN_DATE = parseDateTime("01/Feb/2016:00:00:00").getTime();
	private static long MAX_DATE = parseDateTime("01/Feb/2016:08:00:00").getTime();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length != 2) {
			System.out.println("Usage: srcDir destDir");
			return;
		}
		
		File srcDir = new File(args[0]);
		File destDir = new File(args[1]);
		if(!srcDir.exists()) {
			System.out.println("srcDir not exists: " + args[0]);
			return;
		}
		
		if(!destDir.exists()) {
			destDir.mkdirs();
		}
		
		List<String> srcAccessLogs = new LinkedList<String>();
		File[] files;
		
		if(srcDir.isDirectory()) {
			files = srcDir.listFiles();
		} else {
			files = new File[] {srcDir};
		}
		
		for(File file : files) {
			try {
				List<String> allLines = Files.readAllLines(Paths.get(file.getAbsolutePath()), Charset.forName("utf-8"));
				srcAccessLogs.addAll(allLines);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		int currentLines = 0;
		int currentFileIndex = 0;
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destDir.getAbsolutePath() + File.separatorChar + "access.log." + currentFileIndex)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for(long timestamp = MIN_DATE; timestamp < MAX_DATE; timestamp += 1000L) {
			// 每秒钟模拟随机10~1000次访问
			int count = randomInt(10, 50);
			for(int i = 0; i < count; ++i) {
				String line = srcAccessLogs.get(randomInt(srcAccessLogs.size()));
				StringBuilder newLine = new StringBuilder();
				int p1 = line.indexOf(' ');
				if(p1 == -1) {
					// invalid line
					continue;
				}
				int p2 = line.indexOf('[', p1);
				if(p2 == -1) {
					// invalid line
					continue;
				}
				int p3 = line.indexOf(']', p2);
				if(p3 == -1) {
					// invalid line
					continue;
				}
				
				newLine.append(randomIp()).append(line.substring(p1, p2))
						.append('[').append(formatDateTime(new Date(timestamp)))
						.append(']').append(line.substring(p3 + 1));
				
				try {
					writer.write(newLine.toString());
					writer.write("\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			currentLines += count;
			if(currentLines >= 15000) {
				// 换文件
				try {
					writer.close();
					++currentFileIndex;
					currentLines = 0;
					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destDir.getAbsolutePath() + File.separatorChar + "access.log." + currentFileIndex)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Random RAND = new Random();
	private static String randomIp() {
		return new StringBuilder()
				.append(randomInt(60, 220)).append('.')
				.append(randomInt(60, 220)).append('.')
				.append(randomInt(60, 220)).append('.')
				.append(randomInt(60, 220)).toString();
	}
	
	public static int randomInt(int upperBound){
    	return RAND.nextInt(upperBound);
    }
	
	private static int randomInt(int lowerBound, int upperBound) {
		return RAND.nextInt(upperBound - lowerBound) + lowerBound;
	}
		
	public static Date parseDateTime(String date) {
		try {
			return DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("非法日志格式");
		}
	}
	
	public static String formatDateTime(Date date) {
		return DATE_FORMAT.format(date) + " +0000";
	}
}
