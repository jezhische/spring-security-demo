<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 28.02.2018
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404</title>
    <%--To NOT IGNORE ${} tag (https://dzone.com/articles/spring-mvc-and-java-based-configuration-1)--%>
    <%@ page isELIgnored="false" %>
</head>
<body>
<h2>Not found</h2>
<p style="color: red"><i>Controller method is present, but resourse is not found</i></p>
    <%--<h2>Not found, ye, resource ${pageContext.getRequest} is not found!</h2>--%>
<br><hr><br>
<a href="${pageContext.request.contextPath}">home</a>
</body>
</html>
