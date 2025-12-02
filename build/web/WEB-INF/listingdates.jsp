<%-- 
    Document   : listingdates
    Created on : Nov 27, 2024, 6:29:29 PM
    Author     : Hawy
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>UA&P Tutors - Book a Session</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
   
    <link rel ="stylesheet" href ="../darkmode.css">
    <link rel="stylesheet" href="../newcss.css">
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
</head>
<body>

    <jsp:include page="include/header.jsp" />


    <div style="display: flex; flex-direction: column; justify-content: center; min-height: 85vh;">

        <c:choose>

            <c:when test="${!error && !empty recurringListings}">
                <h1 style="text-align: center;">
                    Booking A Tutoring Session With ${tutor.first_name} ${tutor.last_name}
                </h1>
                <div class="profile-container" style="display: flex; flex-wrap: wrap; gap: 20px; justify-content: center;">

                    <c:forEach var="listing" items="${recurringListings}">
                        <form class = 'listing-date' action="/UAPTutors/profile/do.booklisting" method="POST">
                            <h2 style="font-size: 1.2em; margin-bottom: 10px;">Date: ${listing.date}</h2>
                            <input type="hidden" name="listingid" value="${listing.id}">
                            <input type="hidden" name="date" value="${listing.date}">
                            <input type="submit" value="Book">
                        </form>
                    </c:forEach>
                </div>
            </c:when>

            <c:when test="${!error && empty recurringListings}">
                <div style="display: flex; justify-content:center; align-items:center;">
                    <div class="error-container">
                        <h1>Fully Booked!</h1>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <div style="display: flex; justify-content:center; align-items:center;">
                    <div class="error-container">
                        <h1>ERROR</h1>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <div class="button-container">
            <c:if test="${!empty tutor}">
                <a class="nav-button" href="/UAPTutors/do.searchprofile?user=${tutor.id}">Back To Tutor Profile</a>
            </c:if>
            <a class="nav-button" href="/UAPTutors/index.jsp">Back To Home</a>
        </div>
    </div>
    <button class="dark-mode-toggle" onclick="toggleDarkMode()"><i class="fa fa-lightbulb-o"></i></button>

    <jsp:include page="include/footer.jsp" />
</body>
</html>
