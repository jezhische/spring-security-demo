<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--<link rel="stylesheet" type="text/css" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"/>--%>
        <%@ page isELIgnored="false" %>
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/bootstrap.min.css"/>
        <%--<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/favicon.png">--%>
        <%--<title>authentication</title>--%>
</head>
<body>
<div class="container-fluid">
    <h1>This is the raw custom authentication page</h1>
    <div class="form-inline">
        <form:form action="${pageContext.request.contextPath}/authentication/login/process" method="post">
                    <%--Username: <input type="text" name="username" placeholder="username">--%>
            <c:if test="${param.error != null}">
                <i> Sorry! You entered wrong name/password </i>
            </c:if>
            <p><div class="form-group">
            <label for="username">Username: </label>
            <input type="text" class="form-control" id="username" name="username" placeholder="name">
        </div></p>
            <p><div class="form-group">
                <%--Password: <input type="password" name="password" placeholder="password">--%>
                    <label for="pwd">Password: </label>
            <input type="password" class="form-control" id="pwd" name="password" placeholder="password">
        </div></p>
            <div class="checkbox">
                <label><input type="checkbox">Remember me </label>
            </div>
            <%--info:      http://htmlbook.ru/html/button--%>
            <button type="submit" class="btn btn-default">
                <%--<img src="${pageContext.request.contextPath}/WEB-INF/img/ejik.png" style="vertical-align: middle">--%>
                Login
            </button>
            <%--<input type="submit" value="Login">--%>
        </form:form>
    </div>
</div>

<br><hr><br>
<%--<div class="container-fluid">--%>
    <%--<form class="form-inline" action="authentication/login/process" method="post">--%>
        <%--<div class="form-group">--%>
            <%--<label for="lg">Username:</label>--%>
            <%--<input type="text" class="form-control" id="lg">--%>
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
