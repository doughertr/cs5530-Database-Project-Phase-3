<%@page import="cs5530.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body style="background-color:powderblue;">
<!-- INSERT CODE HERE -->
<!-- First get a basic menu displaying for this screen,
	then add in links for it, so it connects to the various other pages I'll need,
	build the other pages, and give them forms to enter info -->
entered page:<% this.getClass().getSimpleName(); %>!

<h1>Manage Your Properties</h1>
<ol>

<!-- 		System.out.println("------Property Management------");
			System.out.println("1. Add a New TH");
			System.out.println("2. Update an existing TH");
			System.out.println("3. View all your listed THs");
			System.out.println("4. Update availability for a TH");
			System.out.println("5. Go back");
			System.out.println("Please enter your choice:"); -->

	<li><a href="NewTH.jsp">Add a new TH listing</a></li>
	<li><a href="UpdateTH.jsp">Update a TH listing</a></li>
	<li><a href="ViewAllTH.jsp">View all your listed TH's</a></li>
	<li><a href="MainUserMenu.jsp">Go back to main menu</a></li>
</ol>

</body>
</html>