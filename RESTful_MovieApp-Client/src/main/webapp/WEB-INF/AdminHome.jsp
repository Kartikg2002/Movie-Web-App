<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Home</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/AdminHomeStyle.css">
</head>
<body>
    <div class="header">
        <div class="header-content">
            <h1>${appName}</h1>
            <div class="header-nav">
                <a href="/">Home</a>
                <a href="/viewMovies">View Movies</a>
            </div>
        </div>
    </div>
    
    <div class="form-container">
        <h3>Add Movie</h3>
        
        <c:if test="${result!=null}">
            <p class="notification">${result}</p>
        </c:if>
        
        <form action="addMovie" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">Movie Name:</label>
                <input type="text" name="name" id="name" required/>
            </div>

            <div class="form-group">
                <label for="time">Movie Duration:</label>
                <input type="text" id="time" name="duration" placeholder="HH:MM:SS" pattern="^([01]\d|2[0-3]):([0-5]\d):([0-5]\d)$" required>
            </div>

            <div class="form-group">
                <label for="releaseDate">Movie Release Date:</label>
                <input type="date" name="releaseDate" id="releaseDate" required/>
            </div>

            <div class="form-group">
                <label for="category">Movie Category:</label>
                <select name="category" id="category">
                    <option value="Comedy">Comedy</option>
                    <option value="Thrill">Thrill</option>
                    <option value="Action">Action</option>
                    <option value="Romance">Romance</option>
                </select>
            </div>

            <div class="form-group">
                <label for="description">Movie Description:</label>
                <input type="text" name="description" id="description" required/>
            </div>

            <div class="form-group">
                <label for="image">Movie Photo:</label>
                <input type="file" accept="image/*" name="image" id="image"/>
            </div>
            
            <sec:csrfInput />
            <input type="submit" value="Add Movie" class="submit-button"/>
        </form>
    </div>
</body>
</html>
