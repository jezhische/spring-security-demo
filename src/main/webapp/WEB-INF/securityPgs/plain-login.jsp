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

    <%--<form class="form-inline" action="/action_page.php">--%>
            <%--<div class="form-group">--%>
                <%--<label for="email">Email address:</label>--%>
                <%--<input type="email" class="form-control" id="email">--%>
            <%--</div>--%>
            <%--<div class="form-group">--%>
                <%--<label for="pwd">Password:</label>--%>
                <%--<input type="password" class="form-control" id="pwd">--%>
            <%--</div>--%>
            <%--<div class="checkbox">--%>
                <%--<label><input type="checkbox"> Remember me</label>--%>
            <%--</div>--%>
            <%--<button type="submit" class="btn btn-default">Submit</button>--%>
    <%--</form>--%>

    <form:form action="${pageContext.request.contextPath}/authentication/login/process" method="post">
        Login <input type="text" name="username" placeholder="login">
        Password <input type="text" name="password" placeholder="password">
        <input type="submit" value="Submit">
    </form:form>

    <div class="row">
        <div class="col-sm-2">
            <a class="head-brand" href="#"><h4>FISHER-PLACE</h4></a>
        </div>
        <div class="col-sm-2">
        </div>
        <div class="col-sm-2">
        </div>
        <div class="col-sm-2">
            <div class="form-group">
                <input type="email" class="form-control" id="email" placeholder="Введите email">
            </div>
        </div>
        <div class="col-sm-2">
            <div class="form-group">
                <input type="password" class="form-control" id="password" placeholder="введите пароль">
            </div>
        </div>
        <div class="col-sm-2">
            <button type="button" class="btn btn-primary">Вход</button>
            <button type="button" class="btn btn-primary">Регистрация</button>
        </div>
    </div>
</div>
</body>
</html>
