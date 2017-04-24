<%@page import="cs5530.*" import="java.util.*,java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Feedback</title>
</head>
<body style="background-color:powderblue;">
<% 
User u = User.class.cast(session.getAttribute("User")); 
TH th = TH.class.cast(session.getAttribute("TH")); 
ArrayList<Feedback> reviews = Feedback.getAllFeedbackForTH(th.hid);
String indexStr = request.getParameter("indexValue");
if(indexStr == null)
{%>
	<form action="SeeAllFeedback.jsp">
	<h1>Viewing All Feedback for <%=th.name %> </h1>
	<ol>
	<%
	for(Feedback feedback : reviews)
	{ %>
		<li> <%=feedback.toString()%></li>
	<%
	} %>
	</ol>

	To rate the usefulness of feedback enter the feedback number:
	<input type=text name="indexValue" length=5>
    <input type="submit" value="rate feedback" />
    <BR><BR>
	</form>
	<form action="SingleTHMenu.jsp">
    <input type="submit" value="Go Back" />
	</form>
<%} 
else
{
	int index = Integer.parseInt(indexStr);	
	Feedback fb = reviews.get(index - 1);
	if (fb.login != u.login)
	{
		session.setAttribute("Feedback",fb);
		response.sendRedirect(response.encodeRedirectURL("UpdateTH.jsp"));
	}
	else
	{%>
		<form name="availabilityDetails" method=get action="MakeReservation.jsp">
		<h> Start Date must be before end date </h>
		<input type="submit" value = "Go back"/>
		</form>
	<%}
}

%>
</body>
</html>