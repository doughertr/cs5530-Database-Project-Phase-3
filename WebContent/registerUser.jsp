<%@ page language="java" import="cs5530.*" %>
<%@ page language="java" import="cs5530.User" %>
<%@ page language="java" import="cs5530.Connector" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register User</title>

<script LANGUAGE="javascript">

function check_all_fields(form_obj){
	if( form_obj.loginValue.value == "")
	{
		alert("Please enter a username");
		return false;
	}
	else if(form_obj.passwordValue.value == "")
	{
		alert("Please enter a password");
		return false;
	}
	else if(form_obj.fnValue.value == "")
	{
		alert("Please enter a first name");
		return false;
	}
	else if(form_obj.lnValue.value == "")
	{
		alert("Please enter a last name");
		return false;
	}
	else if(form_obj.addressValue.value == "")
	{
		alert("Please enter an address");
		return false;
	}
	else if(form_obj.phoneValue.value == "")
	{
		alert("Please enter a phone number");
		return false;
	}
	return true;
}

</script> 
</head>
<body style="background-color:powderblue;">
<%
String login = request.getParameter("loginValue");
String password = request.getParameter("passwordValue");
String fName = request.getParameter("fnValue");
String lName = request.getParameter("lnValue");
String address = request.getParameter("addressValue");
String phone = request.getParameter("phoneValue");
if(login == null && password == null && fName == null && lName == null && address == null && phone == null)
{%>
	<form name="userCredentials" method=get onsubmit="return check_all_fields(this)" action="login.jsp">
		Enter a new Username:
		<input type=text name="loginValue" length=15>
		<BR><BR>
		Enter a new Password:
		<input type=text name="passwordValue" length=25>
		<BR><BR>
		Enter your First Name:
		<input type=text name="fnValue" length=25>
		<BR><BR>
		Enter your Last Name:
		<input type=text name="lnValue" length=25>
		<BR><BR>
		Enter your Home Address:
		<input type=text name="addressValue" length=50>
		<BR><BR>
		Enter your Phone Number:
		<input type=text name="phoneValue" length=15>
		<BR><BR>
		<button type=submit> Register for Uotel! </button>
		<BR><BR>
	</form>
	<form action="login.jsp">
	<p>Already signed up?</p>
    <input type="submit" value="Login" />
	</form>
<%
} 
else{
	Connector con = new Connector();
	User user = User.createUser(login, password, fName, lName, address, phone, out);
	
	if(user != null)
	{%>
		logged in
	<%}
	else
	{%>
		Failed to Login
	<%}
}%>
</body>
</html>