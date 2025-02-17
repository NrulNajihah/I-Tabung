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
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.db.DbCon;
import com.google.gson.Gson;
import java.io.PrintWriter;
import static java.lang.Math.log;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;

/**
 *
 * @author NAJIHAH
 */
public class AdminServlet extends HttpServlet {

    private ProjectDAO projectDAO;

    public void init() {
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

        String path = request.getParameter("path1");
        String tIDStr = request.getParameter("tID");

        System.out.println("AdminServlet ==> path1 = " + path);

        try {
            switch (path) {
                case "newPro":
                    displayNewProForm(request, response);
                    break;
                case "editPro":
                    displayEditProForm(request, response);
                    break;
                case "editDate":
                    displayEditDateForm(request, response);
                    break;
                case "insertPro":
                    createPro(request, response);
                    break;
                case "insertProH":
                    createProH(request, response);
                    break;
                case "updatePro":
                    updatePro(request, response);
                    break;
                case "updateDate":
                    updateDate(request, response);
                    break;
                case "deletePro":
                    deletePro(request, response);
                    break;
                case "listPro":
                    retrievePro(request, response);
                    break;
                case "listProH":
                    retrieveProH(request, response);
                    break;
                case "listProE":
                    retrieveProE(request, response);
                    break;
                case "listProInc":
                    retrieveProI(request, response);
                    break;
                case "listProI":
                    retrieveProInfo(request, response);
                    break;
                 case "listExt":
                    retrieveExt(request, response);
                    break;
                 case "listReport":
                    retrieveReport(request, response);
                    break;
                case "Home":
                    Home(request, response);
                    break;
                case "fetchProject":
                    if (tIDStr != null) {
                        int tID = Integer.parseInt(tIDStr);
                        fetchProject(request, response, tID);
                    }
                    break;
                case "fetchCharts":
                    if (tIDStr != null) {
                        int tID = Integer.parseInt(tIDStr);
                        fetchCharts(request, response, tID);
                    }
                    break;
                default:
                    retrievePro(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ParseException ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayNewProForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       // Retrieve list of users
        try {
                List<User> usrList = getUser();
                request.setAttribute("listofUser", usrList);
                System.out.println("List of user retrieved: " + usrList.size());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to load votes.");
            }

        try {
            List<Tabung> tabungs = getAllTbgs();
            request.setAttribute("listTabung", tabungs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ServletContext sc = getServletContext();
        RequestDispatcher dispatcher = sc.getRequestDispatcher("/AdminServlet?path1=listPro");
        dispatcher.forward(request, response);
    }

    private void displayEditProForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        Integer uID = ((User) session.getAttribute("user")).getuID();

        // Retrieve list of users
        try {
                List<User> usrList = getUser();
                request.setAttribute("listofUser", usrList);
                System.out.println("List of user retrieved: " + usrList.size());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to load votes.");
            }

        // Retrieve list of tabungs
        try {
            List<Tabung> listTabung = getAllTbgs();
            request.setAttribute("listTabung", listTabung);
            System.out.println("Tabung retrieved: " + (listTabung != null ? listTabung.size() : 0));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            // Retrieve the current project details
            Project currentproject = projectDAO.selectProID(id);
            if (currentproject != null) {
                request.setAttribute("project", currentproject);
            } else {
                request.setAttribute("error", "project not found.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid project ID format.");
            response.sendRedirect("error.jsp");
            return;
        }

        ServletContext sc = getServletContext();
        // Forward the request to the JSP page that displays the edit form
        RequestDispatcher dispatcher = sc.getRequestDispatcher("/AdminServlet?path1=listPro");
        dispatcher.forward(request, response);
    }
    
    private void displayEditDateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        Integer uID = ((User) session.getAttribute("user")).getuID();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            // Retrieve the current project details
            Project currentproject = projectDAO.selectProID(id);
            if (currentproject != null) {
                request.setAttribute("project", currentproject);
            } else {
                request.setAttribute("error", "project not found.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid project ID format.");
            response.sendRedirect("error.jsp");
            return;
        }
        
         int id = Integer.parseInt(request.getParameter("id"));
            // Retrieve all incomes related to the user and project
            ExtendDAO extDAO = new ExtendDAO();
            List<Extend> ExtList = extDAO.selectAllExts(id);
            request.setAttribute("ExtList", ExtList);
            System.out.println("List of extend retrieved: " + ExtList.size());

        ServletContext sc = getServletContext();
        // Forward the request to the JSP page that displays the edit form
        RequestDispatcher dispatcher = sc.getRequestDispatcher("/ExtendDate.jsp");
        dispatcher.forward(request, response);
    }

    private void createPro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer uID = (Integer) session.getAttribute("uID");

            if (uID == null) {
                System.out.println("No user session found");
                throw new ServletException("User session is missing");
            }

            System.out.println("uID: " + uID);

            // Get project parameters from request
            String tIdStr = request.getParameter("tID");

            if (tIdStr == null) {
                throw new ServletException("kpID or tID parameter is missing");
            }

            Integer tID = Integer.parseInt(tIdStr);

            // Get additional project details from request
            String pname = request.getParameter("pname");
            String totalAllStr = request.getParameter("total_All");
            BigDecimal total_All = BigDecimal.valueOf(Double.parseDouble(totalAllStr));
            String totalExpStr = request.getParameter("total_Exp");
            BigDecimal total_Exp = BigDecimal.valueOf(Double.parseDouble(totalExpStr));

            // Calculate baki
            BigDecimal baki = total_All.subtract(total_Exp);

            // Parse dates
            String startPStr = request.getParameter("startP");
            String endPStr = request.getParameter("endP");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            java.util.Date parsedStartDate = formatter.parse(startPStr);
            java.sql.Date startP = new java.sql.Date(parsedStartDate.getTime());

            java.util.Date parsedEndDate = formatter.parse(endPStr);
            java.sql.Date endP = new java.sql.Date(parsedEndDate.getTime());

            // Create and insert the Project
            Project project = new Project(uID, pname, tID, total_All, total_Exp, baki, startP, endP);
            ProjectDAO projectDAO = new ProjectDAO();
            int id = projectDAO.insertProject(project); // Get the generated project ID

            // Log after project insert
            System.out.println("Project inserted with ID: " + id);

            // Step 2: Retrieve uID from leadproj using kpID
            MemberDAO memberDAO = new MemberDAO();

            // Step 3: Insert the lead member into the member table
            memberDAO.insertLead(uID, id); // Use the retrieved lead uID

            // Insert original end date
            ExtendDAO extendDAO = new ExtendDAO();
            extendDAO.insertExtend(id, endP);

            // Log to confirm the insertion
            System.out.println("Lead member inserted with uID: " + uID + ", project ID: " + id);

            // Redirect to project list
            request.getSession().setAttribute("insertProj", "insertSuccess");
            response.sendRedirect(request.getContextPath() + "/AdminServlet?path1=listPro");

        } catch (ParseException | SQLException ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Error processing project creation", ex);
        }
    }
    
    private void createProH(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer uID = (Integer) session.getAttribute("uID");

            if (uID == null) {
                System.out.println("No user session found");
                throw new ServletException("User session is missing");
            }

            System.out.println("uID: " + uID);

           // Get project parameters from request
            String cIdStr = request.getParameter("cID");
          System.out.println("Project inserted with ID: " + cIdStr);
            if (cIdStr == null) {
                throw new ServletException("kpID or cID parameter is missing");
            }

            Integer cID = Integer.parseInt(cIdStr);


            // Validate cID
            CompDAO componentDAO = new CompDAO();
            if (!componentDAO.isComponentExists(cID)) {
                throw new ServletException("Invalid cID: Component does not exist");
            }

            // Get additional project details from request
            String pname = request.getParameter("pname");
            String totalAllStr = request.getParameter("total_All");
            BigDecimal total_All = BigDecimal.valueOf(Double.parseDouble(totalAllStr));
            String totalExpStr = request.getParameter("total_Exp");
            BigDecimal total_Exp = BigDecimal.valueOf(Double.parseDouble(totalExpStr));

            // Calculate baki
            BigDecimal baki = total_All.subtract(total_Exp);

            // Parse dates
            String startPStr = request.getParameter("startP");
            String endPStr = request.getParameter("endP");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            java.util.Date parsedStartDate = formatter.parse(startPStr);
            java.sql.Date startP = new java.sql.Date(parsedStartDate.getTime());

            java.util.Date parsedEndDate = formatter.parse(endPStr);
            java.sql.Date endP = new java.sql.Date(parsedEndDate.getTime());

            // Create and insert the Project
            Project project = new Project(uID, pname, cID, total_All, total_Exp, baki, startP, endP);
            ProjectDAO projectDAO = new ProjectDAO();
            int id = projectDAO.insertProH(project); // Get the generated project ID

            // Log after project insert
            System.out.println("Project inserted with ID: " + id);

            // Step 2: Retrieve uID from leadproj using kpID
            MemberDAO memberDAO = new MemberDAO();

            // Step 3: Insert the lead member into the member table
            memberDAO.insertLead(uID, id); // Use the retrieved lead uID

            // Insert original end date
            ExtendDAO extendDAO = new ExtendDAO();
            extendDAO.insertExtend(id, endP);

            // Log to confirm the insertion
            System.out.println("Lead member inserted with uID: " + uID + ", project ID: " + id);

            // Redirect to project list
            request.getSession().setAttribute("insertProj", "insertSuccess");
            response.sendRedirect(request.getContextPath() + "/AdminServlet?path1=listProH");

        } catch (ParseException | SQLException ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Error processing project creation", ex);
        }
    }


     private List<User> getUser() throws SQLException, ClassNotFoundException {
    userDAO usrDAO = new userDAO(DbCon.getConnection()); // Get connection from DbCon
    return usrDAO.selectUser(); // Retrieve users by role
}

    private List<Tabung> getAllTbgs() throws SQLException, ClassNotFoundException {
        TabungDAO tbgDAO = new TabungDAO();
        return tbgDAO.selectAllListTbg();
    }
    
    private List<Tabung> getTbgs(int uID) throws SQLException, ClassNotFoundException {
        TabungDAO tbgDAO = new TabungDAO();
        return tbgDAO.selectAllTbgs(uID);
    }

    private List<Component> getAllComps() throws SQLException, ClassNotFoundException {
        CompDAO compDAO = new CompDAO();
        return compDAO.selectAllListCmp();
    }
    
    private void retrievePro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");
        System.out.println("uID: " + uID);
        
        // Retrieve list of users
        try {
                List<User> usrList = getUser();
                request.setAttribute("listofUser", usrList);
                System.out.println("List of user retrieved: " + usrList.size());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to load votes.");
            }

        try {
            List<Tabung> tabungs = getAllTbgs();
            request.setAttribute("listTabung", tabungs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<Project> listProject = projectDAO.selectAllProjects();
        Date currentDate = new Date(); // Current date for status determination

        for (Project project : listProject) {
            // Determine the status of each project based on the current date
            Date startP = project.getStartP();
            Date endP = project.getEndP();

            if (currentDate.before(startP) || currentDate.after(endP)) {
                project.setStatus("Inactive");
            } else {
                project.setStatus("Active");
            }
        }
        request.setAttribute("listProject", listProject);
        request.getRequestDispatcher("/ProjectAd.jsp").forward(request, response);
    }
    
    private void retrieveProH(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");
        System.out.println("uID: " + uID);
        
        // Retrieve list of users
        try {
                List<User> usrList = getUser();
                request.setAttribute("listofUser", usrList);
                System.out.println("List of user retrieved: " + usrList.size());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to load votes.");
            }
        
         try {
            List<Tabung> listTabung = getAllTbgs();
            request.setAttribute("listTabung", listTabung);
            System.out.println("Tabung retrieved: " + (listTabung != null ? listTabung.size() : 0));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            List<Component> components = getAllComps();
            request.setAttribute("listComp", components);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<Project> listProject = projectDAO.selectAllProjects();
        Date currentDate = new Date(); // Current date for status determination

        for (Project project : listProject) {
            // Determine the status of each project based on the current date
            Date startP = project.getStartP();
            Date endP = project.getEndP();

            if (currentDate.before(startP) || currentDate.after(endP)) {
                project.setStatus("Inactive");
            } else {
                project.setStatus("Active");
            }
        }
        request.setAttribute("listProject", listProject);
        request.getRequestDispatcher("/ProjHiCoe.jsp").forward(request, response);
    }

    private void retrieveProE(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");
        System.out.println("uID: " + uID);

        List<Project> listofProject = projectDAO.selectAllProjects();
        Date currentDate = new Date(); // Current date for status determination

        for (Project project : listofProject) {
            // Determine the status of each project based on the current date
            Date startP = project.getStartP();
            Date endP = project.getEndP();

            if (currentDate.before(startP) || currentDate.after(endP)) {
                project.setStatus("Inactive");
            } else {
                project.setStatus("Active");
            }
        }
        request.setAttribute("listofProject", listofProject);
        request.getRequestDispatcher("/ListProE.jsp").forward(request, response);

    }

    private void retrieveProI(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");
        System.out.println("uID: " + uID);

        List<Project> listofProject = projectDAO.selectAllProjects();
        Date currentDate = new Date(); // Current date for status determination

        for (Project project : listofProject) {
            // Determine the status of each project based on the current date
            Date startP = project.getStartP();
            Date endP = project.getEndP();

            if (currentDate.before(startP) || currentDate.after(endP)) {
                project.setStatus("Inactive");
            } else {
                project.setStatus("Active");
            }
        }
        request.setAttribute("listofProject", listofProject);
        request.getRequestDispatcher("/ListProI.jsp").forward(request, response);

    }

    private void retrieveProInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer usrID = (Integer) session.getAttribute("uID");

        if (usrID == null) {
            System.err.println("User ID not found in session.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not authorized.");
            return;
        }

        String proname = request.getParameter("pname");
        if (proname == null || proname.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project name is missing or empty.");
            return;
        }

        try {
            ProjectDAO projectDAO = new ProjectDAO();
            Project project = projectDAO.getProjectByName(usrID, proname);
            if (project != null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new Gson().toJson(project)); // Convert Project to JSON
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Project not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Class not found.");
        }
    }
    
    private void retrieveExt(HttpServletRequest request, HttpServletResponse response)
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

        // Retrieve all incomes related to the user and project
        ExtendDAO extDAO = new ExtendDAO();
        List<Extend> ExtList = extDAO.selectAllExts(id);
        request.setAttribute("ExtList", ExtList);

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
        request.getRequestDispatcher("/ExtendDate.jsp").forward(request, response);
    }
    
    private void retrieveReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException{

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");
        System.out.println("uID: " + uID);

        List<Project> listofProject = projectDAO.selectAllProjects();
        Date currentDate = new Date(); // Current date for status determination

        for (Project project : listofProject) {
            // Determine the status of each project based on the current date
            Date startP = project.getStartP();
            Date endP = project.getEndP();

            if (currentDate.before(startP) || currentDate.after(endP)) {
                project.setStatus("Inactive");
            } else {
                project.setStatus("Active");
            }
        }
        request.setAttribute("listofProject", listofProject);
        request.getRequestDispatcher("/ReportAd.jsp").forward(request, response);

    }
    

    private void Home(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer uID = (Integer) session.getAttribute("uID");

        if (uID == null) {
            response.sendRedirect("signIn.jsp");
            return;
        }

        try {
            List<Tabung> tabungs = getAllTbgs();
            request.setAttribute("listTabung", tabungs);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<Project> listTotal = projectDAO.selectTotals();
        request.setAttribute("listTotal", listTotal);

        // Forward to the JSP page
        request.getRequestDispatcher("/Admin.jsp").forward(request, response);
    }
    
    private void fetchProject(HttpServletRequest request, HttpServletResponse response, int tID)
        throws ServletException, IOException {
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("signIn.jsp"); // Redirect to login page if user is not logged in
        return;
    }

    List<Project> projectList = projectDAO.selectAllProjtID(tID);

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    Date currentDate = new Date();

    try {
        for (Project project : projectList) {
            Date startP = project.getStartP();
            Date endP = project.getEndP();

            // Determine project status based on date
            if (currentDate.before(startP) || currentDate.after(endP)) {
                project.setStatus("Inactive");
            } else {
                project.setStatus("Active");
            }

            // Generate status badge
            String statusBadgeClass = project.getStatus().equals("Active") ? "badge badge-success" : "badge badge-danger";

            // Output the project details
            out.println("<tr>");
            out.println("<td>" + project.getPname() + "</td>");
            out.println("<td>" + project.getStartP() + "</td>");
            out.println("<td>" + project.getEndP() + "</td>");
            out.println("<td><span class='" + statusBadgeClass + "'>" + project.getStatus() + "</span></td>");
            out.println("</tr>");
        }
    } finally {
        out.close(); // Ensure PrintWriter is closed properly
    }
}

    private void fetchCharts(HttpServletRequest request, HttpServletResponse response, int tID)
        throws ServletException, IOException {
    
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("signIn.jsp"); // Redirect to login if user is not logged in
        return;
    }

    List<Project> projectList = projectDAO.selectAllProC(tID);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    PrintWriter out = response.getWriter();

    // Map to store the project data
    Map<String, Map<String, BigDecimal>> data = new HashMap<>();

    try {
        for (Project project : projectList) {
            Map<String, BigDecimal> projectData = new HashMap<>();
            projectData.put("total_All", project.getTotal_All());
            projectData.put("total_Exp", project.getTotal_Exp());
            projectData.put("baki", project.getBaki());

            data.put(project.getPname(), projectData);
        }

        // Convert map to JSON
        Gson gson = new Gson();
        String json = gson.toJson(data);

        response.setStatus(HttpServletResponse.SC_OK); // Explicitly set success status
        out.print(json);

    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        out.print("{\"error\": \"Unable to fetch chart data.\"}");
    } finally {
        out.close(); // Ensure PrintWriter is closed properly
    }
}

