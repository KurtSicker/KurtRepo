<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<h1>Take Our Survey!</h1>
<h3>Vote for your <em>favorite</em> National Park!</h3><br>

<c:url var="formAction" value="/surveyInput"/>
<form:form action="${formAction}" method="POST" modelAttribute="SurveyResult">  

<div class="formInputGroup">
 	 	<div>
            <label for="parkCode">Favorite National Park</label>
            <select name="parkCode" id="parkCode">
				<c:forEach var="aPark" items="${allParks}">
					<option value="${aPark.parkCode}">${aPark.parkName}</option>
				</c:forEach>
			</select>           
        </div> 
 		
 		<div>
            <label for="email">Your Email</label>
			<form:input  path="email" class="form-control"/>            
        	<form:errors path="email" cssClass="error"/>   
        </div>
        
        <div>
            <label for="state">State of Residence</label>
			<select name="state" id="state">
				<option value="AL">Alabama</option>
				<option value="AK">Alaska</option>
				<option value="AZ">Arizona</option>
				<option value="AR">Arkansas</option>
				<option value="CA">California</option>
				<option value="CO">Colorado</option>
				<option value="CT">Connecticut</option>
				<option value="DE">Delaware</option>
				<option value="DC">District Of Columbia</option>
				<option value="FL">Florida</option>
				<option value="GA">Georgia</option>
				<option value="HI">Hawaii</option>
				<option value="ID">Idaho</option>
				<option value="IL">Illinois</option>
				<option value="IN">Indiana</option>
				<option value="IA">Iowa</option>
				<option value="KS">Kansas</option>
				<option value="KY">Kentucky</option>
				<option value="LA">Louisiana</option>
				<option value="ME">Maine</option>
				<option value="MD">Maryland</option>
				<option value="MA">Massachusetts</option>
				<option value="MI">Michigan</option>
				<option value="MN">Minnesota</option>
				<option value="MS">Mississippi</option>
				<option value="MO">Missouri</option>
				<option value="MT">Montana</option>
				<option value="NE">Nebraska</option>
				<option value="NV">Nevada</option>
				<option value="NH">New Hampshire</option>
				<option value="NJ">New Jersey</option>
				<option value="NM">New Mexico</option>
				<option value="NY">New York</option>
				<option value="NC">North Carolina</option>
				<option value="ND">North Dakota</option>
				<option value="OH">Ohio</option>
				<option value="OK">Oklahoma</option>
				<option value="OR">Oregon</option>
				<option value="PA">Pennsylvania</option>
				<option value="RI">Rhode Island</option>
				<option value="SC">South Carolina</option>
				<option value="SD">South Dakota</option>
				<option value="TN">Tennessee</option>
				<option value="TX">Texas</option>
				<option value="UT">Utah</option>
				<option value="VT">Vermont</option>
				<option value="VA">Virginia</option>
				<option value="WA">Washington</option>
				<option value="WV">West Virginia</option>
				<option value="WI">Wisconsin</option>
				<option value="WY">Wyoming</option>
			</select>  
        </div>

        <div>
            <label for="activityLevel">Activity Level</label><br>
			<input type="radio" id="inactive" name="activityLevel" value="inactive" checked>
				<label for="inactive">Inactive</label><br>
			<input type="radio" id="sedentary" name="activityLevel" value="sedentary" >
				<label for="sedentary">Sedentary</label><br>
			<input type="radio" id="active" name="activityLevel" value="active">
				<label for="active">Active</label>    
			<input type="radio" id="extremelyActive" name="activityLevel" value="extremelyActive">
				<label for="extremelyActive">Extremely Active</label>         
        </div>
   
 
        <br>
        <div>
            <input class="formSubmitButton" type="submit" value="Submit"/>
        </div>

</div>
</form:form>


<c:import url="/WEB-INF/jsp/common/footer.jsp" />