<%-- 
    Document   : Lead
    Created on : 15 Aug 2024, 11:50:47 am
    Author     : NAJIHAH
--%>

<%@page import="com.dao.userDAO"%>
<%@page import="com.db.DbCon"%>
<%@page import="com.model.User"%>
<%@page import="java.util.*"%>
<%@page import="com.connection.LeadServlet"%>
<%@page import="com.dao.*"%>
<%@page import="com.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    User user = (User) request.getSession().getAttribute("user");
if (user != null) {
    request.setAttribute("user", user);
}
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Tabung INOS</title>
        <meta
            content="width=device-width, initial-scale=1.0, shrink-to-fit=no"
            name="viewport"
            />
        <link
            rel="icon"
            href="assets/img/kaiadmin/favicon.ico"
            type="image/x-icon"
            />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <!-- Fonts and icons -->
        <script src="assets/js/plugin/webfont/webfont.min.js"></script>
        <script>
            WebFont.load({
                google: {families: ["Public Sans:300,400,500,600,700"]},
                custom: {
                    families: [
                        "Font Awesome 5 Solid",
                        "Font Awesome 5 Regular",
                        "Font Awesome 5 Brands",
                        "simple-line-icons",
                    ],
                    urls: ["assets/css/fonts.min.css"],
                },
                active: function () {
                    sessionStorage.fonts = true;
                },
            });
        </script>

        <!-- CSS Files -->
        <link rel="stylesheet" href="css/bootstrap.min.css" />
        <link rel="stylesheet" href="css/plugins.min.css" />
        <link rel="stylesheet" href="css/kaiadmin.min.css" />
        <style>
            .sidebar-wrapper {
                background-image: url('img/bg.jpeg');
                background-size: cover;
                background-position: center;
                opacity: 0.9; /* Adjust the opacity as needed */
            }

        </style>

    </head>
    <body>
        <div class="wrapper">
            <!-- Sidebar -->
            <div class="sidebar" data-background-color="dark">
                <div class="sidebar-logo">
                    <!-- Logo Header -->
                    <div class="logo-header" data-background-color="dark">
                        <a href="<%=request.getContextPath()%>/AdminServlet?path1=Home" class="logo">
                            <img
                                src="img/Logo.png"
                                alt="navbar brand"
                                class="navbar-brand"
                                height="70"
                                />
                        </a>
                        <div class="nav-toggle">
                            <button class="btn btn-toggle toggle-sidebar">
                                <i class="gg-menu-right"></i>
                            </button>
                            <button class="btn btn-toggle sidenav-toggler">
                                <i class="gg-menu-left"></i>
                            </button>
                        </div>
                        <button class="topbar-toggler more">
                            <i class="gg-more-vertical-alt"></i>
                        </button>
                    </div>
                    <!-- End Logo Header -->
                </div>
                <div class="sidebar-wrapper scrollbar scrollbar-inner">
                    <div class="sidebar-content">
                        <ul class="nav nav-secondary">
                            <li class="nav-item">
                                <a

                                    href="<%=request.getContextPath()%>/AdminServlet?path1=Home"
                                    class="collapsed"
                                    aria-expanded="false"
                                    >
                                    <i class="fas fa-home"></i>
                                    <p>Dashboard</p>
                                    <span class="active"></span>
                                </a>

                            </li>
                            <li class="nav-item">
                                <a href="<%=request.getContextPath()%>/AdminServlet?path1=listProH" >
                                    <i class="fas fa-tasks"></i>
                                    <p>Projek (HiCOE)</p>

                                </a>
                            </li>
                            <li class="nav-item">
                                <a href="<%=request.getContextPath()%>/AdminServlet?path1=listPro" >
                                    <i class="fas fa-pen-square"></i>
                                    <p>Projek</p>

                                </a>
                            </li>
                            <li class="nav-item">
                                <a data-bs-toggle="collapse" href="#tables">
                                    <i class="fas fa-money-bill-wave"></i>
                                    <p>Kewangan</p>
                                    <span class="caret"></span>
                                </a>
                                <div class="collapse" id="tables">
                                    <ul class="nav nav-collapse">
                                        <li>
                                            <a href="<%=request.getContextPath()%>/AdminServlet?path1=listProInc" >
                                                <span class="sub-item">Peruntukan</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="<%=request.getContextPath()%>/AdminServlet?path1=listProE" >
                                                <span class="sub-item">Perbelanjaan</span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                            <li class="nav-item">
                                <a href="<%=request.getContextPath()%>/AdminServlet?path1=listReport" >
                                    <i class="fas fa-chart-bar"></i>
                                    <p>Laporan</p>

                                </a>
                            </li>
                            <li class="nav-item active submenu">
                                <a data-bs-toggle="collapse" href="#maps">
                                    <i class="fas fa-gear"></i>
                                    <p>SetUp</p>
                                    <span class="caret"></span>
                                </a>
                                <div class="collapse" id="maps">
                                    <ul class="nav nav-collapse">
                                        <li>
                                            <a href="<%=request.getContextPath()%>/LeadServlet?action1=listLp">
                                                <span class="sub-item">User</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="<%=request.getContextPath()%>/VoteServlet?action5=listvt" >
                                                <span class="sub-item">Malumat Utama</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href ="<%=request.getContextPath()%>/TabungServlet?action2=listTbg" >
                                                <span class="sub-item">Tabung</span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </li>  
                            <li class="nav-section">
                                <span class="sidebar-mini-icon">
                                    <i class="fa fa-ellipsis-h"></i>
                                </span>
                                <h4 class="text-section">Setting</h4>
                            </li>
                            <li class="nav-item">
                                <%
                                                                                                                                                   if (user != null) {
                                %>
                                <a href="Logout">
                                    <i class="fas fa-sign-out-alt" style="color: red;"></i>
                                    <p>Logout</p>
                                </a>

                                <%
                                                  } else {
                                %>
                                <a href="signIn.jsp"><i class="fas fa-sign-in-alt" style="color:greenyellow;"></i>
                                    <p>LogIn</p>
                                </a>
                                <%
                                                  }
                                %>

                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- End Sidebar -->

            <div class="main-panel">
                <div class="main-header">
                    <div class="main-header-logo">
                        <!-- Logo Header -->
                        <div class="logo-header" data-background-color="dark">
                            <a href="index.html" class="logo">

                            </a>
                            <div class="nav-toggle">
                                <button class="btn btn-toggle toggle-sidebar">
                                    <i class="gg-menu-right"></i>
                                </button>
                                <button class="btn btn-toggle sidenav-toggler">
                                    <i class="gg-menu-left"></i>
                                </button>
                            </div>
                            <button class="topbar-toggler more">
                                <i class="gg-more-vertical-alt"></i>
                            </button>
                        </div>
                        <!-- End Logo Header -->
                    </div>
                    <!-- Navbar Header -->
                    <nav
                        class="navbar navbar-header navbar-header-transparent navbar-expand-lg border-bottom"
                        >
                        <div class="container-fluid">
                            <nav
                                class="navbar navbar-header-left navbar-expand-lg navbar-form nav-search p-0 d-none d-lg-flex"
                                >
                                <div class="input-group">
                                    <div class="input-group-prepend">

                                    </div>

                                </div>
                            </nav>

                            <ul class="navbar-nav topbar-nav ms-md-auto align-items-center">

                                <li class="nav-item topbar-user dropdown hidden-caret">

                                    <span class="op-7">Hi,</span>
                                    <span class="fw-bold"><%
                                                // Retrieve the user object from the session
                                                HttpSession userSession = request.getSession();
                                                User loggedInUser = (User) userSession.getAttribute("user");
            

                                                if (loggedInUser != null) {
                                                    out.print( loggedInUser.getUsername());
                                                } else {
                                                    out.print("Guest");
                                                }
                                        %></span>



                                </li>
                            </ul>
                        </div>
                    </nav>
                    <!-- End Navbar -->
                </div>

                <div class="container">
                    <div class="page-inner">
                        <div class="page-header">
                            <h3 class="fw-bold mb-3">User</h3>
                            <ul class="breadcrumbs mb-3">
                                <li class="nav-home">
                                    <a href="<%=request.getContextPath()%>/AdminServlet?path1=Home">
                                        <i class="fas fa-home"></i>
                                    </a>
                                </li>
                                <li class="separator">
                                    <i class="fas fa-arrow-right"></i>
                                </li>
                                <li class="nav-item">
                                    <a href="<%=request.getContextPath()%>/LeadServlet?action1=listLp">User</a>
                                </li>
                            </ul>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="card-title">Register Admin</div>
                                    </div>
                                    <form action="LeadServlet" method="post">
                                        <!-- Hidden action to specify the update operation -->
                                        <input type="hidden" name="action1" value="updateLp">

                                        <div class="card-body">
                                            <div class="row">
                                                <!-- Dropdown to select the user -->
                                                <div class="form-group">
                                                    <label for="defaultSelect">User</label>
                                                    <select id="userSelect" class="form-select form-control" name="uID" style="width:45rem;" onchange="fetchRolesByUser()">
                                                        <c:forEach var="user" items="${listofUser}">
                                                            <option value="${user.uID}">${user.username}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <!-- Role Select -->
                                                <div class="col-md-6 col-lg-4">
                                                    <div class="form-group">
                                                        <label for="selectRole">Role</label>
                                                        <select class="form-select" id="selectRole" name="role"></select>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>

                                        <div class="card-action">
                                            <button class="btn btn-success" type="submit">Save</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex align-items-center">
                                            <h4 class="card-title">User Information</h4>

                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table
                                                id="basic-datatables"
                                                class="display table table-striped table-hover"
                                                >
                                                <thead>
                                                    <tr>
                                                        <th>Name</th>
                                                        <th>Role</th>

                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="user" items="${listLead}">
                                                        <tr>
                                                            <td><c:out value="${user.username}"/></td>  
                                                            <td><c:out value="${user.role}"/></td> 
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Popup Box for Messages -->
                    <div id="popupBox" style="display: none;">
                        <div id="popupMessage"></div>
                        <button id="okButton">OK</button>
                    </div>
                </div>

                <footer class="footer">
                    <div class="container-fluid d-flex justify-content-between">
                        <nav class="pull-left">
                            <ul class="nav">
                                <li class="nav-item">

                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#"> Help </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#"> Licenses </a>
                                </li>
                            </ul>
                        </nav>
                        <div class="copyright">
                            I-Tabung
                        </div>
                        <div>

                        </div>
                    </div>
                </footer>
            </div>

            <!-- End Custom template -->
        </div>
        <!--   Core JS Files   -->
        <script src="js/jquery-3.7.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>

        <!-- jQuery Scrollbar -->
        <script src="js/jquery-scrollbar/jquery.scrollbar.min.js"></script>

        <!-- Chart JS -->
        <script src="js/chart.min.js"></script>

        <!-- jQuery Sparkline -->
        <script src="assets/js/plugin/jquery.sparkline/jquery.sparkline.min.js"></script>

        <!-- Chart Circle -->
        <script src="js/circles.min.js"></script>

        <!-- Datatables -->
        <script src="js/datatables.min.js"></script>

        <!-- Kaiadmin JS -->
        <script src="js/kaiadmin.min.js"></script>
        <script>
                                                         $('#basic-datatables').DataTable({
                                                             "pageLength": 10,
                                                             "language": {
                                                                 "paginate": {
                                                                     "previous": "Previous",
                                                                     "next": "Next"
                                                                 }
                                                             },
                                                             "order": [],
                                                             "columnDefs": [{
                                                                     "orderable": false, "targets": [4]   // Disable sorting on Status column
                                                                 }]
                                                         });

        </script>
        <script>
            function fetchRolesByUser() {
                var selectedUID = document.getElementById("userSelect").value;

                // Log selected UID for debugging
                console.log("Selected UID: ", selectedUID);

                // Fetch role data
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "LeadServlet?action1=fetchPosition&uID=" + selectedUID, true);
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        document.getElementById("selectRole").innerHTML = xhr.responseText; // Populate role options
                    } else if (xhr.readyState === 4) {
                        console.error("Failed to fetch role data: ", xhr.responseText);
                    }
                };
                xhr.send();
            }

