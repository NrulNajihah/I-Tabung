/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.db.DbCon;
import com.dao.userDAO;
import com.model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NAJIHAH
 */
public class Newpwd extends HttpServlet {

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

        HttpSession session = request.getSession();
        String password = request.getParameter("password");
        String confirmpwd = request.getParameter("confirmpwd");
        String email = (String) session.getAttribute("email"); // Retrieve email from session

        RequestDispatcher dispatcher = null;

        // Check if the passwords match
        if (password != null && confirmpwd != null && password.equals(confirmpwd)) {
            try {
                // Hash the new password (implement password hashing before storing)
                String hashedPassword = hashPassword(password); 

                // Update the user's password in the database
                boolean updateSuccess = usrDAO.updatePwdByEmail(email, hashedPassword);

                if (updateSuccess) {
                    request.setAttribute("status", "resetSuccess");
                    dispatcher = request.getRequestDispatcher("signIn.jsp");
                } else {
                    request.setAttribute("status", "resetFailed");
                    dispatcher = request.getRequestDispatcher("ForgotPwd.jsp");
                }

                dispatcher.forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("status", "resetFailed");
                dispatcher = request.getRequestDispatcher("ForgotPwd.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            // Passwords do not match, show error
            request.setAttribute("status", "passwordMismatch");
            dispatcher = request.getRequestDispatcher("ForgotPwd.jsp");
            dispatcher.forward(request, response);
        }
    }

    // Placeholder method for hashing the password (implement a secure hash like BCrypt)
    private String hashPassword(String password) {
        // Example: return BCrypt.hashpw(password, BCrypt.gensalt());
        return password; // Replace with actual hashing logic
    }
}
