<%-- 
    Document   : EnterOtp
    Created on : 10 Oct 2024, 12:17:53 pm
    Author     : NAJIHAH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html
    lang="en"
    class="light-style customizer-hide"
    dir="ltr"
    data-theme="theme-default"
    data-assets-path="../assets/"
    data-template="vertical-menu-template-free"
    >
    <head>
        <meta charset="utf-8" />
        <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"
            />

        <title>I-TABUNG</title>

        <meta name="description" content="" />

        <!-- Favicon -->
        <link rel="icon" type="image/x-icon" href="../assets/img/favicon/favicon.ico" />

        <!-- Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap"
            rel="stylesheet"
            />

        <!-- Icons. Uncomment required icon fonts -->
        <link rel="stylesheet" href="../assets/vendor/fonts/boxicons.css" />

        <!-- Core CSS -->
        <link rel="stylesheet" href="css/style_1_1.css">
        <style>
            .text-danger {
                --bs-text-opacity: 1;
                color: red !important;
            }
        </style>


        <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
        <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
        <script src="../assets/js/config.js"></script>
    </head>

    <body>
        <!-- Content -->

        <div class="container" id="container">

            <div class="form-container sign-in">
                <form id="formAuthentication" class="mb-3" action="ValidOtp" method="post">
                    <h1 style="color:white">Enter OTP 🔒</h1>
                    <%
                                 if(request.getAttribute("message")!=null)
                                 {
                                         out.print("<p class='text-danger mb-4'>"+request.getAttribute("message")+"</p>");
                                 }
		  
                    %>
                    <span class="mb-4">
                        <label>OTP</label><br>
                        <input
                            type="text"
                            class="form-control"
                            id="otp"
                            name="otp"
                            placeholder="Enter your OTP"
                            style="width:300px;"
                            autofocus
                            />
                    </span>
                    <button class="btn btn-primary d-grid w-100">Continue</button>
                    <a href="signIn.jsp" class="d-flex align-items-center justify-content-center">
                        <i class="bx bx-chevron-left scaleX-n1-rtl bx-sm"></i>
                        Back to login
                    </a>
                </form>

            </div>

            <div class="toggle-container">
                <div class="toggle">
                    <div class="toggle-panel toggle-right">
                        <img src="img/sign.png" alt="sign up image" style="width:489px;">
                    </div>
                </div>
            </div>

            <!-- Popup Box for Messages -->
            <div id="popupBox" style="display: none;">
                <div id="popupMessage"></div>
                <button id="okButton">OK</button>
            </div>
        </div>

        <!-- Place this tag in your head or just before your close body tag. -->
        <script async defer src="https://buttons.github.io/buttons.js"></script>
        <script>
            const container = document.getElementById('container');
            const popupBox = document.getElementById('popupBox');
            const okButton = document.getElementById('okButton');

            document.addEventListener('DOMContentLoaded', function () {
                // Check for any error messages passed in the URL
                const params = new URLSearchParams(window.location.search);
                const errorFromUrl = params.get('error');

                if (errorFromUrl) {
                    const popupMessage = document.getElementById('popupMessage');
                    popupMessage.textContent = errorFromUrl;
                    popupBox.style.display = 'block';
                }

                // Check for success or error messages from the session
                var status = "<%= request.getAttribute("status") != null ? request.getAttribute("status") : "" %>";
                var error = "<%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>";

                if (status === "sendMessage") {
                    alert('OTP successfully sent!');
            <% 
                request.removeAttribute("status"); // Clear status to avoid repeated alerts
            %>
                } else if (error) {
                    alert(error);
            <% 
                request.removeAttribute("error"); // Clear error to avoid repeated alerts
            %>
                }

                // Close the popup message when OK button is clicked
                okButton.addEventListener('click', () => {
                    popupBox.style.display = 'none';
                });
            });
        </script>

    </body>
</html>

