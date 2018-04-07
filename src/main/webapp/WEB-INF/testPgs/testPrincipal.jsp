<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 05.04.2018
  Time: 22:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ page isELIgnored="false" %>
    <title>testPrincipal</title>
</head>
<body>
<p>maryname = ${maryname}</p>
<p>maryauthorities = ${maryauthorities}</p>
<br><hr>
<p>user = ${user}</p>
<p>username = ${username}</p>
<p>password = ${password}</p>
<p>details = ${details}</p>
<p>authorities = ${authorities}</p>
<p>credentials = ${credentials}</p>

<br><hr><br>
<a href="${pageContext.request.contextPath}" style="font-weight: bold; font-style: italic">home</a>
</body>
</html>
