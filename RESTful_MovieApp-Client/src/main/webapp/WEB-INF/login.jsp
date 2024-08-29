<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1> ${appName} </h1>
	<hr>
	<h3>Login Form</h3>
	<c:if test="${not empty param.error}">
		<p style="color: red;">Invalid username or password.</p>
	</c:if>
	
	<c:if test="${not empty result and fn:contains(result, 'SignUp Details Added Successfully!')}">
         <p style="color: Green;">${result}</p>
    </c:if>
    <c:if test="${not empty result and fn:contains(result, 'Username Already Exist!')}">
         <p style="color: red;">${result}</p>
    </c:if>

	<form action="/login" method="post">
		<label>User Name:</label>
		<input type="text" name="username" required />
		<br/><br/>
		<label>Password:</label>
		<input type="password" name="password" required />
		<br/><br/>
		<sec:csrfInput /> 
		<button>Login</button>
	</form>
	<a href="/signUp">SignUp</a>
	<h3>Or Login with Google</h3>
    <a href="/oauth2/authorization/google" >
        Login with Google
    </a>
</body>
</html>