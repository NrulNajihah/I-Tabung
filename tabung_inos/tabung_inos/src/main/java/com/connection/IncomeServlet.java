/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.connection;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;
import jakarta.servlet.RequestDispatcher;
import com.dao.*;
import com.model.*;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.db.DbCon;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;
import com.model.Project;
import java.util.List;

/**
 *
 * @author NAJIHAH
 */
public class IncomeServlet extends HttpServlet {

    private IncomeDAO incDAO;
    private ProjectDAO projectDAO;

    public void init() {
        incDAO = new IncomeDAO();
        projectDAO = new ProjectDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action5");

        System.out.println("IncomeServlet ==> Action5 = " + action);

        try {
            switch (action) {
                case "newInc":
                    displayNewIncForm(request, response);
                    break;
                case "editInc":
                    displayEditIncForm(request, response);
                    break;
                case "insertInc":
                    createInc(request, response);
                    break;
                case "updateInc":
                    updateInc(request, response);
                    break;
                case "deleteInc":
                    deleteInc(request, response);
                    break;
                case "listInc":
                    retrieveInc(request, response);
                    break;
                case "listIncFull":
                    retrieveIncFull(request, response);
                    break;
                default:
                    retrieveInc(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ParseException ex) {
            Logger.getLogger(IncomeServlet.class.getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IncomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayNewIncForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            System.err.println("User not found in session.");
            response.sendRedirect("signIn.jsp");
            return;
        }

        Integer usrID = user.getuID();
        System.out.println("uID: " + usrID);

        String IDStr = request.getParameter("id");
        System.out.println("Request parameter 'id': " + IDStr);

        if (IDStr != null && !IDStr.isEmpty()) {
            Integer id = null;
            try {
                id = Integer.parseInt(IDStr);
                System.out.println("Parsed ID: " + id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
                return;
            }

            ProjectDAO projectDAO = new ProjectDAO();
            List<Project> projects = projectDAO.selectAllPro(id);
            System.out.println("Projects found: " + (projects != null ? projects.size() : 0));

            // Retrieve all incomes related to the user and project
        List<Income> IncList = incDAO.selectAllIncome(id);
        request.setAttribute("listInc", IncList);
        System.out.println("List of income retrieved: " + IncList.size());
        
            if (projects != null && !projects.isEmpty()) {
                Project currentPro = projects.get(0);
                request.setAttribute("project", currentPro);
            } else {
                request.setAttribute("project", null);
            }

            try {
                List<Project> projectList = getAllProjects(usrID);
                request.setAttribute("listProject", projectList);
                System.out.println("List of projects retrieved: " + projectList.size());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to load projects.");
            }
            try {
                List<Vote> voteList = getAllVotes();
                request.setAttribute("listofVote", voteList);
                System.out.println("List of votes retrieved: " + voteList.size());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to load votes.");
            }

            ServletContext sc = getServletContext();
            RequestDispatcher dispatcher = sc.getRequestDispatcher("/Income.jsp");
            dispatcher.forward(request, response);
        } else {
            System.err.println("ID parameter is missing.");
            response.sendRedirect("error.jsp");
        }
    }

    private List<Project> getAllProjects(int uID) throws SQLException, ClassNotFoundException {
        ProjectDAO projectDAO = new ProjectDAO();
        return projectDAO.selectAllProjectsByUserId(uID);
    }
    
    private List<Vote> getAllVotes() throws SQLException, ClassNotFoundException {
        VoteDAO voteDAO = new VoteDAO();
        return voteDAO.selectAllVotes();
    }

    private void displayEditIncForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            System.err.println("User not found in session.");
            response.sendRedirect("signIn.jsp");
            return;
        }

        Integer usrID = user.getuID();
        System.out.println("uID: " + usrID);

        String IDStr = request.getParameter("id");
        System.out.println("Request parameter 'id': " + IDStr);

        if (IDStr != null && !IDStr.isEmpty()) {
            Integer id = null;
            try {
                id = Integer.parseInt(IDStr);
                System.out.println("Parsed ID: " + id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
                return;
            }

            ProjectDAO projectDAO = new ProjectDAO();
            List<Project> projects = projectDAO.selectAllPro(id);
            System.out.println("Projects found: " + (projects != null ? projects.size() : 0));

            if (projects != null && !projects.isEmpty()) {
                Project currentPro = projects.get(0);
                request.setAttribute("project", currentPro);
            } else {
                request.setAttribute("project", null);
            }

            try {
                List<Project> projectList = getAllProjects(usrID);
                request.setAttribute("listProject", projectList);
                System.out.println("List of projects retrieved: " + projectList.size());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to load projects.");
            }
            
             try {
                List<Vote> voteList = getAllVotes();
                request.setAttribute("listofVote", voteList);
                System.out.println("Votes retrieved: " + (voteList != null ? voteList.size() : 0));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to load votes.");
            }

             try {
            int iID = Integer.parseInt(request.getParameter("iID"));
            System.out.println("iID: " + iID);
            // Retrieve the data to be edited
            Income currentincome = incDAO.selectIncByID(iID);
            if (currentincome !=null) {
            request.setAttribute("income", currentincome);
            } else {
                request.setAttribute("error", "Income not found");
            }
             }catch (NumberFormatException e) {
                  e.printStackTrace();
                request.setAttribute("error", "Invalid income ID format.");
                response.sendRedirect("error.jsp");
                return;
            }
            // Forward the request to the JSP
            ServletContext sc = getServletContext();
            RequestDispatcher dispatcher = sc.getRequestDispatcher("/editInc.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void createInc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer uID = (Integer) session.getAttribute("uID");

            if (uID == null) {
                throw new ServletException("No user ID found in session.");
            }

            // Log uID retrieved from session
            System.out.println("uID from session: " + uID);

            userDAO usrDAO = new userDAO(DbCon.getConnection());
            User user = usrDAO.selectUserById(uID);

            if (user == null) {
                throw new ServletException("User not found for the given uID: " + uID);
            }

            String IDStr = request.getParameter("id");
            System.out.println("Request parameter 'id': " + IDStr);

            if (IDStr == null || IDStr.isEmpty()) {
                throw new ServletException("ID parameter is missing or empty");
            }

            Integer id = null;
            try {
                id = Integer.parseInt(IDStr);
                System.out.println("Parsed ID: " + id);
            } catch (NumberFormatException e) {
                throw new ServletException("Invalid ID format: " + IDStr, e);
            }
            
             // Handle the vID and convert it to noVote
            String vIDStr = request.getParameter("vID");
            if (vIDStr == null || vIDStr.isEmpty()) {
                throw new ServletException("vID parameter is missing or empty");
            }

            Integer vID = Integer.parseInt(vIDStr);
            VoteDAO voteDAO = new VoteDAO();
            String noVote = voteDAO.selectnoVoteById(vID);

            if (noVote == null) {
                throw new ServletException("No corresponding noVote found for the given vID");
            }

            // Proceed with the remaining parameters
            String totalAllStr = request.getParameter("total_All");
            if (totalAllStr == null || totalAllStr.isEmpty()) {
                throw new ServletException("Total_All parameter is missing or empty");
            }

            BigDecimal total_All;
            try {
                total_All = new BigDecimal(totalAllStr);
            } catch (NumberFormatException e) {
                throw new ServletException("Invalid number format for Total_All: " + totalAllStr, e);
            }

            // Use the current date for idate if not provided
            String idateStr = request.getParameter("idate");
            java.sql.Date idate;
            if (idateStr == null || idateStr.isEmpty()) {
                idate = new java.sql.Date(System.currentTimeMillis()); // Current date
            } else {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = formatter.parse(idateStr);
                    idate = new java.sql.Date(parsedDate.getTime());
                } catch (ParseException e) {
                    throw new ServletException("Invalid date format for idate: " + idateStr, e);
                }
            }

            // Log all parsed values
            System.out.println("Parsed total_All: " + total_All);
            System.out.println("Parsed idate: " + idate);

            // Create and insert the income
            Income income = new Income(uID, id,vID, total_All, idate);
            IncomeDAO incDAO = new IncomeDAO();
            incDAO.insertIncome(income);

            // Log after income insert
            System.out.println("Income inserted for uID: " + uID + ", ID: " + id);

            // Update the total_All in the project table
            updateProjectTotalAll(id, total_All);

            System.out.println("Income created for ID: " + id);

            request.getSession().setAttribute("insertInc","AddInc");
            response.sendRedirect(request.getContextPath() + "/IncomeServlet?action5=listInc&id=" + id);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IncomeServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Class not found error occurred", ex);
        } catch (SQLException ex) {
            Logger.getLogger(IncomeServlet.class.getName()).log(Level.SEVERE, "SQL error occurred", ex);
            throw new ServletException("SQL error occurred", ex);
        }
    }


    private void updateProjectTotalAll(Integer id, BigDecimal newTotalAll) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try {
            connection = DbCon.getConnection();

            // Fetch the current total_All, total_Exp, and tID from the project table
            String selectSql = "SELECT total_All, total_Exp, tID FROM project WHERE id = ?";
            selectStmt = connection.prepareStatement(selectSql);
            selectStmt.setInt(1, id);
            rs = selectStmt.executeQuery();

            BigDecimal currentTotalAll = BigDecimal.ZERO;
            BigDecimal totalExp = BigDecimal.ZERO;
            Integer tID = null;

            if (rs.next()) {
                currentTotalAll = rs.getBigDecimal("total_All") !=null ? rs.getBigDecimal("total_All") : BigDecimal.ZERO;
                totalExp = rs.getBigDecimal("total_Exp");
                tID = rs.getInt("tID");
            }

            // Calculate the new total_All by adding the newTotalAll
            BigDecimal updatedTotalAll = currentTotalAll.add(newTotalAll);

            // Calculate the new baki value
            BigDecimal newBaki = updatedTotalAll.subtract(totalExp);

            // Update the total_All and baki values in the project table
            String updateSql = "UPDATE project SET total_All = ?, baki = ? WHERE id = ?";
            updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setBigDecimal(1, updatedTotalAll);
            updateStmt.setBigDecimal(2, newBaki);
            updateStmt.setInt(3, id);

            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Project updated successfully.");

                // Ensure that the tID is not null
                if (tID != null) {
                    // Update the Tabung balance based on the total expenses
                    ProjectDAO projectDAO = new ProjectDAO(); // Ensure this is initialized properly
                    TabungDAO tabungDAO = new TabungDAO();   // Ensure this is initialized properly

                    // Calculate new total balance for tabung
                    BigDecimal newTotalAmount = projectDAO.calculateTotalAllByTabung(tID);

                    // Update the balance in tabung
                    tabungDAO.updateAmount(tID, newTotalAmount);
                    System.out.println("Tabung balance updated successfully.");
                } else {
                    System.out.println("Error: tID is null, unable to update Tabung balance.");
                }
            } else {
                System.out.println("Failed to update project.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating project or tabung balance", e);
        }
    }

    private void updateTotalAll(Integer id, BigDecimal total_All) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try {
            connection = DbCon.getConnection();

            // Fetch the current total_All, total_Exp, and tID from the project table
            String selectSql = "SELECT tID, total_All, total_Exp FROM project WHERE id = ?";
            selectStmt = connection.prepareStatement(selectSql);
            selectStmt.setInt(1, id);
            rs = selectStmt.executeQuery();

            BigDecimal currentTotalAll = BigDecimal.ZERO;
            BigDecimal totalExp = BigDecimal.ZERO;
            Integer tID = null;

            if (rs.next()) {
                currentTotalAll = rs.getBigDecimal("total_All") !=null ? rs.getBigDecimal("total_All") : BigDecimal.ZERO;
                totalExp = rs.getBigDecimal("total_Exp");
                tID = rs.getInt("tID");
            }

            // Calculate the new total_All by adding the newTotalAll
            BigDecimal updatedTotalAll = total_All;

            // Calculate the new baki value
            BigDecimal newBaki = updatedTotalAll.subtract(totalExp);

            // Update the total_All and baki values in the project table
            String updateSql = "UPDATE project SET total_All = ?, baki = ? WHERE id = ?";
            updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setBigDecimal(1, updatedTotalAll);
            updateStmt.setBigDecimal(2, newBaki);
            updateStmt.setInt(3, id);

            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Project updated successfully.");

                // Ensure that the tID is not null
                if (tID != null) {
                    // Update the Tabung balance based on the total expenses
                    ProjectDAO projectDAO = new ProjectDAO(); // Ensure this is initialized properly
                    TabungDAO tabungDAO = new TabungDAO();   // Ensure this is initialized properly

                    // Calculate new total balance for tabung
                    BigDecimal newTotalAmount = projectDAO.calculateTotalAllByTabung(tID);

                    // Update the balance in tabung
                    tabungDAO.updateAmount(tID, newTotalAmount);
                    System.out.println("Tabung balance updated successfully.");
                } else {
                    System.out.println("Error: tID is null, unable to update Tabung balance.");
                }
            } else {
                System.out.println("Failed to update project.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating project or tabung balance", e);
        }
    }
    
    private void updateDeletedTotalAll(Integer id, BigDecimal total_All) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try {
            connection = DbCon.getConnection();

            // Fetch the current total_All, total_Exp, and tID from the project table
            String selectSql = "SELECT tID, total_All, total_Exp FROM project WHERE id = ?";
            selectStmt = connection.prepareStatement(selectSql);
            selectStmt.setInt(1, id);
            rs = selectStmt.executeQuery();

            BigDecimal currentTotalAll = BigDecimal.ZERO;
            BigDecimal totalExp = BigDecimal.ZERO;
            Integer tID = null;

            if (rs.next()) {
                currentTotalAll = rs.getBigDecimal("total_All");
                totalExp = rs.getBigDecimal("total_Exp");
                tID = rs.getInt("tID");
            }

            // Calculate the new total_All by adding the newTotalAll
            BigDecimal updatedTotalAll = currentTotalAll;

            // Calculate the new baki value
            BigDecimal newBaki = updatedTotalAll.subtract(totalExp);

            // Update the total_All and baki values in the project table
            String updateSql = "UPDATE project SET total_All = ?, baki = ? WHERE id = ?";
            updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setBigDecimal(1, updatedTotalAll);
            updateStmt.setBigDecimal(2, newBaki);
            updateStmt.setInt(3, id);

            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Project updated successfully.");

                // Ensure that the tID is not null
                if (tID != null) {
                    // Update the Tabung balance based on the total expenses
                    ProjectDAO projectDAO = new ProjectDAO(); // Ensure this is initialized properly
                    TabungDAO tabungDAO = new TabungDAO();   // Ensure this is initialized properly

                    // Calculate new total balance for tabung
                    BigDecimal newTotalAmount = projectDAO.calculateTotalAllByTabung(tID);

                    // Update the balance in tabung
                    tabungDAO.updateAmount(tID, newTotalAmount);
                    System.out.println("Tabung balance updated successfully.");
                } else {
                    System.out.println("Error: tID is null, unable to update Tabung balance.");
                }
            } else {
                System.out.println("Failed to update project.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating project or tabung balance", e);
        }
    }
    

