<%@ include file="/taglibs.jsp"%>

<title>News Aggregator</title>


<h3>Welcome to the News Aggregator!</h3>
<p>
	<spring:bind path="login.*"> 
		<c:if test="${not empty status.errorMessages}"> 
			<div class="error"> 
				<c:forEach var="error" items="${status.errorMessages}"> 
					<c:out value="${error}" escapeXml="false"/><br /> 
				</c:forEach> 
			</div> 
		</c:if> 
	</spring:bind>
   <form method="post" action="<c:url value="login.html"/>" onsubmit="return validateUser(this)"> 
   		<table> 
   		<tr>
   			<td>&nbsp;</td>
   			<TD><span class="fieldError"><c:out value="${param.messageLogin}"/> </span> </TD>
   		</tr>
		<tr> 
			<th>
				<fmt:message key="login.loginname"/>:
			</th> 
			<td> 
				<spring:bind path="login.username"> 
					<input type="text" name="${status.expression}" value="${status.value}"/> 
				</spring:bind>
			</td>
			
		</tr> 
		
		<tr> 
			<th><fmt:message key="login.password"/>:  </th> 
			<td> 
				<spring:bind path="login.password"> 
					<input type="password" name="${status.expression}" value="${status.value}"/> 
				</spring:bind>
			</td> 
		</tr> 
		
		<tr> 
			<td>&nbsp;
			</td> 
			<td> 
				<input type="submit" class="button" name="save" value="login"/> 
			</td> 
		</tr>
			<td>&nbsp;</td> 
			<td>&nbsp;</td> 
		<tr>
		</tr>
			<td>&nbsp;</td> 
			<td><a href="register.html"><fmt:message key="login.register"/></a></td> 
		<tr>
		</tr>
	</table> 
   </form>
</p>

<content tag="underground">
<h3><fmt:message key="login.someinfoabout"/></h3>
<strong><fmt:message key="common.newsagg"/></strong><fmt:message key="login.someinfotext"/>
</content>
