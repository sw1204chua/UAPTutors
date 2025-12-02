<%-- 
    Document   : adminheader
    Created on : 10 8, 24, 5:02:15 PM
    Author     : Chace
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<header>
    <c:choose>
        <c:when test='${sessionScope.currentadmin == null}'>
            <nav class="navbar">
                <div class="logo">
                    <img class="logo-img" src="logo.png" alt="logo">
                    <a href="${pageContext.servletConfig.servletContext.contextPath}/admin/index.jsp" class="logo-text">&nbsp;&nbsp;&nbsp;&nbsp;UA&P Tutors - ADMIN</a>
                </div>
                <ul>
                    <li><a href="index.jsp">Back to UA&P Tutors</a></li>
                </ul>
            </nav>
        </c:when>
        <c:otherwise>
            <nav class="navbar">
                <div class="logo">
                    <img class="logo-img" src="logo.png" alt="logo">
                    <a href="index.jsp" class="logo-text">&nbsp;&nbsp;&nbsp;&nbsp;UA&P Tutors - ADMIN</a>
                </div>
                <ul>
                    <li><a href="${pageContext.servletConfig.servletContext.contextPath}/admin/do.adminrequest">Admin Requests</a></li>
                    <li><a href="${pageContext.servletConfig.servletContext.contextPath}/admin/index.jsp">Home</a></li>
                    <li><a href="${pageContext.servletConfig.servletContext.contextPath}/admin/do.AdminTutorList">List of All Users</a></li>
                    <li><a href="${pageContext.servletConfig.servletContext.contextPath}/do.adminlogout">Log Out</a></li>
                    <li><p style="color:white">Welcome, ${sessionScope.email}</p></li>
                </ul>
            </nav>
        </c:otherwise>
    </c:choose>
</header>
