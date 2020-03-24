<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


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
	   <br>
	   <br>
	   <br>   
	 
    </div>



<c:url var="registerUrl" value="/userRegistrationInput"/>
<form:form action="${registerUrl}" method="POST" modelAttribute="UserRegistration">
    <div class="form-group">
        <label for="username">Username</label>
        <form:input class="form-control" path="username" placeholder="Username"/>
        <form:errors path="username" cssClass="error"/>
    </div>
    <div class="form-group">
        <label for="password">Password</label>
        <form:password class="form-control" path="password" placeholder="Password"/>
        <form:errors path="password" cssClass="error"/>
    </div>
    <div class="form-group">
        <label for="passwordhint">Password Hint</label>
        <form:password class="form-control" path="passwordhint"/>
        <form:errors path="passwordhint" cssClass="error"/>
    </div>
     <div class="form-group">
        <label for="email">Email</label>
        <form:input class="form-control" path="email" placeholder="Email"/>
        <form:errors path="email" cssClass="error"/>
    </div>
    
    <button type="submit" class="btn btn-default">Save User</button>
</form:form>






<c:import url="/WEB-INF/jsp/common/footer.jsp" />
