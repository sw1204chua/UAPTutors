
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>UA&P Tutors</title>  
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">        
        <link rel ="stylesheet" href ="../darkmode.css">
        <link rel ="stylesheet" href ="newcss.css">
        <link rel ="stylesheet" href ="../newcss.css">
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
        <div class='education-container' style='min-height:85vh'>
            <div style='position:absolute; top:10px; right:10px; display:flex; gap:8px'>
            </div>

            <div class='form-container' style=';max-width:800px'>
                <h2>Comment</h2>
                <c:choose>
                    <c:when test ='${error==true}'>    
                        <p>Oops! Something went wrong</p>
                    </c:when>

                    <c:otherwise> 
                        <form action='do.savecomment' method='POST'>
                            <label for='rating'>Rating (1-5):</label>
                            <select name='rating' id='rating' required>
                                <option value='1'>1</option>
                                <option value='2'>2</option>
                                <option value='3'>3</option>
                                <option value='4'>4</option>
                                <option value='5'>5</option>
                            </select>
                            <label for='comment_content'>Comment:</label>
                            <textarea name='comment_content' id='comment_content' required></textarea>
                            <input type = hidden name = booked_id value =${booked_id}> 
                            <input type ='hidden' name ='random' value ='${random}'>
                            <button type='submit'>Submit Comment</button>
                        </form>
                    </c:otherwise>
                </c:choose> 
            </div>
        </div>
        <button class="dark-mode-toggle" onclick="toggleDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
    </body>
    <jsp:include page="include/footer.jsp"/>;
</html>