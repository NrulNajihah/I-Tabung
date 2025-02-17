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
public class ExtendServlet extends HttpServlet {

    private ExtendDAO extDAO;
    private ProjectDAO projectDAO;

    public void init() {
        extDAO = new ExtendDAO();
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

        String action = request.getParameter("action11");

        System.out.println("ExtendServlet ==> Action11 = " + action);

        try {
            switch (action) {
                case "newExt":
                    displayNewExtForm(request, response);
                    break;
//                case "editInc":
//                    displayEditIncForm(request, response);
//                    break;
                case "insertExt":
                    createExt(request, response);
                    break;
//                case "updateInc":
//                    updateInc(request, response);
//                    break;
                case "deleteExt":
                    deleteExt(request, response);
                    break;
                case "listExt":
                    retrieveExt(request, response);
                    break;
                default:
                    retrieveExt(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IncomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ExtendServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayNewExtForm(HttpServletRequest request, HttpServletResponse response)
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
            List<Extend> ExtList = extDAO.selectAllExts(id);
            request.setAttribute("ExtList", ExtList);
            System.out.println("List of extend retrieved: " + ExtList.size());

            if (projects != null && !projects.isEmpty()) {
                Project currentPro = projects.get(0);
                request.setAttribute("project", currentPro);
            } else {
                request.setAttribute("project", null);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher dispatcher = sc.getRequestDispatcher("/Extend.jsp");
            dispatcher.forward(request, response);
        } else {
            System.err.println("ID parameter is missing.");
            response.sendRedirect("error.jsp");
        }
    }

    private void createExt(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
        try {
            HttpSession session = request.getSession();
            Integer uID = (Integer) session.getAttribute("uID");

            if (uID == null) {
                throw new ServletException("No user ID found in session.");
            }

            userDAO usrDAO = new userDAO(DbCon.getConnection());
            User user = usrDAO.selectUserById(uID);

            if (user == null) {
                throw new ServletException("User not found for the given uID");
            }

            // Handle the ID parameter
            String IdStr = request.getParameter("id");
            if (IdStr == null || IdStr.isEmpty()) {
                throw new ServletException("ID parameter is missing or empty");
            }

            Integer id = Integer.parseInt(IdStr);

            String endPStr = request.getParameter("endP");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = formatter.parse(endPStr);
            java.sql.Date endP = new java.sql.Date(parsedDate.getTime());

            // Insert extend
            Extend extend = new Extend(id, endP);
            extDAO.insertExt(extend);

            /// Update project endP
            projectDAO.updateExtDate(id, endP);

            System.out.println("extend created for ID: " + id);

            // Set session attribute for update date
           
            response.sendRedirect(request.getContextPath() + "/ExtendServlet?action11=listExt&id=" + id);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ExpenseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void retrieveExt(HttpServletRequest request, HttpServletResponse response)
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
        List<Extend> ExtList = extDAO.selectAllExts(id);
        request.setAttribute("ExtList", ExtList);

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

        // Forward the request to the JSP page for display
        request.getRequestDispatcher("/Extend.jsp").forward(request, response);
    }

//    private void retrieveIncFull(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException, SQLException {
//
//        HttpSession session = request.getSession();
//        Integer uID = (Integer) session.getAttribute("uID");
//
//        // Check if uID is null, indicating the user is not logged in or session expired
//        if (uID == null) {
//            System.out.println("User is not logged in or session expired.");
//            response.sendRedirect("signIn.jsp"); // Redirect to login page
//            return;
//        }
//
//        System.out.println("uID: " + uID);
//
//        // Retrieve and parse the 'id' parameter
//        String idParam = request.getParameter("id");
//        Integer id = null;
//        if (idParam != null && !idParam.isEmpty()) {
//            try {
//                id = Integer.parseInt(idParam);
//                System.out.println("id: " + id);
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid id format: " + idParam);
//                e.printStackTrace();
//                request.setAttribute("errorMessage", "Invalid ID format.");
//                request.getRequestDispatcher("/error.jsp").forward(request, response);
//                return;
//            }
//        } else {
//            System.out.println("id parameter is missing or empty");
//            request.setAttribute("errorMessage", "ID is required.");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//            return;
//        }
//
//        // Check for DAO initialization (ensure these are not null)
//        if (incDAO == null || projectDAO == null) {
//            throw new ServletException("DAO objects are not initialized.");
//        }
//
//        // Retrieve all incomes related to the user and project
//        List<Income> IncList = incDAO.selectAllIncs(uID, id);
//        request.setAttribute("listInc", IncList);
//
//        // Retrieve the project details if ID is present
//        List<Project> projects = projectDAO.selectAllPro(id);
//
//        // Assuming only one project should be returned
//        if (projects != null && !projects.isEmpty()) {
//            Project currentPro = projects.get(0);
//            request.setAttribute("project", currentPro);
//        } else {
//            System.out.println("No project found with the given ID.");
//            request.setAttribute("project", null);
//        }
//
//        // Forward the request to the JSP page for display
//        request.getRequestDispatcher("/FullInc.jsp").forward(request, response);
//    }
//    private void updateInc(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException, SQLException, ParseException {
//        try {
//            HttpSession session = request.getSession();
//            Integer uID = (Integer) session.getAttribute("uID");
//
//            if (uID == null) {
//                throw new ServletException("No user session found. Please log in again.");
//            }
//
//            // Log user ID for debugging
//            System.out.println("uID from session: " + uID);
//
//            userDAO usrDAO = new userDAO(DbCon.getConnection());
//            User user = usrDAO.selectUserById(uID);
//
//            if (user == null) {
//                throw new ServletException("User not found for the given uID: " + uID);
//            }
//
//            // Retrieve and parse parameters
//            String idStr = request.getParameter("id");
//            String iIDStr = request.getParameter("iID");
//            String vIDStr = request.getParameter("vID");
//            String totalAllStr = request.getParameter("total_All");
//            String iDateStr = request.getParameter("idate");
//
//            int id;
//            int iID;
//            int vID;
//            BigDecimal total_All;
//            java.sql.Date idate;
//
//            try {
//                id = Integer.parseInt(idStr); // This should correspond to the project ID or income ID
//                iID = Integer.parseInt(iIDStr); // This should correspond to the income ID
//                vID = Integer.parseInt(vIDStr);
//                total_All = BigDecimal.valueOf(Double.parseDouble(totalAllStr));
//
//                // Parse and format date
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                java.util.Date parsedDate = formatter.parse(iDateStr);
//                idate = new java.sql.Date(parsedDate.getTime());
//            } catch (NumberFormatException e) {
//                throw new ServletException("Invalid number format for ID or total_All parameters.", e);
//            } catch (ParseException e) {
//                throw new ServletException("Invalid date format for idate parameter.", e);
//            }
//
//            Income income = new Income(iID, uID, id,vID, total_All, idate);
//            System.out.println("Income object before update: iID=" + income.getiID() + ", uID=" + income.getuID()
//                    + ", id=" + income.getId() + ", total_All=" + income.getTotal_All()
//                    + ", idate=" + income.getIdate());
//
//            // Log Income object details
//            System.out.println("Updating Income: " + income);
//
//            boolean isUpdated = incDAO.updateInc(income);
//
//            if (!isUpdated) {
//                throw new SQLException("Failed to update the income record.");
//            }
//            
//            // Fetch the updated total expense for the project
//        BigDecimal newTotalAll = incDAO.calculateTotalAllForProject(id);
//
//            // Update the total_All in the project table if needed
//            updateTotalAll(id, newTotalAll);
//
//            // Redirect to the appropriate page after successful update
//            response.sendRedirect(request.getContextPath() + "/IncomeServlet?action5=listIncFull&id=" + id);
//        } catch (SQLException ex) {
//            Logger.getLogger(IncomeServlet.class.getName()).log(Level.SEVERE, "SQL error occurred", ex);
//            throw new ServletException("SQL error occurred during income update.", ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(IncomeServlet.class.getName()).log(Level.SEVERE, null, ex);
//            throw new ServletException("Error processing update due to class not found.", ex);
//        }
//    }
    private void deleteExt(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        try {
            int exID = Integer.parseInt(request.getParameter("exID"));
            System.out.println("exID: " + exID);
            int id = Integer.parseInt(request.getParameter("id"));
            extDAO.deleteExt(exID);

            response.sendRedirect(request.getContextPath() + "/ExtendServlet?action11=listExt&id=" + id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } catch (SQLException e) {
            Logger.getLogger(ExtendServlet.class.getName()).log(Level.SEVERE, "SQL error occurred", e);
            throw new ServletException("SQL error occurred during income deletion.", e);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
