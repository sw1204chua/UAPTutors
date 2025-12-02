<%-- 
    Document   : searchresults
    Created on : Nov 27, 2024, 6:52:00 PM
    Author     : Hawy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>UA&P Tutors</title>       
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        
        <link rel='stylesheet' href='darkmode.css'>  
        <link rel="stylesheet" href="newcss.css">
        
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
    </head>
    <body>

        <jsp:include page="include/header.jsp"/>


        <div class="tutor-container">
            <div class="filter-container">
                <form action="do.searchresult" method="POST">
                    <h1>Filters:</h1>

                    <h2>Type Of User: </h2>
                    <c:choose>
                        <c:when test="${usertype.contains('Student')}">
                            <input type="checkbox" name="usertype" value="Student" checked> Student<br>
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" name="usertype" value="Student"> Student<br>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${usertype.contains('Tutor')}">
                            <input type="checkbox" name="usertype" value="Tutor" checked> Tutor<br>
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" name="usertype" value="Tutor"> Tutor<br>
                        </c:otherwise>
                    </c:choose>

                    <h2>Subject Category:</h2>
                    <c:forEach var="subject" items="${categories}">
                        <c:choose>
                            <c:when test="${categoryfilter.contains(subject)}">
                                <input type="checkbox" name="subject" value="${subject}" checked>${subject}<br>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" name="subject" value="${subject}">${subject}<br>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <input type="hidden" name="searchquery" value="${searchQuery}">
                    <input type="submit" value="Save" class="save-button" id="filter">
                </form>
            </div>

            <div class="results-container">
                <h1 style="text-align:center">Showing Search Results For: ${searchQuery}</h1>
                <c:if test="${empty searchResults}">
                    <div class="validate-container">
                        <p>No users found with the search: ${searchQuery}</p>
                    </div>
                </c:if>

                <c:forEach var="result" items="${searchResults}">
                    <div class="profile-container">
                        <c:choose>
                            <c:when test="${not empty result.profileImage}">
                                <img id="profilePic" src="images/${result.id}.jpg" alt="Profile Picture" class="profile-pic">
                            </c:when>
                            <c:otherwise>
                                <img id="profilePic" src="defaultprofilepic.jpg" alt="Profile Picture" class="profile-pic">
                            </c:otherwise>
                        </c:choose>

                        <div style="max-width:75%">
                            <c:choose>
                                <c:when test="${user != null && result.id == user.id}">
                                    <a href="profile/do.profile"><h1>${result.first_name} ${result.last_name} (You)</h1></a>
                                </c:when>
                                <c:when test="${result.isTutor}">
                                    <a href="do.searchprofile?user=${result.id}"><h1>${result.first_name} ${result.last_name} (Tutor)</h1></a>
                                </c:when>
                                <c:otherwise>
                                    <a href="do.searchprofile?user=${result.id}"><h1>${result.first_name} ${result.last_name} (Student)</h1></a>
                                </c:otherwise>
                            </c:choose>

                            <c:if test="${result.isTutor}">
                                <c:choose>
                                    <c:when test = '${result.getSubject()!=null && !result.getSubject().isEmpty()}'>
                                        Subjects: 
                                        <c:if test ='${result.getSubject().size()==1}'>
                                            <c:set var = 'subject' value = '${result.getSubject()[0]}' />                                           
                                        </c:if>
                                        <c:forEach var ='subject' varStatus = 'i' step = '1' items = '${result.getSubject()}'>
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
                            </c:if>                            
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="invis-container"></div>
        </div>
            <button class="dark-mode-toggle" onclick="toggleDarkMode()"><i class="fa fa-lightbulb-o"></i></button>   

        <jsp:include page="include/footer.jsp"/>
    </body>
</html>
