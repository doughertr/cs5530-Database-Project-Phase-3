<%@page import="cs5530.*" import="java.util.*,java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Leave Feedback</title>
</head>
<body>
<% 
User u = User.class.cast(session.getAttribute("User")); 
TH th = TH.class.cast(session.getAttribute("TH")); 
String scoreStr = request.getParameter("indexValue");
String messageStr = request.getParameter("ratingValue");
boolean canLeaveFeedback = Feedback.canUserLeaveFeedback(th.hid, u.login);
if(!canLeaveFeedback)
{%>
	<form method=get action="SeeAllFeedback.jsp">
	<h>You have already left feedback for this TH</h>
	<input type="submit" value = "Go back"/>
	</form>
<%
}
else if(scoreStr == null && messageStr == null)
{%>
	<form action="LeaveFeedback.jsp">
	<h1>Leaving Feedback for <%=th.name %> </h1>

<!-- String scoreString;
		String message;

		System.out.println("Score (0 = terrible, 10 = excellent):");
		while ((scoreString = in.readLine()) == null && scoreString.length() == 0)
			;
		int score = Integer.parseInt(scoreString);

		System.out.println("Message:");
		while ((message = in.readLine()) == null)
			;

		String currDate = new Date(System.currentTimeMillis()).toString();

		Feedback temp = new Feedback(-1, score, message, currDate, th.hid, u.login);
		Feedback.leaveFeedback(temp); -->
	<p> </p>
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