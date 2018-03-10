<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ page isELIgnored="false" %>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/bootstrap.min.css"/>
    <title>authentication</title>
</head>
<body>
<div class="container-fluid">
    <h1>This is the raw custom failure authentication page</h1>
    <br><br>
    <div><i>Trial to login failed. Try again.</i></div>
    <div class="form-inline">
        <form:form action="${pageContext.request.contextPath}/authentication/login/process" method="post">
            <p><div class="form-group">
            <label for="username">Username: </label>
            <input type="text" class="form-control" id="username" name="username">
        </div></p>
            <p><div class="form-group">
            <label for="pwd">Password: </label>
            <input type="password" class="form-control" id="pwd" name="password">
        </div></p>
            <div class="checkbox">
                <label><input type="checkbox">Remember me </label>
            </div>
            <button type="submit" class="btn btn-default">Login</button>
        </form:form>
    </div>
</div>

<br><hr><br>
</body>
</html>
