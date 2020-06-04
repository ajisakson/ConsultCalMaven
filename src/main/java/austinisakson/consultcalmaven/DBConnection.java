/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Database info:
Connection String
Server name:  52.206.157.109 
Database name:  U05qn6
Username:  U05qn6
Password:  53688580422
*/

package austinisakson.consultcalmaven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ajisa
 */
public class DBConnection {
    
    private static final String databaseName = "U05qn6";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/" + databaseName;
    private static final String username = "U05qn6";
    private static final String password = "53688580422";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    
    static Connection conn;
    
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception {
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(DB_URL, username, password);
        System.out.println("Connection successful!");
    }
    
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
        conn.close();
        System.out.println("Connection closed!");
    }
}
