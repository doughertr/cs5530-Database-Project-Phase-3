<%@page import="cs5530.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Favorite THs</title>
</head>
<body>
	<h1>Favorite THs</h1>
	<%
		User u = User.class.cast(session.getAttribute("User"));
		ArrayList<TH> favorites = User.getFavorites(u.login);
		boolean run = false;
		if(!run)
		{
			for (int i = 0; i < favorites.size(); i++)
			{	
				out.write("<p>" + (i + 1) + ". " + favorites.get(i).name + "</p>");
			}
			run = false;
		}
	%>
</body>
</html>