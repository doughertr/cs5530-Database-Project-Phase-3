<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<script LANGUAGE="javascript">

function check_all_fields(form_obj){
	alert(form_obj.searchAttribute.value+"='"+form_obj.attributeValue.value+"'");
	if( form_obj.attributeValue.value == ""){
		alert("Search field should be nonempty");
		return false;
	}
	return true;
}

</script> 

<%
String userName = request.getParameter("nameValue");
String password = request.getParameter("passwordValue");
if( userName == null && password == null ){
%>

	Enter User Name:
	<form name="user" method=get onsubmit="return check_all_fields(this)" action="login.jsp">
		<!-- <input type=hidden name="userName" value="login"> -->
		<input type=text name="nameValue" length=10>
		<input type=submit>
	</form>
	<BR><BR>
	Enter Password:
	<form name="password" method=get onsubmit="return check_all_fields(this)" action="login.jsp">
		<!-- <input type=hidden name="password" value="Password"> -->
		<input type=text name="passwordValue" length=10>
		<input type=submit>
	</form>

<%

} 
%>

</body>
</html>