<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 17.03.2018
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>leaders-meetup</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Reference Bootstrap files -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <%@ page isELIgnored="false" %>
</head>

<body>
<div class="container-fluid col-xs-offset-1">
    <h1>Admin meetup secret page</h1>
    <br><hr><br>
    Hi there! Meet you in the pub today night. Me myself
    <br><hr><br>
    <a href="${pageContext.request.contextPath}"><b><i>Back to Home Page</i></b></a>
</div>

</body>
</html>
