/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import com.model.*;
import java.sql.Statement;

/**
 *
 * @author NAJIHAH
 */
public class ProjectDAO {

    private Connection con;
    private String jdbcURL = "jdbc:mysql://localhost:3306/tabung_inos";
    private String username = "root";
    private String password = "root";

    private static final String SELECT_ALL_PROS
            = "SELECT p.id, u.username, p.pname, t.tname, p.startP, p.endP, p.total_All, p.total_Exp, p.baki "
            + "FROM project p "
            + "INNER JOIN member m ON p.id = m.id "
            + "INNER JOIN user u ON p.uID = u.uID "
            + "INNER JOIN tabung t ON p.tID = t.tID "
            + "WHERE m.uID = ?";

    private static final String SELECT_ALL_PRO
            = "SELECT * FROM project WHERE uID = ? && id = ?;";
    private static final String SELECT_ALL_LISTPROS = "SELECT * FROM project";
    private static final String SELECT_PRO_BY_ID = "SELECT p.id, p.uID, u.username, p.pname, t.tname, p.total_All, p.total_Exp, p.baki, p.startP, p.endP "
            + "FROM project p "
            + "INNER JOIN user u ON p.uID = u.uID " // Join with the user table to get the username
            + "INNER JOIN tabung t ON p.tID = t.tID "
            + "WHERE p.id = ?";
    private static final String SELECT_PROID
            = "SELECT * FROM project WHERE id = ?;";
    private static final String UPDATE_PRO = "UPDATE project SET uID = ?, pname = ?, tID = ?, total_All = ?, total_Exp = ?, baki = ?, startP = ?, endP = ? \n"
            + "WHERE id = ?";
    private static final String DELETE_PRO = "DELETE from project WHERE id = ?;";
    private static final String SELECT_PRO_BY_TID
            = "SELECT p.id, p.pname, p.tID, p.startP, p.endP, p.total_All, p.total_Exp, p.baki "
            + "FROM project p "
            + "INNER JOIN member m ON p.id = m.id "
            + "WHERE m.uID = ? AND p.tID = ?";

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

