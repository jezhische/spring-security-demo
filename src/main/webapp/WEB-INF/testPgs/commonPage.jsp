<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>commonPage</title>
    <%@ page isELIgnored="false" %>
</head>
<body>
<h1>Hi! This is the commonPage for all registered users!</h1>
<br>
<p>Due to the following string in the DemoSequrityConfig: .antMatchers("/common/**").permitAll()</p>
<br><hr><br>
<a href="${pageContext.request.contextPath}" style="font-weight: bold; font-style: italic">
    home
</a>
<br><hr>
<a href="${pageContext.request.contextPath}/common/testPathVar/Joe" style="font-weight: bold; font-style: italic; color: darkorange">
    testPathVar/Joe
</a>
 \\\
<a href="${pageContext.request.contextPath}/common/testPathVar/Susy" style="font-weight: bold; font-style: italic; color: darkorange">
    testPathVar/Susy
</a>
\\\
<%--<form method="get"--%>
</body>
</html>
