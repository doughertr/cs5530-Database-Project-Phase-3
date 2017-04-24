<%@page import="cs5530.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main Menu</title>
</head>
<body style="background-color:powderblue;">
<%User u = User.class.cast(session.getAttribute("User")); %>
<h1>Main Menu</h1>
<ol>
	<li><a href="UserHistory.jsp">View Reservations and record stays</a></li>
	<li><a href="THBrowsingMenu.jsp">Browse Available Houses</a></li>
	<li><a href="ManagePropertyMenu.jsp">Manage Properties</a></li>
	<li><a href="ShoppingCartMenu.jsp">View Shopping Cart</a></li>
	<li><a href="StatsMenu.jsp">View User Stats</a></li>
	<li><a href="CommunityMenu.jsp">View Community Page</a></li>
	<li><a href="AdminMenu.jsp">View Admin Menu</a></li>
	<form action="login.jsp">
    <input type="submit" value="logout" />
	</form>
</ol>
</body>
</html>