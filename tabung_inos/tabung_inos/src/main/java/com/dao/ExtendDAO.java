/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import com.model.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NAJIHAH
 */
public class ExtendDAO {

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

    public void insertExt(Extend extend) throws SQLException {
        String sql = "INSERT INTO extend (id, endP) VALUES (?, ?)";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {

            // Assuming 'pst' is a prepared statement for insertion
// Use id for insertion
            pst.setInt(1, extend.getId()); // Get the id from the Extend object
            System.out.println("id : " + extend.getId()); // Log the id for verification

// Set endP as a java.sql.Date
            pst.setDate(2, extend.getEndP()); // This should be java.sql.Date
            System.out.println("endP : " + extend.getEndP()); // Log endP for verification

            // Execute the SQL statement
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Insertion failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log the exception for debugging
            throw e;  // Rethrow the exception to be handled by the calling code
        }
    }

    public void insertExtend(int id, Date endP) throws SQLException {
        String sql = "INSERT INTO extend (id, endP) VALUES (?, ?)";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {

            // Use id for insertion
            pst.setInt(1, id);

            // Set endP from the Project table
            pst.setDate(2, endP);

            // Execute the SQL statement
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Insertion failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log the exception for debugging
            throw e;  // Rethrow the exception to be handled by the calling code
        }
    }

    public List<Extend> selectAllExts(int id) {
        List<Extend> ExtList = new ArrayList<>();
        String query = "SELECT * FROM extend WHERE id =?";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int exID = rs.getInt("exID");
                Date endP = rs.getDate("endP");

                // Debugging output
                System.out.println("Fetched exID: " + exID);
                System.out.println("Fetched endP: " + endP);

                Extend entend = new Extend(exID, id, endP);
                ExtList.add(entend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ExtList;
    }

    public boolean deleteExt(int extID) throws SQLException {
        boolean recordDeleted = false;
        String DELETE_EXT = "DELETE from extend WHERE exID = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EXT)) {
            preparedStatement.setInt(1, extID);
            recordDeleted = preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("deleteExt => Value for recordDeleted = " + recordDeleted);
        return recordDeleted;
    }
}
