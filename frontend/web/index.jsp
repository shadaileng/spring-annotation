<%--
  Created by IntelliJ IDEA.
  User: Shadaileng
  Date: 2018/11/28
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Index</title>
      <% pageContext.setAttribute("APP_PATH", request.getContextPath()); %>
  </head>
  <body>
    <h1>Index</h1>
    <a href="${ APP_PATH }/hello">hello</a><br><br>
    <a href="${ APP_PATH }/tomcat">hello tomcat</a><br><br>
    <a href="${ APP_PATH }/success">success</a><br><br>
    <a href="${ APP_PATH }/async">async</a><br><br>
    <img src="${ APP_PATH }/imgs/toolbar.png" alt="toolbar.png"><br><br>
  </body>
</html>
