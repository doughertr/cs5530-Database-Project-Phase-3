<%@page import="cs5530.*" import="java.util.*,java.lang.*, java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Favorite Added</title>
</head>
<body style="background-color:powderblue;">
<% 
User u = User.class.cast(session.getAttribute("User")); 
TH th = TH.class.cast(session.getAttribute("TH")); 
User.addFavorite(u.login, th.hid, new Date(System.currentTimeMillis()).toString());
%>

<form method=get action="SingleTHMenu.jsp">
<h1>Added <%=th.name%> to favorites</h1>
<input type="submit" value = "Go back"/>

</form>
</body>
</html>