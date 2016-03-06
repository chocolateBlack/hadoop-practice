/**
 * 
 */
package cn.edu.buaa.practice.todb.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import cn.edu.buaa.practice.todb.JobResultExportHandler;

/**
 * @author BurningIce
 *
 */
public class DeviceJobResultExportHandler implements JobResultExportHandler {
	private final static String SQL_INSERT = "insert into STAT_DEVICE values (?,?,?)";
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.todb.JobResultExportHandler#sqlInsert()
	 */
	@Override
	public String sqlInsert() {
		return SQL_INSERT;
	}

	@Override
	public void setParameterForPS(PreparedStatement pstmt, Date date, String key, String value) throws SQLException {
		byte deviceType = Byte.parseByte(key);
		long pageViews = Long.parseLong(value);
		
		pstmt.setTimestamp(1, new Timestamp(date.getTime()));
		pstmt.setByte(2, deviceType);
		pstmt.setLong(3, pageViews);
	}

}
