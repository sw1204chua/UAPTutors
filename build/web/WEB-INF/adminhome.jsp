<%-- 
    Document   : adminhome
    Created on : Nov 26, 2024, 11:18:54 PM
    Author     : Hawy
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>         
        <title>UA&P Tutors</title>
          <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admin.css">
        <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admindarkmode.css">
        <meta name='viewport' content='width=device-width, initial-scale=1.0'>
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
    </head>
    <jsp:include page="include/adminheader.jsp"/>

    <body>
        <div style = height:85vh>
            <div class = container>
                <c:choose>
                    <c:when test = '${currentadmin==null}'>

                        <div class = validate-container>
                            <h1>ERROR: PLEASE LOG IN</h1>
                            <a class ='nav-button' href = '../adminlogin.jsp'>Go back to Login</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class = home-container>
                            <h1>Welcome to UA&P Tutors Admin Page!</h1>
                            <p style = 'font-size:20px'; 'text-align:justify' ;'float:top'>
                                You can approve other admin requests, edit comments and profiles, and delete comments and profiles!</p>
                            <div class='search-container'>
                
                            <form action='do.AdminTutorList' method='GET'>
                            <input type='text' name='search' placeholder='Search...'>
                            <button type='submit'>Search</button>
                        </form>
                         </div>
                            <div class="home-button-container">
                                <a href="do.adminrequest">Admin Requests</a>
                                <a href="do.AdminTutorList">All tutors listed</a>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <button class="dark-mode-toggle" onclick="toggleAdminDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
    </body>          
    <jsp:include page="include/adminfooter.jsp"/>;
</html>

