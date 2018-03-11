<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
        <%@ page isELIgnored="false" %>
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/bootstrap.min.css"/>--%>
    <title>authentication</title>
    <style>
        .error {color: red;
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            font-size: 20px;
            font-style: italic;
            font-weight: bold;
        }
        .fontArial {
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            font-weight: bold;
        }
    </style>
</head>
<body>
<%--<div class="container-fluid">--%>
<h1>This is the raw custom authentication page</h1>
<%--<div class="form-inline">--%>
<form:form action="${pageContext.request.contextPath}/authentication/login/process" method="post">

    <%--обработка ошибки с помощью jstl:--%>
    <c:if test="${param.error != null}">
        <i> Sorry! You entered wrong name/password </i>
    </c:if>
    <br><hr><br>

    <%--обработка ошибки с помощью java:--%>
    <% String msg = "Please login your name and password";
        String failureMsg = "Trial to login is unsuccessful. Try again";
        if (request.getParameter("error") != null) {
//    or:     if (request.getParameter("failed") != null) {failureMsg = "Trial to login is unsuccessful. Try again";}
//    and in DemoSecurityConfig to set .failureUrl("/authentication/login?failed") in configure(HttpSecurity http)
    %>
    <p class="error">
        <%= failureMsg%>
    </p>
    <%} else {%>
    <p class="fontArial">
        <%= msg%>
    </p>
    <%}%>
    <div class="fontArial">
        <p>
                <%--<div class="form-group">--%>
                <%--<label for="username">Username: </label>--%>
                        <%--<input type="text" class="form-control" id="username" name="username" placeholder="name">--%>
                        <%--</div>--%>
                    Username: <input type="text" name="username" placeholder="username">
        </p>
        <p>
                <%--<div class="form-group">--%>
            Password: <input type="password" name="password" placeholder="password">
                        <%--<label for="pwd">Password: </label>--%>
                        <%--<input type="password" class="form-control" id="pwd" name="password" placeholder="password">--%>
                        <%--</div>--%>
        </p>
            <%--<div class="checkbox">--%>
            <%--<label><input type="checkbox">Remember me </label>--%>
            <%--</div>--%>
            <%--info:      http://htmlbook.ru/html/button--%>
            <%--<button type="submit" class="btn btn-default">Login</button>--%>
        <input type="submit" value="Login">
    </div>
</form:form>
<%--</div>--%>
<%--</div>--%>

<br><hr><br>
</body>
</html>
