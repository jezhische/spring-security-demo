<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 07.03.2018
  Time: 8:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--<link rel="stylesheet" type="text/css" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"/>--%>
    <link rel="stylesheet" type="text/css" href="static/bootstrap3.3.7/css/bootstrap.min.css"/>
    <title>authentication</title>
</head>
<body>
<div class="container-fluid">
    <h1>This is the raw authentication page</h1>
</div>
<form:form action="${pageContext.request.contextPath}/authentication/login/process" method="post">
    <p>
        Username: <input type="text" name="username" placeholder="username">
    </p>
    <p>
        Password: <input type="text" name="password" placeholder="password">
    </p>
    <input type="submit" value="Login">
</form:form>

<%--<form class="form-inline" action="authentication/login/process" method="post">--%>
<%--<div class="form-group">--%>
            <%--<label for="lg">Username:</label>--%>
            <%--<input type="text" class="form-control form-control-feedback" id="lg">--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
            <%--<label for="pwd">Password:</label>--%>
            <%--<input type="password" class="form-control" id="pwd">--%>
        <%--</div>--%>
        <%--<div class="checkbox">--%>
            <%--<label><input type="checkbox"> Remember me</label>--%>
        <%--</div>--%>
        <%--<button type="submit" class="btn btn-default">Login</button>--%>
    <%--</form>--%>
<%--</div>--%>
</body>
</html>
