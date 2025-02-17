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
public class IncomeDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/tabung_inos";
    private String username = "root";
    private String password = "root";

    private static final String SELECT_ALL_INCS
            = "SELECT i.iID, i.uID, i.id,v.noVote, i.total_All,i.idate  "
            + "FROM incomes i "
            + "INNER JOIN vote v ON i.vID = v.vID"
            + "WHERE i.uID=? && i.id = ?";
    private static final String SELECT_INC_BY_IID = "SELECT * FROM incomes WHERE iID = ?;";
    private static final String UPDATE_INC = "UPDATE incomes SET  id = ?, vID = ?, total_All = ?, idate = ? WHERE iID = ?;";
    private static final String DELETE_INC = "DELETE from incomes WHERE iID = ?";

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

    private Integer getpIDByName(String kpName) throws SQLException {
        Integer id = null;
        String query = "SELECT id FROM project WHERE pname = ?";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, kpName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        }
        return id;
    }

    public void insertIncome(Income income) throws SQLException {
        String sql = "INSERT INTO incomes ( id, vID, total_All, idate) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {

            // Set parameters for the SQL statement
            pst.setInt(1, income.getId());
            pst.setInt(2, income.getvID());
            pst.setBigDecimal(3, income.getTotal_All());
            pst.setDate(4, income.getIdate()); // Ensure this is java.sql.Date

            // Log input values for debugging
            System.out.println("ID: " + income.getId());
            System.out.println("Total_All: " + income.getTotal_All());
            System.out.println("Idate: " + income.getIdate());

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

    public List<Income> selectAllIncs(int id) {
        List<Income> IncList = new ArrayList<>();
        String query = "SELECT i.iID, i.id, v.noVote, i.total_All, i.idate "
                + "FROM incomes i "
                + "INNER JOIN vote v ON i.vID = v.vID "
                + "WHERE i.id = ?";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int iID = rs.getInt("iID");
                String noVote = rs.getString("noVote");
                BigDecimal total_All = rs.getBigDecimal("total_All");
                Date idate = rs.getDate("idate");

                // Debugging output
                System.out.println("Fetched iID: " + iID);
                System.out.println("Fetched noVote: " + noVote);
                System.out.println("Fetched total_All: " + total_All);
                System.out.println("Fetched idate: " + idate);

                Income income = new Income(iID, id, noVote, total_All, idate);
                IncList.add(income);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return IncList;
    }

    public List<Income> selectAllIncome(int id) {
        List<Income> IncList = new ArrayList<>();
        String query = "SELECT MAX(i.iID) as iID, i.id, v.noVote, SUM(i.total_All) as total_All\n"
                + "FROM incomes i\n"
                + "INNER JOIN vote v ON i.vID = v.vID\n"
                + "WHERE i.id = ?\n"
                + "GROUP BY i.id, v.noVote; ";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int iID = rs.getInt("iID");
                String noVote = rs.getString("noVote");
                BigDecimal total_All = rs.getBigDecimal("total_All");

                // Debugging output
                System.out.println("Fetched iID: " + iID);
                System.out.println("Fetched noVote: " + noVote);
                System.out.println("Fetched total_All: " + total_All);

                Income income = new Income(iID, id, noVote, total_All);
                IncList.add(income);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return IncList;
    }

    public List<Income> selectVoteID(int id) {
        List<Income> vtList = new ArrayList<>();
        String query = "SELECT i.vID, v.noVote, v.vote "
                + "FROM incomes i "
                + "INNER JOIN vote v ON i.vID = v.vID "
                + "WHERE  i.id = ?";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int vID = rs.getInt("vID");
                String noVote = rs.getString("noVote");
                String vote = rs.getString("vote");

                // Debugging output
                System.out.println("Fetched vID: " + vID + ", noVote: " + noVote + ", vote: " + vote);

                Income income = new Income(id, vID, noVote, vote); // Assuming the Income object has these fields
                vtList.add(income);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vtList;
    }

    public Income selectIncByID(int inID) {
        Income income = null;
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(SELECT_INC_BY_IID)) {
            pst.setInt(1, inID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int iID = rs.getInt("iID");
                int id = rs.getInt("id");
                int vID = rs.getInt("vID");
                BigDecimal total_All = rs.getBigDecimal("total_All");
                Date idate = rs.getDate("idate");
                income = new Income(iID, id, vID, total_All, idate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return income;
    }

    public BigDecimal calculateTotalAllForProject(int projectId) throws SQLException {
        BigDecimal total_All = BigDecimal.ZERO;
        String query = "SELECT SUM(total_All) AS total FROM incomes WHERE id = ?";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, projectId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                total_All = rs.getBigDecimal("total");
                if (total_All == null) {
                    total_All = BigDecimal.ZERO;
                }
            }
        }
        return total_All;
    }

    public Income getIncomeByVoteID(int id, int vID) throws SQLException {
        Income income = null;
        String query = "SELECT total_All FROM incomes WHERE id=? && vID = ?";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.setInt(2, vID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                BigDecimal total_All = rs.getBigDecimal("total_All");
                income = new Income(id, vID, total_All);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return income;
    }

    public boolean updateInc(Income income) throws SQLException {
        boolean recordUpdated;
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(UPDATE_INC)) {

            // Ensure the correct order of setting parameters
            pst.setInt(1, income.getId());  // Project ID
            pst.setInt(2, income.getvID());  // Project ID
            pst.setBigDecimal(3, income.getTotal_All()); // Total amount
            pst.setDate(4, income.getIdate()); // Income date
            pst.setInt(5, income.getiID());  // Income ID (Primary key)

            // Log the actual values being set
            System.out.println("In iID : " + income.getiID());
            System.out.println("In id : " + income.getId());
            System.out.println("In vID : " + income.getvID());
            System.out.println("In total : " + income.getTotal_All());
            System.out.println("In date: " + income.getIdate());

            // Execute the update
            recordUpdated = pst.executeUpdate() > 0;
        }
        return recordUpdated;
    }

    public boolean deleteInc(int incID) throws SQLException {
        boolean recordDeleted = false;

        // Delete the record from the incomes table
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_INC)) {

            // Set parameter and execute update
            preparedStatement.setInt(1, incID);
            recordDeleted = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        System.out.println("deleteInc => Value for recordDeleted = " + recordDeleted);
        return recordDeleted;
    }

    public boolean checkExp(int iID, int id) throws SQLException {
    String listQuery = "SELECT vID FROM incomes WHERE iID = ?";
    String mainQuery = "SELECT COUNT(*) FROM expense WHERE vID = ? AND id = ?";

    try (Connection connection = getConnection()) {
        int fetchedVID = -1;

        // Fetch vID using iID
        try (PreparedStatement listStmt = connection.prepareStatement(listQuery)) {
            listStmt.setInt(1, iID);

            try (ResultSet rsList = listStmt.executeQuery()) {
                if (rsList.next()) {
                    fetchedVID = rsList.getInt("vID");
                    System.out.println("Fetched vID from 'incomes': " + fetchedVID);
                }
            }
        }

        // Only proceed if vID was successfully fetched
        if (fetchedVID != -1) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(mainQuery)) {
                preparedStatement.setInt(1, fetchedVID);
                preparedStatement.setInt(2, id);

                // Log parameters
                System.out.println("Checking for vID: " + fetchedVID + ", ID: " + id);

                // Execute query and process result
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        System.out.println("Fetched count: " + count);
                        return count > 0; // Return true if at least one match is found
                    }
                }
            }
        } else {
            System.out.println("No vID found for iID: " + iID);
        }
    }

    return false; // Default if no matching rows are found
}

    public List<Income> selectAllExpense(int uID, int id) {
        List<Income> ExpList = new ArrayList<>();
        String query = "SELECT v.noVote, \n"
                + "       COALESCE(i.total_All, 0) AS total_All, \n"
                + "       COALESCE(e.total_Exp, 0) AS total_Exp, \n"
                + "       COALESCE(i.total_All, 0) - COALESCE(e.total_Exp, 0) AS balance\n"
                + "FROM (SELECT vID, SUM(total_All) AS total_All \n"
                + "           FROM incomes \n"
                + "           WHERE id = ? \n"
                + "           GROUP BY vID) i\n" // Changed alias to 'i'
                + "INNER JOIN vote v ON i.vID = v.vID\n"
                + "LEFT JOIN (SELECT vID , SUM(total_Exp) AS total_Exp \n"
                + "           FROM expense \n"
                + "           WHERE id = ? \n"
                + "           GROUP BY vID) e ON i.vID = e.vID\n" // 'e' used only for 'expense' subquery
                + "GROUP BY v.noVote, i.total_All, e.total_Exp;";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            // Set the parameters for both subqueries
            pst.setInt(1, id);  // For incomes subquery
            pst.setInt(2, id);  // For expense subquery

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String noVote = rs.getString("noVote");
                BigDecimal total_Exp = rs.getBigDecimal("total_Exp");
                BigDecimal total_All = rs.getBigDecimal("total_All");
                BigDecimal balances = rs.getBigDecimal("balance");

                // Debugging output
                System.out.println("Fetched noVote: " + noVote);
                System.out.println("Fetched total_Exp: " + total_Exp);
                System.out.println("Fetched total_All: " + total_All);
                System.out.println("Calculated balance: " + balances);

                // Add to the list of expenses
                Income income = new Income(noVote, total_Exp, total_All, balances);
                ExpList.add(income);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ExpList;
    }

}
