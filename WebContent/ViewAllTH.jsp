<%@page import="cs5530.*" import="java.util.*,java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body style="background-color:powderblue;">
<% User u = User.class.cast(session.getAttribute("User")); 
ArrayList<TH> properties = TH.listAllTHsForUser(u.login);%>
	
	<form action="ViewAllTH.jsp">
	<p>Viewing TH Listings Owned By <%=u.login %> </p>
	<ol>
	<%
	for(TH property : properties)
	{ %>
		<li> <%=property.toString()%></li>
	<%
	} %>
	</ol>

	Enter The Index for the TH you want to edit:
	<input type=text name="indexValue" length=5>
    <input type="submit" value="Update A TH" />
	</form>
</body>
</html>