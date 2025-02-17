/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import java.sql.*;
import com.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NAJIHAH
 */
public class userDAO {

    private Connection con;
    private String query;
    private PreparedStatement pst;
    private ResultSet rs;

    public userDAO(Connection con) {
        this.con = con;
    }

    public void userSignUp(User user) throws SQLException {

        try {
            query = "INSERT INTO user( username, email, password, phone) VALUES (?, ?, ?, ?);";
            pst = this.con.prepareStatement(query);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getPhone());
            rs = pst.executeQuery();

        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }

    }

    public User userLogin(String username, String password) {
        User user = null;
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                // If user exists, populate the User object
                user = new User();
                user.setuID(rs.getInt("uID"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role")); // Fetch the role from the database
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User selectUserById(int userId) throws SQLException {
        User user = null;
        query = "SELECT * FROM user WHERE uID=?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, userId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setuID(rs.getInt("uID"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error during user selection by ID", e);
        }

        return user;
    }

    public User selectPsw(String email) throws SQLException {
        User user = null;
        query = "SELECT uID, password, email FROM user WHERE email=?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, email);
            System.out.println("email :" + email);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    user = new User(); // Initialize the user object
                    user.setuID(rs.getInt("uID"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));

                    // Print the values fetched from the ResultSet
                    System.out.println("Fetched uID :" + user.getuID()); // Use getter method for uID
                    System.out.println("Fetched password :" + user.getPassword()); // Use getter method for password
                    System.out.println("Fetched email :" + email);
                }

            }
        } catch (SQLException e) {
            throw new SQLException("Error during user selection by ID", e);
        }

        return user;
    }

    public List<User> selectRole(int uID) throws SQLException {
        String query = "SELECT role FROM user WHERE uID = ?";
        List<User> users = new ArrayList<>();

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, uID);
            try (ResultSet rs = pst.executeQuery()) {
                // Iterate through the result set to populate the user list
                while (rs.next()) {
                    String role = rs.getString("role");

                    User user = new User(uID, role);
                    users.add(user); // Add each user to the list
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error during user selection by ID", e);
        }

        return users;
    }

    public List<User> selectUser() throws SQLException {
        String query = "SELECT uID,username FROM user";
        List<User> users = new ArrayList<>();

        try (PreparedStatement pst = con.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                // Iterate through the result set to populate the user list
                while (rs.next()) {
                    User user = new User();
                    user.setuID(rs.getInt("uID"));
                    user.setUsername(rs.getString("username"));

                    users.add(user); // Add each user to the list
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error during user selection by ID", e);
        }

        return users;
    }

    public List<User> selectUsersByRole(String role) throws SQLException {
        String sql = "SELECT uID, username FROM user WHERE role = 'user'";
        List<User> users = new ArrayList<>();

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();

            // Iterate through the result set and populate the user list
            while (rs.next()) {
                User user = new User();
                user.setuID(rs.getInt("uID"));
                user.setUsername(rs.getString("username"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Rethrow exception to be handled by calling code
        }
        return users;
    }

    public List<User> selectAllUsers() throws SQLException {
        String sql = "SELECT * FROM user";
        List<User> users = new ArrayList<>();

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();

            // Iterate through the result set and populate the user list
            while (rs.next()) {
                User user = new User();
                user.setuID(rs.getInt("uID"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Rethrow exception to be handled by calling code
        }
        return users;
    }

    public boolean updateUser(User user) throws SQLException {
        boolean recordUpdate;
        String update = "UPDATE user SET  role = ? WHERE uID =?";
        try (PreparedStatement pst = con.prepareStatement(update)) {
            pst.setString(1, user.getRole());
            pst.setInt(2, user.getuID());

            System.out.println("In : " + user.getuID());

            recordUpdate = pst.executeUpdate() > 0;
        }
        return recordUpdate;
    }

    public boolean updatePwdByEmail(String email, String password) throws SQLException {
        String query = "UPDATE user SET password = ? WHERE email = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, password);  // Store hashed password
        pst.setString(2, email);
        
        System.out.println("Fetched email :" + email);
        System.out.println("In password :" + password); // Use getter method for password
        
        
        int rowCount = pst.executeUpdate();
        return rowCount > 0;  // Return true if the update was successful
    }
}
