<%-- 
    Document   : register
    Created on : 21 Oct 2024, 11:27:08 am
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="css/style_1_1.css">
        <style>
            .container span {
                margin-block-start: 0.67em;
                margin-block-end: 0.67em;
                margin-inline-start: 0px;
                margin-inline-end: 0px;
                position: relative;
                margin-left: 3rem;
            }
        </style>
    </style>
    <title>I-TABUNG</title>

</head>

<body>

    <div class="container" id="container">
        <div class="form-container sign-in">
            <form action="processRegister.jsp" method="post" onsubmit="return validateForm()">
                <h1 style="color:white">CREATE ACCOUNT</h1>
                <p style="color:white">Enter your details to use all of site features</p>
                <div class="mb-3">
                    <label>Username</label>
                    <input type="text" placeholder="Username" name="username" id="username" required onkeyup="validateInput('username')">
                    <label>Email</label>
                    <input type="email" placeholder="Email" id="email" name="email" required onkeyup="validateInput('email')">
                    <label>Password</label>
                    <input type="password" placeholder="Password" id="password" name="password" required onkeyup="checkPasswordCriteria()">
                    <small id="passwordHelp" class="form-text text-muted">
                        Your password must be at least 8 characters, contain at least one uppercase letter, one lowercase letter, one number, and one special character.
                    </small><br>
                    <label>Phone Number</label>
                    <input type="text" placeholder="Phone Number" id="phone" name="phone" required onkeyup="validateInput('phone')">
                    <input type="hidden" id="role" name="role" value="user" required>
                </div>
                <button type="submit" id="submitBtn" disabled>Sign In</button>
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

    <script>
        // Password validation criteria
        function validatePasswordCriteria(password) {
            const criteria = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,}$/;
            return criteria.test(password);
        }

        // Check password criteria on keyup and update border color
        function checkPasswordCriteria() {
            const password = document.getElementById("password").value;
            const submitBtn = document.getElementById("submitBtn");
            const passwordHelp = document.getElementById("passwordHelp");

            // Check if the password meets the criteria
            if (validatePasswordCriteria(password)) {
                document.getElementById("password").style.borderColor = 'green';
                passwordHelp.style.color = '#98FF98';
            } else {
                document.getElementById("password").style.borderColor = 'red';
                passwordHelp.style.color = 'red';
            }

            checkFormCompletion(); // Check if the entire form is valid before enabling the button
        }

        // Validate individual input fields and update border color
        function validateInput(fieldId) {
            const input = document.getElementById(fieldId);
            if (input.value.trim() !== "") {
                input.style.borderColor = 'green';
            } else {
                input.style.borderColor = 'red';
            }

            checkFormCompletion(); // Check if the entire form is valid before enabling the button
        }

        // Function to check if all fields are valid and enable submit button if they are
        function checkFormCompletion() {
            const username = document.getElementById("username").value.trim() !== "";
            const email = document.getElementById("email").value.trim() !== "";
            const phone = document.getElementById("phone").value.trim() !== "";
            const passwordValid = validatePasswordCriteria(document.getElementById("password").value);

            // Enable the submit button only if all fields are valid
            document.getElementById("submitBtn").disabled = !(username && email && phone && passwordValid);
        }

        // Form validation with popup for error messages
        function validateForm() {
            const password = document.getElementById("password").value;
            const username = document.getElementById("username").value;
            const email = document.getElementById("email").value;
            const phone = document.getElementById("phone").value;

            if (!username || !email || !phone || !password) {
                showPopup("Please fill in all fields.");
                return false;
            }

            if (!validatePasswordCriteria(password)) {
                showPopup("Please ensure the password meets the criteria.");
                return false;
            }

            return true; // Allow form submission if all criteria are met
        }

        // Show popup function
        function showPopup(message) {
            const popupBox = document.getElementById("popupBox");
            const popupMessage = document.getElementById("popupMessage");
            popupMessage.textContent = message; // Set the message
            popupBox.style.display = "block"; // Display the popup
        }

        // Close popup function
        function closePopup() {
            const popupBox = document.getElementById("popupBox");
            popupBox.style.display = "none";
        }
    </script>



</body>

</html>

