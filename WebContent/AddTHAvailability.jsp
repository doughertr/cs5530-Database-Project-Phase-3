<%@page import="cs5530.*" import="java.util.*,java.lang.*,java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Availability</title>

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
TH updateTH = TH.class.cast(session.getAttribute("TH")); 

String start = request.getParameter("startValue");
String end = request.getParameter("endValue");
String price = request.getParameter("priceValue");

/* String name, String address, String url, String category, String phoneNum,
String yearBuilt, String login, ArrayList<String> keywords */
if(start == null && end == null && price == null)
{%>
	<h>Adding Availability For TH</h>
	<p><%= updateTH.toString() %></p>
	<form method=get onsubmit="return check_all_fields(this)" action="AddTHAvailability.jsp">
		<p>All Dates should be in the format YYYY-MM-DDS</p>
		Start Date Of Availability:
		<input type=text name="startValue" length=15>
		<BR><BR>
		End Date Of Availability:
		<input type=text name="endValue" length=50>
		<BR><BR>
		Price per night:
		<input type=text name="priceValue" length=25>
		<BR><BR>
		<button type=submit> Add Availability </button>
		<BR><BR>
	</form>
	<form action="UpdateTH.jsp">
    <input type="submit" value = "Go Back"/>
	</form>
	<form action="ManagePropertyMenu.jsp">
    <input type="submit" value = "Return to Property Management Menu"/>
	</form>
<%
} 
else{
	Date startDate = Date.valueOf(start);
	Date endDate = Date.valueOf(end);
	if(!startDate.before(endDate))
	{ %> 
		<form name="availabilityDetails" method=get action="UpdateTH.jsp">
		<h> Start Date must be before end date </h>
		<input type="submit" value = "Go back"/>
	</form>
	<%
	}
	else
	{
		double pricePerNight = Double.parseDouble(price);
		Available.addNewAvailability(updateTH.hid, startDate.toString(), endDate.toString(), pricePerNight);
		response.sendRedirect(response.encodeRedirectURL("ManagePropertyMenu.jsp"));
	}
}%>

</body>
</html>