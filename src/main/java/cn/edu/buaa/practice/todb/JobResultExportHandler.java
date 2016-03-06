/**
 * 
 */
package cn.edu.buaa.practice.todb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author BurningIce
 *
 */
public interface JobResultExportHandler {
	public String sqlInsert();
	public void setParameterForPS(PreparedStatement pstmt, Date date, String key, String value) throws SQLException;
}
