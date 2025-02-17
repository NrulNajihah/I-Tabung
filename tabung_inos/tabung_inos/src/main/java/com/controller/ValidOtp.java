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
public class ValidOtp extends HttpServlet {

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

    String otpParam = request.getParameter("otp");
    HttpSession session = request.getSession();
    
    // Retrieve OTP from session as a String
    String sessionOtpStr = (String) session.getAttribute("otp");
    Integer otp = null;

    // Convert session OTP to Integer if it's not null
    if (sessionOtpStr != null) {
        try {
            otp = Integer.parseInt(sessionOtpStr);
        } catch (NumberFormatException e) {
            // Handle error if conversion fails
            System.out.println("Error parsing OTP from session: " + e.getMessage());
        }
    }

    System.out.println("OTP :" + otp);

    RequestDispatcher dispatcher = null;

    try {
        // Check if otpParam is not null and can be parsed
        if (otpParam != null && !otpParam.isEmpty()) {
            int value = Integer.parseInt(otpParam);

            if (otp != null && value == otp) {
                request.setAttribute("email", request.getParameter("email"));
                request.setAttribute("status", "success");
                dispatcher = request.getRequestDispatcher("ForgotPwd.jsp");
            } else {
                request.setAttribute("message", "Wrong OTP");
                dispatcher = request.getRequestDispatcher("EnterOtp.jsp");
            }
        } else {
            request.setAttribute("message", "OTP cannot be empty");
            dispatcher = request.getRequestDispatcher("EnterOtp.jsp");
        }
    } catch (NumberFormatException e) {
        request.setAttribute("message", "Invalid OTP format");
        dispatcher = request.getRequestDispatcher("EnterOtp.jsp");
    } finally {
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
}
}