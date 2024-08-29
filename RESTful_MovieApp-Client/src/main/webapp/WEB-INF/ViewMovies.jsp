<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>${appName} - Movie List</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ViewMoviesStyle.css">
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
        <section class="search-section">
            <form action="searchMovie" method="get" class="search-form">
                <label for="movieName">Movie Name:</label>
                <input type="search" id="movieName" name="name" required />
                <button type="submit">Search</button>
            </form>
        </section>
        
        <section class="movies-section">
            <h3>Movies</h3>
            <c:if test="${result != null}">
                <p class="notification">${result}</p>
            </c:if>
            <c:forEach items="${movies}" var="m">
                <div class="movie-card">
                    <div class="movie-details">
                        <p><strong>Name:</strong> <c:out value="${m.name}"/></p>
                        <p><strong>Duration:</strong> <c:out value="${m.duration}"/></p>
                        <p><strong>Release Date:</strong> <c:out value="${m.releaseDate}"/></p>
                        <p><strong>Category:</strong> <c:out value="${m.category}"/></p>
                        <p><strong>Description:</strong> <c:out value="${m.description}"/></p>
                        <p><strong>Rating:</strong> <c:out value="${m.rating}"/></p>
						<c:if test="${pageContext.request.userPrincipal.authorities=='[ROLE_ADMIN]'}">
							<form action="deleteMovie" method="post" class="delete-form">
								<input type="hidden" name="name" value="${m.name}" required />
								<sec:csrfInput />
								<button type="submit">Delete Movie</button>
							</form>
							<a href="/updateMovie?name=${m.name}" class="update-link">UpdateMovie</a>
						</c:if>
					</div>
                    <div class="movie-image">
                        <img alt="${m.name}" src="/getMovieImage?name=${m.name}" />
                    </div>
                </div>
            </c:forEach>
        </section>
    </main>
</body>
</html>
