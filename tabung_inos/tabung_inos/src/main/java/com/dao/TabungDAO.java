/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import com.model.Tabung;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; 
import java.math.BigDecimal;
/**
 *
 * @author NAJIHAH
 */
public class TabungDAO {
    private Connection con;
    private String jdbcURL = "jdbc:mysql://localhost:3306/tabung_inos";
    private String username = "root";
    private String password = "root";

     private static final String INSERT_TBG = "INSERT INTO tabung(uID,noTb, tname, tdate, amount, balance) VALUES (?, ?, ?, ?, ?,?);";
    private static final String SELECT_ALL_TBGS = "SELECT * FROM tabung WHERE uID = ?;";
    private static final String SELECT_ALL_LISTTBGS = "SELECT * FROM tabung;";
    private static final String SELECT_TBG_BY_TBGID = "SELECT * FROM tabung WHERE tID = ?;";
    private static final String UPDATE_TBG = "UPDATE tabung SET uID =  ?,noTb =? , tname = ?, tdate = ?, amount = ? WHERE tID = ?;";
    private static final String DELETE_TBG = "DELETE from tabung WHERE tID = ?;";

    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertTabung(Tabung tabung) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TBG)) {
            preparedStatement.setInt(1, tabung.getuID());
            preparedStatement.setInt(2, tabung.getNoTb());
            preparedStatement.setString(3, tabung.getTname());
            preparedStatement.setDate(4, new Date(tabung.getTdate().getTime()));
            preparedStatement.setBigDecimal(5, tabung.getAmount());
            preparedStatement.setBigDecimal(6, tabung.getBalance());

            // Execute the SQL statement
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Insertion failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log the exception or handle it appropriately
            throw e;  // Rethrow the exception to be handled by the calling code
        }
    }

    public List<Tabung> selectAllTbgs(int uID) {
        List<Tabung> tabungList = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TBGS)) {
            preparedStatement.setInt(1, uID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int tID = resultSet.getInt("tID");
                int noTb = resultSet.getInt("noTb");
                String tname = resultSet.getString("tname");
                Date tdate = resultSet.getDate("tdate");
                BigDecimal amount = resultSet.getBigDecimal("amount");
                BigDecimal balance = resultSet.getBigDecimal("balance");
                Tabung tabung = new Tabung(tID, uID,noTb, tname, tdate, amount, balance);
                tabungList.add(tabung);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabungList;
    }

    public List<Tabung> selectAllListTbg() {
        List<Tabung> tabung = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LISTTBGS); ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                int tID = rs.getInt("tID");
                int noTb = rs.getInt("noTb");
                int uID_tabung = rs.getInt("uID");
                String tname = rs.getString("tname");
                Date tdate = rs.getDate("tdate");
                BigDecimal amount = rs.getBigDecimal("amount");
                BigDecimal balance = rs.getBigDecimal("balance");
                tabung.add(new Tabung(tID, uID_tabung, noTb, tname, tdate, amount,balance));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabung;
    }
    
    

    public Tabung selectTbgByID(int tbgID) {
        Tabung tabung = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TBG_BY_TBGID)) {
            preparedStatement.setInt(1, tbgID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int tID = rs.getInt("tID");
                int noTb = rs.getInt("noTb");
                int uID_tabung = rs.getInt("uID");
                String tname = rs.getString("tname");
                Date tdate = rs.getDate("tdate");
                BigDecimal amount = rs.getBigDecimal("amount");
                BigDecimal balance = rs.getBigDecimal("balance");
                tabung = new Tabung(tID, uID_tabung, noTb, tname, tdate, amount,balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabung;
    }
    
     public List<Tabung> selectTbg(int tID) {
        List<Tabung> tabungList = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TBG_BY_TBGID)) {
            preparedStatement.setInt(1, tID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int noTb = rs.getInt("noTb");
                int uID_tabung = rs.getInt("uID");
                String tname = rs.getString("tname");
                Date tdate = rs.getDate("tdate");
                BigDecimal amount = rs.getBigDecimal("amount");
                BigDecimal balance = rs.getBigDecimal("balance");

                Tabung tabung = new Tabung(tID, uID_tabung, noTb, tname, tdate, amount,balance);
                tabungList.add(tabung);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabungList;
    }

    public boolean updateTbg (Tabung tabung) throws SQLException {
        boolean recordUpdate;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TBG)) {
            preparedStatement.setInt(1, tabung.getuID());
            preparedStatement.setInt(2, tabung.getNoTb());
            preparedStatement.setString(3, tabung.getTname());
            preparedStatement.setDate(4, new Date(tabung.getTdate().getTime()));
            preparedStatement.setBigDecimal(5, tabung.getAmount());
            preparedStatement.setInt(6, tabung.gettID());

            System.out.println("In : " + tabung.gettID());

            recordUpdate = preparedStatement.executeUpdate() > 0;
        }
        return recordUpdate;
    }
    
    public boolean deleteTbg(int tbgID) throws SQLException {
    boolean recordDeleted=false;
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TBG)) {
                 preparedStatement.setInt(1, tbgID);
                 recordDeleted = preparedStatement.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    System.out.println("deleteTbg => Value for recordDeleted = "+ recordDeleted);
    return recordDeleted;
}
    
    public String selectTNameById(int tID) throws SQLException {
    String tname = null;
    String query = "SELECT tname FROM tabung WHERE tID = ?";
    try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, tID);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            tname = rs.getString("tname");
        }
    }
    return tname;
}
    
    public void updateAmount(int tID, BigDecimal newAmount) throws SQLException {
    String sql = "UPDATE tabung SET amount = ? WHERE tID = ?";

    try (Connection connection = getConnection();
         PreparedStatement pstmt = connection.prepareStatement(sql)) {
         
        pstmt.setBigDecimal(1, newAmount);
        pstmt.setInt(2, tID);

        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Tabung amount updated successfully.");
        } else {
            System.out.println("Failed to update tabung amount.");
        }
    }
}
    
    public void updateBalance(int tID) throws SQLException {
    String selectTabungSql = "SELECT amount FROM tabung WHERE tID = ?";
    String selectTotalExpSql = "SELECT SUM(total_Exp) AS totalExp FROM project WHERE tID = ?";
    String updateTabungSql = "UPDATE tabung SET balance = ? WHERE tID = ?";
    
    BigDecimal amount = BigDecimal.ZERO;
    BigDecimal totalExp = BigDecimal.ZERO;
    BigDecimal newBalance;

    try (Connection connection = getConnection()) {
        // Fetch the amount from tabung
        try (PreparedStatement pstmt = connection.prepareStatement(selectTabungSql)) {
            pstmt.setInt(1, tID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                amount = rs.getBigDecimal("amount");
            }
        }

        // Fetch the total expenses from project
        try (PreparedStatement pstmt = connection.prepareStatement(selectTotalExpSql)) {
            pstmt.setInt(1, tID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalExp = rs.getBigDecimal("totalExp");
                if (totalExp == null) {
                    totalExp = BigDecimal.ZERO;  // Default to zero if null
                }
            }
        }

        // Calculate the new balance
        newBalance = amount.subtract(totalExp);

        // Update the balance in tabung
        try (PreparedStatement pstmt = connection.prepareStatement(updateTabungSql)) {
            pstmt.setBigDecimal(1, newBalance);
            pstmt.setInt(2, tID);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Tabung balance updated successfully.");
            } else {
                System.out.println("Failed to update tabung balance.");
            }
        }
    }
}

}