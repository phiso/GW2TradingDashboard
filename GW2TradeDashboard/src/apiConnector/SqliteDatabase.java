/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiConnector;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Philipp Sommer <phiso08@aol.de>
 */
public class SqliteDatabase {

    private final String dbPath;
    private final File dbFile;
    private Connection dbConnection;

    public SqliteDatabase(String path, boolean autoCommit) throws ClassNotFoundException, SQLException {
        dbPath = path;
        dbFile = new File(dbPath);
        Class.forName("org.sqlite.JDBC");
        if (dbFile.exists() && !dbFile.isDirectory()) {
            dbConnection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            dbConnection.setAutoCommit(autoCommit);
        }
    }

    public ResultSet simpleSQLQuery(String query) throws SQLException {
        //query = query.toLowerCase();
        Statement stmt = dbConnection.createStatement();
        ResultSet result = stmt.executeQuery(query);
        stmt.close();
        return result;
    }

    public void simpleSQLUpdate(String query) throws SQLException {
        Statement stmt = dbConnection.createStatement();
        int result = stmt.executeUpdate(query);
        stmt.close();
    }

    public String getDBPath() {
        return dbPath;
    }

    public File getDBFile() {
        return dbFile;
    }

    public Connection getDBConnection() {
        return dbConnection;
    }
}
