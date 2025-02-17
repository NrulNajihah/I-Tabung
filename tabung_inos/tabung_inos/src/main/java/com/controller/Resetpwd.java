/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

import com.db.DbCon;
import com.dao.*;
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
public class Resetpwd extends HttpServlet {

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
    String email = request.getParameter("email");
    System.out.println("email: " + email);

    // Validate the input
    if (email != null && !email.isEmpty()) {
        try {
            // Authenticate the user and fetch their details from the database
            User reset = usrDAO.selectPsw(email);

            if (reset != null) {
                // Generate OTP
                String otp = generateOtp();

                // Send OTP via Email
                boolean emailSent = sendOtpEmail(email, otp);

                if (emailSent) {
                    // Get the session and store uID and OTP
                    HttpSession session = request.getSession();
                    session.setAttribute("uID", reset.getuID()); // Store uID in session
                    session.setAttribute("otp", otp); // Store OTP in session
                    session.setAttribute("email", reset.getEmail());

                    // Redirect to EnterOtp.jsp
                    request.setAttribute("status", "sendMessage");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/EnterOtp.jsp");
                    dispatcher.forward(request, response);
                } else {
                    // Failed to send email
                    request.setAttribute("error", "Failed to send OTP email. Please try again.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/Reset.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                // User not found in the database
                request.setAttribute("error", "No user found with the entered email. Please register first.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/Reset.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Resetpwd.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "Database error occurred. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Reset.jsp");
            dispatcher.forward(request, response);
        }
    } else {
        // Input validation error
        request.setAttribute("error", "Email is required.");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Reset.jsp");
        dispatcher.forward(request, response);
    }
}

    // Helper method to generate OTP

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate 6-digit OTP
        return String.valueOf(otp);
    }

// Helper method to send OTP email
    private boolean sendOtpEmail(String toEmail, String otp) {
        final String fromEmail = "your-email@example.com"; // Use your email
        final String password = "your-password"; // Use your email password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Use your SMTP server
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Authenticate the email
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("songcheryl5@gmail.com", "ieqr fsdm pmim lsup");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otp);

            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
