/*
@author Sieben Veldeman
 */

package databank.jdbc_implementatie;

import databank.database_algemeen.DataAccessContext;
import databank.database_algemeen.DataAccessProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCDataAccessProvider implements DataAccessProvider {

    private static String JDBC_URL = "jdbc:sqlite:";

    @Override
    public DataAccessContext getDataAccessContext() throws SQLException{
        try {
            return new JDBCDataAccessContext(getConnection());
        }
        catch (SQLException ex) {
            throw new SQLException("Could not create DataAccessContext");
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }

    public void editURL(String filepath) {

        String temp = "jdbc:sqlite:";
        JDBC_URL = temp + filepath;
    }

    // Methode die alle nodige tabellen aanmaakt in de databank.
    public void createDataBase() throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmnt = conn.prepareStatement("create table period   (\"id\" integer primary key, \"hour\", \"minute\");");
        stmnt.executeUpdate();
        stmnt = conn.prepareStatement("create table students (\"id\" integer primary key, \"name\");");
        stmnt.executeUpdate();
        stmnt = conn.prepareStatement("create table teacher  (\"id\" integer primary key, \"name\");");
        stmnt.executeUpdate();
        stmnt = conn.prepareStatement("create table location (\"id\" integer primary key, \"name\");");
        stmnt.executeUpdate();
        stmnt = conn.prepareStatement("create table lecture  (\"students_id\", \"teacher_id\", \"location_id\", \"course\", \"day\", \"first_block\", \"duration\");");
        stmnt.executeUpdate();
    }
}
