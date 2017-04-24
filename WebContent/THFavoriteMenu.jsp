<%@page import="cs5530.*" import="java.util.*,java.lang.*"%>
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
	<form name="favoriteSelection">
		Please select your choice number:
		<select name="choice">
			<%
				for(int i = 0; i < favorites.size(); i++)
				{
					out.write("<option value=" + favorites.get(i) + ">" + (i + 1) + "</option>");
				}
			%>
		</select>
	</form>
	<%
		String stringSelectionOption = request.getParameter("choice");
		if(stringSelectionOption != null)
		{
			int selectionOption = Integer.parseInt(stringSelectionOption);
			session.setAttribute("THSelection", favorites.get(selectionOption));
			response.sendRedirect(response.encodeRedirectURL("SingleTHMenu.jsp"));
		}
	%>
</body>
</html>