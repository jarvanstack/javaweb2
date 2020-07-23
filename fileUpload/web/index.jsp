
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>文件上传和下载</title>
</head>
<body>
<h2>1.文件上传<span><%=request.getAttribute("error")%></span></h2>
<form action = "<%=request.getContextPath()%>/upload.do" method="post" enctype="multipart/form-data">
  文件上传<input type="file" name="file01">
  <button type="submit">上传</button>
</form>
<h2>2.工具测试<span><%=request.getAttribute("test")%></span></h2>
<form action="<%=request.getContextPath()%>/test.do" method="post" enctype="multipart/form-data">
  工具测试<input type="file" name="file01">
  <button type="submit">上传</button>
</form>
<br>
<h2>2.文件下载</h2>
<form action="${pageContext.request.contextPath}/download.do" method="post">
  <button type="submit">下载文件</button>
</form>
</body>
</html>
