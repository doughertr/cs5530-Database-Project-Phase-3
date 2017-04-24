package cs5530;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.jsp.JspWriter;

public class CommunityMenu
{
	
	@SuppressWarnings("null")
	public static void displayMenu(User u) throws Exception
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{
			System.out.println("------Community Options------");
			System.out.println("1. Check Degrees of Seperation");
			System.out.println("2. See users you've rated on trust");
			System.out.println("3. Add trust rating for user");
			System.out.println("4. Go back");
			System.out.println("Please enter your choice:");

			String choice;
			int c;
			while ((choice = in.readLine()) == null && choice.length() == 0);
			try
			{
				c = Integer.parseInt(choice);
			} catch (Exception e)
			{
				continue;
			}

			// invalid menu selection
			if (c < 1 | c > 4)
				continue;
			if (c == 1)
			{
				String usr1 = null, usr2 = null;
				
				System.out.println("Enter user 1's login: ");
				while ((usr1 = in.readLine()) == null && usr1.length() == 0);
				
				System.out.println("Enter user 2's login: ");
				while ((usr2 = in.readLine()) == null && usr2.length() == 0);
				
				//degreesOfSeperation(usr1,usr2);
			}
			if (c == 2)
			{
				//list all trusted users
				System.out.println("Trusted and Untrusted Users: ");
				ArrayList<String> trustRecords = User.getTrustRecordings(u.login);
				for(int i = 0; i < trustRecords.size();i++)
				{
					System.out.println(trustRecords.get(i));
				}
			}
			if (c == 3)
			{
				String usr;
				String trust;
				System.out.println("Enter User Login: ");
				while ((usr = in.readLine()) == null && usr.length() == 0);
				System.out.println("Enter trust rating (0 = not trusted, 1 = trusted): ");
				while ((trust = in.readLine()) == null && trust.length() == 0);
				Boolean isTrusted = Integer.parseInt(trust) == 1;
				
				User.addTrustForUser(u.login, usr, isTrusted);
			}
			if (c == 4)
			{
				return;
			}
		}
	}

	public static void degreesOfSeperation(String usr1, String usr2, JspWriter out) throws IOException
	{
		try 
		{
			//checking for 1 degree of separation
			String sql = "SELECT COUNT(*) as separation "
					   + 	"FROM Favorites f1, Favorites f2 "
					   + 	"WHERE f1.login = '"+ usr1 +"' AND f2.login = '" + usr2 + "' AND f1.hid = f2.hid "
					   + 	"GROUP BY f1.hid "
					   + 	"HAVING COUNT(*) >= 1;";
			
			ResultSet rs = null;
			rs = Connector.stmt.executeQuery(sql);
			if(rs.next() && rs.getInt("separation") > 0)
			{
				out.write("<p>" + usr1 + " has 1 degree of separation from " + usr2 + "</p>");
			}
			//if 1 degree of separation doesn't exist, check for 2 degrees!
			else
			{
				sql = "SELECT COUNT(*) as separation "
						+ 	"FROM Users u "
						+	"WHERE EXISTS (SELECT * "
						+					"FROM Favorites f1, Favorites f2 "
						+  					"WHERE f1.login = '" + usr1 + "' AND f2.login = u.login AND f1.hid = f2.hid "
						+  					"GROUP BY f1.hid "
						+  					"HAVING COUNT(*) >= 1) "
						+  	"AND EXISTS (SELECT * "
						+					"FROM Favorites f3, Favorites f4 "
						+  					"WHERE f3.login = '" + usr2 + "' AND f4.login = u.login AND f3.hid = f4.hid "
						+  					"GROUP BY f3.hid "
						+  					"HAVING COUNT(*) >= 1);";
				rs = Connector.stmt.executeQuery(sql);
				if(rs.next() && rs.getInt("separation") > 0)
				{
					out.write("<p>" + usr1 + " has 2 degrees of separation from " + usr2 + "</p>");
				}	
				else
				{
					out.write("<p>" + "There exists no degrees of separation between " + usr1 + " and " + usr2 + "</p>");
				}
				rs.close();
			}
		} 
		catch (Exception e) 
		{
			out.write("query failed: " + e.getMessage());
			return;
		}
	}

}
