<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 01.03.2018
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<%--todo: NB: на самом деле это"classpath:static/bootstrap3.3.7/css/bootstrap.min.css"--%>
    <link rel="stylesheet" type="text/css" href="static/bootstrap3.3.7/css/bootstrap.min.css"/>
    <title>springsecurity-demo-home</title>
    <%--To NOT IGNORE ${} tag (https://dzone.com/articles/spring-mvc-and-java-based-configuration-1)--%>
    <%@ page isELIgnored="false" %>
</head>
<body>
<%--div - `generic language/style container`--%>
<div class="container-fluid">
    <h2>SpringSecurity-demo home</h2>
    <br><hr><br>
    <a href="_404">_404</a>
</div>
</body>
</html>