    private void retrieveInc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");
        System.out.println("uID: " + uID);

        // Retrieve and parse the 'id' parameter
        String idParam = request.getParameter("id");
        Integer id = null;
        if (idParam != null && !idParam.isEmpty()) {
            try {
                id = Integer.parseInt(idParam);
                System.out.println("id: " + id);
            } catch (NumberFormatException e) {
                System.out.println("Invalid id format: " + idParam);
                e.printStackTrace();
                // Handle invalid id format, e.g., redirect to an error page
                request.setAttribute("errorMessage", "Invalid ID format.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
        } else {
            System.out.println("id parameter is missing or empty");
            // Handle the case where 'id' is missing
            request.setAttribute("errorMessage", "ID is required.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Retrieve all incomes related to the user and project
        List<Income> IncList = incDAO.selectAllIncome(id);
        request.setAttribute("listInc", IncList);
        
        // Retrieve the project details if ID is present
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> projects = projectDAO.selectAllPro(id); // Adjust based on method return type
       

        // Assuming only one project should be returned
        if (projects != null && !projects.isEmpty()) {
            Project currentPro = projects.get(0);
            request.setAttribute("project", currentPro);
        } else {
            request.setAttribute("project", null);
        }
        
         // Retrieve and set the list of votes (if applicable)
        try {
            List<Vote> vote = getAllVotes();
            request.setAttribute("listofVote", vote);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Handle exceptions related to vote retrieval
            request.setAttribute("errorMessage", "Error retrieving votes.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        

        // Forward the request to the JSP page for display
        request.getRequestDispatcher("/Income.jsp").forward(request, response);
    }

    private void retrieveIncFull(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");

        // Check if uID is null, indicating the user is not logged in or session expired
        if (uID == null) {
            System.out.println("User is not logged in or session expired.");
            response.sendRedirect("signIn.jsp"); // Redirect to login page
            return;
        }

        System.out.println("uID: " + uID);

        // Retrieve and parse the 'id' parameter
        String idParam = request.getParameter("id");
        Integer id = null;
        if (idParam != null && !idParam.isEmpty()) {
            try {
                id = Integer.parseInt(idParam);
                System.out.println("id: " + id);
            } catch (NumberFormatException e) {
                System.out.println("Invalid id format: " + idParam);
                e.printStackTrace();
                request.setAttribute("errorMessage", "Invalid ID format.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
        } else {
            System.out.println("id parameter is missing or empty");
            request.setAttribute("errorMessage", "ID is required.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Check for DAO initialization (ensure these are not null)
        if (incDAO == null || projectDAO == null) {
            throw new ServletException("DAO objects are not initialized.");
        }

        // Retrieve all incomes related to the user and project
        List<Income> IncList = incDAO.selectAllIncs(id);
        request.setAttribute("listInc", IncList);

        // Retrieve the project details if ID is present
        List<Project> projects = projectDAO.selectAllPro(id);

        // Assuming only one project should be returned
        if (projects != null && !projects.isEmpty()) {
            Project currentPro = projects.get(0);
            request.setAttribute("project", currentPro);
        } else {
            System.out.println("No project found with the given ID.");
            request.setAttribute("project", null);
        }

        // Forward the request to the JSP page for display
   
        request.getRequestDispatcher("/FullInc.jsp").forward(request, response);
    }

    private void updateInc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
        try {
            HttpSession session = request.getSession();
            Integer uID = (Integer) session.getAttribute("uID");

            if (uID == null) {
                throw new ServletException("No user session found. Please log in again.");
            }

            // Log user ID for debugging
            System.out.println("uID from session: " + uID);

            userDAO usrDAO = new userDAO(DbCon.getConnection());
            User user = usrDAO.selectUserById(uID);

            if (user == null) {
                throw new ServletException("User not found for the given uID: " + uID);
            }

            // Retrieve and parse parameters
            String idStr = request.getParameter("id");
            String iIDStr = request.getParameter("iID");
            String vIDStr = request.getParameter("vID");
            String totalAllStr = request.getParameter("total_All");
            String iDateStr = request.getParameter("idate");

            int id;
            int iID;
            int vID;
            BigDecimal total_All;
            java.sql.Date idate;

            try {
                id = Integer.parseInt(idStr); // This should correspond to the project ID or income ID
                iID = Integer.parseInt(iIDStr); // This should correspond to the income ID
                vID = Integer.parseInt(vIDStr);
                total_All = BigDecimal.valueOf(Double.parseDouble(totalAllStr));

                // Parse and format date
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = formatter.parse(iDateStr);
                idate = new java.sql.Date(parsedDate.getTime());
            } catch (NumberFormatException e) {
                throw new ServletException("Invalid number format for ID or total_All parameters.", e);
            } catch (ParseException e) {
                throw new ServletException("Invalid date format for idate parameter.", e);
            }

            Income income = new Income(iID, id,vID, total_All, idate);
            System.out.println("Income object before update: iID=" + income.getiID()
                    + ", id=" + income.getId() + ", total_All=" + income.getTotal_All()
                    + ", idate=" + income.getIdate());

            // Log Income object details
            System.out.println("Updating Income: " + income);

            boolean isUpdated = incDAO.updateInc(income);

            if (!isUpdated) {
                throw new SQLException("Failed to update the income record.");
            }
            
            // Fetch the updated total expense for the project
        BigDecimal newTotalAll = incDAO.calculateTotalAllForProject(id);

            // Update the total_All in the project table if needed
            updateTotalAll(id, newTotalAll);

            // Redirect to the appropriate page after successful update
            request.getSession().setAttribute("updateInc","EditInc");
            response.sendRedirect(request.getContextPath() + "/IncomeServlet?action5=listIncFull&id=" + id);
        } catch (SQLException ex) {
            Logger.getLogger(IncomeServlet.class.getName()).log(Level.SEVERE, "SQL error occurred", ex);
            throw new ServletException("SQL error occurred during income update.", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IncomeServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Error processing update due to class not found.", ex);
        }
    }

    private void deleteInc(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException, ClassNotFoundException {
    try {
        int iID = Integer.parseInt(request.getParameter("iID"));
        int id = Integer.parseInt(request.getParameter("id"));

        // Check if there is an associated expense
        boolean hasExpense = incDAO.checkExp(iID, id);
        request.setAttribute("hasExpense", hasExpense);

        if (!hasExpense) {
            // Proceed with deletion if no associated expense
            incDAO.deleteInc(iID);

            // Fetch and update the new total if deletion was successful
            BigDecimal newTotalAll = incDAO.calculateTotalAllForProject(id);
            updateTotalAll(id, newTotalAll);

            request.getSession().setAttribute("deleteInc", "Remove");
        } else {
            // Notify the user or handle cases where deletion is not allowed
            request.getSession().setAttribute("keepInc", "Cannot");
        }

        // Redirect to the updated list page
        response.sendRedirect(request.getContextPath() + "/IncomeServlet?action5=listIncFull&id=" + id);

    } catch (NumberFormatException e) {
        e.printStackTrace();
        response.sendRedirect("error.jsp");
    } catch (SQLException e) {
        Logger.getLogger(IncomeServlet.class.getName()).log(Level.SEVERE, "SQL error occurred", e);
        throw new ServletException("SQL error occurred during income deletion.", e);
    }
}


    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
