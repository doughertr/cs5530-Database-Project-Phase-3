package cs5530;

import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.jsp.JspWriter;

public class MainUserMenu
{

	public static void displayMenuText(JspWriter out) throws IOException
	{
		out.write("<ol>");
		out.write("<li>------Main Menu------</li>");
		out.write("<li>View Reservations and record stays</li>");
		out.write("<li>View Past Stays</li>");
		out.write("<li>Browse Available Houses</li>");
		out.write("<li>Manage Properties</li>");
		out.write("<li>View Shopping Cart</li>");
		out.write("<li>View User Stats</li>");
		out.write("<li>Community</li>");
		out.write("<li>View Admin Menu</li>");
		out.write("<li>Exit</li>");
		out.write("</ol>");
	}

	public static void displayMenu(User currUser) throws Exception
	{
		int c = 0;
		String choice = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{

			while ((choice = in.readLine()) == null && choice.length() == 0);
			try
			{
				c = Integer.parseInt(choice);
			} catch (Exception e)
			{
				continue;
			}

			// invalid menu selection
			if (c < 1 | c > 9)
				continue;
			// View reservations
			if (c == 1)
			{
				System.out.println(
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
				}

			}
			// view past stays
			else if (c == 2)
			{
				
			}
			// Browse TH
			else if (c == 3)
			{
				THBrowsingMenu.displayMenu(currUser);
			}
			// Register a new TH
			else if (c == 4)
			{
				ManagePropertyMenu.displayMenu(currUser);
			}
			// view shopping cart
			else if (c == 5)
			{
				ShoppingCartMenu.displayMenu(currUser);
			}
			// view stats
			else if (c == 6)
			{
				StatsMenu.displayMenu(currUser);
			} else if (c == 7)
			{
				// Community Menu
				CommunityMenu.displayMenu(currUser);
			} else if (c == 8)
			{
				AdminMenu.displayMenu(currUser);

			} 
			else
			{
				return;
			}
		}
	}

	private static void recordAStay(Reservation r, User u, BufferedReader in) throws IOException
	{
		Period p = r.reservationPeriod;
		System.out.println("Did you stay the whole length of your reservation? (y/n)");
		if (!in.readLine().trim().toLowerCase().equals("y"))
		{
			Date stayStart;
			Date stayEnd;
			String start;
			String end;
			
			System.out.println("All Dates should be in the format YYYY-MM-DD");
			
			System.out.println("Enter Start Date of your stay: ");
			while ((start = in.readLine()) == null && start.length() == 0);
			stayStart = Date.valueOf(start);
			
			System.out.println("Enter End Date of your stay: ");
			while ((end = in.readLine()) == null && end.length() == 0);
			stayEnd = Date.valueOf(end);
			
			if(!stayStart.before(stayEnd))
			{
				System.out.println("Start date must be before end date");
				return;
			}
			
			if(!p.isWithinPeriod(stayStart, stayEnd))
			{
				System.out.println("Stay must be within reservation period");
				return;
			}
			else
			{
				//make a stay object
				//Add it to the list of stays
				//then go through and commit in the shopping cart menu
				Period newPeriod = Period.addNewPeriod(start, end);
				Stay temp = new Stay(u.login, r.hid, newPeriod, r.cost);
				u.newStays.add(temp);
			}
		}
		else
		{
			Stay temp = new Stay(u.login, r.hid, r.pid, r.cost);
			u.newStays.add(temp);
		}
	}

}
