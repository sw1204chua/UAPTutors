<%-- 
    Document   : header
    Created on : 10 5, 24, 4:42:23 PM
    Author     : Chace
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
</div>
<header>
    <c:choose>
        <c:when test = '${sessionScope.current == null}'>
            <nav class="navbar">
                <div class="logo">
                    <img class="logo-img" src="logo.png" alt="logo">
                    <a href ="index.jsp" style="text-decoration:none" class="logo-text">&nbsp;&nbsp;&nbsp;&nbsp;UA&P Tutors</a>
                </div>
                <div class="search-container">

                    <form action="/UAPTutors/do.searchresult" method="GET"> 
                        <input type="text" class="search-bar" name="inputFieldName" placeholder="Search...">
                        <button type="submit">Search</button>
                    </form>
                </div>
                <ul>
                    <li><a href="${pageContext.servletConfig.servletContext.contextPath}/index.jsp">Home</a></li>
                    <li><a href="${pageContext.servletConfig.servletContext.contextPath}/do.tutors">Tutors</a></li>
                    <li><a href="${pageContext.servletConfig.servletContext.contextPath}/register.jsp">Register</a></li>
                    <li><a href="${pageContext.servletConfig.servletContext.contextPath}/login.jsp">Login</a></li>
                </ul>          
            </nav>
        </c:when>
        <c:otherwise>
            <nav class="navbar">
                <div class="logo">
                    <img class="logo-img" src="/UAPTutors/logo.png" alt="logo">
                    <a href ="/UAPTutors/index.jsp" style="text-decoration:none" class="logo-text">&nbsp;&nbsp;&nbsp;&nbsp;UA&P Tutors</a>
                </div>
                <div class="search-container">
                    <form action="/UAPTutors/do.searchresult" method="GET"> 
                        <input type="text" name="inputFieldName" placeholder="Search...">
                        <button type="submit">Search</button>
                    </form>
                </div>
                <ul>
                    <li><a href="${pageContext.servletConfig.servletContext.contextPath}/index.jsp">Home</a></li>
                    <li><a href="${pageContext.servletConfig.servletContext.contextPath}/do.tutors">Tutors</a></li>
                    <li><a href="${pageContext.servletConfig.servletContext.contextPath}/profile/do.profile">My Profile</a></li>
                    <li><a href ="${pageContext.servletConfig.servletContext.contextPath}/do.logout">Log Out</a></li>
                    <li><p style ='color:white'>Welcome, ${sessionScope.first_name}</p></li>
                </ul>
            </nav>
           
        </c:otherwise>
    </c:choose>
</header>

