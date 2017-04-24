<%@page import="cs5530.*" import="java.util.*,java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Community Menu</title>
</head>
<body style="background-color:powderblue;">
<h1>Community Menu</h1>
<%
//current user
User u = User.class.cast(session.getAttribute("User"));

//main menu selection
String selection = request.getParameter("indexValue");

//degree of sepreration selection
String usr1 = request.getParameter("degreeUsr1");
String usr2 = request.getParameter("degreeUsr2");

//user to trust
String trustUsr = request.getParameter("trustUsr");
String trustScore = request.getParameter("trustScore");

if(selection == null)
{%>
	<form action="CommunityMenu.jsp">
		<ol>
			<li>Check degrees of seperation between two users</li>
			<li>See users you've rated on trust</li>
			<li>Add trust rating for user</li>
			<li>Go back</li>
		</ol>
		<br>
		Enter your number selection:
		<input type=text name="indexValue">
		<input type=submit value="Submit">
	</form>
<%}
else if(selection.equals("1"))
{%>
	<h2>Enter usernames to check separation:</h2>
	<form action="CommunityMenu.jsp">
		Enter first user's name:		
		<input type=text name="degreeUsr1">
		<br>
		Enter second user's name:
		<input type=text name="degreeUsr2">
		<input type=submit value="Find Separation">
	</form>
<%
}
else if(selection.equals("2"))
{
	out.write("<h2>Trusted and Untrusted Users: </h2>");
	out.write("<ol>");
	ArrayList<String> trustRecords = User.getTrustRecordings(u.login);
	for(int i = 0; i < trustRecords.size();i++)
	{
		out.write("<li>" + trustRecords.get(i) + "<li>");
	}
	out.write("</ol>");
}
else if(selection.equals("3"))
{%>
	<h2>Enter a trust rating for a user:</h2>
	<form action="CommunityMenu.jsp">
		Enter a username:		
		<input type=text name="trustUsr">
		<br>
		Enter trust rating (0 = not trusted, 1 = trusted):
		<input type=text name="trustScore">
		<input type=submit value="Submit">
	</form>
<%}
else
{
	selection = null;
}


//DEGREE OF SEPERATION
if(usr1 != null && usr2 != null)
{
	CommunityMenu.degreesOfSeperation(usr1, usr2, out);
}

//TRUST SCORE
if(trustUsr != null && trustScore != null)
{
	boolean isTrusted = Integer.parseInt(trustScore) == 1;
	User.addTrustForUser(u.login, trustUsr, isTrusted);
}

%>
</body>
</html>