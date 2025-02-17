<%-- 
    Document   : Reset
    Created on : 8 Oct 2024, 2:48:48 pm
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
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="css/style_1_1.css">
        <title>I-TABUNG</title>
        <style>
            .container span{
                margin-left:0.5rem;
            }
        </style>

    </head>

    <body>
        <!-- Content -->

        <div class="container" id="container">

            <div class="form-container sign-in">
                <form id="formAuthentication" class="mb-3" action="Resetpwd" method="post">
                    <h1 style="color:white">Forgot Password?</h1>
                    <p style="color:white">Enter your email to reset your password</p>
                    <span class="mb-4">
                        <label>Email</label><br>
                        <input
                            type="text"
                            class="form-control"
                            id="email"
                            name="email"
                            placeholder="Enter your email"
                            style="width:300px;"
                            autofocus
                            />
                    </span>
                    <button class="btn btn-primary d-grid w-100">Generate OTP</button>
                    <a href="signIn.jsp" style="text-align: left;">Back to login</a>
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
                    alert('Password successfully reset! Created a new password');
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

