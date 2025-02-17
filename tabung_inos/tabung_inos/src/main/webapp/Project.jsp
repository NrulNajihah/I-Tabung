<%-- 
    Document   : Lead
    Created on : 15 Aug 2024, 11:50:47 am
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
                        <a href="<%=request.getContextPath()%>/ProjectServlet?action3=HomeStatus" class="logo">
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

                                    href="<%=request.getContextPath()%>/ProjectServlet?action3=HomeStatus"
                                    class="collapsed"
                                    aria-expanded="false"
                                    >
                                    <i class="fas fa-home"></i>
                                    <p>Dashboard</p>
                                    <span class="active"></span>
                                </a>

                            </li>
                             <li class="nav-item">
                                <a href="#" >
                                    <i class="fas fa-tasks"></i>
                                    <p>Projek (HiCOE)</p>

                                </a>
                            </li>
                            <li class="nav-item active submenu">
                                <a href="<%=request.getContextPath()%>/ProjectServlet?action3=listPro">
                                    <i class="fas fa-pen-square"></i>
                                    <p>Projek</p>
                                    <span class="active"></span>
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
                                            <a href="<%=request.getContextPath()%>/ProjectServlet?action3=listProInc" >
                                                <span class="sub-item">Funds</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="<%=request.getContextPath()%>/ProjectServlet?action3=listProE" >
                                                <span class="sub-item">Expenses</span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                            <li class="nav-item">
                                <a href="<%=request.getContextPath()%>/ProjectServlet?action3=listReport" >
                                    <i class="fas fa-chart-bar"></i>
                                    <p>Laporan</p>

                                </a>
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
                            <h3 class="fw-bold mb-3">Projek</h3>
                            <ul class="breadcrumbs mb-3">
                                <li class="nav-home">
                                    <a href="<%=request.getContextPath()%>/ProjectServlet?action3=HomeStatus">
                                        <i class="fas fa-home"></i>
                                    </a>
                                </li>
                                <li class="separator">
                                    <i class="fas fa-arrow-right"></i>
                                </li>
                                <li class="nav-item">
                                    <a href="<%=request.getContextPath()%>/ProjectServlet?action3=listPro">Projek</a>
                                </li>
                            </ul>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="card-title">Daftar Projek</div>
                                    </div>
                                    <c:if test="${project == null}">
                                        <form action="ProjectServlet" method="post">
                                            <input type="hidden" name="action3" value="insertPro">
                                        </c:if>
                                        <c:if test="${project != null}">
                                            <form action="ProjectServlet" method="post" >
                                                <input type="hidden" name="action3" value="updatePro">
                                                <input type="hidden" name="id" value="<c:out value='${project.id}' />"><!-- Hidden ID input field -->
                                            </c:if>
                                            <div class="card-body">

                                                <div class="row" >
                                                    <c:if test="${project != null}">
                                                        <input type="hidden" name="id" value="<c:out value='${project.id}'/>"/> 
                                                    </c:if>



                                                    <div class="form-group">
                                                        <label for="defaultSelect">Ketua Projek</label>
                                                        <select class="form-select form-control" name="uID" style="width:45rem;">
                                                            <c:forEach var="user" items="${listofUser}">
                                                                <option value="${user.uID}" <c:if test="${lead != null && user.uID == lead.uID}">selected</c:if>>
                                                                    ${user.username}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>   

                                                    <div class="form-group">
                                                        <label for="largeInput">Nama Projek</label>
                                                        <input
                                                            type="text"
                                                            class="form-control"
                                                            name="pname"
                                                            value="<c:out value='${project.pname}'/>"
                                                            placeholder="Fill project name"
                                                            style="width:50rem;"
                                                            />   
                                                    </div>
                                                    <div class="col-md-6 col-lg-4">
                                                        <div class="form-group">
                                                            <label for="defaultSelect">Tabung</label>
                                                            <select class="form-select form-control" name="tID">
                                                                <c:forEach var="tabung" items="${listTabung}">
                                                                    <option value="${tabung.tID}" <c:if test="${project != null && tabung.tID == project.tID}">selected</c:if>>
                                                                        <c:out value='${tabung.noTb}'/> - <c:out value='${tabung.tname}'/>
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>



                                                        <div class="form-group">
                                                            <label for="largeInput">Jumlah Peruntukan(RM)</label>
                                                            <input
                                                                type="text"
                                                                class="form-control form-control-lg"
                                                                name="total_All"
                                                                value="<c:out value='${project.total_All != null ? project.total_All : "0.00"}'/>"
                                                                placeholder="Fill allocation amount"
                                                                readonly
                                                                />
                                                            <label for="largeInput">Jumlah Perbelanjaan(RM)</label>
                                                            <input
                                                                type="text"
                                                                class="form-control form-control-lg"
                                                                name="total_Exp"
                                                                value="<c:out value='${project.total_Exp != null ? project.total_Exp : "0.00"}'/>"
                                                                readonly
                                                                />
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 col-lg-4">

                                                        <div class="form-group">
                                                            <label for="largeInput">Tarikh Mula</label>
                                                            <input
                                                                type="date"
                                                                class="form-control form-control-lg"
                                                                name="startP"
                                                                value="<c:out value='${project.startP}'/>"
                                                                placeholder="Fill start Project"
                                                                "
                                                                />   
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="largeInput">Tarikh Tamat</label>
                                                            <input
                                                                type="date"
                                                                class="form-control form-control-lg"
                                                                name="endP"
                                                                value="<c:out value='${project.endP}'/>"
                                                                placeholder="Fill project name"

                                                                />  
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="card-action">
                                                <button class="btn btn-success">Submit</button>
                                            </div>
                                        </form>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex align-items-center">
                                            <h4 class="card-title">Maklumat Projek</h4>

                                        </div>
                                    </div>
                                    <div class="card-body">

                                        <div class="table-responsive">
                                            <table
                                                id="basic-datatables"
                                                class="display table table-striped table-hover"
                                                >
                                                <thead>
                                                    <tr >
                                                        <th>Ketua Projek</th>
                                                        <th>Tabung</th>
                                                        <th>Nama Projek</th>
                                                        <th>Baki(RM)</th>
                                                        <th>Status</th>
                                                        <th style="display: flex; justify-content: center; align-items: center; gap: 10px;">
                                                            Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="project" items="${listProject}">
                                                        <tr>
                                                            <td><c:out value="${project.username}"/></td>
                                                            <td><c:out value="${project.tname}"/></td>
                                                            <td><c:out value="${project.pname}"/></td>
                                                            <td><c:out value="${project.baki}"/></td>
                                                            <td> <c:choose>
                                                                    <c:when test="${project.status == 'Active'}">
                                                                        <span class="badge badge-success"><c:out value="${project.status}"/></span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge badge-danger"><c:out value="${project.status}"/></span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td style="display: flex; justify-content: center; align-items: center; gap: 10px;">

                                                                <a href="<%=request.getContextPath()%>/MemberServlet?action10=listMmb&id=${project.id}"><button
                                                                        type="button"
                                                                        title="Add Project Member"
                                                                        class="btn btn-icon btn-round btn-black"
                                                                        >
                                                                        <i class="fas fa-user-plus"></i>
                                                                    </button></a>

                                                                <a href="ProjectServlet?action3=editDate&id=<c:out value='${project.id}' />"><button
                                                                        type="button"
                                                                        title="Extend Date"
                                                                        class="btn btn-icon btn-round btn-black"
                                                                        >
                                                                        <i class="fas fa-calendar"></i>
                                                                    </button></a>
                                                                <a href="ProjectServlet?action3=editPro&id=<c:out value='${project.id}' />">
                                                                    <button
                                                                        type="button"
                                                                        title="Edit Project"
                                                                        class="btn btn-icon btn-round btn-primary"
                                                                        >
                                                                        <i class="fa fa-edit"></i>

                                                                    </button>
                                                                </a>
                                                                <a href="ProjectServlet?action3=deletePro&id=<c:out value='${project.id}' />">
                                                                    <button
                                                                        type="button"
                                                                        title="Remove"
                                                                        class="btn btn-icon btn-round btn-danger"
                                                                        data-original-title="Remove">
                                                                        <i class="fa fa-times"></i>
                                                                    </button>
                                                                </a>
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
                    <!-- Popup Box for Messages -->
                    <div id="popupBox" style="display: none;">
                        <div id="popupMessage">Done</div>
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
                const insertProj = '<%= session.getAttribute("insertProj") != null ? session.getAttribute("insertProj") : "" %>';
                const updateProj = '<%= session.getAttribute("updateProj") != null ? session.getAttribute("updateProj") : "" %>';
                const deleteProj = '<%= session.getAttribute("deleteProj") != null ? session.getAttribute("deleteProj") : "" %>';
                const loginError = '<%= session.getAttribute("loginError") != null ? session.getAttribute("loginError") : "" %>';

                if (insertProj === "insertSuccess") {
                    alert('Successfully Inserted New Project!');
            <% session.removeAttribute("insertProj"); %>
                } else if (updateProj === "updateSuccess") {
                    alert('Successfully Updated Project!');
            <% session.removeAttribute("updateProj"); %>
                } else if (deleteProj === "deleteSuccess") {
                    alert('Successfully Deleted A Project!');
            <% session.removeAttribute("deleteProj"); %>
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

