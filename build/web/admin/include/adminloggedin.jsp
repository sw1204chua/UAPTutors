<%-- 
    Document   : adminloggedin
    Created on : 10 8, 24, 9:18:25 PM
    Author     : Chace
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
</div>
    <header>
        
        <nav class="navbar">
            <div class="logo">
                <img class="logo-img" src="logo.png" alt="logo">
                <a href ="index.jsp" style="text-decoration:none" class="logo-text">&nbsp;&nbsp;&nbsp;&nbsp;UA&P Tutors - ADMIN</a>
            </div>
            <div class="search-container">
              
            </div>
            <ul>
                <li><a href="do.adminrequest">Admin Requests</a></li>
                <li><a href="index.jsp">Home</a></li>
                <li><a href="do.AdminTutorList">List of All Users</a></li>
                <li><a href ="do.adminlogout">Log Out</a></li>
                <li><p style ='color:white'>Welcome, ${sessionScope.email}</p></li>
            </ul>
            
        </nav>
    </header>
 