/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import java.sql.*;
import com.model.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell
 */
public class CompDAO {

     private String jdbcURL = "jdbc:mysql://localhost:3306/tabung_inos";
    private String username = "root";
    private String password = "root";

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
    
    public void insertComp(Component component) throws SQLException {
    String sql = "INSERT INTO component (tID, compID, compName, total_All, compDate) VALUES (?, ?, ?, ?, ?)";
try (Connection connection = getConnection(); 
         PreparedStatement pst = connection.prepareStatement(sql)) {
    pst.setInt(1, component.gettID());
    pst.setString(2, component.getCompID());
    pst.setString(3, component.getCompName());
    pst.setBigDecimal(4, component.getTotal_All());
    pst.setDate(5, component.getCompDate());

    System.out.println("Executing query: " + pst.toString());
    pst.executeUpdate();
} catch (SQLException ex) {
    ex.printStackTrace();
    throw new SQLException("SQL error occurred while inserting the component.", ex);
}

}
    
    public BigDecimal calculateTotalAllByTabung(int tID) throws SQLException {
        String sql = "SELECT SUM(total_All) AS totalAmount FROM component WHERE tID = ?";
        BigDecimal totalAmount = BigDecimal.ZERO;

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, tID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalAmount = rs.getBigDecimal("totalAmount");
            }
        }

        return totalAmount != null ? totalAmount : BigDecimal.ZERO;
    }

    
    public boolean doesTabungExist(int tID) throws SQLException {
    String query = "SELECT COUNT(*) FROM tabung WHERE tID = ?";
    try (Connection connection = getConnection(); 
         PreparedStatement pst = connection.prepareStatement(query)) {
        pst.setInt(1, tID);
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Fetched tID: " + tID + " count = " + count);
                return count > 0; // Returns true if count > 0
            }
        }
    }
    return false; // Returns false if no rows exist
}
    
     public boolean isComponentExists(int cID) throws SQLException {
    String query = "SELECT COUNT(*) FROM component WHERE cID = ?";
    try (Connection connection = getConnection(); 
         PreparedStatement pst = connection.prepareStatement(query)) {
        pst.setInt(1, cID);
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Fetched cID: " + cID + " count = " + count);
                return count > 0; // Returns true if count > 0
            }
        }
    }
    return false; // Returns false if no rows exist
}
    
    public List<Component> selectAllComp(int tID) {
        List<Component> cmpList = new ArrayList<>();
        String query = "SELECT cID, compID, compName, total_All, compDate FROM component WHERE tID = ?; ";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, tID);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int cID = rs.getInt("cID");
                String compID = rs.getString("compID");
                String compName = rs.getString("compName");
                BigDecimal total_All = rs.getBigDecimal("total_All");
                Date compDate = rs.getDate("compDate");

                // Debugging output
                System.out.println("Fetched cID: " + cID);

                Component component = new Component(cID, compID, compName, total_All, compDate);
                cmpList.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cmpList;
    }
    
    public List<Component> selectAllListCmp() {
        List<Component> component = new ArrayList<>();
        String select ="SELECT * from component";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(select); ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                int cID = rs.getInt("cID");
                String compID = rs.getString("compID");
                String compName = rs.getString("compName");
                BigDecimal total_All = rs.getBigDecimal("total_All");
                Date compDate = rs.getDate("compDate");
                component.add(new Component(cID,  compID, compName,  total_All, compDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return component;
    }
}
