/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.connection;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;
import jakarta.servlet.RequestDispatcher;
import com.dao.*;
import com.dao.VoteDAO;
import com.model.*;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.db.DbCon;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;
import com.model.Project;
import java.util.List;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import static com.itextpdf.kernel.pdf.PdfName.Color;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import java.util.stream.Collectors;

/**
 *
 * @author NAJIHAH
 */
@MultipartConfig(maxFileSize = 16177215) //16MB // This annotation is required to handle multipart/form-data requests
public class ExpenseServlet extends HttpServlet {

    private ExpenseDAO expDAO;
    private ProjectDAO projectDAO;

    public void init() {
        expDAO = new ExpenseDAO();
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

        String action = request.getParameter("action4");

        System.out.println("ExpenseServlet ==> Action4 = " + action);

        try {
            switch (action) {
                case "newExp":
                    displayNewExpForm(request, response);
                    break;
                case "editExp":
                    displayEditExpForm(request, response);
                    break;
                case "insertExp":
                    createExp(request, response);
                    break;
                case "updateExp":
                    updateExp(request, response);
                    break;
                case "deleteExp":
                    deleteExp(request, response);
                    break;
                case "listExp":
                    retrieveExp(request, response);
                    break;
                case "listExpFull":
                    retrieveExpFull(request, response);
                    break;
                case "report":
                    generateReport(request, response);
                    break;
                default:
                    retrieveExp(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ParseException ex) {
            Logger.getLogger(ExpenseServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ExpenseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayNewExpForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

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

            // Retrieve all expenses related to the user and project
            IncomeDAO incDAO = new IncomeDAO();
            List<Income> ExpList = incDAO.selectAllExpense(usrID, id);
            request.setAttribute("listofExp", ExpList);

            ProjectDAO projectDAO = new ProjectDAO();
            List<Project> projects = projectDAO.selectAllPro(id);
            System.out.println("Projects found: " + (projects != null ? projects.size() : 0));

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

            // Retrieve and set the list of votes (if applicable)
            try {
                // Pass the uID and id to the getVotes method to get all votes
                List<Income> vtList = getVotes(usrID, id);

                // Remove duplicates using getUniqueVotes method
                List<Income> uniqueVotes = getUniqueVotes(vtList);

                // Set the filtered unique votes list as an attribute for the request
                request.setAttribute("vtList", uniqueVotes);

                // Print the size of the uniqueVotes list to the console for debugging
                System.out.println("Unique votes retrieved: " + (uniqueVotes != null ? uniqueVotes.size() : 0));

            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("error", "Invalid parameters.");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ExpenseServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher dispatcher = sc.getRequestDispatcher("/Expense.jsp");
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

    private List<Vote> getAllVotes() throws SQLException, ClassNotFoundException {
        VoteDAO voteDAO = new VoteDAO();
        return voteDAO.selectAllVotes();
    }

    private List<Income> getVotes(int uID, int id) throws SQLException, ClassNotFoundException {
        IncomeDAO incDAO = new IncomeDAO();
        return incDAO.selectVoteID(id);
    }

    public List<Income> getUniqueVotes(List<Income> incomeList) {
        Set<Integer> vIDSet = new HashSet<>();
        List<Income> uniqueIncomeList = new ArrayList<>();

        for (Income income : incomeList) {
            if (!vIDSet.contains(income.getvID())) {
                vIDSet.add(income.getvID());
                uniqueIncomeList.add(income);
            }
        }
        return uniqueIncomeList;
    }

    private void displayEditExpForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

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

            try {
                List<Vote> voteList = getAllVotes();
                request.setAttribute("listofVote", voteList);
                System.out.println("Votes retrieved: " + (voteList != null ? voteList.size() : 0));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to load votes.");
            }

            // Safely parse eID parameter
            try {
                int eID = Integer.parseInt(request.getParameter("eID"));
                System.out.println("eID: " + eID);

                // Retrieve the data to be edited
                Expense currentExpense = expDAO.selectExpByID(eID);
                if (currentExpense != null) {
                    request.setAttribute("expense", currentExpense);
                } else {
                    request.setAttribute("error", "Expense not found.");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("error", "Invalid expense ID format.");
                response.sendRedirect("error.jsp");
                return;
            }

            // Forward the request to the JSP
            ServletContext sc = getServletContext();
            RequestDispatcher dispatcher = sc.getRequestDispatcher("/editExp.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    private void createExp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
        try {
            HttpSession session = request.getSession();
            Integer uID = (Integer) session.getAttribute("uID");

            if (uID == null) {
                response.sendRedirect("signIn.jsp");
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

            // Handle the vID and convert it to noVote
            String vIDStr = request.getParameter("vID");
            if (vIDStr == null || vIDStr.isEmpty()) {
                throw new ServletException("vID parameter is missing or empty");
            }

            Integer vID = Integer.parseInt(vIDStr);
            VoteDAO voteDAO = new VoteDAO();
            String noVote = voteDAO.selectnoVoteById(vID);

            if (noVote == null) {
                throw new ServletException("No corresponding noVote found for the given vID");
            }

            // Proceed with the remaining parameters
            String claim = request.getParameter("claim");
            String edateStr = request.getParameter("edate");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = formatter.parse(edateStr);
            java.sql.Date edate = new java.sql.Date(parsedDate.getTime());

            String noPO = request.getParameter("noPO");
            String totalExpStr = request.getParameter("total_Exp");
            BigDecimal total_Exp = BigDecimal.valueOf(Double.parseDouble(totalExpStr));
            Part filePart = request.getPart("receipt");
            byte[] receipt = null;

            if (filePart != null && filePart.getSize() > 0) {
                receipt = filePart.getInputStream().readAllBytes();
            }

            // Fetch the total_All from the income table for validation
            IncomeDAO incomeDAO = new IncomeDAO();
            Income income = incomeDAO.getIncomeByVoteID(id, vID);
            // Check if income exists for the given vID (vote ID)
            if (income == null || vID == null) {
                // Handle the error scenario if income record or vID does not exist
                request.setAttribute("bugMessage", "Nombor maklumat tidak wujud dalam peruntukan.");
                request.getRequestDispatcher("/ExpenseServlet?action4=newExp&id=" + id).forward(request, response);
                return;
            }
            BigDecimal total_All = income.getTotal_All();

// Fetch the total sum of expenses for the same vID
            BigDecimal total_ExpForVote = expDAO.getTotalExpenseByVoteID(id, vID);

// If no existing expenses for this vID, treat it as zero
            if (total_ExpForVote == null) {
                total_ExpForVote = BigDecimal.ZERO;
            }

// Add the new expense to the total_ExpForVote
            BigDecimal total_ExpWithNew = total_ExpForVote.add(total_Exp);

// Check if the sum of expenses exceeds total_All
            if (total_ExpWithNew.compareTo(total_All) > 0) {
                // Handle the error scenario if total_ExpWithNew exceeds total_All
                request.setAttribute("errorMessage", "Jumlah perbelanjaan tidak boleh melebihi jumlah peruntukan yang ditetapkan.");
                request.getRequestDispatcher("/ExpenseServlet?action4=newExp&id=" + id).forward(request, response);
                return;
            }

            // Create and insert the expense
            Expense expense = new Expense(uID, id, vID, claim, edate, noPO, total_Exp, receipt, filePart != null ? filePart.getSubmittedFileName() : null);
            expDAO.insertExpense(expense);

            // Deduct the total_Exp from total_All and update the income table
            income.setTotal_All(total_All.subtract(total_Exp));
            incomeDAO.updateInc(income);

            // Update the total_Exp in the project table
            updateProjectTotalExp(id, total_Exp);

            System.out.println("Expense created for ID: " + id);

            request.getSession().setAttribute("insertExp", "AddExp");
            response.sendRedirect(request.getContextPath() + "/ExpenseServlet?action4=listExp&id=" + id);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ExpenseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateProjectTotalExp(Integer id, BigDecimal total_Exp) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = DbCon.getConnection();

            // First, retrieve the current total_All value from the project table
            String selectSql = "SELECT tID, total_All, total_Exp FROM project WHERE id = ?";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            BigDecimal total_All = BigDecimal.ZERO;
            BigDecimal currentTotalExp = BigDecimal.ZERO;
            Integer tID = null;

            if (rs.next()) {
                total_All = rs.getBigDecimal("total_All");
                currentTotalExp = rs.getBigDecimal("total_Exp") != null ? rs.getBigDecimal("total_Exp") : BigDecimal.ZERO;
                tID = rs.getInt("tID");
            } else {
                throw new SQLException("No project found with the given ID");
            }

            // Calculate the new total_Exp and baki
            BigDecimal newTotalExp = currentTotalExp.add(total_Exp);
            BigDecimal baki = total_All.subtract(newTotalExp);

            // Update the total_Exp and baki values
            String updateSql = "UPDATE project SET total_Exp = ?, baki = ? WHERE id = ?";
            pstmt = connection.prepareStatement(updateSql);
            pstmt.setBigDecimal(1, newTotalExp);
            pstmt.setBigDecimal(2, baki);
            pstmt.setInt(3, id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Project updated successfully.");

                // Ensure that the tID is not null
                if (tID != null) {
                    // Update the Tabung balance based on the total expenses
                    ProjectDAO projectDAO = new ProjectDAO(); // Ensure this is initialized properly
                    TabungDAO tabungDAO = new TabungDAO();   // Ensure this is initialized properly

                    // Calculate new total balance for tabung
                    BigDecimal newTotalBalance = projectDAO.calculateTotalExpByTabung(tID);

                    // Update the balance in tabung
                    tabungDAO.updateBalance(tID);
                    System.out.println("Tabung balance updated successfully.");
                } else {
                    System.out.println("Error: tID is null, unable to update Tabung balance.");
                }
            } else {
                System.out.println("Failed to update project.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating project or tabung balance", e);
        }
    }

    private void updateTotalExp(Integer id, BigDecimal total_Exp) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = DbCon.getConnection();

            // First, retrieve the current total_All value from the project table
            String selectSql = "SELECT tID, total_All, total_Exp FROM project WHERE id = ?";
            pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            BigDecimal total_All = BigDecimal.ZERO;
            BigDecimal currentTotalExp = BigDecimal.ZERO;
            Integer tID = null;

            if (rs.next()) {
                total_All = rs.getBigDecimal("total_All");
                currentTotalExp = rs.getBigDecimal("total_Exp") != null ? rs.getBigDecimal("total_Exp") : BigDecimal.ZERO;
                tID = rs.getInt("tID");
            } else {
                throw new SQLException("No project found with the given ID");
            }

            // Calculate the new total_Exp and baki
            BigDecimal newTotalExp = total_Exp;
            BigDecimal baki = total_All.subtract(newTotalExp);

            // Update the total_Exp and baki values
            String updateSql = "UPDATE project SET total_Exp = ?, baki = ? WHERE id = ?";
            pstmt = connection.prepareStatement(updateSql);
            pstmt.setBigDecimal(1, newTotalExp);
            pstmt.setBigDecimal(2, baki);
            pstmt.setInt(3, id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Project updated successfully.");

                // Ensure that the tID is not null
                if (tID != null) {
                    // Update the Tabung balance based on the total expenses
                    ProjectDAO projectDAO = new ProjectDAO(); // Ensure this is initialized properly
                    TabungDAO tabungDAO = new TabungDAO();   // Ensure this is initialized properly

                    // Calculate new total balance for tabung
                    BigDecimal newTotalBalance = projectDAO.calculateTotalExpByTabung(tID);

                    // Update the balance in tabung
                    tabungDAO.updateBalance(tID);
                    System.out.println("Tabung balance updated successfully.");
                } else {
                    System.out.println("Error: tID is null, unable to update Tabung balance.");
                }
            } else {
                System.out.println("Failed to update project.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating project or tabung balance", e);
        }
    }

    private void editTotalExp(Integer id, BigDecimal newExp) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try {
            connection = DbCon.getConnection();

            // Recalculate the total_Exp by summing all expenses associated with the project ID
            String sumSql = "SELECT SUM(total_Exp) AS totalExp FROM expense WHERE id = ?";
            selectStmt = connection.prepareStatement(sumSql);
            selectStmt.setInt(1, id);
            rs = selectStmt.executeQuery();

            BigDecimal total_Exp = BigDecimal.ZERO;
            if (rs.next()) {
                total_Exp = rs.getBigDecimal("totalExp");
                if (total_Exp == null) {
                    total_Exp = BigDecimal.ZERO;
                }
            }

            // Fetch the current total_All and tID from the project table
            String selectProjectSql = "SELECT tID, total_All FROM project WHERE id = ?";
            selectStmt = connection.prepareStatement(selectProjectSql);
            selectStmt.setInt(1, id);
            rs = selectStmt.executeQuery();

            BigDecimal total_All = BigDecimal.ZERO;
            Integer tID = null;

            if (rs.next()) {
                total_All = rs.getBigDecimal("total_All");
                tID = rs.getInt("tID");
            } else {
                throw new SQLException("No project found with the given ID");
            }

            // Calculate the baki
            BigDecimal baki = total_All.subtract(total_Exp);

            // Update the total_Exp and baki in the project table
            String updateSql = "UPDATE project SET total_Exp = ?, baki = ? WHERE id = ?";
            updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setBigDecimal(1, total_Exp);
            updateStmt.setBigDecimal(2, baki);
            updateStmt.setInt(3, id);

            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Project updated successfully.");

                // Ensure that the tID is not null
                if (tID != null) {
                    // Update the Tabung balance based on the updated total_Exp
                    TabungDAO tabungDAO = new TabungDAO();   // Ensure this is initialized properly

                    // Update the balance in tabung
                    tabungDAO.updateBalance(tID);
                    System.out.println("Tabung balance updated successfully.");
                } else {
                    System.out.println("Error: tID is null, unable to update Tabung balance.");
                }
            } else {
                System.out.println("Failed to update project.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating project or tabung balance", e);
        }
    }

    private void retrieveExp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");
        System.out.println("uID: " + uID);
        if (uID == null) {
            System.err.println("User not found in session.");
            response.sendRedirect("signIn.jsp");
            return;
        }

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

        // Retrieve all expenses related to the user and project
        IncomeDAO incDAO = new IncomeDAO();
        List<Income> ExpList = incDAO.selectAllExpense(uID, id);
        request.setAttribute("listofExp", ExpList);

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

        // Retrieve and set the list of votes (if applicable)
        try {
            // Pass the uID and id to the getVotes method to get all votes
            List<Income> vtList = getVotes(uID, id);

            // Remove duplicates using getUniqueVotes method
            List<Income> uniqueVotes = getUniqueVotes(vtList);

            // Set the filtered unique votes list as an attribute for the request
            request.setAttribute("vtList", uniqueVotes);

            // Print the size of the uniqueVotes list to the console for debugging
            System.out.println("Unique votes retrieved: " + (uniqueVotes != null ? uniqueVotes.size() : 0));

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid parameters.");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ExpenseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Forward the request to the JSP page for display
        request.getRequestDispatcher("/Expense.jsp").forward(request, response);
    }

    private void retrieveExpFull(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");
        System.out.println("uID: " + uID);
         if (uID == null) {
            System.err.println("User not found in session.");
            response.sendRedirect("signIn.jsp");
            return;
        }

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

        // Retrieve all expenses related to the user and project
        List<Expense> ExpList = expDAO.selectAllExps(id);
        request.setAttribute("listofExp", ExpList);

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
        request.getRequestDispatcher("/ExpFull.jsp").forward(request, response);
    }

    private void generateReport(HttpServletRequest request, HttpServletResponse response)
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
                request.setAttribute("errorMessage", "Invalid ID format.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
        } else {
            System.out.println("id parameter is missing or empty");
            request.setAttribute("errorMessage", "ID is required.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Retrieve the project details if ID is present
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> projects = projectDAO.selectAllPro(id);  // Adjust based on method return type

        Project project = null;
        if (projects != null && !projects.isEmpty()) {
            project = projects.get(0);  // Only use the first one
            request.setAttribute("project", project);
        } else {
            request.setAttribute("project", null);
        }

        // Retrieve all expenses related to the user and project
        List<Expense> report = expDAO.selectAllExpenses(id);
        BigDecimal totalExpense = expDAO.getTotalExpense(id);

        // Set up the response to be a PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=expense_report.pdf");

        try {
            // Initialize PDF writer and document
            PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            pdfDocument.setDefaultPageSize(PageSize.A4);

            // Set the title of the report
            document.add(new Paragraph("Laporan Perbelanjaan")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph(" "));  // Add a space

            // Add project details
            if (project != null) {
                document.add(new Paragraph("Ketua Projek: " + project.getUsername())
                        .setFontSize(12));
                document.add(new Paragraph("Nama Projek: " + project.getPname())
                        .setFontSize(12));
                document.add(new Paragraph("Peruntukan (RM): " + project.getTotal_All()
                        + "    Perbelanjaan (RM): " + project.getTotal_Exp()
                        + "    Baki Semasa (RM): " + project.getBaki())
                        .setFontSize(12));
            } else {
                document.add(new Paragraph("Project details not available").setFontSize(12));
            }

            document.add(new Paragraph(" "));  // Add another space for separation

            // Retrieve unique vote categories from the report (expenses list)
            Set<String> voteCategories = report.stream()
                    .map(Expense::getNoVote) // Extract noVote from each expense
                    .collect(Collectors.toSet());  // Collect unique noVotes into a Set

            VoteDAO voteDAO = new VoteDAO();  // Initialize your VoteDAO

// Iterate over each unique vote category
            for (String voteCategory : voteCategories) {
                // Fetch the vote description using voteCategory
                Vote vote = voteDAO.getVoteByNo(voteCategory);  // Get the Vote object from the DAO

                // Add category heading with vote description
                if (vote != null) {
                    document.add(new Paragraph(voteCategory + " - " + vote.getVote())
                            .setBold().setFontSize(14));
                } else {
                    // Handle the case where the vote could not be found
                    document.add(new Paragraph(voteCategory + " - " + "Unknown Vote")
                            .setBold().setFontSize(14));
                }

                // (Rest of your code for generating the PDF)
                // Start a new table for this category
                float[] columnWidths = {1, 4, 2, 3, 2};
                Table table = new Table(UnitValue.createPercentArray(columnWidths));
                table.setWidth(UnitValue.createPercentValue(100));

                // Add table headers
                table.addHeaderCell(createCell("Bil", true));
                table.addHeaderCell(createCell("Tuntutan Pembekal", true));
                table.addHeaderCell(createCell("Tarikh", true));
                table.addHeaderCell(createCell("NO PO/Baucer", true));
                table.addHeaderCell(createCell("Jumlah (RM)", true));

                // Iterate through expenses for this vote category
                int count = 1;
                BigDecimal categoryTotal = BigDecimal.ZERO;  // Sum for current category
                for (Expense expense : report) {
                    if (expense.getNoVote().equals(voteCategory)) {  // Filter by specific vote category
                        table.addCell(createCell(String.valueOf(count++), false));  // Bil
                        table.addCell(createCell(expense.getClaim(), false));  // Tuntutan Pembekal
                        table.addCell(createCell(expense.getEdate().toString(), false));  // Tarikh
                        table.addCell(createCell(expense.getNoPO(), false));  // NO PO/Baucer
                        table.addCell(createCell(String.valueOf(expense.getTotal_Exp()), false));  // Jumlah (RM)
                        categoryTotal = categoryTotal.add(expense.getTotal_Exp());  // Sum category total
                    }
                }

                // Add the table to the document
                document.add(table);

                // Add total for the category
                document.add(new Paragraph("Jumlah: " + categoryTotal.toString())
                        .setBold()
                        .setTextAlignment(TextAlignment.RIGHT));

                // Add space between categories
                document.add(new Paragraph(" "));
            }

            // Close the document
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// Helper method to create a table cell with styling
    private Cell createCell(String content, boolean isHeader) {
        Cell cell = new Cell();
        cell.setTextAlignment(TextAlignment.CENTER);
        if (isHeader) {
            cell.setBold();
            cell.setBackgroundColor(new DeviceRgb(200, 200, 200)); // Light gray background for header
        }
        cell.add(new Paragraph(content));
        return cell;
    }

    private void updateExp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
        try {
            HttpSession session = request.getSession();
            Integer uID = (Integer) session.getAttribute("uID");

            if (uID == null) {
                throw new ServletException("No user session found. Please log in again.");
            }

            // Log user ID for debugging
            System.out.println("uID from session: " + uID);

            userDAO usrDAO = new userDAO(DbCon.getConnection());
            User user = usrDAO.selectUserById(uID);

            if (user == null) {
                throw new ServletException("User not found for the given uID: " + uID);
            }

            // Retrieve and parse parameters
            String idStr = request.getParameter("id");
            String eIDStr = request.getParameter("eID");
            String vIDStr = request.getParameter("vID");
            String claim = request.getParameter("claim");
            String totalExpStr = request.getParameter("total_Exp");
            String eDateStr = request.getParameter("edate");

            int id;
            int eID;
            int vID; // Added vID variable
            BigDecimal total_Exp;
            java.sql.Date edate;

            try {
                id = Integer.parseInt(idStr);
                eID = Integer.parseInt(eIDStr);
                vID = Integer.parseInt(vIDStr);  // Parse vID
                total_Exp = BigDecimal.valueOf(Double.parseDouble(totalExpStr));

                // Parse and format date
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = formatter.parse(eDateStr);
                edate = new java.sql.Date(parsedDate.getTime());
            } catch (NumberFormatException e) {
                throw new ServletException("Invalid number format for ID or total_Exp parameters.", e);
            } catch (ParseException e) {
                throw new ServletException("Invalid date format for edate parameter.", e);
            }

            // Create or update the Expense object
            Expense expense = new Expense(eID, id, vID, claim, total_Exp, edate);  // Ensure Expense class has these fields
            System.out.println("Expense object before update: eID=" + expense.geteID() + ", id=" + expense.getId()
                    + ", vID=" + expense.getvID() // Log vID
                    + ", claim=" + expense.getClaim() // Log claim
                    + ", total_Exp=" + expense.getTotal_Exp()
                    + ", edate=" + expense.getEdate());

            boolean isUpdated = expDAO.updateExp(expense);  // Update the method in DAO to handle these fields

            if (!isUpdated) {
                throw new SQLException("Failed to update the expense record.");
            }

            // Fetch the total_All from the income table for validation
            IncomeDAO incomeDAO = new IncomeDAO();
            Income income = incomeDAO.getIncomeByVoteID(id, vID);
            // Handle null income case
            if (income == null) {
                request.setAttribute("bugMessage", "No income found for the given Vote ID.");
                request.getRequestDispatcher("/ExpenseServlet?action4=newExp&id=" + id).forward(request, response);
                return;
            }
            BigDecimal total_All = income.getTotal_All();

            // Check if total_Exp exceeds total_All
            if (total_Exp.compareTo(total_All) > 0) {
                // Handle the error scenario if total_Exp is greater than total_All
                request.setAttribute("errorMessage", "Total expenditure cannot exceed the available total.");
                request.getRequestDispatcher("/ExpenseServlet?action4=newExp&id=" + id).forward(request, response);

                return;
            }

            // Fetch the updated total expense for the project
            BigDecimal newTotalExp = expDAO.calculateTotalExpForProject(id);

            // Update the total_Exp in the project table
            editTotalExp(id, total_Exp);

            // Redirect to the appropriate page after successful update
            request.getSession().setAttribute("updateExp","EditExp");
            response.sendRedirect(request.getContextPath() + "/ExpenseServlet?action4=listExpFull&id=" + id);

        } catch (SQLException ex) {
            Logger.getLogger(ExpenseServlet.class
                    .getName()).log(Level.SEVERE, "SQL error occurred", ex);
            throw new ServletException("SQL error occurred during expense update.", ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ExpenseServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Error processing update due to class not found.", ex);
        }
    }

    private void deleteExp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {

        try {
            // Retrieve the expense ID and project ID from request parameters
            int eID = Integer.parseInt(request.getParameter("eID"));
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("eID: " + eID);
            System.out.println("Project ID: " + id);

            // Delete the expense using DAO
            expDAO.deleteExp(eID);

            // Fetch the updated total expense for the project
            BigDecimal newTotalExp = expDAO.calculateTotalExpForProject(id);

            // Update the project table with the new total expense
            updateTotalExp(id, newTotalExp);

            // Redirect to the list of expenses
             request.getSession().setAttribute("deleteExp","Remove");
            response.sendRedirect(request.getContextPath() + "/ExpenseServlet?action4=listExpFull&id=" + id);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");

        } catch (SQLException e) {
            Logger.getLogger(ExpenseServlet.class
                    .getName()).log(Level.SEVERE, "SQL error occurred", e);
            throw new ServletException("SQL error occurred during expense deletion.", e);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
