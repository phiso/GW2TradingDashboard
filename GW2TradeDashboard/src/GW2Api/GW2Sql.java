/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api;

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
import java.util.Properties;
import java.util.logging.Level;

/**
 *
 * @author Philipp
 */
public class GW2Sql {

    private static GW2Sql instance;
    private static Connection connection;
    private static String dbPath;
    private static Integer timeout;

    private GW2Sql(String dbPath, Integer timeout) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.dbPath = dbPath;
        this.timeout = timeout;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Properties prop = new Properties();
        // dont use secure connection, for simplicity
        prop.setProperty("verifyServerCertificate", "false");
        prop.setProperty("useSSL", "false");
        connection = DriverManager.getConnection("jdbc:mysql:" + dbPath + "?user=admin&password=admin", prop);
        if (connection.isValid(timeout)) {
            LogMngr.getLogger().log(Level.INFO, "Database connection is valid.");
        } else {
            LogMngr.getLogger().log(Level.WARNING, "Unable to validate database connection!");
        }
    }

    public static GW2Sql getInstance(String dbPath, Integer timeout) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (GW2Sql.instance == null) {
            GW2Sql.instance = new GW2Sql(dbPath, timeout);
        }
        return GW2Sql.instance;
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
        String query = String.format("SELECT EXISTS(SELECT * FROM MonitoredItems WHERE itemId = %s AND charactername = %s LIMIT 1) as 'exist'", itemId, character);
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

    public static void insertPrice(Integer itemId, Integer buyPrice, Integer sellPrice, Integer buyQuant, Integer sellQuant) throws SQLException {
        String query = String.format("INSERT INTO pricelist (itemid, buyprice, sellprice, buyquantity, sellquantity) values (%d, %d, %d, %d, %d)",
                itemId, buyPrice, sellPrice, buyQuant, sellQuant);
        simpleUpdate(query);
    }

    public static Map<Date, GW2TradeItem> getPrices(Integer itemId) throws SQLException {
        Map<Date, GW2TradeItem> results = new HashMap<>();
        String query = String.format("SELECT * FROM PriceList WHERE ItemId = %s", itemId);
        ResultSet rs = simpleQuery(query);
        while (rs.next()) {
            String id = rs.getString("itemid");
            Date timestamp = rs.getDate("timestamp");
            results.put(timestamp, new GW2TradeItem(Integer.parseInt(id), rs.getInt("buyprice"),
                    rs.getInt("sellprice"), rs.getInt("buyquantity"), rs.getInt("sellquantity")));
        }
        return results;
    }

    public static Connection getConnection() {
        return connection;
    }
}
