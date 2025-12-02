<%-- 
    Document   : adminviewprofile
    Created on : Nov 30, 2024, 10:13:54 PM
    Author     : Hawy
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <title>UA&P Tutors</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
            function openEducationOverlay() {
                document.getElementById('education-overlay').style.display = 'flex';
            }
            function closeEducationOverlay() {
                document.getElementById('education-overlay').style.display = 'none';
            }
            function openSkillsOverlay() {
                document.getElementById('skills-overlay').style.display = 'flex';
            }
            function closeSkillsOverlay() {
                document.getElementById('skills-overlay').style.display = 'none';
            }
            function openAwardsOverlay() {
                document.getElementById('awards-overlay').style.display = 'flex';
            }
            function closeAwardsOverlay() {
                document.getElementById('awards-overlay').style.display = 'none';
            }
            function openLanguagesOverlay() {
                document.getElementById('languages-overlay').style.display = 'flex';
            }
            function closeLanguagesOverlay() {
                document.getElementById('languages-overlay').style.display = 'none';
            }
            function openCListingOverlay() {
                document.getElementById('clisting-overlay').style.display = 'flex';
            }
            function closeCListingOverlay() {
                document.getElementById('clisting-overlay').style.display = 'none';
            }
        </script>   
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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.css' rel='stylesheet' />
        <script src=\"https://kit.fontawesome.com/6a85e3c1f2.js\" crossorigin=\"anonymous\"></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js'></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.js'></script>
        <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admin.css">
        <link rel ="stylesheet" href ="${pageContext.servletConfig.servletContext.contextPath}/admindarkmode.css">
    </head>

    <jsp:include page="include/adminheader.jsp" />

    <body>          
        <c:choose>
            <c:when test="${not error}">
                <!-- Main Container -->
                <div class='container' style='justify-content:space-evenly'>
                    <!-- Profile Section -->
                    <div class='main-container'>
                        <div class='profile-container'>
                            ${imageTag}
                            <div id='picture-overlay' class='overlay'>
                                <div class='form-container' style='width:800px;max-width:800px'>
                                    <span class='close-button' onclick='closePictureOverlay()'>&times;</span>
                                    <h3>Upload Photo</h3>
                                    <form action='do.editphoto' method='POST' enctype='multipart/form-data'>
                                        Upload Profile Photo:
                                        <input type="hidden" name="userId" value="${user.id}">
                                        <input type="file" name="profileImage" accept="image/*" required>
                                        <input type="submit" value="Save">
                                        <button type="button" onclick="closePictureOverlay()">Cancel</button>
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
                                    <c:when test="${is_tutor}">
                                        <p>${firstname} ${lastname} is available to be a tutor</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p>${firstname} ${lastname} is <b>not</b> available to be a tutor</p>
                                    </c:otherwise>
                                </c:choose>
                                <p>Average Rating: ${averageRating} stars (${ratingCount} review/s)</p>
                                <button class='profile-button' onclick='openProfileOverlay()'>Update</button>
                                <div id='profile-overlay' class='overlay'>
                                    <div class='form-container' style='width:800px;max-width:800px'>
                                        <span class='close-button' onclick='closeProfileOverlay()'>&times;</span>
                                        <h3>Edit Profile</h3>
                                        <form action='do.editprofile' method='POST'>
                                            First Name: <input type="text" name="firstname" value="${firstname}">
                                            Last Name: <input type="text" name="lastname" value="${lastname}">
                                            Email: <input type="email" name="email" value="${email}">
                                            <input type="hidden" name="id" value="${user.id}">
                                            <input type="hidden" name="password" value="${user.password}">
                                            Do you want to be a tutor? (Must have one subject of expertise)<br>
                                            <div>
                                                <input type="radio" id="yes" name="is_tutor" value="true" <c:if test="${is_tutor}">checked</c:if>>
                                                    <label for="yes">Yes</label>
                                                    <input type="radio" id="no" name="is_tutor" value="false" <c:if test="${not is_tutor}">checked</c:if>>
                                                    <label for="no">No</label><br>
                                                </div>
                                                <input type="submit" value="Save">
                                                <button type="button" onclick="closeProfileOverlay()">Cancel</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- About Me Section -->
                            <div class='aboutme-container'>
                                <h2>About Me</h2>
                                <p id='aboutmeDisplay'>${about}</p>
                            <button class='profile-button' onclick='openAboutOverlay()'>Update</button>
                        </div>
                        <div id='about-overlay' class='overlay'>
                            <div class='form-container' style='width:800px;max-width:800px'>
                                <span class='close-button' onclick='closeAboutOverlay()'>&times;</span>
                                <h3>Edit About Me</h3>
                                <form action='do.editaboutme' method='POST'>
                                    <input type="hidden" name="id" value="${user.id}">
                                    <textarea name="aboutme" style='height:200px'>${about}</textarea><br>
                                    <input type="submit" value="Save">
                                    <button type="button" onclick="closeAboutOverlay()">Cancel</button>
                                </form>
                            </div>
                        </div>

                        <!-- Subjects Section -->
                        <div class='education-container'>
                            <div style='position:absolute; top:10px; right:10px; display:flex; gap:8px'>
                                <a class='edit-add-buttons' href='do.subjectlist?user=${user.id}'>Update</a>
                            </div>
                            <div>
                                <h2>Subjects of Expertise</h2>
                                <c:choose>
                                    <c:when test="${empty usersubjects}">
                                        <p>No Information Yet</p>
                                    </c:when>
                                    <c:otherwise>
                                        <ul class='profile'>
                                            <c:forEach var="subject" items="${usersubjects}">
                                                <li class='profile'><h2>${subject.subject_name}</h2>${subject.category_name}</li>
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
                </div>
                <div class='comment-container'>
                    <h2>Comments</h2>


                    <div class='comment-list'>
                        <c:if test = '${!comments.isEmpty()}'>
                            <c:forEach var = 'comment' items = '${comments}'>
                                <c:set var = 'commentlisting' value = '${comment.getStudentlisting()}'/>
                                <c:set var="commentlisting" value="${comment.studentlisting}" />
                                <c:set var="student" value="${commentlisting.student}" />
                                <c:set var="commentImageTag" value="<img id='profilePic' src='../defaultprofilepic.jpg' alt='Profile Picture' class='profile-pic'>" />
                                <c:if test="${not empty student.profileImage}">
                                    <c:set var="commentImageTag" value="<img id='profilePic' src='../images/${student.id}.jpg' alt='Profile Picture' onerror='this.onerror=null; this.src=../defaultprofilepic.jpg' class='profile-pic'>" />
                                </c:if>
                                <div class='comment-item'>
                                    <br>${commentImageTag}<a href = do.profile?user=${student.id}> ${student.first_name} ${student.last_name}</a>: ${comment.getComment_content()}
                                    <br>Rating: ${comment.getRating()}
                                    <br>Subject: ${commentlisting.subject}
                                    <form action = 'do.deletecomment' method = 'POST'>
                                        <input type = hidden name = commentid value = "${comment.getComment_id()}"> 
                                        <input type = submit value = Delete>
                                    </form>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                </div> 
            </c:when>
            <c:otherwise>
                <div style='min-height:85vh'>
                    <div class='validate-container'>
                        <h1>Oops! Something went wrong</h1>
                        <a class='nav-button' href='index.jsp'>Back to Home</a>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
        <button class="dark-mode-toggle" onclick="toggleAdminDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
    </body>
    
    <jsp:include page="include/adminfooter.jsp" />
</html>
