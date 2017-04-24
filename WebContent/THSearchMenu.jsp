<%@page import="cs5530.*" import="java.util.*,java.lang.*"%>
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
		<br>
		Sorting Options
		<select name="sortingOption">
			<option value="highPrice">Highest Average Price</option>
			<option value="feedbackScore">Highest Average Feedback Score</option>
			<option value="trustedUser">Highest Trusted User Score</option>
			<option value="noSoting">No Sorting</option>
		</select>
		<br>
		<br>
		<button type="submit" value="searchButton">Search</button>
	</form>
	<%
		
	String address = request.getParameter("addressSearch");
	String category = request.getParameter("categroySearch");
	String keyword = request.getParameter("keywordSearch");
	String low = request.getParameter("lowpriceSearch");
	String high = request.getParameter("highpriceSearch");
	String stringSelectionOption = request.getParameter("choice");
	ArrayList<TH> searchResults = null;
		
	String sqlSearch = "(SELECT * FROM TH t WHERE";
	int counter = 0;
	if(address != null && address != "")
	{
		counter++;
		//handling all searched addresses 
		String[] addresses = address.split("\\b((?=AND|OR|and|or)|(?<=AND|OR|and|or))");
		HelperFunctions.trimArray(addresses);
		
		for(int i = 0; i < addresses.length; i++)
		{
			sqlSearch += " address LIKE \"%" + addresses[i] + "%\" ";
			
			if(i >= addresses.length-1)
				break;
			
			sqlSearch += " " + addresses[++i] + " ";
		}
	}
	
	if(category != null && category != "")
	{
		if(counter != 0)
			sqlSearch += " AND ";
		counter++;
		//handling all searched categories
		String[] categories = category.split("\\b((?=AND|OR|and|or)|(?<=AND|OR|and|or))");
		HelperFunctions.trimArray(categories);
		for(int i = 0; i < categories.length; i++)
		{
			sqlSearch += " category LIKE \"%" + categories[i] + "%\" ";
			
			if(i >= categories.length-1)
				break;
			
			sqlSearch += " " + categories[++i] + " ";
		}
		
	}
	
	if(keyword != null && keyword != "")
	{
		if(counter != 0)
			sqlSearch += " AND ";
		counter++;
		//handling all searched keywords
		String[] keywords = keyword.split("\\b((?=AND|OR|and|or)|(?<=AND|OR|and|or))");
		HelperFunctions.trimArray(keywords);
		for(int i = 0; i < keywords.length; i++)
		{
			sqlSearch += " EXISTS("
					+ 		"SELECT * "
					+ 			"FROM Keywords k, Has_Keywords hk "
					+ 			"WHERE k.wid = hk.wid AND hk.hid = t.hid AND k.word LIKE '%" + keywords[i] + "%') ";
			
			if(i >= keywords.length-1)
				break;
			
			sqlSearch += " " + keywords[++i] + " ";
		}
	}
	
	if(high != null && low != null && high != "" && low != "")
	{
		double h = Double.parseDouble(high), l = Double.parseDouble(low);
		if(counter != 0)
			sqlSearch += " AND ";
		counter++;
		//handling all searched price ranges
		sqlSearch += " EXISTS("
				+ 		"SELECT * "
				+ 			"FROM Available a "
				+ 			"WHERE a.hid = t.hid AND a.price_per_night >= '" + l + "' AND a.price_per_night <= '" + h + "') ";
		
	}
	//end off SQL block
	sqlSearch += " ) as h ";
	//intializing the sql sorting options
	String sqlSort = "";
	String sortingOption = request.getParameter("sortingOption");
	if(sortingOption != null && sortingOption != "")
	{
		if(sortingOption.equals("highPrice"))
		{
			sqlSort = "SELECT * "
					+	"FROM TH t1, (SELECT h.hid, AVG(a.price_per_night) as avg_price "
					+					"FROM " + sqlSearch + " , Available a "
					+					"WHERE h.hid = a.hid "
					+					"GROUP BY h.hid) as t2 "
					+	"WHERE t1.hid = t2.hid "
					+	"ORDER BY avg_price desc;";
		}
		else if(sortingOption.equals("feedbackScore"))
		{
			sqlSort = "SELECT * "
					+	"FROM TH t1, (SELECT h.hid, AVG(f.feedback_score) as avg_score "
					+					"FROM " + sqlSearch + " , Feedback f "
					+					"WHERE h.hid = f.hid "
					+					"GROUP BY h.hid) as t2 "
					+	"WHERE t1.hid = t2.hid "
					+	"ORDER BY avg_score desc;";
		}
		else if(sortingOption.equals("trustedUser"))
		{
			sqlSort ="SELECT * "
					+	"FROM TH t1, (SELECT h.hid, AVG(f.feedback_score) as avg_trusted_score "
					+					"FROM " + sqlSearch + " , Feedback f, Users u "
					+					"WHERE h.hid = f.hid "
					+ 						"AND f.login = u.login "
					+ 						"AND NOT EXISTS (SELECT * "
					+											"FROM Trusts tr "
					+											"WHERE u.login = tr.login2 "
					+ 												"AND tr.is_trusted_by = 0) "
					+											"GROUP BY h.hid) as t2 "
					+	"WHERE t1.hid = t2.hid "
					+	"ORDER BY avg_trusted_score desc;";
		}
		else
		{
			//keep the query the same
			sqlSort = sqlSearch.substring(1, sqlSearch.length() - 8) + ";";
		}
		searchResults = THBrowsingMenu.displaySearchResults(sortingOption, sqlSort, out);
		
	}
	if(searchResults != null)
	{

		%>
		<br>
		<form name="THSelection">
			Please Select your choice number:
			<select name="choice">
				<%
					for(int i = 0; i < searchResults.size(); i++)
					{
						out.write("<option value=" + searchResults.get(i) + ">" + (i + 1) + "</option>");
					}
				%>
			</select>
			<button type=submit>Submit</button>
		</form>
		<%
		if(stringSelectionOption != null)
		{
			int selectionOption = Integer.parseInt(stringSelectionOption);
			session.setAttribute("TH", searchResults.get(selectionOption));
			response.sendRedirect(response.encodeRedirectURL("SingleTHMenu.jsp"));
		}
		else
		{
		}
	}
	%>
		
</body>
</html>