<%-- 
    Document   : profilesearch
    Created on : Nov 27, 2024, 8:01:54 PM
    Author     : Hawy
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <title>UA&P Tutors</title>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.js"></script>
        <link href='fullcalendar.min.css' rel='stylesheet' />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="darkmode.css"> 
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

        <jsp:include page="include/header.jsp" />


        <c:if test="${!error}">
            <div class="container"> <!-- container for profile and calendar -->

                <!-- Main Section -->
                <div class="main-container">
                    <div class="profile-container">
                        ${imageTag}
                        <div>
                            <h1>${firstname} ${lastname}</h1>
                            <p>Email: ${email}</p>
                            <c:choose>
                                <c:when test="${is_tutor}">
                                    <p>${firstname} ${lastname} is available to be a tutor</p>
                                </c:when>
                                <c:otherwise>
                                    <p>${firstname} ${lastname} is <b>not</b> available to be a tutor</p>
                                </c:otherwise>
                            </c:choose>
                            Average Rating: ${averageRating} stars (${ratingCount} review/s)

                        </div>
                    </div>

                    <!-- About Me Section -->
                    <div class="aboutme-container">
                        <h2>About Me</h2>
                        <p id="aboutmeDisplay">${about}</p>
                    </div>

                    <!-- Subject Section -->
                    <div class="education-container">
                        <h2>Subjects of Expertise</h2>
                        <c:choose>
                            <c:when test="${empty usersubjects}">
                                <p>No Information Yet</p>
                            </c:when>
                            <c:otherwise>
                                <ul class="profile">
                                    <c:forEach items="${usersubjects}" var="line">
                                        <li class="profile">
                                            <h2>${line.subject_name}</h2> ${line.category_name}
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Skills Section -->
                    <div class="skills-container">
                        <h2>Skills</h2>
                        <c:choose>
                            <c:when test="${empty skills}">
                                <p>No Information Yet</p>
                            </c:when>
                            <c:otherwise>
                                <ul class="profile">
                                    <c:forEach items="${skills}" var="line">
                                        <li class="profile"><h2>${line}</h2></li>
                                            </c:forEach>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Awards Section -->
                    <div class="awards-container">
                        <h2>Awards</h2>
                        <c:choose>
                            <c:when test="${empty awards}">
                                <p>No Information Yet</p>
                            </c:when>
                            <c:otherwise>
                                <ul class="profile">
                                    <c:forEach items="${awards}" var="line">
                                        <li class="profile"><h2>${line}</h2></li>
                                            </c:forEach>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Languages Section -->
                    <div class="language-container">
                        <h2>Languages</h2>
                        <c:choose>
                            <c:when test="${empty languages}">
                                <p>No Information Yet</p>
                            </c:when>
                            <c:otherwise>
                                <ul class="profile">
                                    <c:forEach items="${languages}" var="line">
                                        <li class="profile"><h2>${line}</h2></li>
                                            </c:forEach>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div> <!-- main-container -->

                <!-- Calendar Section -->
                <c:if test="${userprofile.getIsTutor() and user != null}">
                    <div class="calendar-container">
                        <h2>${userprofile.first_name}'s Calendar</h2>
                        <div id="calendar"></div>
                    </div>
                </c:if>

                <!-- Available Listings Section -->
                <c:if test="${userprofile.isTutor and user != null}">
                    <div class="listing-container">
                        <h1> Available Listings </h1>
                        <c:set var="listingFound" value="false" />
                        <c:choose>
                            <c:when test="${user == null}">
                                <p>Please Sign In To Book</p><br />
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${listings}" var="listing">
                                    <c:if test="${listing.tutor.id == id and !listing.isPending and !listing.isConfirmed}">
                                        <div style="display:flex; margin-top:10px">
                                            <form action="/UAPTutors/profile/do.listingdates" method="POST" style="display:flex;justify-content:space-around">
                                                <h2>Listing ID: ${listing.id} <br>Subject: ${listing.subject} <br>Price: PHP ${listing.price}</h2>
                                                <input type="hidden" name="listingid" value="${listing.id}" />
                                                <input type="submit" value="See Dates" />
                                            </form>
                                        </div>
                                        <c:set var="listingFound" value="true" />
                                    </c:if>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>

                        <c:if test="${!listingFound}">
                            <p>No Listings Found</p>
                        </c:if>
                    </div>
                </c:if>
            </div>
            <!-- Comment Section -->
            <div class="comment-container">
                <h2>Comments</h2>
                <div class="comment-list">
                    <c:if test="${!empty comments}">
                        <c:forEach items="${comments}" var="comment">
                            <c:set var="commentListing" value="${comment.getStudentlisting()}" />
                       
                            <c:set var="student" value="${commentListing.getStudent()}" />
                            <c:set var="commentImageTag" value="<img id='profilePic' src='defaultprofilepic.jpg' alt='Profile Picture' class='profile-pic'>" />
                            <c:if test="${student.profileImage != null and not empty student.profileImage}">
                                <c:set var="commentImageTag" value="<img id='profilePic' src='images/${student.id}.jpg' alt='Profile Picture' class='profile-pic'>" />
                            </c:if>
                            <div class="comment-item">
                                <br>${commentImageTag} ${student.getFirst_name()} ${student.getLast_name()}: ${comment.getComment_content()}
                                <br>Rating: ${comment.getRating()}
                                <br>Subject: ${commentListing.getSubject()}
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div> 

        </div>
    </c:if>

    <c:if test="${error}">
        <div style="height:85vh">
            <div class="container">
                <div class="validate-container">
                    <h1>ERROR: NO USER FOUND</h1>
                    <a class="nav-button" href="index.jsp">Go Back To Home</a>
                </div>
            </div>
        </div>
    </c:if>

   <script>
        $(document).ready(function () {
            var listingData = ${listingData};

            $('#calendar').fullCalendar({
                header: {left: 'prev', center: 'title', right: 'next'},
                events: listingData,
                editable: false,
                eventLimit: true,               
            });
        });
    </script>
    
    <button class="dark-mode-toggle" onclick="toggleDarkMode()"><i class="fa fa-lightbulb-o"></i></button>

    <jsp:include page="include/footer.jsp" />
</body>
</html>

