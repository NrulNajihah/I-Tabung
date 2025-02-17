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
import com.model.Project;
import java.util.List;
/**
 *
 * @author NAJIHAH
 */
public class AhliServlet extends HttpServlet {
private MemberDAO memberDAO;
    private ProjectDAO projectDAO;

    public void init() {
        memberDAO = new MemberDAO();
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

        String path = request.getParameter("path2");

        System.out.println("AhliServlet ==> path2 = " + path);

        try {
            switch (path) {
                case "newMmb":
                    displayNewMmbForm(request, response);
                    break;
                case "insertMmb":
                    createMmb(request, response);
                    break;
                case "deleteMmb":
                    deleteMmb(request, response);
                    break;
                case "listMmb":
                    retrieveMmb(request, response);
                    break;
                default:
                    retrieveMmb(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AhliServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayNewMmbForm(HttpServletRequest request, HttpServletResponse response)
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

        List<Member> mmbList = memberDAO.selectAllMember(id);
        request.setAttribute("ListMmb", mmbList);
        System.out.println("List of member retrieved: " + mmbList.size());
        
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
            
            String role = request.getParameter("role"); // Get role from request parameters
            try {
                List<User> usrList = getUser(role);
                request.setAttribute("listofUser", usrList);
                System.out.println("List of user retrieved: " + usrList.size());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to load votes.");
            }

            ServletContext sc = getServletContext();
            RequestDispatcher dispatcher = sc.getRequestDispatcher("/Ahli.jsp");
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
    
   private List<User> getUser(String role) throws SQLException, ClassNotFoundException {
    userDAO usrDAO = new userDAO(DbCon.getConnection()); // Get connection from DbCon
    return usrDAO.selectUsersByRole(role); // Retrieve users by role
}



    private void createMmb(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer usrID = (Integer) session.getAttribute("uID");

            if (usrID == null) {
                throw new ServletException("No user ID found in session.");
            }

            // Log uID retrieved from session
            System.out.println("uID from session: " + usrID);

            userDAO usrDAO = new userDAO(DbCon.getConnection());
            User user = usrDAO.selectUserById(usrID);

            if (user == null) {
                throw new ServletException("User not found for the given uID: " + usrID);
            }
            
            String uIDStr = request.getParameter("uID");
            System.out.println("Request parameter 'uID': " + uIDStr);

            if (uIDStr == null || uIDStr.isEmpty()) {
                throw new ServletException("ID parameter is missing or empty");
            }

            Integer uID = null;
            try {
                uID = Integer.parseInt(uIDStr);
                System.out.println("Parsed ID: " + uID);
            } catch (NumberFormatException e) {
                throw new ServletException("Invalid ID format: " + uIDStr, e);
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
            String position = request.getParameter("position");
            
            String role = request.getParameter("role"); // Get role from request parameters
            try {
                List<User> usrList = getUser(role);
                request.setAttribute("listofUser", usrList);
                System.out.println("List of user retrieved: " + usrList.size());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to load votes.");
            }

            // Log all parsed values
            System.out.println("Parsed username: " + uID);
            System.out.println("Parsed role: " + role);

            // Create and insert the income
            Member member = new Member(uID, id,position);
            MemberDAO memberDAO = new MemberDAO();
            memberDAO.insertMember(member);

            // Log after member insert
            System.out.println("Income inserted for uID: " + uID + ", ID: " + id);

            request.getSession().setAttribute("insertMmb", "AddMmb");
            response.sendRedirect(request.getContextPath() + "/AhliServlet?path2=listMmb&id=" + id);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AhliServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Class not found error occurred", ex);
        } catch (SQLException ex) {
            Logger.getLogger(AhliServlet.class.getName()).log(Level.SEVERE, "SQL error occurred", ex);
            throw new ServletException("SQL error occurred", ex);
        }
    }

    private void retrieveMmb(HttpServletRequest request, HttpServletResponse response)
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

         List<Member> mmbList = memberDAO.selectAllMember(id);
        request.setAttribute("ListMmb", mmbList);
        System.out.println("List of member retrieved: " + mmbList.size());
        
        
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
        
         String role = request.getParameter("role"); // Get role from request parameters
            try {
                List<User> usrList = getUser(role);
                request.setAttribute("listofUser", usrList);
                System.out.println("List of user retrieved: " + usrList.size());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to load votes.");
            }
        

        // Forward the request to the JSP page for display
        request.getRequestDispatcher("/Ahli.jsp").forward(request, response);
    }


    private void deleteMmb(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        try {
            int mID = Integer.parseInt(request.getParameter("mID"));
            System.out.println("mID: " + mID);
            int id = Integer.parseInt(request.getParameter("id"));
            memberDAO.deleteMmb(mID);

            request.getSession().setAttribute("deleteMmb", "Remove");
            response.sendRedirect(request.getContextPath() + "/AhliServlet?path2=listMmb&id=" + id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
