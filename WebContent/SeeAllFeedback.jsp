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
String ratingStr = request.getParameter("ratingValue");
if(indexStr == null && ratingStr == null)
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

	<p>Rate the usefulness of a feedback</p>
	Index of Feedback to rate:
	<input type=text name="indexValue" length=5>
	Rating (0 = useless, 1 = useful, 2 = very useful):
	<input type=text name="ratingValue" length=5>
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
	int rating = Integer.parseInt(ratingStr);
	Feedback fb = reviews.get(index - 1);
	if (fb.login != u.login)
	{
		Feedback.rateUsefulnessOfFeedback(rating, fb.fid, u.login);%>
		<form method=get action="SeeAllFeedback.jsp">
		<h>You gave the feedback from <%=fb.login%> a rating of <%=rating %></h>
		<input type="submit" value = "Go back"/>
		</form>
	<%}
	else
	{%>
		<form method=get action="SeeAllFeedback.jsp">
		<h>You cannot review your own feedback</h>
		<input type="submit" value = "Go back"/>
		</form>
	<%}
}

%>
</body>
</html>