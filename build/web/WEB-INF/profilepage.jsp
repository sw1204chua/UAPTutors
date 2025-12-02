<%-- 
    Document   : profilepage
    Created on : Nov 27, 2024, 8:32:08 PM
    Author     : Hawy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>UA&P Tutors</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

        <link rel ="stylesheet" href ="../darkmode.css">
        <link rel='stylesheet' href='../newcss.css'>
        <style>
            #togL, .fa fa-angle-down {
                display: inline-block;
                transition: filter 0.3s ease;
            }
            #togL:hover, .fa fa-angle-down:hover {
                filter: brightness(50%);
                cursor: pointer;
            }
        </style>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <style>
            body { font-family: Arial, sans-serif; }
            .overlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.7);
                z-index: 1000;
                justify-content: center;
                align-items: center;
            }
            .close-button {
                position: absolute;
                top: 10px;
                right: 10px;
                cursor: pointer;
            }
        </style>

        <script type='text/javascript'>
            function openPictureOverlay() {
                document.getElementById('picture-overlay').style.display = 'flex';
            }
            function closePictureOverlay() {
                document.getElementById('picture-overlay').style.display = 'none';
            }
            function openProfileOverlay() {
                document.getElementById('profile-overlay').style.display = 'flex';
            }
            function closeProfileOverlay() {
                document.getElementById('profile-overlay').style.display = 'none';
            }
            function openAboutOverlay() {
                document.getElementById('about-overlay').style.display = 'flex';
            }
            function closeAboutOverlay() {
                document.getElementById('about-overlay').style.display = 'none';
            }

            function openCListingOverlay() {
                document.getElementById('clisting-overlay').style.display = 'flex';
            }
            function closeCListingOverlay() {
                document.getElementById('clisting-overlay').style.display = 'none';
            }
            function openAvailableListingOverlay() {
                document.getElementById('available-listing-overlay').style.display = 'flex';
            }

            function closeAvailableListingOverlay() {
                document.getElementById('available-listing-overlay').style.display = 'none';
            }

            function openPendingListingOverlay() {
                document.getElementById('pending-listing-overlay').style.display = 'flex';
            }

            function closePendingListingOverlay() {
                document.getElementById('pending-listing-overlay').style.display = 'none';
            }

            function openConfirmedListingOverlay() {
                document.getElementById('confirmed-listing-overlay').style.display = 'flex';
            }

            function closeConfirmedListingOverlay() {
                document.getElementById('confirmed-listing-overlay').style.display = 'none';
            }
        </script>      
        <link href='../fullcalendar.min.css' rel='stylesheet' />
        <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js'></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.js'></script>
    </head>
    <jsp:include page="include/header.jsp"/>
    <body>  
        <c:choose>
            <c:when test = '${error == false}'>

                <div class='container' style = 'justify-content:left'> 

                    <div class='main-container'>
                        <div class='profile-container'>
                            ${imageTag}
                            <div id='picture-overlay' class='overlay'>
                                <div class='form-container' style='width:800px;max-width:800px '>
                                    <span class='close-button' onclick='closePictureOverlay()'>&times;</span>
                                    <h3>Upload Photo</h3>
                                    <form action='do.editphoto' method='POST' enctype='multipart/form-data'>
                                        Upload Profile Photo:
                                        <input type = hidden name = userid value = '${user.getId()}'>
                                        <input type=file name=profileImage accept=image/* required>
                                        <input type='submit' value='Save'>
                                        <button type='button' onclick='closePictureOverlay()'>Cancel</button>
                                    </form>
                                </div>
                            </div> 
                            <script>
                                document.getElementById('profilePic').onclick = function () {
                                    openPictureOverlay();
                                };
                            </script>
                            <div>
                                <h1>${firstname} ${lastname}</h1>
                                <p>Email: ${email}</p>
                                <c:choose>
                                    <c:when test = '${is_tutor == true}'>
                                        <p>${firstname} ${lastname} is available to be a tutor</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p>${firstname} ${lastname} is <b>not</b> available to be a tutor</p>
                                    </c:otherwise>
                                </c:choose>
                                Average Rating: ${averageRating} stars (${ratingCount} review/s)
                                <button class ='profile-button' onclick='openProfileOverlay()'>Edit</button>
                                <div id='profile-overlay' class='overlay'>
                                    <div class='form-container' style='width:800px;max-width:800px '>
                                        <span class='close-button' onclick='closeProfileOverlay()'>&times;</span>
                                        <h3>Edit Profile</h3>
                                        <form action='do.editprofile' method='POST'>                          
                                            First Name: <input type = 'text' name='firstname' value = '${firstname}'>
                                            Last Name: <input type = 'text' name='lastname' value = '${lastname}'>
                                            Email: <input type = 'email' name='email' value = '${email}'>
                                            Password: <input type = 'password' name='password'>
                                            Do you want to be a tutor? (Must have one subject of expertise)<br>
                                            <div>
                                                <c:choose>
                                                    <c:when test = '${user.getIsTutor() == true}'>
                                                        <input type = 'radio' id = 'yes' name = 'is_tutor' value = 'true' checked>
                                                        <label for='yes'>Yes</label>
                                                        <input type = 'radio' id = 'no' name = 'is_tutor' value = 'false'>
                                                        <label for='no'>No</label><br>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type = 'radio' id = 'yes' name = 'is_tutor' value = 'true'>
                                                        <label for='yes'>Yes</label>
                                                        <input type = 'radio' id = 'no' name = 'is_tutor' value = 'false' checked>
                                                        <label for='no'>No</label><br>

                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <input type='submit' value='Save'>
                                            <button type='button' onclick='closeProfileOverlay()'>Cancel</button>
                                        </form>
                                    </div>
                                </div> 
                            </div>
                        </div>


                        <div class='aboutme-container'>
                            <h2>About Me</h2>
                            <p id='aboutmeDisplay'>${about}</p>
                            <button class ='profile-button' onclick='openAboutOverlay()'>Update</button>
                        </div>
                        <div id='about-overlay' class='overlay'>
                            <div class='form-container' style='width:800px;max-width:800px '>
                                <span class='close-button' onclick='closeAboutOverlay()'>&times;</span>
                                <h3>Edit About Me</h3>
                                <form action='do.editaboutme' method='POST'>
                                    <textarea name='aboutme' style = 'height:200px'>${about}</textarea><br>
                                    <input type='submit' value='Save'>
                                    <button type='button' onclick='closeAboutOverlay()'>Cancel</button>
                                </form>
                            </div>
                        </div> 

                        <div class='education-container'>
                            <div style = 'position:absolute; top:10px; right:10px; display:flex; gap:8px'>

                                <a class = 'edit-add-buttons' href ='do.subjectlist'>Update</a>
                            </div>
                            <div>
                                <h2>Subjects of Expertise</h2>
                                <c:choose>
                                    <c:when test = '${usersubjects.isEmpty()}'>        
                                        <p>No Information Yet</p>                            
                                    </c:when>
                                    <c:otherwise>
                                        <ul class ='profile'>
                                            <c:forEach var = 'line' items = '${usersubjects}'>
                                                <li class = 'profile'><h2>${line.getSubject_name()}</h2>${line.getCategory_name()}</li>    
                                                    </c:forEach>                                              

                                        </ul> 
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>

                        <div class='skills-container'>
                            <h2>Skills</h2>          
                            <c:if test="${empty skills}">
                                <p>No Information Yet</p>
                            </c:if>
                            <c:if test="${not empty skills}">
                                <ul class="profile">
                                    <c:forEach var="line" items="${skills}">
                                        <li class="profile"><h2>${line}</h2></li>
                                            </c:forEach>
                                </ul>
                            </c:if>

                            <div style = 'position:absolute; top:10px; right:10px; display:flex; gap:8px'>

                                <a class = 'edit-add-buttons' href ='do.skillslist'>Update</a>
                            </div>
                            <div id='skills-overlay' class='overlay'>
                                <div class='form-container' style='width:800px;max-width:800px '>
                                    <span class='close-button' onclick='closeSkillsOverlay()'>&times;</span>
                                    <h3>Add Skill</h3>
                                    <form action='do.addskill' method='POST'>
                                        Skill Name:
                                        <input type = 'text' name = 'skill'>
                                        <input type='submit' value='Save'>
                                        <button type='button' onclick='closeSkillsOverlay()'>Cancel</button>
                                    </form>
                                </div>
                            </div> 
                        </div>

                        <div class='awards-container'>
                            <h2>Awards</h2>
                            <c:if test="${empty awards}">
                                <p>No Information Yet</p>
                            </c:if>
                            <c:if test="${not empty awards}">
                                <ul class="profile">
                                    <c:forEach var="line" items="${awards}">
                                        <li class="profile"><h2>${line}</h2></li>
                                            </c:forEach>
                                </ul>
                            </c:if>

                            <div style='position:absolute; top:10px; right:10px; display:flex; gap:8px'>

                                <a class='edit-add-buttons' href='do.awardslist'>Update</a>
                            </div>
                            <div id='awards-overlay' class='overlay'>
                                <div class='form-container' style='width:800px;max-width:800px'>
                                    <span class='close-button' onclick='closeAwardsOverlay()'>&times;</span>
                                    <h3>Add Award</h3>
                                    <form action='do.addaward' method='POST'>
                                        Award Name:
                                        <input type='text' name='award'>
                                        <input type='submit' value='Save'>
                                        <button type='button' onclick='closeAwardsOverlay()'>Cancel</button>
                                    </form>
                                </div>
                            </div> 
                        </div>

                        <div class='skills-container'>
                            <h2>Languages</h2>
                            <c:if test="${empty languages}">
                                <p>No Information Yet</p>
                            </c:if>
                            <c:if test="${not empty languages}">
                                <ul class="profile">
                                    <c:forEach var="line" items="${languages}">
                                        <li class="profile"><h2>${line}</h2></li>
                                            </c:forEach>
                                </ul>
                            </c:if>

                            <div style='position:absolute; top:10px; right:10px; display:flex; gap:8px'>

                                <a class='edit-add-buttons' href='do.languageslist'>Update</a>
                            </div>
                            <div id='languages-overlay' class='overlay'>
                                <div class='form-container' style='width:800px;max-width:800px'>
                                    <span class='close-button' onclick='closeLanguagesOverlay()'>&times;</span>
                                    <h3>Add Language</h3>
                                    <form action='do.addlanguage' method='POST'>
                                        Language Name:
                                        <input type='text' name='language'>
                                        <input type='submit' value='Save'>
                                        <button type='button' onclick='closeLanguagesOverlay()'>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>


                    </div>


                    <div class='calendar-container'> 

                        <h2>My Calendar</h2>

                        <div id='calendar'></div>
                        <c:if test = '${user!=null&&user.getIsTutor()}'>

                            <h2>Click a day on your calendar to create a listing!</h2>
                        </c:if>

                        <div class = 'profile-container' style = 'margin-left:0; width:auto; flex-direction:column'>
                            <div style = 'display:flex; justify-content:space-between'>
                                <h2>Your Sessions: </h2>

                                <label id ='togL' for='togC'><i class="fa fa-angle-down" style="font-size:24px"></i></label>         

                            </div>
                            <input id ='togC' type ='checkbox'>
                            <div id ='content'>
                                <c:set var = 'sessionFound' value = '${false}' />
                                <table>
                                    <c:forEach var="listing" items="${userSessions}">
                                        <c:if test="${listing.getStudent() != null && listing.getStudent().getId() == user.getId() && !listing.getIsComplete()}">
                                            <c:set var = 'sessionFound' value = '${true}' />
                                            <tr>
                                                <td>
                                                    <div style="display:flex; justify-content:space-between; margin-top:10px;">
                                                        <form action="do.paybooking" method="POST" style="display:flex; justify-content:space-around">
                                                            <h4>Subject: ${listing.getSubject()} <br>Price: PHP${listing.getPrice()} <br>Date: ${listing.getBooking_date()}
                                                                <c:if test="${not empty listing.rejected_payment}">
                                                                    <br>Payment Rejected!<br>Reason: ${listing.getRejected_payment()}
                                                                </c:if>
                                                            </h4>
                                                            <input type="hidden" name="booked_id" value="${listing.getBooked_id()}">
                                                            </td>
                                                            <td>
                                                                <c:if test="${listing.getIsPending()}">
                                                                    <input type="button" value="Pending" style="background-color:#FFA500; margin-left:20px">
                                                                </c:if>
                                                                <c:if test="${listing.getIsPaid()}">
                                                                    <input type="button" value="Waiting for Payment Confirmation" style="background-color:#808080; margin-left:20px">
                                                                </c:if>
                                                                <c:if test="${!listing.getIsPending() && !listing.getIsPaid()}">
                                                                    <input type="submit" value="Pay" style="background-color:#FFA500; margin-left:20px">
                                                                </c:if>
                                                            </td>
                                                        </form>
                                                    </div>
                                            </tr>
                                        </c:if>

                                        <c:if test="${listing.getTutor() != null && listing.getTutor().getId() == user.getId() && !listing.getIsPending() && listing.getIsConfirmed() && !listing.getIsComplete()}">
                                            <c:set var = 'sessionFound' value = '${true}' />
                                            <tr>
                                                <td>
                                                    <div style="display:flex; justify-content:space-between; margin-top:10px;">
                                                        <form action="do.completebooking" method="POST" style="display:flex; justify-content:space-around">
                                                            <h4>Subject: ${listing.getSubject()} <br>Price: PHP${listing.getPrice()} <br>Date: ${listing.getDate()}
                                                                <c:if test="${listing.getReferencenumber()!=null && not empty listing.getReferencenumber()}">
                                                                    <br>Reference: ${listing.getReferencenumber()}
                                                                </c:if>
                                                            </h4>
                                                            <input type="hidden" name="booked_id" value="${listing.getBooked_id()}">
                                                            </td>
                                                            <td>
                                                                <c:if test="${listing.getIsPaid()!=null&&listing.getIsPaid()}">
                                                                    <input type="submit" value="Mark As Complete" style="background-color:green; margin-left:20px">
                                                                </c:if>
                                                                <c:if test="${listing.getIsPaid()==null||!listing.getIsPaid()}">
                                                                    <input type="button" value="Wait for Payment" style="background-color:#FFA500; margin-left:20px">
                                                                </c:if>
                                                            </td>
                                                        </form>

                                                        <c:if test="${listing.getIsPaid()!=null&&listing.getIsPaid()}">
                                                            <td>
                                                                <form action="do.paymentdeclined" method="POST" style="display:flex; justify-content:space-around">
                                                                    <input type="hidden" name="booked_id" value="${listing.booked_id}">
                                                                    <input type="submit" value="Mark as Unpaid" style="background-color:#FFA500; margin-left:20px">
                                                                </form>
                                                            </td>
                                                        </c:if>
                                                    </div>
                                            </tr>
                                        </c:if>
                                    </c:forEach>

                                </table>
                                <c:if test = '${sessionFound == false}'>
                                    No Current Sessions
                                </c:if>
                                <div style='position:static; bottom:10px; left:10px; display:flex; gap:8px'>
                                    <button class='edit-add-buttons' onclick='openCListingOverlay()'>View Completed Listings</button>
                                    <div id='clisting-overlay' class='overlay' style = 'flex-direction:column'>
                                        <div class='form-container' id ='listing'>
                                            <h3>Completed Sessions</h3>
                                        </div>
                                        <div class='form-container' id ='listing'>
                                            <span class='close-button' onclick='closeCListingOverlay()'>&times;</span>

                                            <c:set var="completedFound" value="false" />

                                            <table>
                                                <c:forEach var="listing" items="${userSessions}">
                                                    <c:if test="${listing.student != null}">
                                                        <c:if test="${listing.student.id == user.id || listing.tutor.id == user.id}">
                                                            <c:if test="${listing.isComplete}">
                                                                <c:set var="completedFound" value="true" />
                                                                <tr>
                                                                <form action="do.addcomment" method="POST" style="display:flex; justify-content:space-around">
                                                                    <td>
                                                                        <div style="display:flex; justify-content:space-between; margin-top:10px;">

                                                                            <h4>
                                                                                Tutor: ${listing.tutor.first_name} ${listing.tutor.last_name} <br>
                                                                                Student: ${listing.student.first_name} ${listing.student.last_name} <br>
                                                                                Subject: ${listing.subject} <br>
                                                                                Price: PHP${listing.price} <br>
                                                                                Date: ${listing.date}
                                                                            </h4>
                                                                            <input type="hidden" name="booked_id" value="${listing.booked_id}">

                                                                            </td>
                                                                            <td>
                                                                                <c:if test="${listing.student.id == user.id && !listing.hasComment}">
                                                                                    <input type="submit" value="Leave A Comment" style="background-color:#FFA500; margin-left:20px">
                                                                                </c:if>
                                                                            </td>
                                                                </form>

                                                                </tr>
                                                            </c:if>
                                                        </c:if>
                                                    </c:if>
                                                </c:forEach>
                                            </table>

                                            <c:if test="${!completedFound}">
                                                No Completed Sessions Yet <br>
                                            </c:if>
                                        </div>
                                        <div class='form-container' id ='listing'>
                                            <button type='button' onclick='closeCListingOverlay()'>Back</button>
                                        </div>
                                    </div> 
                                </div>
                            </div>
                        </div>

                    </div> 
                    <c:if test="${user.isTutor}">
                        <div class="listing-container">

                            <h1>Available Listings (${listings.size()}):</h1>
                            <button class="view-all-button" onclick="openAvailableListingOverlay()">View All Available Listings</button>

                            <div id="available-listing-overlay"  style = 'flex-direction:column' class="overlay">
                                <div class="form-container" style="width:60%;max-width:60%;margin:0">        
                                    <h3>Available Listings</h3>
                                </div> 
                                <div class="form-container" id = "listing" style="width:60%;max-width:60%;margin:0;">

                                    <%--<ul class="profile">--%>
                                    <c:set var="listingFound" value="false" />
                                    <c:forEach var="listing" items="${listings}">
                                        <c:if test="${listing.tutor.id == user.id}">
                                            <%--li--%>
                                            <div style="display:flex; margin-top:10px">
                                                <form action="do.deletelisting" method="POST" style="display:flex; justify-content:space-around">
                                                    <h2>Subject: ${listing.subject} <br> Price: PHP${listing.price} <br> Date: ${listing.date} <br> Repeat: ${listing.recur}</h2>
                                                    <input type="hidden" name="listingid" value="${listing.id}">
                                                    <input type="hidden" name="user" value="${user.id}">
                                                    <input type="submit" value="Delete" style="background-color:#FF0000">
                                                </form>
                                            </div>
                                            <c:set var="listingFound" value="true" />
                                            <%--/li--%>
                                        </c:if>
                                    </c:forEach>
                                    <%--/ul--%>
                                    <c:if test="${!listingFound}">
                                        <p>No Available Listings</p>
                                    </c:if>
                                </div>
                                <div class="form-container" id = 'listing'>        
                                    <button type="button" style="display:flex;justify-content:center;align-items:center;" onclick="closeAvailableListingOverlay()">Close</button>     
                                </div> 
                            </div>    
                            <h1>Pending Listings (${pending_count}):</h1>
                            <button class="view-all-button" onclick="openPendingListingOverlay()">View All Pending Listings</button>

                            <!-- Hidden Pending Listings Overlay -->
                            <div id="pending-listing-overlay" style = 'flex-direction:column' class="overlay">
                                <div class ='form-container' id = 'listing'>   
                                    <h3>Pending Listings</h3>
                                </div>
                                <div class="form-container" id = 'listing'>
                                    <span class="close-button" onclick="closePendingListingOverlay()">&times;</span>

                                    <ul class="profile">
                                        <c:set var="pendingFound" value="false" />
                                        <c:forEach var="listing" items="${studentListings}">
                                            <c:if test="${listing.isPending && !listing.isComplete}">
                                                <li>
                                                    <div style="display:flex; margin-top:10px">
                                                        <form action="do.approvebooking" method="POST" style="display:flex; justify-content:space-around">
                                                            <h2>Subject: ${listing.subject} <br> Price: PHP${listing.price} <br> Date: ${listing.booking_date} <br> Student: <a href="../do.searchprofile?user=${listing.student.id}">${listing.student.first_name} ${listing.student.last_name}</a></h2>
                                                            <input type="hidden" name="booked_id" value="${listing.booked_id}">
                                                            <input type="hidden" name="user" value="${user.id}">
                                                            <div>
                                                                <input type="submit" value="Approve" style="background-color:green;width:100%">
                                                                </form>

                                                                <form action="do.declinebooking" method="POST" style="display:flex; justify-content:space-around">
                                                                    <input type="hidden" name="booked_id" value="${listing.booked_id}">
                                                                    <input type="hidden" name="user" value="${user.id}">
                                                                    <input type="submit" value="Decline" style="background-color:#FF0000">
                                                                </form>
                                                            </div>
                                                    </div>
                                                    <c:set var="pendingFound" value="true" />
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                    <c:if test="${!pendingFound}">
                                        <p>No Pending Listings</p>
                                    </c:if>
                                </div>
                                <div class="form-container" style="width:60%;max-width:60%;margin:0">        
                                    <button type="button" style="display:flex;justify-content:center;align-items:center;" onclick="closePendingListingOverlay()">Close</button>     
                                </div> 
                            </div>

                            <h1>Confirmed Listings (${confirmed_count}):</h1>
                            <button class="view-all-button" onclick="openConfirmedListingOverlay()">View All Confirmed Listings</button>

                            <!-- Hidden Confirmed Listings Overlay -->
                            <div id="confirmed-listing-overlay" style = 'flex-direction:column' class="overlay">
                                <div class ='form-container' id = 'listing'>   
                                    <h3>Pending Listings</h3>
                                </div>
                                <div class="form-container" id = 'listing'>

                                    <ul class="profile">
                                        <c:set var="approvedFound" value="false" />
                                        <c:forEach var="listing" items="${studentListings}">
                                            <c:if test="${listing.isConfirmed && !listing.isComplete}">
                                                <li>
                                                    <div style="display:flex; margin-top:10px">
                                                        <form action="do.approvebooking" method="POST" style="display:flex; justify-content:space-around">
                                                            <h2>Subject: ${listing.subject} <br> Price: PHP${listing.price} <br> Date: ${listing.booking_date} <br> Student: <a href="../do.searchprofile?user=${listing.student.id}">${listing.student.first_name} ${listing.student.last_name}</a></h2>
                                                            <input type="button" value="Confirmed" style="background-color:green">
                                                        </form>
                                                    </div>
                                                    <c:set var="approvedFound" value="true" />
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                    <c:if test="${!approvedFound}">
                                        <p>No Confirmed Listings</p>
                                    </c:if>
                                </div>
                                <div class="form-container" id = 'listing'>        
                                    <button type="button" style="display:flex;justify-content:center;align-items:center;" onclick="closeConfirmedListingOverlay()">Close</button>     
                                </div> 
                            </div>
                        </div>
                    </c:if>
                </div>

            </div> 


            <div class='comment-container'>
                <h2>Comments</h2>


                <div class='comment-list'>
                    <c:if test="${not empty comments}">
                        <c:forEach var="comment" items="${comments}">
                            <c:set var="commentlisting" value="${comment.studentlisting}" />
                            <c:set var="student" value="${commentlisting.student}" />
                            <c:set var="commentImageTag" value="<img id='profilePic' src='../defaultprofilepic.jpg' alt='Profile Picture' class='profile-pic'>" />
                            <c:if test="${not empty student.profileImage}">
                                <c:set var="commentImageTag" value="<img id='profilePic' src='../images/${student.id}.jpg' alt='Profile Picture' onerror='this.onerror=null; this.src=../defaultprofilepic.jpg' class='profile-pic'>" />
                            </c:if>
                            <div class='comment-item'>
                                <br>${commentImageTag} <a href = '../do.searchprofile?user=${student.getId()}'>${student.first_name} ${student.last_name}</a>: ${comment.comment_content}
                                <br>Rating: ${comment.rating}
                                <br> Subject: ${commentlisting.subject}
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div> 

        </c:when>
        <c:otherwise>
            <div style = min-height:85vh>
                <div class = validate-container >

                    <h1>Oops! Something went wrong</h1>
                    <a class ='nav-button' href = '../index.jsp'>Back to Home</a>

                </div>
            </div>

        </c:otherwise>
    </c:choose>
    <script>
        $(document).ready(function () {
            var isTutor = ${user.getIsTutor()};

            var listingData = ${listingData};

            $('#calendar').fullCalendar({
                header: {left: 'prev', center: 'title', right: 'next'},
                events: listingData,
                editable: false,
                eventLimit: true,

                // Only add dayClick if the user is a tutor
                dayClick: function (date, jsEvent, view) {
                    if (isTutor) {
                        var clickedDate = date.format(); // Format the date as a string (e.g., YYYY-MM-DD)
                        window.location.href = 'do.listinginformation?date=' + clickedDate + '&tutor=' + ${user.getId()};
                    }
                }
            });
        });
    </script>

    <button class="dark-mode-toggle" onclick="toggleDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
</body>
<jsp:include page="include/footer.jsp"/>
</html>
