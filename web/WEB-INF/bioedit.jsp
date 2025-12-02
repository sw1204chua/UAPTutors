<%-- 
    Document   : bioedit
    Created on : Nov 26, 2024, 7:32:32 PM
    Author     : Hawy
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>UAPTutors</title>    
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">



        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <c:choose>
            <c:when test = "${admin==null}">               
                
                <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/darkmode.css">
                <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/newcss.css">
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
                <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admin.css">
                <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admindarkmode.css">
                <script>
                    // Toggle dark mode function
                    function toggleAdminDarkMode() {
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
        <c:when test = "${admin!=null}">
            <jsp:include page="include/adminheader.jsp"/>           
        </c:when>   
        <c:otherwise>
            <jsp:include page="include/header.jsp"/>
        </c:otherwise>
    </c:choose>
    <body>
        <div style = min-height:85vh>
            <div class='validate-container'>

                <c:choose>
                    <c:when test = '${type.equals("subject")}'>                  
                        <c:choose>
                            <c:when test = '${error == true}'>
                                <h1>Oops! Something went wrong</h1>
                            </c:when>
                            <c:otherwise>
                                <div class = form-container style = 'max-width:none'>
                                    <form action = 'do.savesubjectedits' method = 'POST' style = 'width:100%'> 
                                        <h3>Edit ${fn:escapeXml(item)}</h3>
                                        Subject Name:<br>
                                        <input type = 'text' name = subject_name value = '${fn:escapeXml(item)}'>
                                        Category<br>
                                        <input type = 'text' name = category value = ${fn:escapeXml(item2)}>
                                        <input type = hidden name = oldsubject_name value =${fn:escapeXml(item)}>
                                        <button type = submit>Save</button>
                                    </form>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>                      
                        <c:choose>
                            <c:when test = '${error == true}'>
                                <h1>Oops! Something went wrong</h1>
                            </c:when>
                            <c:otherwise>
                                <div class = form-container style = 'max-width:none'>
                                    <form action = 'do.save${type.toLowerCase()}edits' method = 'POST' style = 'width:500px'> 
                                        <h3>Edit ${fn:escapeXml(item)}</h3>
                                        ${type}:<br>
                                        <input type = 'text' name = item value = '${fn:escapeXml(item)}'>
                                        <input type = hidden name = old_item value ='${fn:escapeXml(item)}'}>
                                        <button type = submit>Save</button>
                                    </form>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise> 
                </c:choose>


                <a href = '${href1}' class ='nav-button'>${href1msg}</a>
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
            <jsp:include page="include/footer.jsp"/>
        </c:otherwise>
    </c:choose>
</html>
