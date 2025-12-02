
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>UAPTutors</title>    
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">       
        <link rel ="stylesheet" href ="../darkmode.css">
        <link rel = 'stylesheet' href = '../newcss.css'>
        <link rel ="stylesheet" href ="newcss.css">
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
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

    </head>

    <jsp:include page="include/header.jsp"/>

    <body>
        <div style = 'display: flex; flex-direction: column; justify-content: center; min-height: 85vh'>
            <c:choose>  
                <c:when test = '${error == false}'>

                    <h1 style = text-align:center>Booking A Tutoring Session With ${tutor.getFirst_name()} ${tutor.getLast_name()}</h1>
                    <div class = form-container style = max-width:none>
                        <form action='do.createbooking' method='POST'>
                            <h2>Booking Details:</h2>
                            <div class='form-row'>
                                <div class='form-group'>
                                    <label for='tutor'>Tutor:</label>               
                                    <input type='text' id='tutor' value=' ${tutor.getFirst_name()} ${tutor.getLast_name()}' readonly>
                                </div>
                                <div class='form-group'>
                                    <label for='subject'>Subject:</label>
                                    <input type='text' id='subject' value='${listing.getSubject()}' readonly>
                                </div>
                            </div>

                            <div class='form-row'>
                                <div class='form-group'>
                                    <label for='price'>Price:</label>
                                    <input type='text' id='price' value='${listing.getPrice()}' readonly>
                                </div>


                                <div class='form-group'>
                                    <label for='paymentmethod'>Payment Method:</label>
                                    <input type='text' id='paymentmethod' value='${listing.getMop()}' readonly>
                                </div>
                            </div>

                            <div class='form-row'>
                                <div class='form-group'>
                                    <label for='number'>Number:</label>  
                                    <input type='text' id='number' value='${listing.getNumber()}' readonly>
                                </div>

                                <div class='form-group'>
                                    <label for='date'>Date:</label>
                                    <input type='text' id='date' value='${date}' readonly>
                                </div>
                            </div>

                            <input type='hidden' name='date' value='${date}'>
                            <input type='hidden' name='listingid' value='${listingid}'>
                            <input type ='hidden' name ='random' value='${random}'>
                            <input type='submit' value='Book'>


                        </form>
                    </div>     
                    <div class = button-container>
                        <a class = nav-button href = '/UAPTutors/do.searchprofile?user=${tutor.getId()}'>Back To Tutor Profile</a>                       
                        <a class = nav-button href = '/UAPTutors/index.jsp'>Back To Home</a>
                    </div>
                </c:when>
                <c:otherwise>   
                    <div style = 'display: flex; justify-content:center; align-items:center'>
                        <div class = error-container>
                            <h1>Oops! Something went wrong </h1>
                        </div>
                    </div>
                    <div class = button-container>
                        <a class = nav-button href = '/UAPTutors/index.jsp'>Back To Home</a>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
        <button class="dark-mode-toggle" onclick="toggleDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
    </body>

    <jsp:include page="include/footer.jsp"/>;

</html>