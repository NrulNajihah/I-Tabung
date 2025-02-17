<%-- 
    Document   : Expenses
    Created on : 1 Oct 2024, 3:15:35 pm
    Author     : NAJIHAH
--%>

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
                    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                    <% if (errorMessage != null) { %>
                    <div class="alert alert-warning">
                        <strong>Warning!</strong> <%= errorMessage %>
                    </div>
                    <% } %>
                    <% String bugMessage = (String) request.getAttribute("bugMessage"); %>
                    <% if (bugMessage != null) { %>
                    <div class="alert alert-warning">
                        <strong>Warning!</strong> <%= bugMessage %>
                    </div>
                    <% } %>
                    <div class="page-inner">
                        <div class="page-header">
                            <h3 class="fw-bold mb-3">Perbelanjaan</h3>
                            <ul class="breadcrumbs mb-3">
                                <li class="nav-home">
                                    <a href="<%=request.getContextPath()%>/AdminServlet?path1=HomeStatus">
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
                            </ul>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="card-title">Maklumat Projek</div>
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
                                                <label for="total_Exp">Jumlah Perbelanjaan</label>
                                                <input
                                                    id="total_Exp"
                                                    type="text"
                                                    class="form-control form-control-lg"
                                                    name="total_Exp"
                                                    value="<c:out value='${project.total_Exp}'/>"
                                                    readonly
                                                    />
                                            </div>


                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex align-items-center">
                                            <h4 class="card-title">Maklumat Perbelanjaan</h4>
                                            <a class="btn btn-info ms-auto" href="<%=request.getContextPath()%>/ExpensesServlet?path4=listExpFull&id=${project.id}">
                                                <i class="fa fa-info"></i>
                                                More
                                            </a>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table
                                                id="add-row"
                                                class="display table table-striped table-hover"
                                                >
                                                <thead>
                                                    <tr>

                                                        <th>Maklumat</th>
                                                        <th>Jumlah Peruntukan</th>
                                                        <th>Jumlah Perbelanjaan</th>
                                                        <th>Baki Semasa</th>

                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="income" items="${listofExp}">
                                                        <tr>

                                                            <td><c:out value="${income.noVote}" /></td>
                                                            <td><c:out value="${income.total_All}" /></td>
                                                            <td><c:out value="${income.total_Exp}" /></td>
                                                            <td><c:out value="${income.balances}" /></td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="card-title">Daftar Perbelanjaan</div>

                                    </div>
                                    <c:if test="${expense == null}">
                                        <form action="ExpensesServlet" method="post" enctype="multipart/form-data">
                                            <input type="hidden" name="path4" value="insertExp">
                                            <c:if test="${project != null}">
                                                <input type="hidden" name="id" value="${project.id}"/> 
                                            </c:if>
                                            <c:if test="${user != null}">
                                                <input type="hidden" name="uID" value="${user.uID}"/> 
                                            </c:if>
                                            <div class="card-body">
                                                <div class="row">

                                                    <div class="form-group">
                                                        <label for="defaultSelect">Maklumat</label>
                                                        <select class="form-select form-control" name="vID" style="width:45rem;">
                                                            <c:forEach var="income" items="${vtList}">
                                                                <option value="${income.vID}" <c:if test="${expense != null && income.vID == expense.vID}">selected</c:if>>
                                                                    ${income.noVote} - ${income.vote}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="claim">Tuntutan</label>
                                                        <input type="text" class="form-control" name="claim" style="width:45rem;" placeholder="Fill claim"/>
                                                    </div>

                                                    <div class="col-md-6 col-lg-4">
                                                        <div class="form-group">
                                                            <label for="edate">Tarikh</label>
                                                            <input type="date" class="form-control form-control-lg" name="edate" placeholder="DD/MM/YYYY"/>
                                                        </div>
                                                            <div class="form-group">
                                                            <label for="edate">NoPo/ baucer</label>
                                                            <input type="text" class="form-control form-control-lg" name="noPO" placeholder="No Po" />
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 col-lg-4">
                                                        <div class="form-group">
                                                            <label for="total_Exp">Perbelanjaan(RM)</label>
                                                            <input type="text" class="form-control form-control-lg" name="total_Exp" placeholder="Fill amount"/>
                                                        </div><br>
                                                        <div class="form-group">
                                                            <label for="receipt">Muat Naik Resit:</label>
                                                            <input type="file" name="receipt" accept="application/pdf">
                                                        </div>
                                                    </div>
                                                    
                                                </div>
                                                <div class="card-action">
                                                    <button type="submit" class="btn btn-success">Simpan</button>

                                                </div>
                                        </form> <!-- Correct closing of the form -->
                                    </c:if>
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
        <script src="assets/js/plugin/datatables/datatables.min.js"></script>

        <!-- Bootstrap Notify -->
        <script src="assets/js/plugin/bootstrap-notify/bootstrap-notify.min.js"></script>

        <!-- jQuery Vector Maps -->
        <script src="assets/js/plugin/jsvectormap/jsvectormap.min.js"></script>
        <script src="assets/js/plugin/jsvectormap/world.js"></script>

        <!-- Sweet Alert -->
        <script src="assets/js/plugin/sweetalert/sweetalert.min.js"></script>

        <!-- Kaiadmin JS -->
        <script src="js/kaiadmin.min.js"></script>
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
            const insertExp = '<%= session.getAttribute("insertExp") != null ? session.getAttribute("insertExp") : "" %>';
            const loginError = '<%= session.getAttribute("loginError") != null ? session.getAttribute("loginError") : "" %>';

            if (insertExp === "AddExp") {
                alert('Successfully Add Expense!');
        <% session.removeAttribute("insertExp"); %>
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

