
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<html>
<head>
    <title>sign in</title>
</head>
<body>
<h1>${message}</h1>
<form action="${pageContext.request.contextPath}/signIn.do" method="post">
    <p>账号:<input type="text" name = "username" value=""></p>
    <p>密码:<input type="password" name = "password" value=""></p>
    <p>邮箱:<input type="text" name = "mailbox" value=""></p>
    <p><button type="submit" >注册</button> </p>
</form>
</body>
</html>
