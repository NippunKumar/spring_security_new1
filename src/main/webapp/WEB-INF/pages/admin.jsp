<%--
  Created by IntelliJ IDEA.
  User: cashify
  Date: 18/1/17
  Time: 3:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<html>
<body>
<h1>Title : ${title}</h1>

<h1>Message : ${message}</h1>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form:form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="POST">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
    <input type="submit" value="Logout"/>
</form:form>
<%--
<c:url value="/j_spring_security_logout" var="logoutUrl"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>--%>
<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h2>
        Welcome : ${pageContext.request.userPrincipal.name} <%--| <a
            href="javascript:formSubmit()"> Logout</a>--%>
    </h2>
</c:if>

</body>
</html>