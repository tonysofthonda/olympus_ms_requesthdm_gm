package com.honda.olympus.ms.requesthdm_gm.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class SqlUtil {
	
	private SqlUtil() {
	    throw new IllegalStateException("SqlUtil class");
	  }
	
	public static Long getLong(ResultSet rs, String field) throws SQLException {
		Long value = rs.getLong(field);
		return rs.wasNull() ? null : value;
	}

	public static Integer getInt(ResultSet rs, String field) throws SQLException {
		int value = rs.getInt(field);
		return rs.wasNull() ? null : value;
	}

	public static String getTimeStamp() {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return sdf.format(timestamp);
	}

}
