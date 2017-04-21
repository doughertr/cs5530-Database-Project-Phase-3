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
	if(form_obj.newUser.value == "true")
	{
		return true;
	}
	if( form_obj.nameValue.value == "")
	{
		alert("Please enter a username");
		return false;
	}
	else if(form_obj.passwordValue.value == "")
	{
		alert("Please enter a password");
		return false;
	}
	return true;
}

</script> 
</head>
<body style="background-color:powderblue;">
<%
String userName = request.getParameter("nameValue");
String password = request.getParameter("passwordValue");
if( userName == null && password == null ){
%>
	<form name="userCredentials" method=get onsubmit="return check_all_fields(this)" action="login.jsp">
		Enter a new Username:
		<input type=text name="nameValue" length=15>
		<BR><BR>
		Enter a new Password:
		<input type=text name="passwordValue" length=10>
		<BR><BR>
		Enter your First Name:
		<input type=text name="fnValue" length=10>
		<BR><BR>
		Enter your Last Name:
		<input type=text name="lnValue" length=10>
		<BR><BR>
		Enter your Home Address:
		<input type=text name="addressValue" length=10>
		<BR><BR>
		Enter your Phone Number:
		<input type=text name="phoneValue" length=10>
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
	User user = User.validateUser(userName, password);
	
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