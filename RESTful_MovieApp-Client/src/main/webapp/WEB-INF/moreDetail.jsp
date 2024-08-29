<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Movie Details</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/moreDetailStyle.css">
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
        <h3>More Details About Movie</h3>
        <c:if test="${result!=null}">
            <p class="notification">${result}</p>
        </c:if>
        <div class="movie-container">
            <div class="movie-details">
                <p><strong>Name:</strong> <c:out value="${movie.name}"/></p><br>
                <p><strong>Duration:</strong> <c:out value="${movie.duration}"/></p><br>
                <p><strong>Release Date:</strong> <c:out value="${movie.releaseDate}"/></p><br>
                <p><strong>Category:</strong> <c:out value="${movie.category}"/></p><br>
                <p><strong>Description:</strong> <c:out value="${movie.description}"/></p><br>
                <p><strong>Rating:</strong> <c:out value="${movie.rating}"/></p>
                
                <form action="movieRating" method="post" class="rating-form">
                    <label for="rating"><strong>Rate The Movie:</strong></label>
                    <select name="rating" id="rating">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                    <input type="hidden" name="name" value="${movie.name}" />
                    <sec:csrfInput />
                    <button type="submit">Submit</button>
                </form>
            </div>

            <div class="movie-image">
                <img src="/getMovieImage?name=${movie.name}" alt="${movie.name}"/>
            </div>
        </div>
    </main>
</body>
</html>
