/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.db.DbCon;
import com.dao.*;
import com.model.*;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author NAJIHAH
 */
@WebServlet("/user-login")
public class userLogin extends HttpServlet {

    private userDAO usrDAO;

    private static final long serialVersionUID = 1L;

    @Override
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
        response.setContentType("text/html;charset=UTF-8");

        // Get the parameters from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("user :" + username);
        System.out.println("pass :" + password);
        // Validate the input
        if (username != null && password != null) {
            // Authenticate the user and fetch their role from the database
            User user = usrDAO.userLogin(username, password);

            if (user != null) {
                // Store user information in session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("uID", user.getuID());

                // Get the user's role from the User object
                String role = user.getRole();
                System.out.println("role :" + role);

                // Set a success message for the login
                session.setAttribute("loginSuccess", "true");

                // Redirect based on the role
                String contextPath = request.getContextPath();
                if (role.equals("user")) {
                    response.sendRedirect(contextPath + "/ProjectServlet?action3=HomeStatus");
                } else if (role.equals("admin")) {
                    response.sendRedirect(contextPath + "/AdminServlet?path1=Home");
                } else {
                    // Optional: handle unknown roles
                    response.sendRedirect("signIn.jsp?error=Unknown role");
                }
            } else {
            // If username or password is null, display "Please Sign In First!!"
            request.getSession().setAttribute("loginWrong", "Invalid");
            response.sendRedirect("signIn.jsp");
        } 
        } else {
                // Invalid username or password
                request.getSession().setAttribute("loginError", "Please Sign In!!");
                response.sendRedirect("signIn.jsp");

    }
}
}
