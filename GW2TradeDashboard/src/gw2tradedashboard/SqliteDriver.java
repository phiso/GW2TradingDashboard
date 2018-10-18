/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import GW2Objects.GW2Item;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Philipp
 */
public class SqliteDriver {
    
    private static SqliteDriver instance;
    
    private static String driverName;
    private static String dbPath;
    private static String jdbc;
    private static Integer timeout = 30;
    private static Connection conn;
    
    private SqliteDriver(String path) throws ClassNotFoundException, SQLException, FileNotFoundException{
        dbPath = path;
        Class.forName("org.sqlite.JDBC");
        File file = new File(dbPath);
        if (file.exists() && !file.isDirectory()){
            conn = DriverManager.getConnection("jdbc:sqlite:".concat(dbPath));
            conn.setAutoCommit(false); 
            System.out.println(path);
        } else {
            throw new FileNotFoundException("DB File not found or not a file.");
        }
    }
    
    public static SqliteDriver getInstance(String path) throws ClassNotFoundException, SQLException, FileNotFoundException{
        if (SqliteDriver.instance == null){
            SqliteDriver.instance = new SqliteDriver(path);
        }
        return SqliteDriver.instance;
    }
    
    public static ResultSet sqlQuery(String query) throws SQLException{
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        if (query.toLowerCase().contains("select")){
            rs = stmt.executeQuery(query);
        } else {
            stmt.executeUpdate(query);
            stmt.close();
        }
        return rs;
    }

    public static void saveItem(GW2Item item) throws SQLException{
        String query = "insert into items (id, name, description, level, rarity, iconPath, vendorValue, type)"
                + " values (\""+item.getId()+"\",\""+item.getName()+"\",\""+item.getDescription()+"\",\""+item.getLevel()+"\","
                + "\""+item.getRarity()+"\",\""+item.getIconPath()+"\",\""+item.getVendorValue()+"\",\""+item.getType()+"\")";
        sqlQuery(query); 
        conn.commit();
    }
}