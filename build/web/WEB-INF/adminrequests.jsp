<%-- 
    Document   : adminrequests
    Created on : Nov 27, 2024, 6:16:42 PM
    Author     : Hawy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Requests to Approve</title>
          <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admin.css">
        <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admindarkmode.css">
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
    </head>
    <jsp:include page="include/adminheader.jsp" />
    <body>
        <div style="min-height:85vh">
            <c:choose>
                <c:when test="${list == null || list.isEmpty()}">
                    <div class="profile-container" style="justify-content: center;">
                        <h1>No requests found</h1>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="req" items="${list}">
                        <c:choose>

                            <c:when test="${req.adminId != currentadmin.id}">
                                <div class="profile-container">

                                    <c:choose>
                                        <c:when test="${req.user.profileImage != null}">
                                            <img id="profilePic" src="../images/${req.user.id}.jpg" alt="Profile Picture" class="profile-pic">
                                        </c:when>
                                        <c:otherwise>
                                            <img id="profilePic" src="defaultprofilepic.jpg" alt="Profile Picture" class="profile-pic">
                                        </c:otherwise>
                                    </c:choose>

                                    <div style="display: flex; flex-direction: column; justify-content: center;">

                                        <p>Action: ${req.actionType}</p>

                                        <a href="do.profile?user=${req.profileId}">
                                            <h2>${req.user.first_name} ${req.user.last_name}</h2>
                                        </a>
                                    </div>

                                    <div style="margin-left: auto; display: flex; align-items: center;">

                                        <form action="do.delete" method="post">
                                            <input type="hidden" name="approval" value="${req.actionId}">
                                            <input type="submit" name="choice" value="Approve">
                                            <input type="submit" name="choice" value="Deny">
                                        </form>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
        <button class="dark-mode-toggle" onclick="toggleAdminDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
    </body>
    <jsp:include page="include/adminfooter.jsp" />
</html>
