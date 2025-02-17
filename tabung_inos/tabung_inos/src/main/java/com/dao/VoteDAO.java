/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import com.model.Vote;
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
public class VoteDAO {

    private Connection con;
    private String jdbcURL = "jdbc:mysql://localhost:3306/tabung_inos";
    private String username = "root";
    private String password = "root";

    private static final String INSERT_VT = "INSERT INTO vote( noVote, vote, noseq) VALUES (?, ?, ?);";
    private static final String SELECT_ALL_LISTVTS = "SELECT * FROM vote;";
    private static final String SELECT_VT_BY_VID = "SELECT * FROM vote WHERE vID = ?;";
    private static final String UPDATE_VT = "UPDATE vote SET noVote = ?, vote = ?, noseq = ? WHERE vID = ?;";
    private static final String DELETE_VT = "DELETE from vote WHERE vID = ?;";

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

    public void insertVote(Vote vote) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VT)) {
            preparedStatement.setString(1, vote.getNoVote());
            preparedStatement.setString(2, vote.getVote());
            preparedStatement.setInt(3, vote.getNoseq());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Vote> selectAllVotes() {
        List<Vote> vote = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement pst
                = connection.prepareStatement(SELECT_ALL_LISTVTS); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int vID = rs.getInt("vID");
                String noVote = rs.getString("noVote");
                String Vote = rs.getString("vote");
                int noseq = rs.getInt("noseq");
                vote.add(new Vote(vID, noVote, Vote, noseq));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vote;
        // Rest of the methods (selectStudentsByStudentId, updateStudent, deleteStudent) go here
        // Close resources and handle exceptions appropriately
    }

    public Vote selectVoteByID(int voteid) {
        Vote vote = null;

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(SELECT_VT_BY_VID)) {
            pst.setInt(1, voteid);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int vID = rs.getInt("vID");
                String noVote = rs.getString("noVote");
                String Vote = rs.getString("vote");
                int noseq = rs.getInt("noseq");
                vote = new Vote(vID, noVote, Vote, noseq);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vote;
    }

    public boolean updateVote(Vote vote) throws SQLException {
        boolean recordUpdate;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_VT)) {
            preparedStatement.setString(1, vote.getNoVote());
            preparedStatement.setString(2, vote.getVote());
            preparedStatement.setInt(3, vote.getNoseq());
            preparedStatement.setInt(4, vote.getvID());

            System.out.println("In : " + vote.getvID());

            recordUpdate = preparedStatement.executeUpdate() > 0;
        }
        return recordUpdate;
    }

    public boolean deleteVote(int voteID) throws SQLException {
        boolean recordDeleted = false;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VT)) {
            preparedStatement.setInt(1, voteID);
            recordDeleted = preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("deleteVote => Value for recordDeleted = " + recordDeleted);
        return recordDeleted;
    }

    public String selectnoVoteById(int vID) throws SQLException {
        String noVote = null;
        String query = "SELECT noVote FROM vote WHERE vID = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, vID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                noVote = rs.getString("noVote");
            }
        }
        return noVote;
    }

    public Vote getVoteByNo(String noVote) {
        Vote vote = null;
        String query = "SELECT * FROM vote WHERE noVote = ?"; // Assuming 'noVote' is the column name

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, noVote);  // Set the noVote parameter
            System.out.println("noVote " +noVote);

            try (ResultSet rs = ps.executeQuery()) {
              if (rs.next()) {
                    vote = new Vote();
                    vote.setVote(rs.getString("vote"));
                    System.out.println("vote " +vote);
                }
            }
       } catch (SQLException e) {
        e.printStackTrace();
    }

        return vote;
    }
}
