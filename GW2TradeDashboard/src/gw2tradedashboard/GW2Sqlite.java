/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import GW2Api.GW2Objects.GW2TradeItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class GW2Sqlite {

    private static GW2Sqlite instance;
    private static Connection connection;
    private static String dbPath;
    private static Integer timeout;

    private GW2Sqlite(String dbPath, Integer timeout) throws ClassNotFoundException, SQLException {
        this.dbPath = dbPath;
        this.timeout = timeout;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    public static GW2Sqlite getInstance(String dbPath, Integer timeout) throws ClassNotFoundException, SQLException {
        if (GW2Sqlite.instance == null) {
            GW2Sqlite.instance = new GW2Sqlite(dbPath, timeout);
        }
        return GW2Sqlite.instance;
    }

    public static ResultSet simpleQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(timeout);
        return statement.executeQuery(query);
    }

    public static Integer simpleUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(timeout);
        return statement.executeUpdate(query);
    }

    private static Boolean entryExists(Integer itemId, String character) throws SQLException {
        String query = String.format("SELECT EXISTS(SELECT * FROM MonitoredItems WHERE itemId = %s AND charcterName = %s LIMIT 1) as 'exist'", itemId, character);
        ResultSet rs = simpleQuery(query);
        Integer ex = rs.getInt("exist");
        return (ex <= 0) ? false : true;
    }

    public static void addMonitoreditem(Integer itemId, String character) throws SQLException {
        if (entryExists(itemId, character)) {
            String query = String.format("UPDATE MonitoredItems SET timesMonitored=timesMonitored+1 WHERE ItemId = %s AND characterName = %s", itemId, character);
            simpleUpdate(query);
        } else {
            String query = String.format("INSERT INTO MonitoredItems (ItemId, CharacterName, timesMonitored) values (%s, %s, 1)", itemId, character);
            simpleUpdate(query);
        }
    }
    
    public static Map<Date, GW2TradeItem> getPrices(Integer itemId) throws SQLException{
        Map<Date, GW2TradeItem> results = new HashMap<>();
        String query = String.format("SELECT * FROM PriceList WHERE ItemId = %s", itemId);
        ResultSet rs = simpleQuery(query);
        ArrayList<String> itemIds = (ArrayList<String>) rs.getArray("Itemid").getArray();
        ArrayList<String> timestamps = (ArrayList<String>) rs.getArray("timestamp").getArray();
        return results;
    }
}
