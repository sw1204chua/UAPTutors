<%-- 
    Document   : index
    Created on : 11 27, 24, 8:13:57 AM
    Author     : 220129
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>         
        <title>UA&P Tutors</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> 
        <link rel ="stylesheet" href ="darkmode.css">
        <link rel='stylesheet' href='newcss.css'>
        <meta name='viewport' content='width=device-width, initial-scale=1.0'>
        <script>
            // Toggle dark mode function
            function toggleDarkMode() {
                document.body.classList.toggle('dark-mode');
                const mode = document.body.classList.contains('dark-mode') ? 'dark' : 'light';
                localStorage.setItem('theme', mode); // Save preference
            }

            // Load saved theme preference on page load
            window.onload = function () {
                if (localStorage.getItem('theme') === 'dark') {
                    document.body.classList.add('dark-mode');
                }
            };
        </script>
    </head>
    <jsp:include page="include/header.jsp"/>
    <body>
        <div class = container style = height:85vh>
            <div class = home-page-container>     
                <div style = 'text-justify:inter-word;text-align: justify;'>
                    <h1 style='text-align: center'>Welcome to UA&P Tutors!</h1>
                    <c:choose>
                        <c:when test ='${user==null}'>

                            <p style = 'font-size:20px'; 'text-align:justify' ;'float:top'>
                                Here at UA&P Tutors, you can find the perfect dragon to help you with your studies!
                                Easily browse tutor profiles, explore available subjects, and book sessions with just a few clicks.
                                You can also become a tutor to help out our fellow dragons while making a bit of money while you're at it!
                                So, what are you waiting for? Register now!
                            <div class='search-container'>

                                <form action='/UAPTutors/do.searchresult' method='GET'>
                                    <input type='text' name='inputFieldName' placeholder='Search...'>
                                    <button type='submit'>Search</button>
                                </form>
                            </div>
                            <div class="home-button-container">
                                <a href="do.tutors">Tutors</a>
                                <a href="register.jsp">Register</a>
                                <a href="login.jsp">Login</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p style = 'font-size:20px'>Visit your profile to edit your details or visit tutors' profiles to book a session with them!</p>
                            <div class='search-container'>

                                <form action='/UAPTutors/do.searchresult' method='GET'>
                                    <input type='text' name='inputFieldName' placeholder='Search...'>
                                    <button type='submit'>Search</button>
                                </form>

                            </div>
                            <div class="home-button-container">
                                <a href="do.tutors">Tutors</a>
                                <a href="profile/do.profile">Your Profile</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <button class="dark-mode-toggle" onclick="toggleDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
    </body>           
    <jsp:include page="include/footer.jsp"/>
</html>
