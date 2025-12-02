<%-- 
    Document   : paymentdeclined
    Created on : 11 27, 24, 8:58:45 AM
    Author     : 220129
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">        
        <link rel ="stylesheet" href ="../darkmode.css">
        <link rel ="stylesheet" href ="../newcss.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <title>UA&P Tutors</title>            
    </head>
    <jsp:include page="include/header.jsp"/>
    <body>
        <div style = min-height:85vh>
            <div style = 'display: flex; flex-direction: column; justify-content: center; min-height: 85vh'>
                <c:choose>
                    <c:when test = '${error==false}'>
                        <h1 style = align-self:center>Declining Payment:</h1>
                        <div class = form-container style = 'text-align:left'>
                            <form method = post action = do.notpaid> 
                                <h2>Reason for declining: </h2>
                                <input type = text name = reason>
                                <input type = hidden name = booked_id value ="${booked_id}">
                                <input type = submit>
                            </form>
                        </div>            
                    </c:when>
                    <c:otherwise>
                        <div style = 'display: flex; justify-content:center; align-items:center'>
                            <div class = error-container>
                                <h1>Oops! Something went wrong </h1>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class = button-container>
                    <a class = nav-button href = do.profile>Back To Profile</a>
                </div>
            </div> 
        </div>
        <button class="dark-mode-toggle" onclick="toggleDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
    </body>
    <jsp:include page="include/footer.jsp"/>
</html>
