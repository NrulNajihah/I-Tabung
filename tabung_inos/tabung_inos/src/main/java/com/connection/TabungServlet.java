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
import com.dao.TabungDAO;
import com.dao.userDAO;
import com.model.Tabung;
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
import java.math.BigDecimal;
import java.sql.*;

/**
 *
 * @author NAJIHAH
 */
public class TabungServlet extends HttpServlet {
private TabungDAO tbgDAO;

    public void init() {
        tbgDAO = new TabungDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action2");

        System.out.println("TabungServlet ==> Action2 = " + action);

        try {
            switch (action) {
                case "newTbg":
                    displayNewTbgForm(request, response);
                    break;
                case "editTbg":
                    displayEditTbgForm(request, response);
                    break;
                case "insertTbg":
                    createTbg(request, response);
                    break;
                case "updateTbg":
                    updateTbg(request, response);
                    break;
                case "listTbg":
                    retrieveTbg(request, response);
                    break;
                default:
                    retrieveTbg(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void displayNewTbgForm(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession();
    Integer uID = ((User) session.getAttribute("user")).getuID();

    // Forward the request to the JSP where the form is located
    ServletContext sc = getServletContext();
    RequestDispatcher dispatcher = sc.getRequestDispatcher("/TabungServlet?action2=listTbg"); // Correct the JSP path
    dispatcher.forward(request, response);
}


   private void displayEditTbgForm(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException {

    HttpSession session = request.getSession();
    Integer uID = ((User) session.getAttribute("user")).getuID();
    int tbgID = Integer.parseInt(request.getParameter("tID"));

    // Retrieve the data to be edited
    Tabung currenttabung = tbgDAO.selectTbgByID(tbgID);
    request.setAttribute("tabung", currenttabung);


    // Forward the request to the JSP
    ServletContext sc = getServletContext();
    RequestDispatcher dispatcher = sc.getRequestDispatcher("/TabungServlet?action2=listTbg");
    dispatcher.forward(request, response);
}

    private void createTbg(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try {
            HttpSession session = request.getSession();
            Integer uID = (Integer) session.getAttribute("uID");

            if (uID != null) {
                System.out.println("uID: " + uID);

                userDAO usrDAO = new userDAO(DbCon.getConnection());
                User user = usrDAO.selectUserById(uID);

                if (user != null) {
                    int noTb = Integer.parseInt(request.getParameter("noTb"));
                    String tname = request.getParameter("tname");
                    String tdateStr = request.getParameter("tdate");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format if necessary
                    java.util.Date parsedDate = formatter.parse(tdateStr);
                    java.sql.Date tdate = new java.sql.Date(parsedDate.getTime());

                    String amountStr = request.getParameter("amount");
                    BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountStr));
                    String balanceStr = request.getParameter("balance");
                    BigDecimal balance = BigDecimal.valueOf(Double.parseDouble(balanceStr));

                    Tabung tabung = new Tabung(uID, noTb, tname, tdate, amount,balance);
                    tbgDAO.insertTabung(tabung);
                    
                    request.getSession().setAttribute("insertTbg", "insertSuccess");
                    response.sendRedirect(request.getContextPath() + "/TabungServlet?action2=listTbg");
                } else {
                    System.out.println("user id didn't get");
                }
            } else {
                System.out.println("user didn't exist");
            }

        } catch (ClassNotFoundException | ParseException ex) {
            Logger.getLogger(TabungServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void retrieveTbg(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");
        System.out.println("uID: " + uID);
        List<Tabung> listTabung = tbgDAO.selectAllListTbg();
        request.setAttribute("listTabung", listTabung);
        request.getRequestDispatcher("/Tabung.jsp").forward(request, response);
    }


     private void updateTbg(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try {
            HttpSession session = request.getSession();
            Integer uID = (Integer) session.getAttribute("uID");

            if (uID != null) {
                System.out.println("uID: " + uID);

                userDAO usrDAO = new userDAO(DbCon.getConnection());
                User user = usrDAO.selectUserById(uID);

                if (user != null) {
                    System.out.println("Hello : - > " + Integer.parseInt(request.getParameter("tID")));
                    int tID = Integer.parseInt(request.getParameter("tID"));
                    int noTb = Integer.parseInt(request.getParameter("noTb"));
                    String tname = request.getParameter("tname");
                    String tdateStr = request.getParameter("tdate");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format if necessary
                    java.util.Date parsedDate = formatter.parse(tdateStr);
                    java.sql.Date tdate = new java.sql.Date(parsedDate.getTime());

                    String amountStr = request.getParameter("amount");
                BigDecimal amount = null;
                if (amountStr != null && !amountStr.isEmpty()) {
                    amount = BigDecimal.valueOf(Double.parseDouble(amountStr));
                }

                    Tabung tabung = new Tabung(tID, uID, noTb, tname, tdate, amount);

                    tbgDAO.updateTbg(tabung);
                    
                    request.getSession().setAttribute("updateTbg","updateSuccess");
                    response.sendRedirect(request.getContextPath() + "/TabungServlet?action2=listTbg");
                } else {
                    System.out.println("user id didn't get");
                }
            } else {
                System.out.println("user didn't exist");
            }

        } catch (ClassNotFoundException | ParseException ex) {
            Logger.getLogger(TabungServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
