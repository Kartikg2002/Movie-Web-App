<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Reviews</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/homeStyle.css">
</head>
<body>
    <header>
        <h1><u>${appName}</u></h1>
    </header>
    
    <div class="container">
        <h2>All Movies</h2>
        <c:if test="${pageContext.request.userPrincipal.authorities=='[ROLE_ADMIN]'}">
            <a href="/adminHome"><button>Admin</button></a> 
        </c:if>
        <form action="logout"  method="post">
		    <sec:csrfInput />  
	        <button>Logout</button>
	    </form>
    </div>
    
    <c:if test="${result != null}">
        <p class="notification">${result}</p>
    </c:if>
    
    <div class="movie-grid">
        <c:forEach items="${movies}" var="m">
            <div class="movie-card">
                <div class="movie-details">
                    <p>Name: <b><c:out value="${m.name}"/></b></p>
                    <p>Duration: <b><c:out value="${m.duration}"/></b></p>
                    <form action="moreDetails" method="post">
                        <input type="hidden" name="name" value="${m.name}" required/>
                        <sec:csrfInput />
                        <button>More Details</button>
                    </form>
                </div>
                <img alt="Movie Poster" src="/getMovieImage?name=${m.name}" class="movie-image">
            </div>
        </c:forEach>
    </div>
        
</body>
</html>