// Attach event listener to the user select element
            document.getElementById('userSelect').addEventListener('change', fetchRolesByUser);

// Optional: Fetch roles for the initial user when the page loads
            window.onload = function () {
                fetchRolesByUser();
            };

        </script>
        <script>
            const container = document.getElementById('container');
            const popupBox = document.getElementById('popupBox');
            const okButton = document.getElementById('okButton');

            document.addEventListener('DOMContentLoaded', function () {
                // Check for any error messages passed in the URL
                const params = new URLSearchParams(window.location.search);
                const error = params.get('error');

                if (error) {
                    const popupMessage = document.getElementById('popupMessage');
                    popupMessage.textContent = 'Please Sign In'; // Set the message to "Please Sign In"
                    popupBox.style.display = 'block';
                }

                // Check for success or error messages from the session

                const updateRole = '<%= session.getAttribute("updateRole") != null ? session.getAttribute("updateRole") : "" %>';
                const loginError = '<%= session.getAttribute("loginError") != null ? session.getAttribute("loginError") : "" %>';

                if (updateRole === "updateSuccess") {
                    alert('Successfully Updated Role!');
            <% session.removeAttribute("updateRole"); %>
                } else if (loginError) {
                    alert(loginError);
            <% session.removeAttribute("loginError"); %>
                }

                // Close the popup message when OK button is clicked
                okButton.addEventListener('click', () => {
                    popupBox.style.display = 'none';
                });
            });
        </script>

    </body>
</html>


</body>
</html>
</body>
</html>
