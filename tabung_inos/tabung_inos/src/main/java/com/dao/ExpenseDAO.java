/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import com.model.Expense;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Types;

/**
 *
 * @author NAJIHAH
 */
public class ExpenseDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/tabung_inos";
    private String username = "root";
    private String password = "root";

    private static final String INSERT_EXP = "INSERT INTO expense( id, vID ,claim, edate, noPO, total_Exp, receipt, receiptFilename) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_ALL_EXPS
            = "SELECT e.eID, e.id, v.noVote, e.claim, e.edate, e.noPO, e.total_Exp, e.receipt, e.receiptFilename "
            + "FROM expense e "
            + "INNER JOIN vote v ON e.vID = v.vID "
            + "WHERE e.id = ?";
    private static final String SELECT_EXP_BY_ID
            = "SELECT * FROM expense WHERE eID = ?;";
    private static final String SELECT_ALL_LISTEXP = "SELECT * FROM expense ";
    private static final String SELECT_EXP_BY_EID = "SELECT * FROM expense WHERE eID = ?;";
    private static final String UPDATE_EXP = "UPDATE expense SET  id = ?, vID = ?, claim =?, edate =?, total_Exp =? WHERE eID = ?";
    private static final String UPDATE_EXP_NOPO = "UPDATE expense SET id = ?, vID = ?, claim = ?, edate = ?, noPO = ?, total_Exp = ? WHERE eID = ?";
    private static final String DELETE_EXP = "DELETE from expense WHERE eID = ?";

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

    private Integer getvIDByNo(String noVote) throws SQLException {
        Integer vID = null;
        String query = "SELECT vID FROM vote WHERE noVote = ?";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, noVote);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                vID = rs.getInt("vID");
            }
        }
        return vID;
    }

    public void insertExpense(Expense expense) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(INSERT_EXP)) {

            pst.setInt(1, expense.getId());
            pst.setInt(2, expense.getvID());
            pst.setString(3, expense.getClaim());
            pst.setDate(4, expense.getEdate());
            pst.setString(5, expense.getNoPO());
            pst.setBigDecimal(6, expense.getTotal_Exp());

            if (expense.getReceipt() != null) {
                pst.setBlob(7, new ByteArrayInputStream(expense.getReceipt()));
                pst.setString(8, expense.getReceiptFilename());
            } else {
                pst.setNull(7, Types.BLOB);
                pst.setNull(8, Types.VARCHAR);
            }

            // Execute the SQL statement
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Insertion failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log the exception or handle it appropriately
            throw e;  // Rethrow the exception to be handled by the calling code
        }
    }
    
    public void insertExp(Expense expense) throws SQLException {
        String exp ="INSERT INTO expense( cID ,claim, edate, noPO, total_Exp, receipt, receiptFilename) VALUES ( ?,  ?, ?, ?, ?, ?, ?);";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(exp)) {

            pst.setInt(1, expense.getcID());
            pst.setString(2, expense.getClaim());
            pst.setDate(3, expense.getEdate());
            pst.setString(4, expense.getNoPO());
            pst.setBigDecimal(5, expense.getTotal_Exp());

            if (expense.getReceipt() != null) {
                pst.setBlob(6, new ByteArrayInputStream(expense.getReceipt()));
                pst.setString(7, expense.getReceiptFilename());
            } else {
                pst.setNull(6, Types.BLOB);
                pst.setNull(7, Types.VARCHAR);
            }

            // Execute the SQL statement
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Insertion failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log the exception or handle it appropriately
            throw e;  // Rethrow the exception to be handled by the calling code
        }
    }

    public List<Expense> selectAllExps(int id) {
        List<Expense> ExpList = new ArrayList<>();
        String query = "SELECT e.eID, e.id, v.noVote, e.claim, e.edate, e.noPO, e.total_Exp, e.receipt, e.receiptFilename "
                + "FROM expense e "
                + "INNER JOIN vote v ON e.vID = v.vID "
                + "WHERE e.id = ?";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int eID = rs.getInt("eID");
                String noVote = rs.getString("noVote"); // Fetches the no from vote
                String claim = rs.getString("claim");
                Date edate = rs.getDate("edate");
                String noPO = rs.getString("noPO");
                BigDecimal total_Exp = rs.getBigDecimal("total_Exp");
                byte[] receipt = rs.getBytes("receipt");
                String receiptFilename = rs.getString("receiptFilename");
                Expense expense = new Expense(eID, id, noVote, claim, edate, noPO, total_Exp, receipt, receiptFilename);
                ExpList.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ExpList;
    }

    public Expense selectExpByID(int exID) {
        Expense expense = null;
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(SELECT_EXP_BY_ID)) {
            pst.setInt(1, exID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int eID = rs.getInt("eID");
                int id = rs.getInt("id");
                int vID = rs.getInt("vID");
                String claim = rs.getString("claim");
                Date edate = rs.getDate("edate");
                String noPO = rs.getString("noPO");
                BigDecimal total_Exp = rs.getBigDecimal("total_Exp");
                expense = new Expense(eID, id, vID, claim, edate, noPO, total_Exp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expense;
    }

    public BigDecimal getTotalExpenseByVoteID(int id, int vID) throws SQLException {
        BigDecimal totalExpense = BigDecimal.ZERO;
        String sql = "SELECT SUM(total_Exp) FROM expense WHERE id=? && vID = ?";

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, vID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalExpense = rs.getBigDecimal(1);
                }
            }
        }
        return totalExpense;
    }

    public boolean updateExp(Expense expense) throws SQLException {
        boolean recordUpdate;
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(UPDATE_EXP)) {

            pst.setInt(1, expense.getId());
            pst.setInt(2, expense.getvID());
            pst.setString(3, expense.getClaim());
            pst.setDate(4, expense.getEdate());
            pst.setBigDecimal(5, expense.getTotal_Exp());
            pst.setInt(6, expense.geteID());
            System.out.println("eID : " + expense.geteID());

            recordUpdate = pst.executeUpdate() > 0;
        }
        return recordUpdate;
    }

    public boolean updateExpNo(Expense expense) throws SQLException {
        boolean recordUpdate;
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(UPDATE_EXP_NOPO)) {

            pst.setInt(1, expense.getId());
            pst.setInt(2, expense.getvID());
            pst.setString(3, expense.getClaim());
            pst.setDate(4, expense.getEdate());
            pst.setString(5, expense.getNoPO());
            pst.setBigDecimal(6, expense.getTotal_Exp());
            pst.setInt(7, expense.geteID());
            System.out.println("eID : " + expense.geteID());

            recordUpdate = pst.executeUpdate() > 0;
        }
        return recordUpdate;
    }

    public boolean deleteExp(int expID) throws SQLException {
        boolean recordDeleted = false;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EXP)) {
            preparedStatement.setInt(1, expID);
            recordDeleted = preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("deleteExp => Value for recordDeleted = " + recordDeleted);
        return recordDeleted;
    }

    public BigDecimal calculateTotalExpForProject(int projectId) throws SQLException {
        BigDecimal totalExp = BigDecimal.ZERO;
        String query = "SELECT SUM(total_Exp) AS total FROM expense WHERE id = ?";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, projectId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                totalExp = rs.getBigDecimal("total");
                if (totalExp == null) {
                    totalExp = BigDecimal.ZERO;
                }
            }
        }
        return totalExp;
    }

    public List<Expense> selectAllExpenses(int id) {
        List<Expense> expList = new ArrayList<>();
        String query = "SELECT e.eID, e.id, v.noVote, e.claim, e.edate, e.noPO, e.total_Exp "
                + "FROM expense e "
                + "INNER JOIN vote v ON e.vID = v.vID "
                + "WHERE e.id = ? "
                + "ORDER BY v.noVote, e.edate";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                // Extract data
                int eID = rs.getInt("eID");
                String noVote = rs.getString("noVote");
                String claim = rs.getString("claim");
                Date edate = rs.getDate("edate");
                String noPO = rs.getString("noPO");
                BigDecimal totalExp = rs.getBigDecimal("total_Exp");

                // Create an Expense object
                Expense expense = new Expense(eID, id, noVote, claim, edate, noPO, totalExp);
                expList.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expList;
    }

    public BigDecimal getTotalExpense(int id) {
        BigDecimal totalExp = BigDecimal.ZERO;
        String query = "SELECT SUM(e.total_Exp) AS total_Exp "
                + "FROM expense e "
                + "WHERE e.id = ?";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                totalExp = rs.getBigDecimal("total_Exp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalExp;
    }

}
