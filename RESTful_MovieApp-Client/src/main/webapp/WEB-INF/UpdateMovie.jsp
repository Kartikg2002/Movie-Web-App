<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>${appName} - Update Movie</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/UpdateMovieStyle.css">
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

    <main class="content">
        <h3>Update Movie</h3>
        <c:if test="${result != null}">
            <p class="notification">${result}</p>
        </c:if>
        <form action="doUpdateMovie" method="post" enctype="multipart/form-data" class="update-form">
            <div class="form-group">
                <label for="name">Movie Name:</label>
                <input type="text" id="name" name="name" value="${movie.name}" required />
            </div>
            <div class="form-group">
                <label for="duration">Movie Duration:</label>
                <input type="text" id="duration" name="duration" value="${movie.duration}" required />
            </div>
            <div class="form-group">
                <label for="releaseDate">Movie Release Date:</label>
                <input type="date" id="releaseDate" name="releaseDate" value="${movie.releaseDate}" required />
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
                <textarea id="description" name="description" rows="4" required>${movie.description}</textarea>
            </div>
            <div class="form-group">
                <label for="image">Movie Image:</label>
                <input type="file" id="image" name="image" accept="image/*" />
            </div>
            <div class="form-group">
                <img alt="${movie.name}" src="/getMovieImage?name=${movie.name}" class="movie-image" />
            </div>
            
            <input type="hidden" name="oldName" value="${movie.name}" required />
            <sec:csrfInput />
            <button type="submit" class="submit-button">Update Movie</button>
        </form>
    </main>
</body>
</html>
