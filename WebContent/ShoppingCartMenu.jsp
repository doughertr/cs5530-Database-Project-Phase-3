<%@page import="cs5530.*" import="java.util.*,java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shopping Cart</title>
</head>
<body style="background-color:powderblue;">
<% 
User u = User.class.cast(session.getAttribute("User"));
ArrayList<Reservation> reservationList = u.getReservations();
ArrayList<Stay> stayList = u.newStays;
%>
	<h1>Shopping Cart</h1>
	<form action="ShoppingCartCheckout.jsp">
	<h2>Reservations in cart:</h2>
	<ol>
		<%
			for(Reservation r : reservationList)
			{ %>
				<li> <%=r.toString()%></li>
			<%
			}%>
	</ol>
	<h2>Reservations in cart:</h2>
	<ol>
		<%
			for(Stay s : stayList)
			{ %>
				<li> <%=s.toString()%></li>
			<%
			}%>
	</ol>
	<button type=submit> Checkout </button>
	<BR><BR>
	</form>
	<form action="MainUserMenu.jsp">
    <input type="submit" value="Go Back" />
	</form>
</body>
</html>