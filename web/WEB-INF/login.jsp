<%-- 
    Document   : login
    Created on : 10 5, 24, 4:37:24 PM
    Author     : Chace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">        
        <link rel='stylesheet' href='darkmode.css'>  
        <link rel='stylesheet' href='newcss.css'>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name='viewport' content='width=device-width, initial-scale=1.0'>
        <script>
            // Toggle dark mode function
            function toggleDarkMode() {
                document.body.classList.toggle('dark-mode');
                const mode = document.body.classList.contains('dark-mode') ? 'dark' : 'light';
                localStorage.setItem('theme', mode); // Save preference
            }

            // Load saved theme preference on page load
            window.onload = function() {
                if (localStorage.getItem('theme') === 'dark') {
                    document.body.classList.add('dark-mode');
                }
            };
        </script>
        
        <title>JSP Page</title>
    </head>
    <jsp:include page="include/header.jsp"/>
    <body>
    <div style = "min-height:100vh">
        <div class="form-container">
            <h2>Login</h2>
            <p style ='text-align: center; color:red'>${param.error}</p><br>
            <form action="do.login" method="POST">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>

                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>

                <button type="submit">Login</button>
            </form>
            <div class="form-link">
                <p>Don't have an account? <a href="register.jsp">Register</a></p>
            </div>
        </div>
    </div>
        <button class="dark-mode-toggle" onclick="toggleDarkMode()"><i class="fa fa-lightbulb-o"></i></button> 
    </body>
    <jsp:include page="include/footer.jsp"/>
</html>
