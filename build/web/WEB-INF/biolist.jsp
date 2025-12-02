<%-- 
    Document   : biolist
    Created on : Nov 26, 2024, 3:03:34 PM
    Author     : Hawy
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>UAPTutors</title>    
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">   
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


        <meta name="viewport" content="width=device-width, initial-scale=1.0">



        <style>
            .modal { display: flex; align-items: center; justify-content: center; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.5); }
            .modal-content {padding: 20px; border-radius: 5px; text-align: center; }
            .modal-content button { margin: 5px; }
            body { font-family: Arial, sans-serif; }
            .overlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
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
        <c:if test = '${error!=null&&error==true}'>
            <div style="height:85vh">
                <div class="container">
                    <div class="validate-container">
                        <h1>ERROR: NO USER FOUND</h1>
                        <a class="nav-button" href="index.jsp">Go Back To Home</a>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test = '${error==null||error == false}'>
            <div class='education-container' style = min-height:85vh>
                <div>
                    <div style = 'display:flex; justify-content:space-between;'>
                        <h2>${title}</h2>                
                        <c:choose>
                            <c:when test = '${type.equals("subject")}'>
                                <c:if test = '${admin==null}'>
                                    <button onclick='openEducationOverlay()'>Add</button>
                                </c:if>
                            </div>
                            <script>
                                function openModal(categoryName, subjectName) {
                                    document.getElementById('modalCategoryName').value = categoryName;
                                    document.getElementById('modalSubjectName').value = subjectName;
                                    document.getElementById('deleteModal').style.display = 'flex';
                                }
                                function closeModal() {
                                    document.getElementById('deleteModal').style.display = 'none';
                                }
                                function openEducationOverlay() {
                                    document.getElementById('education-overlay').style.display = 'flex';
                                }
                                function closeEducationOverlay() {
                                    document.getElementById('education-overlay').style.display = 'none';
                                }
                            </script>
                            <div id='education-overlay' class='overlay'>
                                <div class='form-container' style='width:800px;max-width:800px '>
                                    <span class='close-button' onclick='closeEducationOverlay()'>&times;</span>
                                    <h3>Add Subjects</h3>
                                    <form action='do.addsubject' method='POST'>
                                        Subject Name:
                                        <input type = 'text' name = 'subjectname'>
                                        Category:
                                        <input type = 'text' name='category'>
                                        <input type='submit' value='Save'>
                                        <button type='button' onclick='closeEducationOverlay()'>Cancel</button>
                                    </form>
                                </div>
                            </div> 
                            <c:choose>
                                <c:when test = '${biolist == null || biolist.isEmpty()}'>
                                    <p>No Information Yet</p>
                                </c:when>
                                <c:otherwise>
                                    <ul class='profile'>
                                        <c:forEach var = 'item' items = '${biolist}'>
                                            <li class='profile'>
                                                <div style = 'display:flex;justify-content:space-between'>
                                                    <h2>${item.getSubject_name()}</h2>
                                                    <div>
                                                        <form action='do.editsubject' method='POST' style='display:inline;'>
                                                            <input type='hidden' name='category_name' value='${fn:escapeXml(item.getCategory_name())}'>
                                                            <input type='hidden' name='subject_name' value='${fn:escapeXml(item.getSubject_name())}'>
                                                            <button type='submit' style = 'position:relative'>Edit</button>
                                                        </form>
                                                        <button type='button' onclick='openModal("${fn:escapeXml(item.getCategory_name())}", "${fn:escapeXml(item.getSubject_name())}")' style='position:relative;'>Delete</button>         
                                                    </div>
                                                </div>
                                                ${item.getCategory_name()}
                                            </li>                                                           
                                        </c:forEach>
                                    </ul>
                                    <div id='deleteModal' class='modal' style='display:none;'>
                                        <div class='modal-content'>
                                            <h3>Are you sure you want to delete this subject?</h3>
                                            <form id='deleteForm' action='do.deletesubject' method='POST'>
                                                <input type='hidden' name='category_name' id='modalCategoryName'>
                                                <input type='hidden' name='subject_name' id='modalSubjectName'>
                                                <button type='submit'>Confirm</button>
                                                <button type='button' onclick='closeModal()'>Cancel</button>
                                            </form>
                                        </div>
                                    </div>                
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <c:if test = '${admin==null}'>
                                <button onclick='openItemOverlay()'>Add</button>    
                            </c:if>

                        </div>
                        <script>
                            function openModal(item) {
                                document.getElementById('modalItem').value = item;
                                document.getElementById('deleteModal').style.display = 'flex';
                            }
                            function closeModal() {
                                document.getElementById('deleteModal').style.display = 'none';
                            }
                            function openItemOverlay() {
                                document.getElementById('item-overlay').style.display = 'flex';
                            }
                            function closeItemOverlay() {
                                document.getElementById('item-overlay').style.display = 'none';
                            }
                        </script>
                        <div id='item-overlay' class='overlay'>
                            <div class='form-container' style='width:800px;max-width:800px '>
                                <span class='close-button' onclick='closeItemOverlay()'>&times;</span>
                                <h3>Add ${type}</h3>
                                <form action='do.add${type}' method='POST'>
                                    Name:
                                    <input type = 'text' name = 'item'>
                                    <input type='submit' value='Save'>
                                    <button type='button' onclick='closeItemOverlay()'>Cancel</button>
                                </form>
                            </div>
                        </div> 
                        <c:choose>
                            <c:when test = '${biolist == null || biolist.isEmpty()}'>
                                <p>No Information Yet</p>
                            </c:when>
                            <c:otherwise>
                                <ul class='profile'>
                                    <c:forEach var = 'item' items = '${biolist}'>
                                        <li class='profile'>
                                            <div style = 'display:flex;justify-content:space-between'>
                                                <h2>${item}</h2>
                                                <div>
                                                    <form action="do.edit${type}" method='POST' style='display:inline;'>
                                                        <input type='hidden' name='item' value='${fn:escapeXml(item)}'>
                                                        <button type='submit' style = 'position:relative'>Edit</button>
                                                    </form>
                                                    <button type='button' onclick='openModal("${fn:escapeXml(item)}")' style='position:relative;'>Delete</button>

                                                </div>
                                            </div>
                                        </li>                                                           
                                    </c:forEach>
                                </ul>
                                <div id='deleteModal' class='modal' style='display:none;'>
                                    <div class='modal-content'>
                                        <h3>Are you sure you want to delete this ${type}?</h3>
                                        <form id='deleteForm' action='do.delete${type}' method='POST'>
                                            <input type='hidden' name='item' id='modalItem'>
                                            <button type='submit'>Confirm</button>
                                            <button type='button' onclick='closeModal()'>Cancel</button>
                                        </form>
                                    </div>
                                </div>            
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise> 
                </c:choose>
            </div>

            <br><a href = '${href1}' class ='save-button'>${href1msg}</a>
        </div>
        <c:choose>
            <c:when test = "${admin!=null||adminlogout!=null}">
                <button class="dark-mode-toggle" onclick="toggleAdminDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
                </c:when>
                <c:otherwise>
                <button class="dark-mode-toggle" onclick="toggleDarkMode()"><i class="fa fa-lightbulb-o"></i></button>
                </c:otherwise>
            </c:choose>  
        </c:if>
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