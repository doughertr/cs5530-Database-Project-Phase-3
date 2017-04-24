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
TH updateTH = TH.class.cast(session.getAttribute("TH")); 

String name = request.getParameter("nameValue");
String address = request.getParameter("addressValue");
String url = request.getParameter("urlValue");
String category = request.getParameter("catValue");
String phone = request.getParameter("phoneValue");
String year = request.getParameter("yearValue");
String keywords = request.getParameter("keyValues");

/* String name, String address, String url, String category, String phoneNum,
String yearBuilt, String login, ArrayList<String> keywords */
if(name == null && address == null && url == null && category == null && phone == null && year == null&& keywords == null)
{%>
	<h>Editting TH details</h>
	<p><%= updateTH.toString() %></p>
	<form name="THDetails" method=get action="UpdateTH.jsp">
		TH Listing Name:
		<input type=text name="nameValue" length=15>
		<BR><BR>
		TH Address:
		<input type=text name="addressValue" length=50>
		<BR><BR>
		Photo URL:
		<input type=text name="urlValue" length=25>
		<BR><BR>
		TH Category:
		<input type=text name="catValue" length=25>
		<BR><BR>
		Contact Phone Number:
		<input type=text name="phoneValue" length=50>
		<BR><BR>
		Year Built:
		<input type=text name="yearValue" length=15>
		<BR><BR>
		TH Keywords (Separated by commas):
		<input type=text name="keyValues" length=15>
		<BR><BR>
		<button type=submit> Register your TH </button>
		<BR><BR>
	</form>
	<form action="ManagePropertyMenu.jsp">
    <input type="submit" value = "Go Back"/>
    <BR><BR>
	</form>
	<form action="AddTHAvailability.jsp">
    <input type="submit" value = "Add TH Availability"/>
	</form>
<%
} 
else{
	TH.updateTH(name, address, url, category, phone, year, u.login, keywords, updateTH.hid);
	response.sendRedirect(response.encodeRedirectURL("ManagePropertyMenu.jsp"));
}%>

</body>
</html>