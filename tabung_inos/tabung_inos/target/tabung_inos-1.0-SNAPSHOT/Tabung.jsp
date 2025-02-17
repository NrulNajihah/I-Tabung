<%-- 
    Document   : Tabung
    Created on : 17 Aug 2024, 6:47:30 pm
    Author     : NAJIHAH
--%>

<%@page import="com.dao.TabungDAO"%>
<%@page import="com.db.DbCon"%>
<%@page import="com.model.Tabung"%>
<%@page import="java.util.*"%>
<%@page import="com.connection.TabungServlet"%>
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
                                <a href="<%=request.getContextPath()%>/AdminServlet?path1=listPro" >
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
                                <a href="<%=request.getContextPath()%>/AdminServlet?path1=listReport">
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
                            <h3 class="fw-bold mb-3">Tabung</h3>
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
                                    <a href="<%=request.getContextPath()%>/TabungServlet?action2=listTbg">Tabung</a>
                                </li>
                            </ul>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="card-title">Daftar Tabung</div>
                                    </div>
                                    <c:if test="${tabung == null}">
                                        <form action="TabungServlet" method="post">
                                            <input type="hidden" name="action2" value="insertTbg">
                                        </c:if>
                                        <c:if test="${tabung != null}">
                                            <form action="TabungServlet" method="post" >
                                                <input type="hidden" name="action2" value="updateTbg">
                                                <input type="hidden" name="tID" value="<c:out value='${tabung.tID}'/>"><!-- Hidden ID input field -->
                                            </c:if>
                                            <div class="card-body">

                                                <div class="row" >
                                                    <c:if test="${tabung != null}">
                                                        <input type="hidden" name="tID" value="<c:out value='${tabung.tID}'/>"/> 
                                                    </c:if>

                                                    <c:if test="${user != null}">
                                                        <input type="hidden" name="uID" value="<c:out value='${tabung.uID}'/>"/> 
                                                    </c:if>
                                                    <div class="form-group">
                                                        <label for="largeInput">No Tabung</label>
                                                        <input
                                                            type="text"
                                                            class="form-control"
                                                            name="noTb"
                                                            value="<c:out value='${tabung.noTb}'/>"
                                                            placeholder="Fill no"
                                                            />   
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="largeInput">Nama Tabung</label>
                                                        <input
                                                            type="text"
                                                            class="form-control"
                                                            name="tname"
                                                            value="<c:out value='${tabung.tname}'/>"
                                                            placeholder="Fill name"
                                                            />   
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="largeInput">Tarikh</label>
                                                        <input
                                                            type="date"
                                                            class="form-control"
                                                            name="tdate"
                                                            value="<c:out value='${tabung.tdate}'/>"
                                                            placeholder="Fill date"
                                                            />
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="largeInput">Jumlah</label>
                                                        <input
                                                            type="text"
                                                            class="form-control"
                                                            name="amount"
                                                            value="<c:out value='${tabung.amount != null ? tabung.amount : "0.00"}'/>"
                                                            readonly
                                                            />
                                                        <input
                                                            type="hidden"
                                                            class="form-control"
                                                            name="balance"
                                                            value="<c:out value='${tabung.balance != null ? tabung.balance : "0.00"}'/>"
                                                            readonly
                                                            />
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="card-action">
                                                <button class="btn btn-success">Simpan</button>
                                            </div>
                                        </form>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex align-items-center">
                                            <h4 class="card-title">Maklumat Tabung</h4>

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
                                                        <th>No Tabung</th>
                                                        <th>Nama Tabung</th>
                                                        <th>Tarikh</th>
                                                        <th>Jumlah</th>
                                                        <th>Baki Semasa</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="tabung" items="${listTabung}">
                                                        <tr>
                                                            <td><c:out value="${tabung.noTb}"/></td> 
                                                            <td><c:out value="${tabung.tname}"/></td>
                                                            <td><c:out value="${tabung.tdate}"/></td>
                                                            <td><c:out value="${tabung.amount}"/></td>
                                                            <td><c:out value="${tabung.balance}"/></td>
                                                            <td style="display: flex; justify-content: center; align-items: center; gap: 10px;">
                                                                
                                                                    <a href="CompServlet?path5=listComp&tID=<c:out value='${tabung.tID}' />">
                                                                        <button
                                                                            type="button"
                                                                            title="Komponen"
                                                                            class="btn btn-icon btn-round btn-black"
                                                                            >
                                                                            <i class="fa fa-coins"></i>
                                                                        </button>
                                                                    </a>
                                                                    <a href="TabungServlet?action2=editTbg&tID=<c:out value='${tabung.tID}' />">
                                                                        <button
                                                                            type="button"
                                                                            title="Edit Task"
                                                                            class="btn btn-icon btn-round btn-primary"
                                                                            >
                                                                            <i class="fa fa-edit"></i>
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
                const insertTbg = '<%= session.getAttribute("insertTbg") != null ? session.getAttribute("insertTbg") : "" %>';
                const updateTbg = '<%= session.getAttribute("updateTbg") != null ? session.getAttribute("updateTbg") : "" %>';
                const loginError = '<%= session.getAttribute("loginError") != null ? session.getAttribute("loginError") : "" %>';

                if (insertTbg === "insertSuccess") {
                    alert('Successfully Inserted New Tabung!');
            <% session.removeAttribute("insertTbg"); %>
                } else if (updateTbg === "updateSuccess") {
                    alert('Successfully Updated Tabung!');
            <% session.removeAttribute("updateTbg"); %>
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
