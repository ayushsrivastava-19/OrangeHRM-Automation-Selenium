package com.demoproject.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public static Connection getDBConnection(){
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            return con;
        }catch (SQLException e){
            System.out.println("Error while establishing connection with DB");
            e.printStackTrace();
            return null;
        }
    }
}
