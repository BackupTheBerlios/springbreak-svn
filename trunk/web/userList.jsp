<%@ include file="/taglibs.jsp"%> 
<title>News Aggregator</title>

<h3><fmt:message key="user.userlist"/></h3>

<table class="list"> 
	<thead> 
		<tr> 
			<th><fmt:message key="user.username"/></th>
			<th><fmt:message key="user.firstName"/></th> 
			<th><fmt:message key="user.lastName"/></th> 
			<th><fmt:message key="user.isAdmin"/></th> 
			<th>&nbsp;</th>
		</tr> 
	</thead> 
	
	<tbody> 
		<c:forEach var="user" items="${users}" varStatus="status"> 
			<c:choose> 
				<c:when test="${status.count % 2 == 0}">
					<tr class="even">
				</c:when> 
			<c:otherwise>
			
			<tr class="odd">
				</c:otherwise> 
				</c:choose> 
					<td>
						<a href="showAddUser.html?usernameID=${user.username}">${user.username}</a>
					</td> 
					<td>${user.firstName}</td> 
					<td>${user.lastName}</td> 
					<td>${user.isAdmin}</td> 
					<form method="post" action="<c:url value="/showAddUser.html"/>">  
						<input type="hidden" name="delete" value="true"/>
						<input type="hidden" name="usernameID" value="${user.username}"/>
						<td><input type="submit" value="<fmt:message key="user.deleteuser"/>"></td>
					</form>
			</tr> 
		</c:forEach> 
	</tbody> 
</table>

<content tag="menubar">
<a href="showAddUser.html"><fmt:message key="user.addUser"/></a><br/>
<a href="main.html"><fmt:message key="user.back"/></a><br/>
</content>