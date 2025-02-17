<%-- 
    Document   : signIn
    Created on : 11 Sep 2024, 11:44:30 am
    Author     : NAJIHAH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="css/style_1_1.css">
        <title>I-TABUNG</title>

    </head>

    <body>
        <div class="container" id="container">
            <div class="form-container sign-in">
                <form action="userLogin" method="post">
                    <h1 style="color:white">Let's You Sign In</h1>
                    <p style="color:white">Enter your details to use all of site features</p>
                    <span class="mb-4">
                        <label>Username</label>
                        <input type="text" placeholder="Username" name="username" required>
                    </span>
                    <span class="mb-4">
                        <label>Password</label>
                        <input type="password" placeholder="Password" id="password" name="password" required>
                    </span>
                    <a href="Reset.jsp" style="text-align: left;">Forget Your Password?</a>
                    <button type="submit" id="submitBtn">Sign In</button>
                    <button class="hidden"><a href="register.jsp"><i class="fas fa-globe"></i> Create Account</a></button>
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
            <div id="popupBox">
                <div id="popupMessage"></div>
                <button id="okButton">OK</button>
            </div>
        </div>

        <script>
            // Get DOM elements
            const container = document.getElementById('container');
            const popupBox = document.getElementById('popupBox');
            const okButton = document.getElementById('okButton');

            document.addEventListener('DOMContentLoaded', function () {
                // Check for any error messages passed in the URL
                 const params = new URLSearchParams(window.location.search);
                const errorFromUrl = params.get('Please Sign In!');

                if (errorFromUrl) {
                    const popupMessage = document.getElementById('popupMessage');
                    popupMessage.textContent = errorFromUrl;
                    popupBox.style.display = 'block';
                }

                // Check session attributes for login and registration messages
                const loginSuccess = '<%= session.getAttribute("loginSuccess") %>';
                const loginWrong = '<%= session.getAttribute("loginWrong") %>';
                const loginError = '<%= session.getAttribute("loginError") %>';
                const registerSuccess = '<%= session.getAttribute("registerSuccess") %>';
                const status = "<%= request.getAttribute("status") %>";

                // Show appropriate messages based on session attributes
                if (loginSuccess === "true") {
                    alert('Successfully logged in!');
            <% session.removeAttribute("loginSuccess"); %> // Clear session attribute
                } else if (loginSuccess === "false") {
                    alert('Successfully logged out!');
            <% session.removeAttribute("loginSuccess"); %> // Clear session attribute
                } else if (registerSuccess === "true") {
                    alert('Successfully registered! Please Log In');
            <% session.removeAttribute("registerSuccess"); %> // Clear session attribute
                } else if (status === "resetSuccess") {
                    alert('Password successfully updated!');
            <% session.removeAttribute("updatePassword"); %> // Clear session attribute
                } else if (loginWrong === "Invalid") {
                    alert('Invalid username and password');
            <% session.removeAttribute("loginWrong"); %> // Clear session attribute
                } else if (loginError) {
                    alert("Please Log In First!!");
            <% session.removeAttribute("loginError"); %>
                }
            });
        </script>
    </body>

</html>