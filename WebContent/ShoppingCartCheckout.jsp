<%@page import="cs5530.*" import="java.util.*,java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Checkout</title>
</head>
<body>
<% 
User u = User.class.cast(session.getAttribute("User"));
ArrayList<Reservation> reservationList = u.getReservations();
ArrayList<Stay> stayList = u.newStays;

	for (int i = 0; i < reservationList.size(); i++) 
	{
		Reservation.makeReservation(reservationList.get(i));
	}
	reservationList.clear();
	for (int i = 0; i < stayList.size(); i++) 
	{
		Stay.RecordNewStay(stayList.get(i));
	}
	stayList.clear();
	response.sendRedirect(response.encodeRedirectURL("MainUserMenu.jsp"));
%>
</body>
</html>