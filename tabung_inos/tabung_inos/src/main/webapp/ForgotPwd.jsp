<%-- 
    Document   : ForgorPwd
    Created on : 9 Oct 2024, 8:51:56 am
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
        <style>
            .container span{
                margin-left:0.5rem;
            }

            .form-text {
                margin-top: 0.3rem;
                font-size: 95%;
                color: #b4bdc6;
            }

            .text-danger {
                --bs-text-opacity: 1;
                color: red !important;
            }
        </style>

    </head>

    <body>

        <div class="container" id="container">

            <div class="form-container sign-in">
                <form id="formAuthentication" class="mb-3" action="Newpwd" method="post">
                    <h1 style="color:white">New Password 🔒</h1>
                    <p style="color: white">Create A New Password</p>
                    <span class="mb-4">
                        <label>New Password</label>
                        <input
                            type="password"
                            class="form-control"
                            id="password"
                            name="password"
                            placeholder="Enter your new password"
                            autofocus
                            />

                        <small id="passwordHelp" class="form-text text-muted">
                            Your password must be at least 8 characters, contain at least one uppercase letter, one lowercase letter, one number and a special character.
                        </small><br><br>
                        <label>Confirm Password</label>
                        <input
                            type="password"
                            class="form-control"
                            id="confirmpwd"
                            name="confirmpwd"
                            placeholder="Confirm your new password"
                            />
                        <small id="confirmHelp" class="form-text text-danger" style="display:none;">Passwords do not match!</small><br>
                    </span>
                    <button class="btn btn-primary d-grid w-100">Save </button>
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

        <script>
            // Password validation criteria
            function validatePasswordCriteria(password) {
                // Check if the password has at least 8 characters, 1 uppercase, 1 lowercase, and 1 digit
                const criteria = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,}$/;
                return criteria.test(password);
            }

            // Function to check if passwords match and validate criteria
            function checkPasswordMatch() {
                const password = document.getElementById("password").value;
                const confirmPassword = document.getElementById("confirmpwd").value;
                const submitBtn = document.getElementById("submitBtn");
                const confirmHelp = document.getElementById("confirmHelp");
                const passwordHelp = document.getElementById("passwordHelp");

// Check if the password meets the criteria
                if (validatePasswordCriteria(password)) {
                    document.getElementById("password").style.borderColor = 'green';
                    passwordHelp.style.color = '#98FF98';
                } else {
                    document.getElementById("password").style.borderColor = 'red';
                    passwordHelp.style.color = 'red';
                }

                // Check if passwords match
                if (password !== confirmPassword) {
                    confirmHelp.style.display = 'block'; // Show error message
                    document.getElementById("confirmpwd").style.borderColor = 'red';
                    submitBtn.disabled = true; // Disable the submit button
                } else {
                    confirmHelp.style.display = 'none'; // Hide error message
                    document.getElementById("confirmpwd").style.borderColor = 'green';

                }

                // Check if the password meets the criteria
                if (validatePasswordCriteria(password) && password === confirmPassword) {
                    submitBtn.disabled = false; // Enable the submit button
                } else {
                    submitBtn.disabled = true; // Keep the submit button disabled
                }
            }

            // Attach the validation to password and confirm password fields
            document.getElementById("password").addEventListener("keyup", checkPasswordMatch);
            document.getElementById("confirmpwd").addEventListener("keyup", checkPasswordMatch);

            // Final form validation before submitting
            function validateForm() {
                const password = document.getElementById("password").value;

                // If the password does not meet criteria or the passwords do not match, prevent form submission
                if (!validatePasswordCriteria(password) || document.getElementById("confirmpwd").style.borderColor === 'red') {
                    alert("Please ensure the password meets the criteria and the passwords match.");
                    return false; // Prevent form submission
                }

                return true; // Allow form submission
            }
        </script>
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

                if (status === "success") {
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


