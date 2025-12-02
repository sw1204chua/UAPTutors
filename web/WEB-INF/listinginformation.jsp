<%-- 
    Document   : listinginformation
    Created on : 11 27, 24, 8:22:29 AM
    Author     : 220129
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>UA&P Tutors</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel='stylesheet' href='../darkmode.css'>
        <link rel='stylesheet' href='../newcss.css'>
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
    <jsp:include page="include/header.jsp"/>
    <body>
        <div style = 'display: flex; flex-direction: column; justify-content: center; min-height: 85vh'>
            <c:choose>
                <c:when test = '${validDate&&validTutor&&hasSubject}'>

                    <h1 style = align-self:center> Creating A Listing</h1>
                    <div class = form-container style = 'max-width:none'>

                        <form action='do.createlisting' method='POST'>

                            <div class='form-row'>
                                <div class='form-group'>
                                    <label for='date'>Start Date:</label>
                                    <input type='date' name='date' id='startdate' value='${date}'>
                                </div>
                            </div>

                            <div class='form-row'>
                                <div class='form-group'>
                                    <label for='tutor'>Tutor Name:</label>
                                    <input type='text' name='tutor' id='tutor' value='${tutor.getFirst_name()} ${tutor.getLast_name()}' readonly>
                                </div>
                                <div class='form-group'>
                                    <label for='subject'>Subject:</label>
                                    <select name='subject' id='subject'>
                                        <c:forEach var = 'subject' items = '${tutor.getSubject()}'>
                                            <option value='${subject.getSubject_name()}'> ${subject.getSubject_name()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class='form-row'>
                                <div class='form-group'>
                                    <label for='paymentmethod'>Payment Method:</label>
                                    <select name='paymentmethod' id='paymentmethod'>
                                        <option value='GCash'>GCash</option>
                                        <option value='Maya'>Maya</option>
                                    </select>
                                </div>
                                <div class='form-group'>
                                    <label for='phonenumber'>Phone Number:</label>
                                    <input type='number' name='number' id='phonenumber' min='0' placeholder='0917123456' required>
                                </div>
                            </div>

                            <div class='form-row'>
                                <div class='form-group'>
                                    <label for='price'>Price (PHP):</label>
                                    <input type='number' name='price' id='price' min='0' step='1' placeholder='No Centavos' required>
                                </div>
                                <div class='form-group'>
                                    <label for='repeat'>Repeat Listing (Max 4x):</label>
                                    <select name='repeat' id='repeat'>
                                        <option value='Once'>Once</option>
                                        <option value='Weekly'>Weekly</option>
                                        <option value='Monthly'>Monthly</option>
                                    </select>
                                </div>
                            </div>

                            <input type = 'hidden' name = tutorId value ='${tutor.getId()}'>
                            <input type ="hidden" name ='random' value ='${random}'
                            <div class='form-row'>
                                <input type='submit' value='Submit' style='width: 100%;'>
                            </div>

                        </form>
                    </div>            
                </c:when>
                <c:otherwise>
                    <div style = 'display: flex; justify-content:center; align-items:center'>
                        <div class = error-container>
                            <h1>Oops! Something went wrong </h1>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class = button-container>
                <a class = nav-button href = do.profile>Back To Tutor Profile</a>                
                <a class = nav-button href = '/UAPTutors/index.jsp'>Back To Home</a>
            </div>
        </div>
        <button class="dark-mode-toggle" onclick="toggleDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
    </body>
    <jsp:include page="include/footer.jsp"/>
</html>
