/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author MSIS
 */
public class DbConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/ecomm";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // change if needed

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    
}
