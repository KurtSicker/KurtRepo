<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<h1>${selectedPark.parkName}</h1>

<c:url value="/img/parks/${selectedPark.parkCode.toLowerCase()}.jpg"
	var="parkDetailPic" />
<img src="${parkDetailPic}" alt="National Park picture" />
<h2>"${selectedPark.quote}"</h2>
<h3>${selectedPark.quoteSource}</h3>
<p>${selectedPark.description}</p>

<table>
	<tr>
		<td>State:</td>
		<td>${selectedPark.state}</td>
	</tr>
	<tr>
		<td>Acreage:</td>
		<td>${selectedPark.acreage}</td>
	</tr>
	<tr>
		<td>Average Elevation:</td>
		<td>${selectedPark.elevationInFeet}</td>
	</tr>
	<tr>
		<td>Climate:</td>
		<td>${selectedPark.climate}</td>
	</tr>
	<tr>
		<td>Founded in:</td>
		<td>${selectedPark.yearFounded}</td>
	</tr>
	<tr>
		<td>Annual Visitors:</td>
		<td>${selectedPark.annualVisitorCount}</td>
	</tr>
	<tr>
		<td>Miles of Trail:</td>
		<td>${selectedPark.milesOfTrail}</td>
	</tr>
	<tr>
		<td>Number of Campsites:</td>
		<td>${selectedPark.numberOfCampsites}</td>
	</tr>
	<tr>
		<td>Entry Fee:</td>
		<td>$${selectedPark.entryFee}</td>
	</tr>
</table>
<br>
<div id="weatherBlock">

	<%-- <c:url var="submitAction" value="/tempConversion"></c:url>

	<form action="${submitAction}" method="GET">

		<label for="tempChoice">Fahrenheit/Celcius</label> 
		<select id="tempChoice" name="tempChoice">
			<option value="fahrenheit">Fahrenheit</option>
			<option value="celcius">Celcius</option>
		</select> <input type="submit" value="Submit" />
	</form>
 --%>
	<ul>
		<li><a
			href="<c:url value="/tempConversion"><c:param name="parkCode" value ="${selectedPark.parkCode}"/> <c:param name="tempChoice" value="celcius"/></c:url>">Celcius</a></li>
		<li><a
			href="<c:url value="/tempConversion"><c:param name="parkCode" value ="${selectedPark.parkCode}"/> <c:param name="tempChoice" value="fahrenheit"/></c:url>">Fahrenheit</a></li>

	</ul>
	<br> <br>


			<c:forEach var="theWeather" items="${selectedWeather}">
				<c:choose>
					<c:when test="${theWeather.forecast == 'partly cloudy' }">
						<c:url value="/img/weather/partlyCloudy.png" var="weatherPic" />
						<img src="${weatherPic}" alt="Weather picture" />

					</c:when>

					<c:otherwise>
						<c:url value="/img/weather/${theWeather.forecast}.png"
							var="weatherPic" />
						<img src="${weatherPic}" alt="Weather picture" />

					</c:otherwise>
				</c:choose>

				<p>High: ${theWeather.high}</p>
				<p>Low: ${theWeather.low}</p>
				<p> ${theWeather.weatherAdvisory}</p>
				<p>${theWeather.tempAdvisory}</p>
				<p>${theWeather.differenceAdvisory}</p>

			</c:forEach>
		



</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />