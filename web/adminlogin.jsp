<%-- 
    Document   : adminlogin
    Created on : 10 8, 24, 4:00:27 PM
    Author     : Chace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> 
        <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admin.css">
        <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admindarkmode.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script>
            // Toggle dark mode function
            function toggleAdminDarkMode() {
                document.body.classList.toggle('admin-dark-mode');
                const mode = document.body.classList.contains('admin-dark-mode') ? 'dark' : 'light';
                localStorage.setItem('theme', mode); // Save preference
            }

            // Load saved theme preference on page load
            window.onload = function() {
                if (localStorage.getItem('theme') === 'dark') {
                    document.body.classList.add('admin-dark-mode');
                }
            };
        </script>
        <title>Admin Login</title>
    </head>
    <header>
        <nav class="navbar">
            <div class="logo">
                <img class="logo-img" src="logo.png" alt="logo">
                &nbsp;&nbsp;&nbsp;&nbsp;UA&P Tutors
            </div>
            <ul>
                <li><a href="index.jsp">Back To Main Website</a></li>
            </ul>
        </nav>
    </header>
    <body>
        <div style ="min-height: 85vh">
            <div class="form-container">
                <h2>Admin Login</h2>
                <p style ='text-align: center; color:red'>${param.error}</p><br>
                <form method="post" action="do.admin">
                    Email: <input type="text" name="email"><br>
                    Password: <input type="password" name="password"><br>
                    <input type="submit" value="submit">
                </form>
            </div>
        </div>
        <button class="dark-mode-toggle" onclick="toggleAdminDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
    </body>
    <jsp:include page="admin/include/adminfooter.jsp"/>
</html>