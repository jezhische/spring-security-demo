<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Reference Bootstrap files -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <title>springsecurity-demo-home</title>
    <%--To NOT IGNORE ${} tag (https://dzone.com/articles/spring-mvc-and-java-based-configuration-1)--%>
    <%@ page isELIgnored="false" %>
</head>
<body>
<%--div - `generic language/style container`--%>
<div class="container-fluid col-xs-offset-1">
    <br><hr><br>
    <div class="alert alert-danger col-xs-5">
        <h2>You are not authorized to access this resource</h2>
    </div>
    <br><hr><br>
    <% int status = response.getStatus();%>
    <p class="col-xs-offset-0" style="font-weight: bold">
        <br><br><br><br>
        Status <%=status%>: access denied
    </p>
    <div class="alert alert-danger col-xs-offset-0 col-xs-3">
        Sorry, this page access restricted.
        <a style="color: red; font-style: italic; font-weight: bold">
            Forbidden page!
        </a>
        <form:form action="${pageContext.request.contextPath}" method="post">
            <div style="margin-top: 10px" class="form-group">
                <div class="col-sm-6 controls">
                    <button type="submit" class="btn btn-success">Back to home page</button>
                </div>
            </div>
            <%--Make plain-form instead of spring-form and add CSRF protection manually:--%>
            <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}/">--%>
        </form:form>
    </div>

    <br><hr><br>

    <p>

    </p>

    <br><hr><br>

    <p>

    </p>

    <br><hr><br>

</div>
</body>
</html>
