package org.example.membershipapp.manager;

import java.sql.*;

public class databaseConnect {
    private static Connection cons;
    private static databaseConnect dbConnectionManager;
    private databaseConnect(){
        try {
            cons = DriverManager.getConnection("jdbc:sqlite:membership.sqlite");
        }catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Connection getConnection(){
        if (dbConnectionManager == null)
            dbConnectionManager = new databaseConnect();
        return dbConnectionManager.cons;
    }

    public static int countLen(String databaseName) throws SQLException {
        int len = 0;
        final String SQL = String.format("SELECT count(*) as jumlah FROM %s",databaseName);
        try (PreparedStatement ps = cons.prepareStatement(SQL)) {
            //ps.setString(1, databaseName);
            ResultSet rs = ps.executeQuery();
            len += rs.getInt("jumlah");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return len;
    }
    
    public static int countCondition(String databaseName, String condition) throws SQLException {
        int len = 0;
        final String SQL = String.format("SELECT count(*) as jumlah FROM %s WHERE %s",databaseName,condition);
        try (PreparedStatement ps = cons.prepareStatement(SQL)) {
            //ps.setString(1, databaseName);
            ResultSet rs = ps.executeQuery();
            len = rs.getInt("jumlah");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return len;
    }

}

