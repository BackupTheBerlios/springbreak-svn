<%@ include file="/taglibs.jsp"%>

<title>News Aggregator</title>


<h3><fmt:message key="main.welcome"/> <c:out value="${user.firstName}"/></h3>
<p>
   Hello User!
</p>

<content tag="menubar">
<c:if test="${user.isAdmin == true}">
<a href="showUser.html"><fmt:message key="main.admin"/></a><br/>
</c:if>
<fmt:message key="main.manageRssFeeds"/><br/>
</content>




