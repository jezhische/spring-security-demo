<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 31.03.2018
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>testPathVar</title>
    <%@ page isELIgnored="false" %>
</head>
<body>
<h4 style="color: coral">This is testPathVar page, controller method: </h4>
TestController.@GetMapping("/testPathVar/{name}") public ModelAndView
getTestPathVar(@PathVariable String name, ModelAndView modelAndView)
<h1>${message}</h1>
<br><hr><br>
<p><a href="${pageContext.request.contextPath}/common" style="font-weight: bold; font-style: italic; color: #09c332">
    Back to common page
</a></p>

</body>
</html>
