<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 23.03.2018
  Time: 6:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ page isELIgnored="false" %>
    <title>beans</title>
</head>
<body>
<h2 style="color: coral"><i><b>Application Context Beans:</b></i></h2>
<hr><br>
<c:forEach items="${beanArray}" var="item" varStatus="itemStat">
    <p>appContextBean[${itemStat.index}] = ${item}</p>
</c:forEach>
<br>
-----------------------------------------------------------------------------------------------------------------
<br>
<h2 style="color: coral"><i><b>Web Context Beans:</b></i></h2>
<hr><br>
<c:forEach items="${webBeanArray}" var="item" varStatus="itemStat">
    <p>webContextBean[${itemStat.index}] = ${item}</p>
</c:forEach>

</body>
</html>