    private void updatePro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
        try {
            HttpSession session = request.getSession();
            Integer uID = (Integer) session.getAttribute("uID");
            System.out.println("uID: " + uID);
            userDAO usrDAO = new userDAO(DbCon.getConnection());
            User user = usrDAO.selectUserById(uID);

            if (user != null) {
                int id = Integer.parseInt(request.getParameter("id"));

                
                String pname = request.getParameter("pname");
                String tIDStr = request.getParameter("tID");
                if (tIDStr == null) {
                    throw new ServletException("tID parameter is missing");
                }

                Integer tID = Integer.parseInt(tIDStr);

                String totalAllStr = request.getParameter("total_All");
                String totalExpStr = request.getParameter("total_Exp");
                String startPStr = request.getParameter("startP");
                String endPStr = request.getParameter("endP");

                if (pname == null || totalAllStr == null || totalExpStr == null || startPStr == null || endPStr == null) {
                    throw new ServletException("One or more required parameters are missing");
                }

                BigDecimal total_All = BigDecimal.valueOf(Double.parseDouble(totalAllStr));
                BigDecimal total_Exp = BigDecimal.valueOf(Double.parseDouble(totalExpStr));

                // Calculate baki
                BigDecimal baki = total_All.subtract(total_Exp);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                java.util.Date parsedStartDate = formatter.parse(startPStr);
                java.sql.Date startP = new java.sql.Date(parsedStartDate.getTime());

                java.util.Date parsedEndDate = formatter.parse(endPStr);
                java.sql.Date endP = new java.sql.Date(parsedEndDate.getTime());

                Project project = new Project(id, uID, pname, tID, total_All, total_Exp, baki, startP, endP);
                projectDAO.updatePro(project);

                request.getSession().setAttribute("updateProj", "updateSuccess");
                response.sendRedirect(request.getContextPath() + "/AdminServlet?path1=listPro");
            }
        } catch (ClassNotFoundException | ParseException ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Error processing update.", ex);
        }
    }
    
    private void updateDate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
        try {
            HttpSession session = request.getSession();
            Integer uID = (Integer) session.getAttribute("uID");
            System.out.println("uID: " + uID);
            userDAO usrDAO = new userDAO(DbCon.getConnection());
            User user = usrDAO.selectUserById(uID);

            if (user != null) {
                int id = Integer.parseInt(request.getParameter("id"));

                String endPStr = request.getParameter("endP");

                if (endPStr == null) {
                    throw new ServletException("One or more required parameters are missing");
                }

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                java.util.Date parsedEndDate = formatter.parse(endPStr);
                java.sql.Date endP = new java.sql.Date(parsedEndDate.getTime());

                Project project = new Project(id, uID, endP);
                projectDAO.updateDate(project);
                
                // Insert original end date
            ExtendDAO extendDAO = new ExtendDAO();
            extendDAO.insertExtend(id, endP);

            request.getSession().setAttribute("updateDated", "ExtendDate");
                response.sendRedirect(request.getContextPath() + "/AdminServlet?path1=listExt&id=" + id);
            }
        } catch (ClassNotFoundException | ParseException ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Error processing update.", ex);
        }
    }

    private void deletePro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("In id = " + id);

        // Delete the project
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = projectDAO.selectProID(id); // Fetch the project to get tID
        if (project == null) {
            throw new ServletException("Project not found with ID: " + id);
        }

        // Perform the deletion
        projectDAO.deletePro(id);

        // Update related tabung records
        TabungDAO tabungDAO = new TabungDAO();
        Integer tID = project.gettID(); // Get the tID from the project
        System.out.println("In tID = " + tID);

        // Calculate new totals and update Tabung
        BigDecimal newTotalAmount = projectDAO.calculateTotalAllByTabung(tID);
        tabungDAO.updateAmount(tID, newTotalAmount);

        BigDecimal newTotalBalance = projectDAO.calculateTotalExpByTabung(tID);
        tabungDAO.updateBalance(tID);

        // Redirect after successful deletion
        request.getSession().setAttribute("deleteProj", "deleteSuccess");
        response.sendRedirect(request.getContextPath() + "/AdminServlet?path1=listPro");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
