<%@page import="cs5530.*" import="java.util.*,java.lang.*,java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Record a Stay</title>

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

<%
User u = User.class.cast(session.getAttribute("User"));
Reservation r = Reservation.class.cast(session.getAttribute("Reservation"));
Period p = r.reservationPeriod;
String start = request.getParameter("startValue");
String end = request.getParameter("endValue");

if(start == null && end == null)
{%>
	<h1>Recording a stay</h1>
	<h>During reservation: <%=r.toString() %></h>
	<p>All Dates should be in the format YYYY-MM-DD</p>

	<form method=get onsubmit="return check_all_fields(this)" action="RecordStay.jsp">
		Start Date Of Your Stay:
		<input type=text name="startValue" length=15>
		<BR><BR>
		End Date Of Stay:
		<input type=text name="endValue" length=50>
		<BR><BR>
		<button type=submit> Record Stay </button>
		<BR><BR>
	</form>
	<form action="UserHistory.jsp">
    <input type="submit" value = "Go Back"/>
	</form>
<%
} 
else{
	Date startDate = Date.valueOf(start);
	Date endDate = Date.valueOf(end);
	if(!startDate.before(endDate))
	{ %> 
		<form method=get action="UserHistory.jsp">
		<h> Start Date must be before end date </h>
		<input type="submit" value = "Go back"/>
		</form>
	<%
	}
	else if(!p.isWithinPeriod(startDate, endDate))
	{
		%> 
		<form method=get action="UserHistory.jsp">
		<h>Start and end date must be within reservation period</h>
		<input type="submit" value = "Go back"/>
		</form>
	<%
	}
	else
	{
		Period newPeriod = Period.addNewPeriod(start, end);
		Stay temp = new Stay(u.login, r.hid, newPeriod, r.cost);
		u.newStays.add(temp);
		response.sendRedirect(response.encodeRedirectURL("UserHistory.jsp"));
	}
}%>
</html>