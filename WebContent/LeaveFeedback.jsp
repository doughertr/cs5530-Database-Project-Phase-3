<%@page import="cs5530.*" import="java.util.*,java.lang.*, java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Leave Feedback</title>
</head>
<body style="background-color:powderblue;">
<% 
User u = User.class.cast(session.getAttribute("User")); 
TH th = TH.class.cast(session.getAttribute("TH")); 
String scoreStr = request.getParameter("scoreValue");
String messageStr = request.getParameter("messageValue");
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
	<p>Your Feedback Details:</p>
	Score (0 = terrible, 10 = excellent):
	<input type=text name="scoreValue" length=5>
	Feedback Message:
	<input type=text name="messageValue" length=5>
    <input type="submit" value="Leave Feedback" />
    <BR><BR>
	</form>
	<form action="SeeAllFeedback.jsp">
    <input type="submit" value="Go Back" />
	</form>
<%} 
else
{
	int score = Integer.parseInt(scoreStr);
	String currDate = new Date(System.currentTimeMillis()).toString();
	Feedback temp = new Feedback(-1, score, messageStr, currDate, th.hid, u.login);
	Feedback.leaveFeedback(temp);
	response.sendRedirect(response.encodeRedirectURL("SingleTHMenu.jsp"));
}

%>
</body>
</html>