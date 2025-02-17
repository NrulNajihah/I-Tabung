/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.model.Lead;

/**
 *
 * @author NAJIHAH
 */
public class LeadDAO {

    private Connection con;
    private String jdbcURL = "jdbc:mysql://localhost:3306/tabung_inos";
    private String username = "root";
    private String password = "root";

    private static final String INSERT_LP = "INSERT INTO leadproj(uID) VALUES (?);";
    private static final String SELECT_ALL_LPS = "SELECT * FROM leadproj WHERE uID = ?;";
    private static final String SELECT_ALL_LISTLPS = "SELECT lp.kpID, u.username\n"
            + "FROM leadproj lp\n"
            + "INNER JOIN user u ON lp.uID = u.uID\n"
            + "GROUP BY lp.kpID, u.username;";  // Group by both kpID and username

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

    public void insertLead(Lead lead) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LP)) {
            preparedStatement.setInt(1, lead.getuID());

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

    public List<Lead> selectAllLps(int uID) {
        List<Lead> leadList = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LPS)) {
            preparedStatement.setInt(1, uID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int kpID = resultSet.getInt("kpID");
                Lead lead = new Lead(kpID, uID);
                leadList.add(lead);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leadList;
    }

    public List<Lead> selectAllListLp() {
        List<Lead> lead = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LISTLPS); ResultSet rs = preparedStatement.executeQuery()) {

            // Iterate over the result set and construct Lead objects
            while (rs.next()) {
                int kpID = rs.getInt("kpID");
                String username = rs.getString("username"); // Retrieve username instead of uID

                // Assuming the Lead class has a constructor that accepts kpID and username
                lead.add(new Lead(kpID, username)); // Add the Lead object with kpID and username
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lead;
    }

    public String selectUsernameByIds(int kpID, int uID) throws SQLException {
    String username = null;
    String query = "SELECT u.username " +
                   "FROM leadproj lp " +
                   "INNER JOIN user u ON lp.uID = u.uID " +
                   "WHERE lp.kpID = ? AND lp.uID = ?";
    
    try (Connection connection = getConnection(); 
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        
        // Set the kpID and uID parameters in the query
        preparedStatement.setInt(1, kpID);
        preparedStatement.setInt(2, uID);  // This was missing in your original code
        
        try (ResultSet rs = preparedStatement.executeQuery()) {
            // Fetch the username from the result set
            if (rs.next()) {
                username = rs.getString("username");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Error fetching username for kpID: " + kpID + " and uID: " + uID, e);
    }
    
    return username; 
}

}
