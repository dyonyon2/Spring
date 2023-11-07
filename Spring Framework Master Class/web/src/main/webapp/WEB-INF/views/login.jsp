<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Login JSP</title>
</head>
<body>
<p><font color="red">${errorMessage}</font></p>
<form action="/spring-mvc/login" method="post">
Enter your name <input type="text" name="name" /> 
Enter your password <input type="password" name="password" /> <input type="submit" value="login"/>
</form>
<%
String test = "test123";
out.println(test);
Date date = new Date();
%>
<div>Current date is <%=date%></div>
My name is ${name} and password is ${password}
</body>
</html>