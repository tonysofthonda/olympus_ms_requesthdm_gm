package com.honda.olympus.ms.requesthdm_gm.util;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SqlUtil 
{
	
	public static Integer getInt(ResultSet rs, String field) throws SQLException {
		int value = rs.getInt(field);
		return rs.wasNull() ? null : value;
	}
	
}
