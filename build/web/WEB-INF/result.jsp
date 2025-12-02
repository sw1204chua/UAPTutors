<%-- 
    Document   : result
    Created on : Nov 25, 2024, 11:13:05 PM
    Author     : Hawy
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>UA&P Tutors</title>  
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <c:choose>
            <c:when test = "${admin==null&&adminlogout==null}">
                <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/newcss.css">
                <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/darkmode.css">
            </c:when>   
            <c:when test = '${adminlogout!=null}'>
                <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admin.css">
                <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admindarkmode.css">
            </c:when>
            <c:otherwise>
                <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admin.css">
                <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admindarkmode.css">
            </c:otherwise>
        </c:choose>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <c:choose>
            <c:when test = "${admin==null&&adminlogout==null}">

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
            </c:when>   
            <c:otherwise>

                <script>
                    // Toggle dark mode function
                    function toggleAdminDarkMode() {
                        console.log("Toggle button clicked!");
                        document.body.classList.toggle('admin-dark-mode');
                        const mode = document.body.classList.contains('admin-dark-mode') ? 'dark' : 'light';
                        localStorage.setItem('theme', mode); // Save preference
                    }

                    // Load saved theme preference on page load
                    window.onload = function () {
                        if (localStorage.getItem('theme') === 'dark') {
                            document.body.classList.add('admin-dark-mode');
                        }
                    };
                </script>
            </c:otherwise>
        </c:choose>


    </head>
    <c:choose>
        <c:when test = "${admin!=null&&adminlogout==null}">
            <jsp:include page="include/adminheader.jsp"/>           
        </c:when>   
        <c:when test = '${adminlogout!=null}'>
            <jsp:include page="include/adminheader.jsp"/>    
        </c:when>
        <c:otherwise>
            <jsp:include page="include/header.jsp"/>
        </c:otherwise>
    </c:choose>
    <body>
        <div style = min-height:85vh>
            <div class = validate-container >

                <h1>${msg}</h1>                       
                <c:choose>
                    <c:when test = "${!empty href1}">   
                        <a class ='nav-button' href = '${href1}'>${href1msg}</a>
                    </c:when> 
                </c:choose>
                <c:choose>
                    <c:when test = "${admin!=null&&adminlogout==null}">
                        <a class ='nav-button' href = '${pageContext.servletConfig.servletContext.contextPath}/admin/index.jsp'>Go To Home</a>          
                    </c:when>  
                    <c:when test = "${adminlogout!=null}">
                        <a class ='nav-button' href = '${pageContext.servletConfig.servletContext.contextPath}/index.jsp'>Go To Main Website</a>          
                    </c:when> 
                    <c:otherwise>
                        <a class ='nav-button' href = '${pageContext.servletConfig.servletContext.contextPath}/index.jsp'>Go back to Home</a>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
        <c:choose>
            <c:when test = "${admin!=null||adminlogout!=null}">
                <button class="dark-mode-toggle" onclick="toggleAdminDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
                </c:when>
                <c:otherwise>
                <button class="dark-mode-toggle" onclick="toggleDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
                </c:otherwise>
            </c:choose>    
    </body>
    <c:choose>
        <c:when test = "${admin!=null}">
            <jsp:include page="include/adminfooter.jsp"/>           
        </c:when>   
        <c:otherwise>
            <jsp:include page="include/footer.jsp"/>;
        </c:otherwise>
    </c:choose>

</html>