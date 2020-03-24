<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<h1>Favorite Park Rankings</h1>

<table>
	<tr>
		<th></th>
		<th>Park Name:</th>
		<th>Survey Count:</th>
	</tr>
	
		<c:forEach var="aRanking" items="${ranking}">
		<tr>
			<td><c:url
					value="/img/parks/${aRanking[0].toLowerCase()}.jpg"
					var="parkPicSrc" /> <img src="${parkPicSrc}"
				alt="National Park picture" /></td>
			<td>${aRanking[2]}</td>
			<td>${aRanking[1]}</td>
			</tr>
			
		</c:forEach>

	




</table>




<c:import url="/WEB-INF/jsp/common/footer.jsp" />