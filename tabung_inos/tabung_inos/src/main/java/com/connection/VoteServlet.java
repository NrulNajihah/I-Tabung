/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.connection;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;
import jakarta.servlet.RequestDispatcher;
import com.dao.*;
import com.model.*;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author NAJIHAH
 */
public class VoteServlet extends HttpServlet {

    private VoteDAO voteDAO;

    public void init() {
        voteDAO = new VoteDAO();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action5");

        System.out.println("VoteServlet ==> Action5 = " + action);

        try {
            switch (action) {
                case "editvt":
                    displayEditVoteForm(request, response);
                    break;
                case "insertvt":
                    createVote(request, response);
                    break;
                case "updatevt":
                    updateVote(request, response);
                    break;
                case "deletevt":
                    deleteVote(request, response);
                    break;
                default:
                    retrieveVote(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void displayEditVoteForm(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException {

    HttpSession session = request.getSession();

    String vIDParam = request.getParameter("vID");
    int vID = 0;

    // Check if vIDParam is not null or empty
    if (vIDParam != null && !vIDParam.isEmpty()) {
        try {
            vID = Integer.parseInt(vIDParam);
            System.out.println("Vote = " + vID);
        } catch (NumberFormatException e) {
            // Handle the exception (you might want to log this and show an error message to the user)
            throw new ServletException("Invalid vID parameter: must be an integer", e);
        }
    } else {
        // Handle the case where vID is missing (you might want to redirect to an error page or show a message)
        throw new ServletException("Missing vID parameter");
    }

    // Retrieve the data to be edited
    Vote currentvote = voteDAO.selectVoteByID(vID);
    request.setAttribute("vote", currentvote);

    // Forward the request to the JSP
    ServletContext sc = getServletContext();
    RequestDispatcher dispatcher = sc.getRequestDispatcher("/VoteServlet?action5=listvt");
    dispatcher.forward(request, response);
}


    private void createVote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String noVote = request.getParameter("noVote");
        String Vote = request.getParameter("vote");
        int noseq = Integer.parseInt(request.getParameter("noseq"));

        Vote vote = new Vote(noVote, Vote, noseq);
        voteDAO.insertVote(vote);
        
        request.getSession().setAttribute("insertVt","insertSuccess");
        response.sendRedirect(request.getContextPath() + "/VoteServlet?action5=listvt");
    }

    private void retrieveVote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        List<Vote> listofVote = voteDAO.selectAllVotes();

        request.setAttribute("listofVote", listofVote); // Corrected attribute name

        ServletContext sc = getServletContext();
        request.getRequestDispatcher("/Vote.jsp").forward(request, response);
    }

    private void updateVote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        int vID = Integer.parseInt(request.getParameter("vID"));
        String noVote = request.getParameter("noVote");
        String Vote = request.getParameter("vote");
        int noseq = Integer.parseInt(request.getParameter("noseq"));

        Vote vote = new Vote(vID, noVote, Vote, noseq);
        voteDAO.updateVote(vote);
        request.getSession().setAttribute("updateVt","updateSuccess");
        response.sendRedirect(request.getContextPath() + "/VoteServlet?action5=listvt");
    }

    private void deleteVote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        int vID = Integer.parseInt(request.getParameter("vID"));
        voteDAO.deleteVote(vID);
        request.getSession().setAttribute("deleteVt","removeSuccess");
        response.sendRedirect(request.getContextPath() + "/VoteServlet?action5=listvt");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
