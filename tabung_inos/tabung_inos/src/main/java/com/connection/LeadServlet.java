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
import com.dao.userDAO;
import com.model.Lead;
import com.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.db.DbCon;
import java.io.PrintWriter;

/**
 *
 * @author NAJIHAH
 */
public class LeadServlet extends HttpServlet {

    private userDAO usrDAO;

    public void init() throws ServletException {
        try {
            usrDAO = new userDAO(DbCon.getConnection()); // Initialize the userDAO
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action1");
        String uIDStr = request.getParameter("uID");

        System.out.println("LeadServlet ==> Action1 = " + action);

        try {
            switch (action) {
                case "editLp":
                    displayEditForm(request, response);
                    break;
                case "updateLp":
                    updateLp(request, response);
                    break;
                case "listLp":
                    retrieveLp(request, response);
                    break;
                case "fetchPosition":
                    if (uIDStr != null) {
                        int uID = Integer.parseInt(uIDStr);
                        fetchPosition(request, response, uID);
                    }
                    break;
                default:
                    retrieveLp(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LeadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");

        if (uID == null) {
            response.sendRedirect("signIn.jsp");
            return;
        }

        // Retrieve the list of users
        try {
            List<User> usrList = getUser();
            request.setAttribute("listofUser", usrList);
            System.out.println("List of users retrieved: " + usrList.size());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load users.");
        }

        // Retrieve the specific user details for editing
        try {

            // Fetch the user object from the database
            userDAO usrDAO = new userDAO(DbCon.getConnection());
            User currentUser = usrDAO.selectUserById(uID);

            if (currentUser != null) {
                request.setAttribute("user", currentUser);  // Set the user details to request scope
            } else {
                request.setAttribute("error", "User not found.");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid user ID format.");
            response.sendRedirect("error.jsp");
            return;
        }

        // Forward the request to the JSP page for displaying the edit form
        ServletContext sc = getServletContext();
        RequestDispatcher dispatcher = sc.getRequestDispatcher("/LeadServlet?action1=listLp");  // Redirect to the edit user form
        dispatcher.forward(request, response);
    }

    private void fetchPosition(HttpServletRequest request, HttpServletResponse response, int uID) 
        throws ServletException, IOException, SQLException {
    
    
    // Fetch the user role based on uID
    List<User> users = usrDAO.selectRole(uID); // Assuming this returns a list of users with roles
    
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

   try {
    // Assuming there's only one user role
    String currentRole = users.get(0).getRole(); // Fetch the current role of the user
    
    // Dynamically generate the options for "admin" and "user"
    if ("admin".equals(currentRole)) {
        out.println("<option value='admin' selected>admin</option>");
        out.println("<option value='user'>user</option>");
    } else if ("user".equals(currentRole)) {
        out.println("<option value='admin'>admin</option>");
        out.println("<option value='user' selected>user</option>");
    }

} finally {
    out.close();
}
}


    private void updateLp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try {
            // Get the session's uID to check if the user is logged in
            HttpSession session = request.getSession();
            Integer sessionUID = (Integer) session.getAttribute("uID");

            if (sessionUID == null) {
                response.sendRedirect("signIn.jsp");
                return;
            }

            System.out.println("Logged in user ID: " + sessionUID);

            // Get the uID of the user selected from the dropdown in the form
            Integer selectedUID = Integer.parseInt(request.getParameter("uID"));
            String role = request.getParameter("role");

            // Retrieve the selected user from the database
            userDAO usrDAO = new userDAO(DbCon.getConnection());
            User selectedUser = usrDAO.selectUserById(selectedUID);

            if (selectedUser != null) {
                // Update the role of the selected user
                selectedUser.setRole(role);

                // Call the updateUser method to update the user record
                boolean updateSuccess = usrDAO.updateUser(selectedUser);

                if (updateSuccess) {
                    System.out.println("User role updated to admin successfully.");
                } else {
                    System.out.println("Failed to update user role.");
                }

                // Redirect to the list page after the update
                request.getSession().setAttribute("updateRole", "updateSuccess");
                response.sendRedirect(request.getContextPath() + "/LeadServlet?action1=listLp");
            } else {
                System.out.println("Selected user not found with uID: " + selectedUID);
            }

        } catch (NumberFormatException | ClassNotFoundException ex) {
            ex.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private List<User> getUser() throws SQLException, ClassNotFoundException {
        userDAO usrDAO = new userDAO(DbCon.getConnection()); // Get connection from DbCon
        return usrDAO.selectUser(); // Retrieve users by role
    }

    private void retrieveLp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");
        System.out.println("uID: " + uID);

        try {
            List<User> userList = getUser();
            request.setAttribute("listofUser", userList);
            System.out.println("List of user retrieved: " + userList.size());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load user.");
        }
        List<User> listLead = usrDAO.selectAllUsers();
        request.setAttribute("listLead", listLead);
        request.getRequestDispatcher("/Lead.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
