package org.example.membershipapp;

import java.sql.*;

public class madatabaseConnect {
    private static Connection cons;
    private static madatabaseConnect dbConnectionManager;
    private madatabaseConnect(){
        try {
            cons = DriverManager.getConnection("jdbc:sqlite:D:/Kuliah/RPLBO/TugasAkhir_RPLBO/membershipbuying.sqlite");
        }catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Connection getConnection(){
        if (dbConnectionManager == null)
            dbConnectionManager = new madatabaseConnect();
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

    public static void main(String[] args) throws SQLException {
        Connection c = getConnection();
        if (c != null)
            System.out.println("Berhasil");

        int len = countLen("users");
        System.out.println(len);

    }
}
