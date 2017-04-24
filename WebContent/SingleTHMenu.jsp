<%@page import="cs5530.*" import="java.util.*,java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Single TH Menu</title>
</head>
<body style="background-color:powderblue;">
<% 
User u = User.class.cast(session.getAttribute("User")); 
TH th = TH.class.cast(session.getAttribute("TH")); 

String indexStr = request.getParameter("indexValue");
%>
			
<h1>Viewing Details for <%=th.name%></h1>
<ol>
	<li>Listing name: <%=th.name%></li>
	<li>Address: <%=th.address%></li>
	<li>Photo URL: <%=th.url%></li>
	<li>Category: <%=th.category%></li>
	<li>Contact Phone Number: <%=th.phoneNum%></li>
	<li>Year built: <%=th.yearBuilt%></li>
</ol>
<BR><BR>
<form action="addFavorite.jsp">
    <input type="submit" value = "Add TH to Favorites"/>
    <BR><BR>
</form>

<ol>
	<li><a href="MakeReservation.jsp">Book a visit</a></li>
	<li><a href="SeeAllFeedback.jsp">See All Feedback</a></li>
	<li><a href="LeaveFeedback.jsp">Leave feedback</a></li>
</ol>
<BR><BR>
<form action="MainUserMenu.jsp">
    <input type="submit" value = "Return to Main Menu"/>
    <BR><BR>
</form>
			
</body>
</html>