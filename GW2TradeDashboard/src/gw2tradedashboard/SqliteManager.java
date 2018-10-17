/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import apiConnector.SqliteDatabase;
import java.sql.SQLException;

/**
 *
 * @author Philipp
 */
public class SqliteManager {
    
    private static SqliteManager instance;
    private static SqliteDatabase db;
    
    private static void SqliteManager(String path) throws ClassNotFoundException, SQLException{
        db = new SqliteDatabase(path, true);
    }
    
    public static SqliteManager getInstance(String path){
        if (SqliteManager.instance == null){
            SqliteManager.instance = new SqliteManager();
        }
        return SqliteManager.instance;
    }
}
