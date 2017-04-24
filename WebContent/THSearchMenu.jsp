<%@page import="cs5530.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TH Browsing Menu</title>
</head>
<body>
	<h1>Search for a TH</h1>
	<br>
	<p>Search for a TH by inputting values into search fields. Use OR and AND to specify multiple values in a given field</p>
	<br>
	<form name="browsingOptions" method=GET onsubmit="">
		Address
		<input type="text" name="addressSearch">
		<br>
		Categories
		<input type="text" name="categorySearch">
		<br>
		Keywords
		<input type="text" name="keywordSearch">
		<br>
		Price Range
		<input type="text" name="lowpriceSearch">
		<input type="text" name="highpriceSearch">
		<br><br>
		<button type="submit" value="searchButton">Search</button>
	</form>
	<%
		
		String address = request.getParameter("addressSearch");
		String category = request.getParameter("categroySearch");
		String keyword = request.getParameter("keywordSearch");
		String low = request.getParameter("lowpriceSearch");
		String high = request.getParameter("highpriceSearch");
		
	if(address != null)
	{
		String[] addresses = address.split("\\b((?=AND|OR|and|or)|(?<=AND|OR|and|or))");
		HelperFunctions.trimArray(addresses);
		
		String sqlSearch = "(SELECT * FROM TH t WHERE";
		for(int i = 0; i < addresses.length; i++)
		{
			sqlSearch += " address LIKE \"%" + addresses[i] + "%\" ";
			
			if(i >= addresses.length-1)
				break;
			
			sqlSearch += " " + addresses[++i] + " ";
		}
			
		sqlSearch += " category LIKE \"%" + category + "%\" ";
		sqlSearch += " EXISTS("
				+ 		"SELECT * "
				+ 			"FROM Keywords k, Has_Keywords hk "
				+ 			"WHERE k.wid = hk.wid AND hk.hid = t.hid AND k.word LIKE '%" + keyword + "%') ";
		sqlSearch += " EXISTS("
				+ 		"SELECT * "
				+ 			"FROM Available a "
				+ 			"WHERE a.hid = t.hid AND a.price_per_night >= '" + low + "' AND a.price_per_night <= '" + high + "') ";
		
	}
	%>
</body>
</html>