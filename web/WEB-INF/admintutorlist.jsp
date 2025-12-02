<%-- 
    Document   : admintutorlist
    Created on : Nov 27, 2024, 5:52:57 PM
    Author     : Hawy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Tutor List</title>
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
    <jsp:include page="include/adminheader.jsp" />
    <body>
        <div style="min-height:85vh">
            <div class="search-container">
                <form action="do.AdminTutorList" method="post">
                    <input type="text" name="search" placeholder="Search..." value="${param.search}">
                    <button type="submit">Search</button>
                </form>
            </div>

            <form action="do.adminapprove" method="post">
                <c:choose>

                    <c:when test="${not empty param.search}">
                        <h1>Showing results for search: ${param.search}</h1>
                    </c:when>
                </c:choose>

                <c:choose>
                    <c:when test="${users == null || users.isEmpty()}">
                        <div class="profile-container" style="justify-content: center">
                            <h1>No users found</h1>
                        </div>
                    </c:when>
                    <c:otherwise>

                        <c:forEach var="u" items="${users}">
                            <c:set var="isRequested" value="false" />
                            <c:forEach var="request" items="${requests}">
                                <c:if test="${u.getId() == request.getProfileId()}">
                                    <c:set var="isRequested" value="true" />
                                </c:if>
                            </c:forEach>

                            <c:if test="${not isRequested}">
                                <div class="profile-container">

                                    <c:choose>
                                        <c:when test="${u.getProfileImage() != null}">
                                            <img id="profilePic" src="../images/${u.id}.jpg" alt="Custom Picture" class="profile-pic">
                                        </c:when>
                                        <c:otherwise>
                                            <img id="profilePic" src="defaultprofilepic.jpg" alt="Profile Picture" class="profile-pic">
                                        </c:otherwise>
                                    </c:choose>

                                    <a style="align-self:center" href="do.profile?user=${u.getId()}">
                                        <h1>${u.getFirst_name()} ${u.getLast_name()}</h1>
                                    </a>

                                    <div style="margin-left:auto; display:flex; align-items:center;">
                                        <input type ='hidden' name ='random' value='${random}'>
                                        <button type="submit" name="clicked" value="${u.id}" style="margin-right: 10px;">Delete</button>
                                        <input type="checkbox" name="clicked" value="${u.id}">
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>


                <div class="delete-button-container">
                    <input type="submit" name="DeleteSelected" value="Delete Selected Profiles">
                </div>
            </form>
        </div>
        <button class="dark-mode-toggle" onclick="toggleAdminDarkMode()"><i class="fa fa-lightbulb-o"></i></button>

    </body>
    <jsp:include page="include/adminfooter.jsp" />
</html>
