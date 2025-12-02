<%-- 
    Document   : tutorlist
    Created on : 11 27, 24, 9:11:55 AM
    Author     : 220129
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>         
        <title>UA&P Tutors</title>

        <meta name='viewport' content='width=device-width, initial-scale=1.0'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

        <link rel='stylesheet' href='darkmode.css'> 
        <link rel='stylesheet' href='newcss.css'>
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
        <div style = min-height:85vh>
            <c:choose>
                <c:when test = '${users == null || users.isEmpty()}'>
                    <div class='profile-container' style = justify-content:center>
                        <p><b>No Available Tutors</b></p>
                    </div>
                </c:when>
                <c:otherwise>

                    <c:forEach var = 'u' items = '${users}'>
                        <div class='profile-container'>
                            <c:choose>
                                <c:when test = '${u.getProfileImage()!=null}'>    
                                    <img id='profilePic' src='images/${u.id}.jpg' alt='Custom Picture' class='profile-pic'>
                                </c:when>
                                <c:otherwise>
                                    <img id='profilePic' src='defaultprofilepic.jpg' alt='Profile Picture' class='profile-pic'>
                                </c:otherwise>
                            </c:choose>
                            <div style = 'max-width:75%'>
                                <c:choose>
                                    <c:when test = '${user!=null&&u.getId()==user.getId()}'>    
                                        <a href=profile/do.profile><h1>${u.getFirst_name()} ${u.getLast_name()} (You)</h1></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href='do.searchprofile?user=${u.getId()}'><h1>${u.getFirst_name()} ${u.getLast_name()}</h1></a>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test = '${u.getSubject()!=null && !u.getSubject().isEmpty()}'>
                                        Subjects: 
                                        <c:forEach var ='subject' varStatus = 'i' step = '1' items = '${u.getSubject()}'>
                                            <c:choose>  
                                                <c:when test = '${i.last}'>
                                                    ${subject.getSubject_name()}
                                                </c:when>
                                                <c:otherwise>
                                                    ${subject.getSubject_name()},
                                                </c:otherwise>

                                            </c:choose>

                                        </c:forEach>
                                    </c:when>                                         
                                </c:choose>
                            </div>
                        </div>    

                    </c:forEach>
                </c:otherwise>
            </c:choose>

        </div>
        <button class="dark-mode-toggle" onclick="toggleDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
    </body>
    <jsp:include page="include/footer.jsp"/>
</html>
