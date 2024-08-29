<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1> ${appName} </h1>
	<hr>
	<a href="/">Home</a> 
	<hr>
	<h3>SignUp Form</h3>
	<form action="/signUp" method="post">
		<label>User Email:</label>
		<input type="text" name="username" required />
		<br/><br/>
		<label>Password:</label>
		<input type="password" name="password" required />
		<br/><br/>
		<sec:csrfInput /> 
		<button>SignUp</button>
	</form>
</body>
</html>