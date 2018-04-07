<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 05.04.2018
  Time: 5:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ page isELIgnored="false" %>
    <title>userlist</title>
</head>
<body>
${principals}

<br><hr><br>
<a href="${pageContext.request.contextPath}/systems/testPrincipal" style="font-weight: bold; font-style: italic">testPrincipal</a>
<br><hr><br>
<a href="${pageContext.request.contextPath}" style="font-weight: bold; font-style: italic">home</a>
</body>
</html>
