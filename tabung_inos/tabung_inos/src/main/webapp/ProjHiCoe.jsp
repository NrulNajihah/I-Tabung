<%-- 
    Document   : ProjHiCoe
    Created on : 14 Jan 2025, 4:29:33 pm
    Author     : Dell
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

                            <li class="nav-item active submenu">
                                <a href="<%=request.getContextPath()%>/AdminServlet?path1=listProH" >
                                    <i class="fas fa-tasks"></i>
                                    <p>Projek (HiCOE)</p>

                                </a>
                            </li>
                            <li class="nav-item">
                                <a href="<%=request.getContextPath()%>/AdminServlet?path1=listPro">
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
                            <h3 class="fw-bold mb-3">Projek</h3>
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
                                    <a href="<%=request.getContextPath()%>/AdminServlet?path1=listPro">Projek</a>
                                </li>
                            </ul>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="card-title">Daftar Komponen</div>

                                    </div>
                                    <c:if test="${component == null}">
                                        <form action="CompServlet" method="post">
                                            <input type="hidden" name="path5" value="insertComp">

                                            <div class="card-body">
                                                <div class="row">
                                                    <div class="form-group">
                                                        <label for="defaultSelect">Tabung</label>
                                                        <select class="form-select form-control" name="tID" style="width:50rem;">
                                                            <c:forEach var="tabung" items="${listTabung}">
                                                                <option value="${tabung.tID}" <c:if test="${project != null && tabung.tID == project.tID}">selected</c:if>>
                                                                    <c:out value='${tabung.noTb}'/> - <c:out value='${tabung.tname}'/>
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="defaultSelect">ID Komponen</label>
                                                        <input
                                                            type="text"
                                                            class="form-control form-control-lg"
                                                            name="compID"
                                                            value="<c:out value='${component.compID}'/>"
                                                            placeholder="Fill component ID"
                                                            style="width:50rem;"
                                                            />

                                                        <label for="defaultSelect">Nama Komponen</label>
                                                        <input
                                                            type="text"
                                                            class="form-control form-control-lg"
                                                            name="compName"
                                                            value="<c:out value='${component.compName}'/>"
                                                            placeholder="Fill component Name"
                                                            style="width:50rem;"
                                                            />
                                                        <label for="defaultSelect">Jumlah Peruntukan</label>
                                                        <input
                                                            type="text"
                                                            class="form-control form-control-lg"
                                                            name="total_All"
                                                            value="<c:out value='${component.total_All}'/>"
                                                            placeholder="Fill Total"
                                                            style="width:50rem;"
                                                            />
                                                        <label for="defaultSelect">Tarikh</label>
                                                        <input
                                                            type="date"
                                                            class="form-control form-control-lg"
                                                            name="compDate"
                                                            value="<c:out value='${component.compDate}'/>"
                                                            placeholder="Fill Date"
                                                            style="width:50rem;"
                                                            />
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="card-action">
                                                <button type="submit" class="btn btn-success">Save</button>

                                            </div>
                                        </form> <!-- Correct closing of the form -->
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="card-head-row card-tools-still-right">
                                            <h4 class="card-title">HiCOE</h4>
                                        </div>
                                        <p class="card-category">
                                            <select id="compID" class="form-select form-control" name="cID" style="width:50rem;">
                                                <c:forEach var="component" items="${listComp}">
                                                    <option value="${component.compID}" <c:if test="${selectedCID != null && selectedCID == component.compID}">selected</c:if>>
                                                        <c:out value='${component.compID}'/> - <c:out value='${component.compName}'/>
                                                    </option>
                                                </c:forEach>
                                            </select>

                                        </p>
                                    </div>
                                    <div id="formC1"  class="card-body"  style="display: none;">
                                        <c:if test="${project == null}">
                                            <form action="AdminServlet" method="post">
                                                <input type="hidden" name="path1" value="insertProH">
                                            </c:if>
                                            <c:if test="${project != null}">
                                                <form action="AdminServlet" method="post" >
                                                    <input type="hidden" name="path1" value="updatePro">
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
                                                                <label for="defaultSelect" class="d-none">Komponen</label>
                                                                <!-- Hidden input to store cID -->
                                                                <input type="hidden" name="cID" value="${project != null ? project.cID : ''}">

                                                                <!-- Display component name (optional, if needed for user view) -->
                                                                <span>
                                                                    <c:forEach var="component" items="${listComp}">
                                                                        <c:if test="${project != null && component.cID == project.cID}">
                                                                            <c:out value="${component.compID}" /> - <c:out value="${component.compName}" />
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </span>
                                                            </div>

                                                            <div class="form-group">
                                                                <label for="largeInput">Jumlah Peruntukan</label>
                                                                <input
                                                                    type="text"
                                                                    class="form-control form-control-lg"
                                                                    name="total_All"
                                                                    value="<c:out value='${project.total_All != null ? project.total_All : "0.00"}'/>"
                                                                    placeholder="Fill allocation amount"
                                                                    readonly
                                                                    />
                                                                <label for="largeInput">Jumlah Perbelanjaan</label>
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
                                                    <button class="btn btn-success">Simpan</button>
                                                </div>
                                            </form>
                                    </div>

                                    <div id="formC234"  class="card-body" style="display: none;">
                                        <c:if test="${expense == null}">
                                            <form action="ExpensesServlet" method="post" enctype="multipart/form-data">
                                                <input type="hidden" name="path4" value="insertExpH">
                                                <c:if test="${component != null}">
                                                    <input type="hidden" name="cID" value="${component.cID}"/> 
                                                </c:if>
                                                <c:if test="${user != null}">
                                                    <input type="hidden" name="uID" value="${user.uID}"/> 
                                                </c:if>
                                                <div class="card-body">
                                                    <div class="row">


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
                                                        <button type="submit" class="btn btn-success">Save</button>

                                                    </div>
                                            </form> <!-- Correct closing of the form -->
                                        </c:if>
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
                    const insertComp = '<%= session.getAttribute("insertComp") != null ? session.getAttribute("insertComp") : "" %>';
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
                    } else if (insertComp === "addComp") {
                        alert('Successfully Inserted New Component!');
                <% session.removeAttribute("insertComp"); %>
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
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const compIDDropdown = document.getElementById("compID");
                    const formC1 = document.getElementById("formC1");
                    const formC234 = document.getElementById("formC234");

                    // Function to toggle visibility of forms
                    const toggleForms = () => {
                        const selectedValue = compIDDropdown.value;
                        if (selectedValue === "C1") {
                            formC1.style.display = "block";
                            formC234.style.display = "none";
                        } else if (["C2", "C3", "C4"].includes(selectedValue)) {
                            formC1.style.display = "none";
                            formC234.style.display = "block";
                        } else {
                            formC1.style.display = "none";
                            formC234.style.display = "none";
                        }
                    };

                    // Initialize form visibility on page load
                    toggleForms();

                    // Add event listener for dropdown changes
                    compIDDropdown.addEventListener("change", toggleForms);
                });

            </script>


    </body>
</html>
