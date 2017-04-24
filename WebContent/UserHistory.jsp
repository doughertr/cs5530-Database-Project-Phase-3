<%@page import="cs5530.*" import="java.util.*,java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Viewing Reservations</title>
</head>
<body style="background-color:powderblue;">
<% User u = User.class.cast(session.getAttribute("User")); 
ArrayList<Reservation> reservations =  Reservation.getAllReservationsForUser(u.login);

String indexStr = request.getParameter("indexValue");
String stayStr = request.getParameter("stayLengthValue");
if(indexStr == null)
{%>
	<h1>Viewing Reservations for <%=u.firstName%></h1>
	<p>Only confirmed Reservations are shown here, you may have others in your shopping cart</p>
	<!-- System.out.println(
						"Only confirmed Reservations are shown here, you may have others in your shopping cart");
				ArrayList<Reservation> reservations = Reservation.getAllReservationsForUser(currUser.login);
				for (int i = 0; i < reservations.size(); i++)
				{
					System.out.println((i + 1)+ ". " + reservations.get(i).toString());
				}
				System.out.println(
						"Enter a reservation number to record a stay during that period, or hit enter to skip");
				String reservationNum;
				int reservationIndex;
				if (!(reservationNum = in.readLine()).trim().equals(""))
				{
					reservationIndex = Integer.parseInt(reservationNum) - 1;
					Reservation r = reservations.get(reservationIndex);
					recordAStay(r, currUser, in);
				} -->
	<form action="UserHistory.jsp">
	<ol>
	<%
	for(Reservation r : reservations)
	{ %>
		<li> <%=r.toString()%></li>
	<%
	} %>
	</ol>

	<p>To record a stay enter the details below </p>
	Enter The Index for the reservation corresponding to your stay:
	<input type=text name="indexValue" length=5>
    <input type="submit" value="Update" />
    <BR><BR>
    Did you stay the whole length of your stay? (y/n)
    <input type=text name="stayLengthValue" length=1>
	</form>
	<form action="MainUserMenu.jsp">
    <input type="submit" value="Go Back" />
	</form>
	
<%} 
else
{
	int index = Integer.parseInt(indexStr);	
	Reservation res = reservations.get(index - 1);
	if(stayStr.equals("y"))
	{
		Stay temp = new Stay(u.login, res.hid, res.pid, res.cost);
		u.newStays.add(temp); 
		%>
			<form action="MainUserMenu.jsp">
			<p>Successfully added stay</p>
		    <input type="submit" value="Go Back" />
			</form>
		<%
	}
	else
	{
		session.setAttribute("Reservation",res);
		response.sendRedirect(response.encodeRedirectURL("RecordStay.jsp"));
	}
}

%>

</body>
</html>