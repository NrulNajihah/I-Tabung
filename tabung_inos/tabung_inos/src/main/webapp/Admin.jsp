<%-- 
    Document   : Admin
    Created on : 22 Sep 2024, 10:29:07 am
    Author     : NAJIHAH
--%>

<%@page import="com.dao.*"%>
<%@page import="com.db.DbCon"%>
<%@page import="com.model.*"%>
<%@page import="java.util.*"%>
<%@page import="com.google.gson.Gson"%>
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
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-sparklines/2.1.2/jquery.sparkline.min.js"></script>

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
                            <li class="nav-item active submenu">
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
                            <li class="nav-item">
                                <a data-bs-toggle="collapse" href="#maps">
                                    <i class="fas fa-gear"></i>
                                    <p>SetUp</p>
                                    <span class="caret"></span>
                                </a>
                                <div class="collapse" id="maps">
                                    <ul class="nav nav-collapse">
                                        <li>
                                            <a href="<%=request.getContextPath()%>/LeadServlet?action1=editLp">
                                                <span class="sub-item">User</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="<%=request.getContextPath()%>/VoteServlet?action5=listvt" >
                                                <span class="sub-item">Maklumat Utama</span>
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
                        <div
                            class="d-flex align-items-left align-items-md-center flex-column flex-md-row pt-2 pb-4"
                            >
                            <div>
                                <h3 class="fw-bold mb-3">I-TABUNG</h3>
                                <h6 class="op-7 mb-2"></h6>
                            </div>
                            <div class="ms-md-auto py-2 py-md-0">


                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-8">
                                <div class="card card-round">
                                    <div class="card-header">
                                        <div class="card-head-row card-tools-still-right">
                                            <div class="card-title">Dashboard</div>
                                        </div>
                                    </div>
                                    <br>
                                    <!-- Create a row to align the two cards side-by-side -->
                                    <div class="row">
                                        <div class="col-md-5 ms-auto me-auto">
                                            <div class="card card-secondary">
                                                <div class="card-body skew-shadow">
                                                    <h1>
                                                        <c:forEach var="project" items="${listTotal}">
                                                            <c:out value="${project.numTbg}" />
                                                        </c:forEach>
                                                    </h1>
                                                    <h5 class="op-8">Jumlah Tabung</h5>
                                                    <div class="pull-right">
                                                        <h3 class="fw-bold op-8"><a href ="<%=request.getContextPath()%>/TabungServlet?action2=listTbg" title="Display Tabung List">
                                                                <i class="fas fa-money-bill-wave"></i></a></h3>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-5 ms-auto me-auto">
                                            <div class="card card-secondary">
                                                <div class="card-body skew-shadow">
                                                    <h1>
                                                        <c:forEach var="project" items="${listTotal}">
                                                            <c:out value="${project.numPro}" />
                                                        </c:forEach>
                                                    </h1>
                                                    <h5 class="op-8">Jumlah Projek</h5>
                                                    <div class="pull-right">
                                                        <h3 class="fw-bold op-8"><a href="<%=request.getContextPath()%>/AdminServlet?path1=listPro" title="Display Project List">
                                                        <i class="far fa-check-circle"></i></a></h3>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card card-round">
                                    <div class="card-header">
                                        <div class="card-head-row card-tools-still-right">
                                            <h4 class="card-title">Statistik Kewangan</h4>
                                        </div>
                                        <p class="card-category">
                                        <form method="get" action="<%=request.getContextPath()%>/ProjectServlet">
                                            <select id="tabungSelect" name="tID" class="form-select" style="width:200px;">
                                                <c:forEach var="tabung" items="${listTabung}">
                                                    <option value="${tabung.tID}" 
                                                            <c:if test="${selectedTID != null && selectedTID == tabung.tID}">selected</c:if>>
                                                        <c:out value="${tabung.noTb}"/> - <c:out value="${tabung.tname}"/>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </form>
                                        </p>
                                    </div>
                                    <div class="card-body">
                                        <div class="d-flex justify-content-center p-3">
                                            <div id="pieChart"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Project Status -->
                            <div class="col-12 col-md-8">
                                <div class="card card-round">
                                    <div class="card-header">
                                        <div class="card-head-row card-tools-still-right">
                                            <div class="card-title">Status Projek</div>
                                        </div>
                                    </div>
                                    <div class="card-body p-0">
                                        <div class="table-responsive">
                                            <!-- Projects table -->
                                            <table class="table align-items-center mb-0">
                                                <thead class="thead-light">
                                                    <tr>
                                                        <th scope="col">Nama Projek</th>
                                                        <th scope="col">Tarikh Mula</th>
                                                        <th scope="col">Tarikh Tamat</th>
                                                        <th scope="col">Status</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="tableBody">
                                                    <!-- Dynamic Project Rows will be inserted here -->
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
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
        <script src="js/jquery.sparkline.min.js"></script>

        <!-- Chart Circle -->
        <script src="js/circles.min.js"></script>

        <!-- Kaiadmin JS -->
        <script src="js/kaiadmin.min.js"></script>


        <script>
            function fetchProjectsByTabung() {
                var selectedTID = document.getElementById("tabungSelect").value;

                // Log selected TID for debugging
                console.log("Selected TID: ", selectedTID);

                // Fetch projects data
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "AdminServlet?path1=fetchProject&tID=" + selectedTID, true);
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4) {
                        console.log("Project response status: ", xhr.status);
                        if (xhr.status === 200) {
                            document.getElementById("tableBody").innerHTML = xhr.responseText;
                        } else {
                            console.error("Failed to fetch project data: ", xhr.responseText);
                        }
                    }
                };
                xhr.send();

                // Fetch chart data
                var xhrChart = new XMLHttpRequest();
                xhrChart.open("GET", "AdminServlet?path1=fetchCharts&tID=" + selectedTID, true);
                xhrChart.onreadystatechange = function () {
                    if (xhrChart.readyState === 4) {
                        console.log("Chart response status: ", xhrChart.status);
                        if (xhrChart.status === 200) {
                            var data = JSON.parse(xhrChart.responseText);

                            // Prepare the labels and dataset for the pie chart
                            var labels = Object.keys(data);
                            var totalAllValues = labels.map(label => data[label].total_All);
                            var totalExpValues = labels.map(label => data[label].total_Exp);
                            var bakiValues = labels.map(label => data[label].baki);

                            var tooltipContent = labels.map(function (label) {
                                var projectData = data[label];
                                return "<strong>" + label + "</strong><br>" +
                                        "Peruntukan: RM " + projectData.total_All.toFixed(2) + "<br>" +
                                        "Perbelanjaan: RM " + projectData.total_Exp.toFixed(2) + "<br>" +
                                        "Baki: RM " + projectData.baki.toFixed(2);
                            });

                            // Initialize the sparkline pie chart
                            $("#pieChart").sparkline(totalAllValues, {
                                type: "pie",
                                height: "250",
                                tooltipFormatter: function (sparkline, options, fields) {
                                    return tooltipContent[fields.offset];
                                },
                                sliceColors: ['#FF6384', '#36A2EB', '#FFCE56', '#FF9F40', '#4BC0C0']
                            });
                        } else {
                            console.error("Failed to fetch chart data: ", xhrChart.responseText);
                        }
                    }
                };
                xhrChart.send();
            }

// Attach event listener to the select element
            document.getElementById('tabungSelect').addEventListener('change', fetchProjectsByTabung);

// Initial load if necessary
            fetchProjectsByTabung();

        </script>

    </body>
</html>
