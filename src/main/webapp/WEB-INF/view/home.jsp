<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
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
<%--todo: NB: на самом деле это"classpath:static/bootstrap3.3.7/css/bootstrap.min.css", но преобразование пути
осуществляется с помощью DemoAppConfig....addResourceHandler(..., "classpath:static/")--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/bootstrap3.3.7/css/bootstrap.min.css"/>--%>
    <%--<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico">--%>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>springsecurity-demo-home</title>
    <%--To NOT IGNORE ${} tag (https://dzone.com/articles/spring-mvc-and-java-based-configuration-1)--%>
    <%@ page isELIgnored="false" %>
</head>
<body>
<%--div - `generic language/style container`--%>
<div class="container-fluid col-xs-offset-1">
    <br><br>
    <div class="alert alert-success col-xs-5">
        <h2>SpringSecurity-demo home</h2>
    </div>
    <br><hr><br><br><br>
    <a href="_404">_404</a>

    <br><hr><br>
    <p>
        <%--NB: "principal"--%>
        Hello, <security:authentication property="principal.username"/>!
            Your roles is: <security:authentication property="principal.authorities"/>
    </p>
    <br><hr><br>
    <p>
        <%--american "peeps" means people or folk--%>
        <a href="${pageContext.request.contextPath}/leaders">Leadership Meeting</a> (Only for Manager peeps and for admin)
    </p>
    <br><hr><br>
    <p>
        <a href="${pageContext.request.contextPath}/systems">Admin Meeting</a> (Only for Admin peeps)
    </p>
    <br><hr><br>

    <form:form action="${pageContext.request.contextPath}/logout" method="post">
        <%--so that Spring to append a logout parameter: "http://localhost:8086/ssd/authentication/login?logout"--%>
        <%--<input type="submit" value="Logout">--%>
        <div style="margin-top: 10px" class="form-group">
            <div class="col-sm-6 controls">
                <button type="submit" class="btn btn-success">Logout</button>
            </div>
        </div>
        <%--Make plain-form instead of spring-form and add CSRF protection manually:--%>
        <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}/">--%>
    </form:form>
</div>
</body>
</html>
