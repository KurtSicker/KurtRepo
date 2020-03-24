<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />



<c:forEach var="aPark" items="${allParks}">
	<c:url value="/parkDetail" var="parkDetailHref" >
	<c:param name="parkCode" value="${aPark.parkCode}"/>
	</c:url>
	<c:url value="/img/parks/${aPark.parkCode.toLowerCase()}.jpg"
		var="parkPicSrc" />
	<a href="${parkDetailHref}"> <img src="${parkPicSrc}"
		alt="National Park picture" />
	</a>

	<a href="${parkDetailHref}">
		<h3>${aPark.parkName}</h3>
	</a>
	<p>${aPark.description}</p>

</c:forEach>



<c:import url="/WEB-INF/jsp/common/footer.jsp" />


