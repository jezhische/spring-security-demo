<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
        <%@ page isELIgnored="false" %>
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/f52.green.w.png">
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/bootstrap.min.css"/>--%>
    <title>authentication</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Reference Bootstrap files -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
          <%--href="/ssd/static/bootstrap3.3.7maxcdn/bootstrap.min.css">--%>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

    <script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>


<body>
<div>
    <div id="loginbox" style="margin-top: 70px;"
         class="mainbox col-md-3 col-md-offset-2 col-sm-6 col-sm-offset-2">
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="panel-title">Sign in</div>
            </div>
            <div style="padding-top: 30px" class="panel-body">
                <!-- Login Form -->
                <form:form action="${pageContext.request.contextPath}/authentication/login/process" method="post">

                    <%--обработка ошибки с помощью jstl:--%>
                    <c:if test="${param.error != null}">
                        <div style="color: red"  class="col-xs-offset-1">
                            <i> Sorry! You entered wrong name/password (jstl msg) </i>
                        </div>
                    </c:if>
                    <%--обработка logout с помощью jstl:--%>
                    <c:if test="${param.logout != null}">
                <div class="col-xs-offset-1">
                    <i>You have been logout (jstl msg)</i>
                </div>
                    </c:if>
                    <hr>
                    <%--обработка logout с помощью java:--%>
                    <% if (request.getParameter("logout") != null) {%>
                    <%--${logoutMessage} HHHHH--%>
                    <div class="alert alert-success col-xs-offset-1 col-xs-10">
                        You have been logout (java msg)
                    </div>
                    <%}%>

                    <div class="form-group">
                            <%--<div class="col-xs-15">--%>
                        <div  class="col-xs-12">
                            <div>
                                    <%--обработка ошибки с помощью java:--%>
                                <% String msg = "Please login your name and password";
                                    String failureMsg = "Invalid username and password. " +
                                            "Please login your name and password again (java msg)";
                                    if (request.getParameter("error") != null) {
//    or:     if (request.getParameter("failed") != null) {failureMsg = "Trial to login is unsuccessful. Try again";}
//    and in DemoSecurityConfig set .failureUrl("/authentication/login?failed") in configure(HttpSecurity http)

                                %>
                                        <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                            <%= failureMsg%>
                                        </div>
                                        <%} else {%>
                                        <div class="col-xs-offset-1">
                                            <%= msg%>
                                        </div>
                                        <br>
                                        <%}%>
                                        <!--
                            <div class="alert alert-success col-xs-offset-1 col-xs-10">
                            You have been logged out.
                            </div>
                            -->
                            </div>
                        </div>
                    </div>

                    <!-- User name -->
                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
    <%--NB: need name="username" that Spring to listen this field--%>
                        <input type="text" name="username" placeholder="username" class="form-control">
                    </div>
                    <!-- Password -->
                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input type="password" name="password" placeholder="password" class="form-control" >
                    </div>
                    <!-- Login/Submit Button -->
                    <div style="margin-top: 10px" class="form-group">
                        <div class="col-sm-6 controls">
                            <button type="submit" class="btn btn-success">Login</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>

</div>

<h1>Authentication page (already with the db, but still "no operation" plain text password)</h1>


<br><hr><br>
</body>
</html>
