/**
 * 
 */
package cn.edu.buaa.practice.todb.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cn.edu.buaa.practice.todb.JobResultExportHandler;

/**
 * @author BurningIce
 *
 */
public class TimeJobResultExportHandler implements JobResultExportHandler {
	private final static String SQL_INSERT = "insert into STAT_TIME values (?,?)";
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.todb.JobResultExportHandler#sqlInsert()
	 */
	@Override
	public String sqlInsert() {
		return SQL_INSERT;
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.todb.JobResultExportHandler#setParameterForPS(java.sql.PreparedStatement, java.util.Date, java.lang.String, java.lang.String)
	 */
	@Override
	public void setParameterForPS(PreparedStatement pstmt, Date date, String key, String value) throws SQLException {
		// key的格式为：yyyy-MM-dd/H
		Date dateTime = parseDateTime(key);
		long pageViews = Long.parseLong(value);
		
		pstmt.setTimestamp(1, new Timestamp(dateTime.getTime()));
		pstmt.setLong(2, pageViews);	
	}

	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd/H", Locale.CHINA);
	static {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	}
	
	private static Date parseDateTime(String dateText) {
		if(dateText == null || dateText.length() == 0)
			return null;
		
		try {
			return DATE_FORMAT.parse(dateText);
		} catch (ParseException e) {
			System.out.println("failed to parse date: " + dateText);
			return null;
		}
	}
}
