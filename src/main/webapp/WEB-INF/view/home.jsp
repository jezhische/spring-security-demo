<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.authentication.AnonymousAuthenticationToken" %>
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
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/f52.green.w.png">
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
        <%--option with Spring library:--%>
            <%--Hello, <security:authentication property="principal.username"/>!--%>
            <%--option with java:--%>
            <%  String currentUserName = null;
                String firstChar = null;
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (!(authentication instanceof AnonymousAuthenticationToken))
                    currentUserName = authentication.getName();
//              первую букву - в Upper Case:
                if (currentUserName != null) {
                    firstChar = currentUserName.substring(0, 1).toUpperCase();
                    currentUserName = firstChar + currentUserName.substring(1);
                }%>
            Hello, <%= currentUserName%>!

            <%--option with Spring library:--%>
            Your roles is: <security:authentication property="principal.authorities"/>
    </p>
    <br><hr>
    <%--Показывать следующий ресурс только для определенной роли:--%>
    <security:authorize access="hasRole('HUHA')">
        <img src="static/spring-logo.png" class="col-xs-offset-2"/>
    </security:authorize>
    <security:authorize access="hasRole('MANAGER')">
        <img src="static/forManager.jpg" class="col-xs-offset-2"/>
    </security:authorize>
    <security:authorize access="hasRole('ADMIN')">
        <img src="static/forAdmin.jpg" class="col-xs-offset-2"/>
    </security:authorize>

    <hr>
    <security:authorize access="hasRole('HUHA')">
        <a href="${pageContext.request.contextPath}/beans" style="font-weight: bold; font-style: italic">Engineer bean list</a>
        <br><br>
        <p><a href="${pageContext.request.contextPath}/common" style="font-weight: bold; font-style: italic; color: lawngreen">
            Common page for testing
        </a></p>
    </security:authorize>
    <hr>
    <%--Доступ по ссылкам с маппингом /leaders или /systems прописан в DemoSecurityConfig: --%>
    <p>
        <%--american "peeps" means people or folk--%>
        <a href="${pageContext.request.contextPath}/leaders" style="font-weight: bold; font-style: italic">
            Leadership Meeting</a> (Only for Manager peeps and for admin)
    </p>
    <hr>
    <p>
        <a href="${pageContext.request.contextPath}/systems" style="font-weight: bold; font-style: italic">
            Admin Meeting</a> (Only for Admin peeps)
    </p>
    <security:authorize access="hasRole('ADMIN')">
        <hr>
        <a href="${pageContext.request.contextPath}/systems/userlist" style="font-weight: bold; font-style: italic">
            principals admin page (userlist)
        </a>
    </security:authorize>
    <hr><br>

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
