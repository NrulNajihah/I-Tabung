<%-- 
    Document   : FullExp
    Created on : 1 Oct 2024, 3:22:00 pm
    Author     : NAJIHAH
--%>

<%@page import="com.dao.LeadDAO"%>
<%@page import="com.db.DbCon"%>
<%@page import="com.model.Lead"%>
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
        <link rel="stylesheet" href="core/kaiadmin.min.css" />
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
                            <li class="nav-item active submenu">
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
                                            <a href="<%=request.getContextPath()%>/LeadServlet?action1=listLp">
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
                        <div class="page-header">
                            <h3 class="fw-bold mb-3">Perbelanjaan</h3>
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
                                    <a href="<%=request.getContextPath()%>/AdminServlet?path1=listProE">Senarai Projek(Perbelanjaan)</a>
                                </li>
                                <li class ="separator">
                                    <i class ="fas fa-arrow-right"></i>
                                </li>
                                <li class ="nav-item">
                                    <a href="<%=request.getContextPath()%>/ExpensesServlet?path4=newExp&id=${project.id}">Perbelanjaan</a>
                                </li>
                                <li class ="separator">
                                    <i class ="fas fa-arrow-right"></i>
                                </li>
                                <li class ="nav-item">
                                    <a href="<%=request.getContextPath()%>/ExpensesServlet?path4=listExpFull&id=${project.id}">Maklumat Lanjut</a>
                                </li>
                            </ul>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex align-items-center">
                                            <h4 class="card-title">Maklumat Perbelanjaan</h4>


                                        </div>
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="form-group">
                                                    <label for="projectSelect">Projek</label>
                                                    <input

                                                        id="projectSelect"
                                                        class="form-select form-control"
                                                        name="id"
                                                        value="<c:out value='${project.pname}'/>"
                                                        readonly
                                                        />
                                                </div>

                                                <div class="form-group">
                                                    <label for="total_All">Jumlah Perbelanjaan</label>
                                                    <input
                                                        id="total_Exp"
                                                        type="text"
                                                        class="form-control form-control-lg"
                                                        name="total_Exp"
                                                        value="<c:out value='${project.total_Exp}'/>"
                                                        style="width:30rem;"
                                                        readonly
                                                        />
                                                </div>
                                            </div>

                                            <div class="table-responsive">
                                                <table
                                                    id="basic-datatables"
                                                    class="display table table-striped table-hover"
                                                    >
                                                    <thead>
                                                        <tr>

                                                            <th>Maklumat</th>
                                                            <th>Tuntutan</th>
                                                            <th>Tarikh</th>
                                                            <th>No PO</th>
                                                            <th>Jumlah Perbelanjaan</th>
                                                            <th>Resit</th>
                                                            <th>Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="expense" items="${listofExp}">
                                                            <tr>

                                                                <td><c:out value="${expense.noVote}" /></td>
                                                                <td><c:out value="${expense.claim}" /></td>
                                                                <td><c:out value="${expense.edate}" /></td>
                                                                <td><c:out value="${expense.noPO}" /></td>
                                                                <td><c:out value="${expense.total_Exp}" /></td>
                                                                <td>
                                                                    <c:choose>
                                                                        <c:when test="${not empty expense.receiptFilename}">
                                                                            <a href="viewfile.jsp?eID=${expense.eID}"><button
                                                                                    type="button"
                                                                                    title="View File"
                                                                                    class="btn btn-icon btn-round btn-black"
                                                                                    >
                                                                                    <i class="fa fa-file"></i>
                                                                                </button></a>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                            No Receipt
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                <td><div class="form-button-action">
                                                                        <a href="<%=request.getContextPath()%>/ExpensesServlet?path4=editExp&eID=<c:out value='${expense.eID}'/>&id=${project.id}">
                                                                            <button
                                                                                type="button"
                                                                                title="Edit Task"
                                                                                class="btn btn-link btn-primary btn-lg"
                                                                                >
                                                                                <i class="fa fa-edit"></i>

                                                                            </button>
                                                                        </a>
                                                                        <a href="ExpensesServlet?path4=deleteExp&eID=<c:out value='${expense.eID}'/>&id=${project.id}">
                                                                            <button
                                                                                type="button"
                                                                                title="Remove"
                                                                                class="btn btn-link btn-danger"
                                                                                data-original-title="Remove"
                                                                                >
                                                                                <i class="fa fa-times"></i>
                                                                            </button>
                                                                        </a>
                                                                    </div>
                                                                </td>
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
<!--            <script>
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

            </script>-->
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
   
                    const updateExp = '<%= session.getAttribute("updateExp") != null ? session.getAttribute("updateExp") : "" %>';
                    const deleteExp = '<%= session.getAttribute("deleteExp") != null ? session.getAttribute("deleteExp") : "" %>';
                    const loginError = '<%= session.getAttribute("loginError") != null ? session.getAttribute("loginError") : "" %>';

                     if (updateExp === "EditExp") {
                        alert('Successfully Update Expense!');
                <% session.removeAttribute("updateExp"); %>
                    } else if (deleteExp === "Remove") {
                        alert('Successfully Remove Expense!');
                <% session.removeAttribute("deleteExp"); %>
                    } else if (loginError) {
                        alert("Please Sign In");
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


