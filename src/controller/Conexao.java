/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jaide
 */
public class Conexao {
    
    public static Connection get() {
        Connection conn = null;
        String url = "jdbc:sqlite:E:/JavaProjects/CMS/cms.db";
        
        try {
            conn = DriverManager.getConnection(url);
        } catch(SQLException sqle) {
            sqle.getMessage();
        }
        
        return conn;
    } 
    
}
