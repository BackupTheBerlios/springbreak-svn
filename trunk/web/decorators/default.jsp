<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        
<%@ include file="/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title><decorator:title default="Equinox"/></title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
    <link href="${ctx}/styles/global.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/images/favicon.ico" rel="SHORTCUT ICON"/>
    <decorator:head/>
    <!-- HTML & Design contributed by Boer Attila (http://www.calcium.ro) -->
    <!-- Found at http://www.csszengarden.com/?cssfile=/083/083.css&page=2 -->
</head>
<body>
<a name="top"></a>
<div id="container">
    <div id="intro">
        <div id="pageHeader">
            <h1><span>Welcome to Equinox</span></h1>
            <div id="logo" onclick="location.href='<c:url value="/"/>'"
                onkeypress="location.href='<c:url value="/"/>'"></div>
            <h2><span>Spring Rocks!</span></h2>
        </div>
    
        <div id="quickSummary">
            <p>
            	<decorator:getProperty property="page.menubar"/>
            </p>
            <br/>
            <p class="credit">
                <a href="http://www.csszengarden.com/?cssfile=/083/083.css&amp;page=2">
                Design and CSS</a> donated by <a href="http://www.calcium.ro">
                Bo&eacute;r Attila</a>.
            </p>
        </div>
    
        <div id="content">
            <!--%@ include file="/messages.jsp"%-->
            <decorator:body />
        </div>
    </div>

    <div id="supportingText">
        <div id="underground"><decorator:getProperty property="page.underground"/></div>
    
        <div id="footer">
            <a href="http://validator.w3.org/check/referer" title="Check the validity of this site&#8217;s XHTML">xhtml</a> &middot;
            <a href="http://jigsaw.w3.org/css-validator/check/referer" title="Check the validity of this site&#8217;s CSS">css</a> &middot;
            <a href="http://www.apache.org/licenses/LICENSE-2.0" title="View the license for Equinox, courtesy of Apache Software Foundation.">license</a> &middot;
            <a href="http://bobby.watchfire.com/bobby/bobbyServlet?URL=<c:out value="${pageContext.request.requestURL}"/>&amp;output=Submit&amp;gl=sec508&amp;test=" title="Check the accessibility of this site according to U.S. Section 508">508</a> &middot;
            <a href="http://bobby.watchfire.com/bobby/bobbyServlet?URL=<c:out value="${pageContext.request.requestURL}"/>&amp;output=Submit&amp;gl=wcag1-aaa&amp;test=" title="Check the accessibility of this site according to WAI Content Accessibility Guidelines 1">aaa</a>
        </div>

    </div>
	
    <div id="linkList">
        <div id="linkList2">
            <div id="lresources">
                <h3 class="menubar">Resources</h3>
                <ul>
                    <li><a href="http://vecego.0wnz.at/">Roland Vecera</a></li>					
                    <li><a href="http://www.szabolcs.at.tf/">Szabolcs Rozsnyai</a></li>					
                </ul>
            </div>
        </div>
    </div>

</div>

</body>
</html>
