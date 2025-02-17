/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.controller;

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
import com.model.Tabung;
import java.util.List;

/**
 *
 * @author NAJIHAH
 */
public class CompServlet extends HttpServlet {

    private CompDAO compDAO;
    private TabungDAO tabungDAO;

    public void init() {
        compDAO = new CompDAO();
        tabungDAO = new TabungDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getParameter("path5");

        System.out.println("CompServlet ==> path5 = " + path);

        try {
            switch (path) {
                case "newComp":
                    displayNewCompForm(request, response);
                    break;
                case "insertComp":
                    createComp(request, response);
                    break;
//                case "deleteMmb":
//                    deleteMmb(request, response);
//                    break;
                case "listComp":
                    retrieveComp(request, response);
                    break;
                default:
                    retrieveComp(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ParseException ex) {
            Logger.getLogger(CompServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayNewCompForm(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpSession session = request.getSession();
    Integer uID = (Integer) session.getAttribute("uID");

    if (uID == null) {
        response.sendRedirect("login.jsp"); // Redirect to login if no user ID found
        return;
    }

    try {
        // Retrieve list of Tabung and set as request attribute
        List<Tabung> tabungs = getAllTbgs();
        request.setAttribute("listTabung", tabungs);

        // Forward to the desired path
        RequestDispatcher dispatcher = request.getRequestDispatcher("/CompServlet?path5=listComp");
        dispatcher.forward(request, response);
    } catch (SQLException | ClassNotFoundException e) {
        // Log error and redirect to an error page
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve Tabung data.");
    }
}

    
    private List<Project> getAllProjects(int uID) throws SQLException, ClassNotFoundException {
        ProjectDAO projectDAO = new ProjectDAO();
        return projectDAO.selectAllProjectsByUserId(uID);
    }
    private List<Tabung> getAllTbgs() throws SQLException, ClassNotFoundException {
        TabungDAO tbgDAO = new TabungDAO();
        return tbgDAO.selectAllListTbg();
    }
    
   private List<User> getUser(String role) throws SQLException, ClassNotFoundException {
    userDAO usrDAO = new userDAO(DbCon.getConnection()); // Get connection from DbCon
    return usrDAO.selectUsersByRole(role); // Retrieve users by role
}
   
    private void createComp(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException, ParseException {
    try {
        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");

        if (uID == null) {
            throw new ServletException("No user ID found in session.");
        }

        // Retrieve and validate tID
        String tIDStr = request.getParameter("tID");
        if (tIDStr == null || tIDStr.isEmpty()) {
            throw new ServletException("ID parameter is missing or empty");
        }

        Integer tID;
        try {
            tID = Integer.parseInt(tIDStr);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid tID format: " + tIDStr, e);
        }

        // Verify that tID exists in the database
        CompDAO compDAO = new CompDAO();
        if (!compDAO.doesTabungExist(tID)) {
            throw new ServletException("The provided tID does not exist!");
        }

        // Retrieve other request parameters
        String compID = request.getParameter("compID");
        String compName = request.getParameter("compName");
        String totalAllStr = request.getParameter("total_All");
        if (compID == null || compName == null || totalAllStr == null) {
            throw new ServletException("Required parameters are missing.");
        }

        BigDecimal total_All;
        try {
            total_All = BigDecimal.valueOf(Double.parseDouble(totalAllStr));
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid format for total_All: " + totalAllStr, e);
        }

        String cdateStr = request.getParameter("compDate");
        if (cdateStr == null || cdateStr.isEmpty()) {
            throw new ServletException("compDate parameter is missing or empty.");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date compDate;
        try {
            java.util.Date parsedDate = formatter.parse(cdateStr);
            compDate = new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
            throw new ServletException("Invalid date format for compDate: " + cdateStr, e);
        }

        // Insert the component
        Component component = new Component(tID, compID, compName, total_All, compDate);
        compDAO.insertComp(component);

        System.out.println("Component inserted successfully for tID: " + tID);

        // Update the balance in Tabung
        TabungDAO tabungDAO = new TabungDAO();
        BigDecimal newTotalAmount = compDAO.calculateTotalAllByTabung(tID);
        tabungDAO.updateAmount(tID, newTotalAmount);
        System.out.println("Tabung balance updated successfully for tID: " + tID);

        // Set success attribute and redirect
        session.setAttribute("insertComp", "AddComp");
        response.sendRedirect(request.getContextPath() + "/AdminServlet?path1=listProH");

    } catch (SQLException ex) {
        Logger.getLogger(CompServlet.class.getName()).log(Level.SEVERE, "SQL error occurred", ex);
        throw new ServletException("SQL error occurred", ex);
    } catch (Exception ex) {
        Logger.getLogger(CompServlet.class.getName()).log(Level.SEVERE, "An unexpected error occurred", ex);
        throw new ServletException("An unexpected error occurred", ex);
    }
}


    private void retrieveComp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");
        System.out.println("uID: " + uID);

        // Retrieve and parse the 'id' parameter
        String tidParam = request.getParameter("tID");
        Integer tID = null;
        if (tidParam != null && !tidParam.isEmpty()) {
            try {
                tID = Integer.parseInt(tidParam);
                System.out.println("tID: " + tID);
            } catch (NumberFormatException e) {
                System.out.println("Invalid tID format: " + tidParam);
                e.printStackTrace();
                // Handle invalid id format, e.g., redirect to an error page
                request.setAttribute("errorMessage", "Invalid ID format.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
        } else {
            System.out.println("tID parameter is missing or empty");
            // Handle the case where 'id' is missing
            request.setAttribute("errorMessage", "tID is required.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        List<Component> cmpList = compDAO.selectAllComp(tID);
        request.setAttribute("ListComp", cmpList);
        System.out.println("List of Componet retrieved: " + cmpList.size());

        // Retrieve the project details if ID is present
        TabungDAO tabungDAO = new TabungDAO();
        List<Tabung> tabungList = tabungDAO.selectTbg(tID); // Adjust based on method return type

        // Assuming only one project should be returned
        if (tabungList != null && !tabungList.isEmpty()) {
            Tabung currentTbg = tabungList.get(0);
            request.setAttribute("tabung", currentTbg);
        } else {
            request.setAttribute("tabung", null);
        }

        // Forward the request to the JSP page for display
        request.getRequestDispatcher("/ProjHiCoe.jsp").forward(request, response);
    }

//    private void deleteMmb(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException, SQLException, ClassNotFoundException {
//        try {
//            int mID = Integer.parseInt(request.getParameter("mID"));
//            System.out.println("mID: " + mID);
//            int id = Integer.parseInt(request.getParameter("id"));
//            memberDAO.deleteMmb(mID);
//
//            request.getSession().setAttribute("deleteMmb", "Remove");
//            response.sendRedirect(request.getContextPath() + "/AhliServlet?path2=listMmb&id=" + id);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            response.sendRedirect("error.jsp");
//        }
//    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
