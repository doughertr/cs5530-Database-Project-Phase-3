<%@page import="cs5530.*" import="java.util.*,java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
     import="java.util.*,java.lang.*"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update a TH</title>
</head>
<body style="background-color:powderblue;">
<% 
User u = User.class.cast(session.getAttribute("User"));
ArrayList<TH> objects = TH.listAllTHsForUser(u.login); 
TH updateTH = TH.class.cast(session.getAttribute("TH")); %>
<p>Edit TH with details: <%= updateTH.toString() %>!</p>

</body>
</html>