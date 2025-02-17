/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import com.model.*;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
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
public class MemberDAO {

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

    public void insertMember(Member member) throws SQLException {
    String sql = "INSERT INTO member (uID, id, position) VALUES (?, ?, ?)";

    // Enhanced logging to catch null or invalid values early
    if (member == null) {
        throw new IllegalArgumentException("Member object is null");
    }
    if (member.getuID() == 0 || member.getId() == 0 || member.getPosition() == null) {
        throw new IllegalArgumentException("Invalid member data: uID = " + member.getuID() +
                                            ", id = " + member.getId() +
                                            ", position = " + member.getPosition());
    }

    try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {

        // Set parameters for the SQL statement
        pst.setInt(1, member.getuID());
        pst.setInt(2, member.getId());
        pst.setString(3, member.getPosition());

        // Log input values for debugging
        System.out.println("Inserting member with uID: " + member.getuID());
        System.out.println("ID: " + member.getId());
        System.out.println("Position: " + member.getPosition());

        // Execute the SQL statement
        int rowsAffected = pst.executeUpdate();

        if (rowsAffected == 0) {
            throw new SQLException("Insertion failed, no rows affected.");
        } else {
            System.out.println("Member inserted successfully.");
        }

    } catch (SQLException e) {
        e.printStackTrace();  // Log the exception for debugging
        throw e;  // Rethrow the exception to be handled by the calling code
    }
}

    public int getUIDByKPID(int kpID) throws SQLException {
    String query = "SELECT uID FROM project WHERE id = ?";
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, kpID);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return rs.getInt("uID");
        } else {
            throw new SQLException("No user found for kpID: " + kpID);
        }
    }
}
    
    public int getIDByUID(int uID) throws SQLException {
    String query = "SELECT id FROM member WHERE uID = ?";
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, uID);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new SQLException("No user found for uID: " + uID);
        }
    }
}


public void insertLead(int uID, int projectID) throws SQLException {
    String insertMemberSql = "INSERT INTO member (uID, id, position) VALUES (?, ?, 'Ketua Projek')";

    try (Connection connection = getConnection();
         PreparedStatement insertMemberPst = connection.prepareStatement(insertMemberSql)) {

        // Use the leadUID for the insertion
        insertMemberPst.setInt(1, uID);   // Insert leadUID as uID
        insertMemberPst.setInt(2, projectID); // Use projectID as id

        int rowsAffected = insertMemberPst.executeUpdate();

        if (rowsAffected == 0) {
            throw new SQLException("Insertion failed, no rows affected.");
        }

        System.out.println("Lead member inserted successfully with UID: " + uID + " and projectID: " + projectID);
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;  // Rethrow the exception to be handled by the calling code
    }
}

    public List<Member> selectAllMember(int id) {
        List<Member> mmbList = new ArrayList<>();
        String query = "SELECT m.mID, u.username, m.id, m.position\n"
                + "FROM member m\n"
                + "INNER JOIN user u ON m.uID = u.uID\n"
                + "WHERE m.id = ?\n"
                + "GROUP BY u.username;";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int mID = rs.getInt("mID");
                String username = rs.getString("username");
                String position = rs.getString("position");

                // Debugging output
                System.out.println("Fetched mID: " + mID);
                System.out.println("Fetched member: " + username);
                System.out.println("Fetched posisi: " + position);

                Member member = new Member(mID, username, id, position);
                mmbList.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mmbList;
    }

    public boolean deleteMmb(int mID) throws SQLException {
        boolean recordDeleted = false;
        String dlete = "DELETE from member WHERE mID = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(dlete)) {
            preparedStatement.setInt(1, mID);
            recordDeleted = preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("deleteMmb => Value for recordDeleted = " + recordDeleted);
        return recordDeleted;
    }
    
    
}
