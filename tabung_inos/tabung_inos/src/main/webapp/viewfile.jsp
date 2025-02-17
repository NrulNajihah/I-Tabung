<%-- 
    Document   : viewfile
    Created on : 19 Sep 2024, 9:46:40 am
    Author     : NAJIHAH
--%>

<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="jakarta.servlet.http.*" %>

<%
    int eID = Integer.parseInt(request.getParameter("eID"));
    Connection myConnection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    OutputStream outputStream = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String myURL = "jdbc:mysql://localhost:3306/tabung_inos";
        myConnection = DriverManager.getConnection(myURL, "root", "root");

        String myQuery = "SELECT receipt, receiptFilename FROM expense WHERE eID = ?";
        ps = myConnection.prepareStatement(myQuery);
        ps.setInt(1, eID);
        rs = ps.executeQuery();

        if (rs.next()) {
            byte[] fileData = rs.getBytes("receipt");
            String fileType = rs.getString("receiptFilename");

            response.setContentType(fileType);
            response.setContentLength(fileData.length);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + rs.getString("receiptFilename") + "\"");

            outputStream = response.getOutputStream();
            outputStream.write(fileData);
            outputStream.flush();
        } else {
            out.println("File not found for the given ID: " + eID);
        }
    } catch (ClassNotFoundException | SQLException | IOException e) {
        out.println("Error: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (myConnection != null) myConnection.close();
            if (outputStream != null) outputStream.close();
        } catch (SQLException | IOException e) {
            out.println("Error closing resources: " + e.getMessage());
        }
    }
%>
