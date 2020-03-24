<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>National Park Geek</title>
<c:url value="/css/nationalparkgeek.css" var="cssHref" />
<link rel="stylesheet" href="${cssHref}">
</head>

<body>
	<div class="headerStyling">
		<header>
			<c:url value="/" var="homePageHref" />
			<c:url value="/img/logo.png" var="logoSrc" />
			<img src="${logoSrc}" alt="National Park Geek logo" />

		</header>
		<br> <br> <br>

	</div>

	<c:url var="loginUrl" value="/login" />
	<form action="${loginUrl}" method="POST">
		<div class="form-group">
			<label for="username">Username</label> <input type="text"
				class="form-control" id="username" name="username"
				placeholder="Username">
		</div>
		<div class="form-group">
			<label for="password">Password</label> <input type="password"
				class="form-control" id="password" name="password">
		</div>
		<div id="passwordHint">${passwordHint}</div>
		<button type="submit" class="btn btn-default">Login</button>
	</form>
	
	<c:url var="loginHint" value="/loginhint" />
	<form action="${loginHint}" method="POST">
	
		<button id="forgotPassword" type="submit" class="btn btn-default">Forgot
			password?</button>
	</form>
	

	<p>New User?</p>
	<a href="<c:url value="/userRegistrationInput"></c:url>">Register
		here</a>

	<c:import url="/WEB-INF/jsp/common/footer.jsp" />