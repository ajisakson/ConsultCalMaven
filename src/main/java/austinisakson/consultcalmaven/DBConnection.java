/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Database info:
Connection String
Server name:  45.79.52.109 
Database name:  ConsultCal
Username:  consultcal
Password:  ccP@ssw0rd
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
    
    private static final String databaseName = "ConsultCal";
    private static final String DB_URL = "jdbc:mysql://45.79.52.109/" + databaseName;
    private static final String username = "consultcal";
    private static final String password = "ccP@ssw0rd";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    
    static Connection conn;
    
    /**
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception {
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(DB_URL, username, password);
        System.out.println("Connection successful!");
    }
    
    /**
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
        conn.close();
        System.out.println("Connection closed!");
    }
}
