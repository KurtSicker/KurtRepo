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
		<nav>
			<ul>
				<li><a href="<c:url value="/"/>">Home Page</a></li>
				<li><a href="<c:url value="/surveyInput"/>">Survey</a></li>
				<li><a href="<c:url  value="/logoff"/>" >Log Off</a></li>
				
			</ul>
			
			
		
		</nav>
		<p> User: ${appCurrentUser.username}</p>
		<br> <br> <br>

	</div>