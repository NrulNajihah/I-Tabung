<%-- 
    Document   : processRegister
    Created on : 9 Jan 2024, 1:35:05 am
    Author     : NAJIHAH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<% String username = request.getParameter("username"); %>
<% String email = request.getParameter("email"); %>
<% String password = request.getParameter("password"); %>
<% String phone= request.getParameter("phone"); %>
<% String role= request.getParameter("role"); %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Connection connection = null;
            PreparedStatement statement = null;
            
            try {
               Class.forName("com.mysql.cj.jdbc.Driver");
               String myURL = "jdbc:mysql://localhost:3306/tabung_inos";
               
               connection = DriverManager.getConnection(myURL, "root", "root");
               
               String sql = "INSERT INTO user (username, email, password, phone, role) VALUES(?,?,?,?,?)";
               statement = connection.prepareStatement(sql);
               statement.setString(1, username);
               statement.setString(2, email);
               statement.setString(3, password);
               statement.setString(4, phone);
               statement.setString(5, role);
               
               int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                request.getSession().setAttribute("registerSuccess", "true");
                  response.sendRedirect("signIn.jsp");
                } else {
                  out.println("Failed to register user.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
        %>
    </body>
</html>
