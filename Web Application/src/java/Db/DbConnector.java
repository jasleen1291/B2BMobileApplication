/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author jasleen
 */
public class DbConnector {

    public static Connection con;

    public static Connection initConnection() {
        if (con == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/b2b", "root", "root");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return con;
    }
}
