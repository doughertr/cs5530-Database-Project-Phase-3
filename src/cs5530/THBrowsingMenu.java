package cs5530;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;

public class THBrowsingMenu
{
	public static void displayMenu(User u) throws Exception
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{
			System.out.println("------TH Browsing Menu------");
			System.out.println("1. Search for a TH");
			System.out.println("2. See your favorite TH's");
			System.out.println("3. View all listed TH's");
			System.out.println("4. Go back");
			System.out.println("Please enter your choice:");

			String choice;
			int c;
			while ((choice = in.readLine()) == null && choice.length() == 0)
				;
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
				Search(u, in);
			}
			if (c == 2)
			{
				ArrayList<TH> favorites = User.getFavorites(u.login);
				for (int i = 0; i < favorites.size(); i++)
				{
					System.out.println((i + 1) + ". " + favorites.get(i).name);
				}
				System.out.println("Type in a favorite TH number to view it, or hit enter to go back.");
				String viewTH;
				if (!(viewTH = in.readLine()).trim().equals(""))
				{
					int thChoice = Integer.parseInt(viewTH) - 1;
					TH th = favorites.get(thChoice);
					SingleTHMenu.displayMenu(u, th);
				}
			}
			if (c == 3)
			{
				System.out.println("-----All THs-----");
				for(TH house : TH.getAllTH())
				{
					System.out.println(house);
				}
				System.out.println();
			}
			if (c == 4)
			{
				return;
			}
		}
	}

	private static void Search(User U, BufferedReader in) throws Exception
	{
		// could build a really long sql query here
		// or could do each statement separately as a search to form a new temp
		// table
		// and then at the end depending on the and/or union/join them together
		String sqlSearch = "(SELECT * FROM TH t WHERE";
		System.out.println("		Choose a category for a search parameter");
		
		while (true)
		{
			System.out.println("1. Search by address (city, state, street, etc)");
			System.out.println("2. Search by Category");
			System.out.println("3. Search by Keyword");
			System.out.println("4. Search by Price Range");
			System.out.println("5. Quit Search");
			System.out.println("Please enter your choice:");

			String choice;
			int c;
			while ((choice = in.readLine()) == null && choice.length() == 0)
				;
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
			
			//search by address
			if (c == 1)
			{
				String address;
				System.out.println("Enter an address:");
				while ((address = in.readLine()) == null && address.length() == 0);

				sqlSearch += " address LIKE \"%" + address + "%\" ";
			}
			//search by category
			if (c == 2)
			{
				String category;
				System.out.println("Enter a category:");
				while ((category = in.readLine()) == null && category.length() == 0);

				sqlSearch += " category LIKE \"%" + category + "%\" ";
			}
			//search by Keyword
			if (c == 3)
			{
				//where exists 
				String keyword;
				System.out.println("Enter a keyword:");
				while ((keyword = in.readLine()) == null && keyword.length() == 0);

				sqlSearch 	+= " EXISTS("
							+ 		"SELECT * "
							+ 			"FROM Keywords k, Has_Keywords hk "
							+ 			"WHERE k.wid = hk.wid AND hk.hid = t.hid AND k.word LIKE '%" + keyword + "%') ";
			}
			//search by price range
			if (c == 4)
			{
				//selecting all availabilities for a specific house that are within
				//the desired price range
				String l;
				System.out.println("Enter your lowest desired price: ");
				while ((l = in.readLine()) == null && l.length() == 0);
				String h;
				System.out.println("Enter your highest desired price: ");
				while ((h = in.readLine()) == null && h.length() == 0);

				double low = Double.parseDouble(l), high = Double.parseDouble(h);
				
				
				sqlSearch 	+= " EXISTS("
							+ 		"SELECT * "
							+ 			"FROM Available a "
							+ 			"WHERE a.hid = t.hid AND a.price_per_night >= '" + low + "' AND a.price_per_night <= '" + high + "') ";
			}
			if(c == 5)
			{
				return;
			}
			
			
			//Checking for another search parameter
			String cont; 
			System.out.println("Enter another search parameter? (y/n)");
			while ((cont = in.readLine()) == null && cont.length() == 0);
			if(cont.equals("n"))
				break;

			
			//Checking for either AND or OR input
			String type;
			System.out.println("AND or OR?: ");
			while ((type = in.readLine()) == null && type.length() == 0);
			if(type.toLowerCase().equals("and"))
			{
				sqlSearch += " AND ";
				continue;
			}
			else if(type.toLowerCase().equals("or"))
			{
				sqlSearch += " OR ";
				continue;
			}
			else
			{
				System.out.println("invalid result.");
				break;
			}
		}
		
		//end off SQL block
		sqlSearch += " ) as h ";
		
		//display sorting options
		String sqlSort;
		String choice;
		int c;
		System.out.println("------Sorting Options------");
		System.out.println("1. Sort by highest average price");
		System.out.println("2. Sort by highest average feedback score");
		System.out.println("3. Sort by highest average trusted user score");
		System.out.println("4. No Sorting");
		System.out.println("Please enter your choice:");
		
		while ((choice = in.readLine()) == null && choice.length() == 0);
		c = Integer.parseInt(choice);
		
		System.out.println();
		//ordering by average price
		if(c == 1)
		{
			System.out.println("Sorting by highest average price...");
			sqlSort = "SELECT * "
					+	"FROM TH t1, (SELECT h.hid, AVG(a.price_per_night) as avg_price "
					+					"FROM " + sqlSearch + " , Available a "
					+					"WHERE h.hid = a.hid "
					+					"GROUP BY h.hid) as t2 "
					+	"WHERE t1.hid = t2.hid "
					+	"ORDER BY avg_price desc;";
		}
		//ordering by average price
		else if(c == 2)
		{
			System.out.println("Sorting by highest average feedback score...");
			sqlSort = "SELECT * "
					+	"FROM TH t1, (SELECT h.hid, AVG(f.feedback_score) as avg_score "
					+					"FROM " + sqlSearch + " , Feedback f "
					+					"WHERE h.hid = f.hid "
					+					"GROUP BY h.hid) as t2 "
					+	"WHERE t1.hid = t2.hid "
					+	"ORDER BY avg_score desc;";
		}
		//ordering by average trusted user score
		else if(c == 3)
		{
			System.out.println("Sorting by highest average trusted user score...");
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
		//don't sort
		else
		{
			//keep the query the same
			sqlSort = sqlSearch.substring(1, sqlSearch.length() - 8) + ";";
		}
		
		System.out.println();
		
		//array list to store all selected houses
		ArrayList<TH> selectedHouses = new ArrayList<TH>();
		ArrayList<Double> searchResults = new ArrayList<Double>();
		ResultSet rs = null;
		try {
			rs = Connector.stmt.executeQuery(sqlSort);
			int row = 0;
			
			//reading from the relational set and adding into the array list
			while(rs.next())
			{
				//this section adds the searched keywords to a list to be displayed later
				
				//searching by average price
				if(c == 1)
				{
					searchResults.add(rs.getDouble("avg_price"));
				}
				//search by average feedback score
				else if(c == 2)
				{
					searchResults.add(rs.getDouble("avg_score"));
				}
				//search by avg trusted user score
				else if(c == 3)
				{
					searchResults.add(rs.getDouble("avg_trusted_score"));
				}
				else
				{
				}
				
				//Adding all of the other values from the TH
				selectedHouses.add(new TH(rs.getInt("hid"), rs.getString("name"), rs.getString("address"),
						rs.getString("url"), rs.getString("category"), rs.getString("phone_number"),
						rs.getString("year_built"), rs.getString("login")));
				
			}
			rs.close();
			
		} 
		catch (Exception e) 
		{
			System.out.println("cannot execute the query");
			return;
		}
		
		while (true)
		{
			//printing out search results and storing them inside of the array list
			System.out.println("-----All Search Results-----");
			for (int i = 0; i < selectedHouses.size(); i++) 
			{
				if(c == 1)
					System.out.println("#" + (i + 1) + " Average Price: " + searchResults.get(i) + " " + selectedHouses.get(i));
				else if(c == 2)
					System.out.println("#" + (i + 1) + " Average Feedback Score: " + searchResults.get(i) + " " + selectedHouses.get(i));
				else if(c == 3)
					System.out.println("#" + (i + 1) + " Average Trusted User Feedback Score: " + searchResults.get(i) + " " + selectedHouses.get(i));
				else
					System.out.println("#" + (i + 1) + " " + selectedHouses.get(i));
			}
			System.out.println("#" + (selectedHouses.size() + 1) + " EXIT");
			
			System.out.println();
			
			//getting user input
			System.out.println("Select the house number you wish to view (or " + (selectedHouses.size() + 1) + " to exit): ");
			while ((choice = in.readLine()) == null && choice.length() == 0);
			int d = Integer.parseInt(choice);
			
			//invalid choice
			if(d < 1 || d > selectedHouses.size() + 1)
			{
				System.out.println("Invalid choice");
				continue;
			}
			if(d == selectedHouses.size() + 1)
			{
				return;
			}
			
			//finally open up the menu once the choice has been made
			SingleTHMenu.displayMenu(U, selectedHouses.get(d-1));
		}
	}
}