    private Integer getTIDByName(String tName) throws SQLException {
        Integer tID = null;
        String query = "SELECT tID FROM tabung WHERE tName = ?";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, tName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                tID = rs.getInt("tID");
            }
        }
        return tID;
    }

    public int insertProject(Project project) throws SQLException {

        int generatedId = 0; // Default value for the generated ID

        String sql = "INSERT INTO project (uID, pname, tID, total_All, total_Exp, baki, startP, endP) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Request generated keys

            pst.setInt(1, project.getuID());
            pst.setString(2, project.getPname());
            pst.setInt(3, project.gettID());
            pst.setBigDecimal(4, project.getTotal_All());
            pst.setBigDecimal(5, project.getTotal_Exp());

            // Calculate and set baki
            BigDecimal baki = project.getTotal_All().subtract(project.getTotal_Exp());
            pst.setBigDecimal(6, baki);

            pst.setDate(7, new java.sql.Date(project.getStartP().getTime()));
            pst.setDate(8, new java.sql.Date(project.getEndP().getTime()));

            System.out.println("In uID: " + project.getuID());
            System.out.println("In name: " + project.getPname());
            System.out.println("In tID: " + project.gettID());
            System.out.println("In all: " + project.getTotal_All());
            System.out.println("In exp: " + project.getTotal_Exp());
            System.out.println("In baki: " + baki);
            System.out.println("in start: " + project.getStartP());
            System.out.println("In end: " + project.getEndP());
            
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Insertion failed, no rows affected.");
            }

            // Get generated ID
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1); // Retrieve the generated ID
                } else {
                    throw new SQLException("Insertion failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return generatedId; // Return the generated project ID
    }
    
    public int insertProH(Project project) throws SQLException {

        int generatedId = 0; // Default value for the generated ID

        String sql = "INSERT INTO project (uID, pname, cID, total_All, total_Exp, baki, startP, endP) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Request generated keys

            pst.setInt(1, project.getuID());
            pst.setString(2, project.getPname());
            pst.setInt(3, project.getcID());
            pst.setBigDecimal(4, project.getTotal_All());
            pst.setBigDecimal(5, project.getTotal_Exp());

            // Calculate and set baki
            BigDecimal baki = project.getTotal_All().subtract(project.getTotal_Exp());
            pst.setBigDecimal(6, baki);

            pst.setDate(7, new java.sql.Date(project.getStartP().getTime()));
            pst.setDate(8, new java.sql.Date(project.getEndP().getTime()));

            System.out.println("In uID: " + project.getuID());
            System.out.println("In name: " + project.getPname());
            System.out.println("In cID: " + project.getcID());
            System.out.println("In all: " + project.getTotal_All());
            System.out.println("In exp: " + project.getTotal_Exp());
            System.out.println("In baki: " + baki);
            System.out.println("in start: " + project.getStartP());
            System.out.println("In end: " + project.getEndP());
            
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Insertion failed, no rows affected.");
            }

            // Get generated ID
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1); // Retrieve the generated ID
                } else {
                    throw new SQLException("Insertion failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return generatedId; // Return the generated project ID
    }

    public List<Project> selectAllProjectsByUserId(int uID) throws SQLException {
        List<Project> projects = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PROS)) {

            preparedStatement.setInt(1, uID); // Set the uID parameter

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username"); // Fetch username
                    String pname = rs.getString("pname");
                    String tname = rs.getString("tname");
                    BigDecimal total_All = rs.getBigDecimal("total_All");
                    BigDecimal total_Exp = rs.getBigDecimal("total_Exp");
                    BigDecimal baki = rs.getBigDecimal("baki");
                    Date startP = rs.getDate("startP");
                    Date endP = rs.getDate("endP");

                    // Add the project details along with the username
                    projects.add(new Project(id, username, pname, tname, total_All, total_Exp, baki, startP, endP));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching projects by user ID", e);
        }

        return projects;
    }
    
    public List<Project> selectAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String SELECT_ALL = "SELECT p.id, u.username, p.pname, t.tname, p.startP, p.endP, p.total_All, p.total_Exp, p.baki "
            + "FROM project p "
            + "INNER JOIN user u ON p.uID = u.uID "
            + "INNER JOIN tabung t ON p.tID = t.tID ";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username"); // Fetch username
                    String pname = rs.getString("pname");
                    String tname = rs.getString("tname");
                    BigDecimal total_All = rs.getBigDecimal("total_All");
                    BigDecimal total_Exp = rs.getBigDecimal("total_Exp");
                    BigDecimal baki = rs.getBigDecimal("baki");
                    Date startP = rs.getDate("startP");
                    Date endP = rs.getDate("endP");

                    // Add the project details along with the username
                    projects.add(new Project(id, username, pname, tname, total_All, total_Exp, baki, startP, endP));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching projects by user ID", e);
        }

        return projects;
    }

    public List<Project> selectProjectsByUserMembership(int uID) throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT p.id, u.username, p.pname, t.tname, p.startP, p.endP, p.total_All, p.total_Exp, p.baki "
                + "FROM project p "
                + "INNER JOIN member m ON p.id = m.id "
                + "INNER JOIN user u ON p.uID = u.uID "
                + "INNER JOIN tabung t ON p.tID = t.tID "
                + "WHERE m.uID = ?";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, uID);  // Set the user ID parameter
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username"); // Fetch username
                    String pname = rs.getString("pname");
                    String tname = rs.getString("tname");
                    BigDecimal total_All = rs.getBigDecimal("total_All");
                    BigDecimal total_Exp = rs.getBigDecimal("total_Exp");
                    BigDecimal baki = rs.getBigDecimal("baki");
                    Date startP = rs.getDate("startP");
                    Date endP = rs.getDate("endP");

                    // Add the project details along with the username
                    projects.add(new Project(id, username, pname, tname, total_All, total_Exp, baki, startP, endP));
                }
            }
            return projects;
        }
    }

    public Project selectProByID(int id) {
        Project project = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRO_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int uID = resultSet.getInt("uID");
                String username = resultSet.getString("username"); // Fetches the name from leadproj
                String pname = resultSet.getString("pname");
                String tname = resultSet.getString("tname"); // Fetches the name from tabung
                BigDecimal total_All = resultSet.getBigDecimal("total_All");
                BigDecimal total_Exp = resultSet.getBigDecimal("total_Exp");
                BigDecimal baki = resultSet.getBigDecimal("baki");
                Date startP = resultSet.getDate("startP");
                Date endP = resultSet.getDate("endP");

                project = new Project(id, uID, username, pname, tname, total_All, total_Exp, baki, startP, endP);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    public Project selectProID(int proid) {
        Project project = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROID)) {
            preparedStatement.setInt(1, proid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int uID = resultSet.getInt("uID");
                String pname = resultSet.getString("pname");
                int tID = resultSet.getInt("tID");
                BigDecimal total_All = resultSet.getBigDecimal("total_All");
                BigDecimal total_Exp = resultSet.getBigDecimal("total_Exp");
                BigDecimal baki = resultSet.getBigDecimal("baki");
                Date startP = resultSet.getDate("startP");
                Date endP = resultSet.getDate("endP");

                project = new Project(id, uID, pname, tID, total_All, total_Exp, baki, startP, endP);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    public List<Project> selectAllPro(int id) {
        List<Project> projectList = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRO_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int uID = resultSet.getInt("uID");
                String username = resultSet.getString("username"); // Fetches the name from leadproj
                String pname = resultSet.getString("pname");
                String tname = resultSet.getString("tname"); // Fetches the name from tabung
                BigDecimal total_All = resultSet.getBigDecimal("total_All");
                BigDecimal total_Exp = resultSet.getBigDecimal("total_Exp");
                BigDecimal baki = resultSet.getBigDecimal("baki");
                Date startP = resultSet.getDate("startP");
                Date endP = resultSet.getDate("endP");

                Project project = new Project(id, uID, username, pname, tname, total_All, total_Exp, baki, startP, endP);
                projectList.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectList;
    }

    public List<Project> selectAllListPro() {
        List<Project> listofProject = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(SELECT_ALL_LISTPROS); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int uID = rs.getInt("uID");
                String pname = rs.getString("pname");
                int tID = rs.getInt("tID");   // Fetch name from tabung
                BigDecimal total_All = rs.getBigDecimal("total_All");
                BigDecimal total_Exp = rs.getBigDecimal("total_Exp");
                BigDecimal baki = rs.getBigDecimal("baki");
                Date startP = rs.getDate("startP");
                Date endP = rs.getDate("endP");
                Project project = new Project(id, uID, pname, tID, total_All, total_Exp, baki, startP, endP);
                listofProject.add(project);
                System.out.println("Selecting all projects for uID: " + uID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listofProject;
    }

    public Project getProjectByName(int usrID, String name) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM project WHERE uID = ? AND pname = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, usrID);
            preparedStatement.setString(2, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Project(
                        resultSet.getInt("id"),
                        usrID,
                        resultSet.getString("username"),
                        resultSet.getString("pname"),
                        resultSet.getString("tname"),
                        resultSet.getBigDecimal("total_All"),
                        resultSet.getBigDecimal("total_Exp"),
                        resultSet.getBigDecimal("baki"),
                        resultSet.getDate("startP"),
                        resultSet.getDate("endP")
                );
            }
        }
        return null; // No project found
    }

    public boolean updatePro(Project project) throws SQLException {
        boolean recordUpdate;
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(UPDATE_PRO)) {

            pst.setInt(1, project.getuID());
            pst.setString(2, project.getPname());
            pst.setInt(3, project.gettID());
            pst.setBigDecimal(4, project.getTotal_All());
            pst.setBigDecimal(5, project.getTotal_Exp());
            BigDecimal baki = project.getTotal_All().subtract(project.getTotal_Exp());
            pst.setBigDecimal(6, baki);
            pst.setDate(7, new java.sql.Date(project.getStartP().getTime()));
            pst.setDate(8, new java.sql.Date(project.getEndP().getTime()));
            pst.setInt(9, project.getId());

            System.out.println("In : " + project.getId());

            recordUpdate = pst.executeUpdate() > 0;
        }
        return recordUpdate;
    }
    
    public boolean updateDate(Project project) throws SQLException {
        boolean recordUpdate;
        String UPDATE = "UPDATE project SET endP = ? \n"
            + "WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(UPDATE)) {

            pst.setDate(1, new java.sql.Date(project.getEndP().getTime()));
            pst.setInt(2, project.getId());

            System.out.println("In : " + project.getEndP());
            System.out.println("In : " + project.getId());

            recordUpdate = pst.executeUpdate() > 0;
        }
        return recordUpdate;
    }
    
   public boolean updateExtDate(int id, Date newEndP) throws SQLException {
    boolean recordUpdate;
    String UPDATE = "UPDATE project SET endP = ? WHERE id = ?";

    try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(UPDATE)) {
        // Set the parameters for the prepared statement
        pst.setDate(1, new java.sql.Date(newEndP.getTime())); // Set the new endP date
        pst.setInt(2, id);  // Set the project id

        System.out.println("Updating project with ID: " + id + " to new endP: " + newEndP);

        // Execute the update
        recordUpdate = pst.executeUpdate() > 0;  // True if at least one record was updated
    }
    return recordUpdate;
}


    public boolean deletePro(int id) throws SQLException {
        boolean recordDeleted = false;

        try (Connection connection = getConnection()) {
            // First, delete related records in the income table
            String deleteIncomeSQL = "DELETE FROM incomes WHERE id = ?";
            try (PreparedStatement incomeStmt = connection.prepareStatement(deleteIncomeSQL)) {
                incomeStmt.setInt(1, id);
                incomeStmt.executeUpdate();
            }
            // delete related records in the expense table
            String deleteExpSQL = "DELETE FROM expense WHERE id = ?";
            try (PreparedStatement incomeStmt = connection.prepareStatement(deleteExpSQL)) {
                incomeStmt.setInt(1, id);
                incomeStmt.executeUpdate();
            }

            String deleteMMbSQL = "DELETE FROM member WHERE id = ?";
            try (PreparedStatement incomeStmt = connection.prepareStatement(deleteMMbSQL)) {
                incomeStmt.setInt(1, id);
                incomeStmt.executeUpdate();
            }
            
             String deleteExtSQL = "DELETE FROM extend WHERE id = ?";
            try (PreparedStatement incomeStmt = connection.prepareStatement(deleteExtSQL)) {
                incomeStmt.setInt(1, id);
                incomeStmt.executeUpdate();
            }

            // Then, delete the project record
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRO)) {
                preparedStatement.setInt(1, id);
                int rowsAffected = preparedStatement.executeUpdate();
                recordDeleted = rowsAffected > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        System.out.println("deleteProj => Value for recordDeleted = " + recordDeleted);
        return recordDeleted;
    }

    public BigDecimal calculateTotalAllByTabung(int tID) throws SQLException {
        String sql = "SELECT SUM(total_All) AS totalAmount FROM project WHERE tID = ?";
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

    public BigDecimal calculateTotalExpByTabung(int tID) throws SQLException {
        String sql = "SELECT SUM(total_Exp) AS totalBalance FROM project WHERE tID = ?";
        BigDecimal totalBalance = BigDecimal.ZERO;

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, tID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalBalance = rs.getBigDecimal("totalBalance");
            }
        }

        return totalBalance != null ? totalBalance : BigDecimal.ZERO;
    }

    public List<Project> selectAllProjects(int uID) {
        List<Project> projectList = new ArrayList<>();
        String query = "SELECT id, pname, startP, endP FROM project WHERE uID = ?";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, uID);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String pname = rs.getString("pname");
                Date startP = rs.getDate("startP");
                Date endP = rs.getDate("endP");

                // Debugging output
                System.out.println("Fetched Project ID: " + id);
                System.out.println("Fetched Project Name: " + pname);
                System.out.println("Fetched Start Date: " + startP);
                System.out.println("Fetched End Date: " + endP);

                Project project = new Project(id, uID, pname, startP, endP);
                projectList.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectList;
    }

    public List<Project> selectTotal(int uID) {
        List<Project> projects = new ArrayList<>();
        String COUNT = "SELECT \n"
                + "    (SELECT COUNT(tID) FROM tabung) AS numTbg, \n"
                + "    (SELECT COUNT(p.id) \n"
                + "     FROM project p \n"
                + "     INNER JOIN member m ON p.id = m.id \n"
                + "     WHERE m.uID = ?) AS numPro;";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(COUNT)) {

            // Setting the uID in the query
            preparedStatement.setInt(1, uID);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int numTbg = rs.getInt("numTbg");
                    int numPro = rs.getInt("numPro");

                    // Log the fetched counts
                    System.out.println("Fetched Tbg: " + numTbg);
                    System.out.println("Fetched Project: " + numPro);

                    // Assuming Project class can store these counts
                    projects.add(new Project(numTbg, numPro));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    public List<Project> selectTotals() {
        List<Project> projects = new ArrayList<>();
        String COUNT = "SELECT \n"
                + " (SELECT COUNT(tID) FROM tabung ) AS numTbg, \n"
                + " (SELECT COUNT(id) FROM project ) AS numPro;";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(COUNT)) {

            // Setting the uID in the query
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int numTbg = rs.getInt("numTbg");
                    int numPro = rs.getInt("numPro");

                    // Log the fetched counts
                    System.out.println("Fetched Tbg: " + numTbg);
                    System.out.println("Fetched Project: " + numPro);

                    // Assuming Project class can store these counts
                    projects.add(new Project(numTbg, numPro));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    public List<Project> selectAllProtID(int uID, int tID) {
        List<Project> projectList = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRO_BY_TID)) {
            preparedStatement.setInt(1, uID);
            preparedStatement.setInt(2, tID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String pname = resultSet.getString("pname");
                BigDecimal total_All = resultSet.getBigDecimal("total_All");
                BigDecimal total_Exp = resultSet.getBigDecimal("total_Exp");
                BigDecimal baki = resultSet.getBigDecimal("baki");
                Date startP = resultSet.getDate("startP");
                Date endP = resultSet.getDate("endP");

                Project project = new Project(id, uID, pname, tID, total_All, total_Exp, baki, startP, endP);
                projectList.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectList;
    }

    public List<Project> selectAllProjtID(int tID) {
        List<Project> projectList = new ArrayList<>();
        String sqltID = "SELECT id, uID, pname, tID, total_All, total_Exp, baki, startP, endP FROM project WHERE tID = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sqltID)) {
            preparedStatement.setInt(1, tID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int uID = resultSet.getInt("uID");
                String pname = resultSet.getString("pname");
                BigDecimal total_All = resultSet.getBigDecimal("total_All");
                BigDecimal total_Exp = resultSet.getBigDecimal("total_Exp");
                BigDecimal baki = resultSet.getBigDecimal("baki");
                Date startP = resultSet.getDate("startP");
                Date endP = resultSet.getDate("endP");

                Project project = new Project(id, uID, pname, tID, total_All, total_Exp, baki, startP, endP);
                projectList.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectList;
    }

    public List<Project> selectAllProCtID(int uID, int tID) {
        List<Project> projectList = new ArrayList<>();
        String PROCHART = "SELECT p.id, p.pname, p.tID, p.startP, p.endP, p.total_All, p.total_Exp, p.baki "
                + "FROM project p "
                + "INNER JOIN member m ON p.id = m.id "
                + "WHERE m.uID = ? && p.tID = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(PROCHART)) {
            preparedStatement.setInt(1, uID);
            preparedStatement.setInt(2, tID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String pname = resultSet.getString("pname");
                BigDecimal total_All = resultSet.getBigDecimal("total_All");
                BigDecimal total_Exp = resultSet.getBigDecimal("total_Exp");
                BigDecimal baki = resultSet.getBigDecimal("baki");

                Project project = new Project(id, uID, pname, tID, total_All, total_Exp, baki);
                projectList.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectList;
    }

    public List<Project> selectAllProC(int tID) {
        List<Project> projectList = new ArrayList<>();
        String Chart = "SELECT id, uID, pname, tID, total_All, total_Exp, baki FROM project WHERE tID = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(Chart)) {
            preparedStatement.setInt(1, tID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int uID = resultSet.getInt("uID");
                String pname = resultSet.getString("pname");
                BigDecimal total_All = resultSet.getBigDecimal("total_All");
                BigDecimal total_Exp = resultSet.getBigDecimal("total_Exp");
                BigDecimal baki = resultSet.getBigDecimal("baki");

                Project project = new Project(id, uID, pname, tID, total_All, total_Exp, baki);
                projectList.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectList;
    }

}
