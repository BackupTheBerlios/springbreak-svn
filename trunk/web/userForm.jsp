<%@ include file="/taglibs.jsp"%> 
<title>MyUsers ~ User Details</title> 
<p>Please fill in user's information below:</p> 

<spring:bind path="user.*"> 
	<c:if test="${not empty status.errorMessages}"> 
		<div class="error"> 
			<c:forEach var="error" items="${status.errorMessages}"> 
				<c:out value="${error}" escapeXml="false"/><br /> 
			</c:forEach> 
		</div> 
	</c:if> 
</spring:bind>


<form name="user" method="post" action="<c:url value="/showAddUser.html"/>" onsubmit="return validateUser(this)"> 
	<table> 
		<tr> 
			<th>
				<fmt:message key="user.username"/>: 
			</th> 
			<td> 
				
				<spring:bind path="user.user.username">
	               <input type="text" name="${status.expression}" value="<c:out value="${status.value}"/>">
	               <span class="fieldError">${status.errorMessage}</span>
	            </spring:bind>
			</td>
			
		</tr> 
		<tr> 
			<th>
				<fmt:message key="user.firstName"/>: 
			</th> 
			<td> 
				<spring:bind path="user.user.firstName"> 
					<input type="text" name="${status.expression}" value="${status.value}"/> 
					<span class="fieldError">${status.errorMessage}</span> 
				</spring:bind> 
			</td>
			
		</tr> 
		
		<tr> 
			<th><fmt:message key="user.lastName"/>: </th> 
			<td> 
				<spring:bind path="user.user.lastName"> 
					<input type="text" name="${status.expression}" value="${status.value}"/> 
					<span class="fieldError">${status.errorMessage}</span> 
						<c:if test="${not empty status.errorMessage}"> 
						</c:if> 
				</spring:bind> 
			</td> 
		</tr> 
		
		<tr> 
			<th><fmt:message key="user.password"/>: </th> 
			<td> 
				<spring:bind path="user.user.password"> 
					<input type="password" name="${status.expression}" value="${status.value}"/> 
					<span class="fieldError">${status.errorMessage}</span> 
				</spring:bind> 
			</td> 
		</tr> 
		
		<tr> 
			<th><fmt:message key="user.secondPassword"/>: </th> 
			<td> 
				<spring:bind path="user.user.password"> 
					<input type="password" name="${status.expression}" value="${status.value}"/> 
					<span class="fieldError">${status.errorMessage}</span> 
				</spring:bind>
			</td> 
		</tr> 
			
		<tr> 
			<th><fmt:message key="user.email"/>: </th> 
			<td> 
				<spring:bind path="user.user.email"> 
					<input type="text" name="${status.expression}" value="${status.value}"/> 
					<span class="fieldError">${status.errorMessage}</span> 
				</spring:bind> 
			</td> 
		</tr> 
		
		<tr> 
			<th><fmt:message key="user.isAdmin"/>: </th> 
			<td> 
				<spring:bind path="user.user.isAdmin"> 
					<input type="radio" name="${status.expression}" value="true" <c:if test="${status.value == true}">checked</c:if>> True<br>
					<input type="radio" name="${status.expression}" value="false" <c:if test="${status.value == false}">checked</c:if>> False<br>
					<span class="fieldError">${status.errorMessage}</span> 
						<c:if test="${not empty status.errorMessage}"> 
						</c:if> 
				</spring:bind> 
			</td> 
		</tr> 
		
		<tr> 
			<td>
			</td> 
			<td> 
			<c:choose>
  				<c:when test="${not empty user.user.username}">
					<input type="submit" class="button" name="update" value="Save"/> 
				</c:when>
			    <c:otherwise>
					<input type="submit" class="button" name="save" value="Save"/> 
			 	</c:otherwise>
			 </c:choose>
					
				<c:if test="${not empty param.id}"> 
					<input type="submit" class="button" name="delete" value="Delete"/> 
				</c:if> 
			</td> 
	</table> 
</form> 
<content tag="menubar">
<a href="showUser.html"><fmt:message key="user.back"/></a><br/>
</content>
