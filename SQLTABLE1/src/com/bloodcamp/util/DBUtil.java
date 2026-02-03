package com.bloodcamp.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	public static Connection getDBConnection() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            return DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe",
                "arunika",
                "pass123"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
