<%@page import="cs5530.*" import="java.util.*,java.lang.*,java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Make a Reservation</title>

<script LANGUAGE="javascript">

function check_all_fields(form_obj){
	if( form_obj.startValue.value == "")
	{
		alert("Please enter a start date");
		return false;
	}
	else if(form_obj.endValue.value == "")
	{
		alert("Please enter an end date");
		return false;
	}
	else if(form_obj.priceValue.value == "")
	{
		alert("Please enter a price");
		return false;
	}
	
	return true;
}

</script> 
</head>
<body style="background-color:powderblue;">

<!-- 
			Available rentalPeriod = Available.getAvailabilityInRange(th.hid, stayStart.toString(), stayEnd.toString());
			if (rentalPeriod == null)
			{
				System.out.println("This TH is not available during your stay");
			}
			else
			{
				int daysInStay = HelperFunctions.differenceBetweenDays(start, end);
				double cost = daysInStay * rentalPeriod.pricePerNight;
				Reservation r = new Reservation(u.login, th.hid, p, cost);
				u.newReservations.add(r);
			} -->
<%
User u = User.class.cast(session.getAttribute("User"));
TH th = TH.class.cast(session.getAttribute("TH")); 

String start = request.getParameter("startValue");
String end = request.getParameter("endValue");

/* String name, String address, String url, String category, String phoneNum,
String yearBuilt, String login, ArrayList<String> keywords */
if(start == null && end == null)
{%>
	<h>Adding Availability For TH</h>
	<p><%= th.toString() %></p>
	<form method=get onsubmit="return check_all_fields(this)" action="MakeReservation.jsp">
		<p>All Dates should be in the format YYYY-MM-DDS</p>
		Start Date:
		<input type=text name="startValue" length=15>
		<BR><BR>
		End Date:
		<input type=text name="endValue" length=50>
		<BR><BR>
		<button type=submit> Book It!</button>
		<BR><BR>
	</form>
	<form action="SingleTHMenu.jsp">
    <input type="submit" value = "Go Back"/>
	</form>
	<form action="MainUserMenu.jsp">
    <input type="submit" value = "Return to Main Menu"/>
	</form>
<%
} 
else{
	Date startDate = Date.valueOf(start);
	Date endDate = Date.valueOf(end);
	if(startDate.after(endDate))
	{ %> 
		<form name="availabilityDetails" method=get action="MakeReservation.jsp">
		<h> Start Date must be before end date </h>
		<input type="submit" value = "Go back"/>
	</form>
	<%
	}
	else
	{
		Period p = Period.addNewPeriod(start, end);
		Available rentalPeriod = Available.getAvailabilityInRange(th.hid, startDate.toString(), endDate.toString());
		if(rentalPeriod == null)
		{
			%> 
			<form name="availabilityDetails" method=get action="MakeReservation.jsp">
			<h1> <%=th.name %> is not available during this period </h1>
			<input type="submit" value = "Go back"/>
		</form>
		<%
		}
		int daysInStay = HelperFunctions.differenceBetweenDays(start, end);
		double cost = daysInStay * rentalPeriod.pricePerNight;
		Reservation r = new Reservation(u.login, th.hid, p, cost);
		u.newReservations.add(r);
		response.sendRedirect(response.encodeRedirectURL("SingleTHMenu.jsp"));
	}
}%>
</body>
</html>