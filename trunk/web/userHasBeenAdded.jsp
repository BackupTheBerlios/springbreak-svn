<%@ include file="/taglibs.jsp"%> 
<title>MyUsers ~ User Details</title> 
<p>	<h3><fmt:message key="register.userHasBeenAdded"/></h3></p> 

<table> 
	<table> 
		<tr> 
			<th>
				<fmt:message key="user.username"/>: 
			</th> 
			<td> 
				<spring:bind path="user.username">
	               <c:out value="${status.value}"/>
	            </spring:bind>
			</td>
			
		</tr> 
		<tr> 
			<th>
				<fmt:message key="user.firstName"/>: 
			</th> 
			<td> 
				<spring:bind path="user.firstName"> 
					${status.value}
				</spring:bind> 
			</td>
			
		</tr> 
		
		<tr> 
			<th><fmt:message key="user.lastName"/>: </th> 
			<td> 
				<spring:bind path="user.lastName"> 
					${status.value}
				</spring:bind> 
			</td> 
		</tr> 
		
		<tr> 
			<th><fmt:message key="user.email"/>: </th> 
			<td> 
				<spring:bind path="user.email"> 
					${status.value}
				</spring:bind> 
			</td> 
		</tr> 
		
		<tr> 
			<th><fmt:message key="user.isAdmin"/>: </th> 
			<td> 
				<spring:bind path="user.isAdmin"> 
					<c:if test="${status.value == true}">true</c:if>
					<c:if test="${status.value == false}">false</c:if>					
				</spring:bind> 
			</td> 
		</tr> 
		
		<tr>
			<td>&nbsp;</td>
			<td>
				<a href="index.html"><fmt:message key="register.returntologin"/></a>
			</td>
		</tr>
	</table> 
</table>