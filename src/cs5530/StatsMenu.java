package cs5530;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.text.NumberFormatter;

public class StatsMenu 
{
	public static void displayMenu(User u) throws Exception
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{
			System.out.println("------Select a stat to view------");
			System.out.println("1. List Most Visited Houses");
			System.out.println("2. List Most Expensive Houses");
			System.out.println("3. List Highest Scoring Houses");
			System.out.println("4. Go back");
			System.out.println("Please enter your choice:");
			
			String choice;
			int c;
			while ((choice = in.readLine()) == null && choice.length() == 0);
			try 
			{
				c = Integer.parseInt(choice);
			} 
			catch (Exception e) 
			{
				continue;
			}
			String sql;
			
			if (c < 1 || c > 4)
       		 continue;
			
			//top users
       	 	if (c == 1)
       		{
				System.out.println("Enter in the max number of houses to select: ");
				while ((choice = in.readLine()) == null && choice.length() == 0);
				int maxhouses = Integer.parseInt(choice);
				
				sql = "SELECT t.hid, t.name, COUNT(*) as tot_visits "
					+ 	"FROM TH t, Visit v "
					+	"WHERE t.hid = v.hid "
					+	"GROUP BY t.hid, t.name "
					+	"ORDER BY tot_visits desc "
					+ 	"limit " + maxhouses + ";";
				
				ResultSet rs = null;
				try {
					rs = Connector.stmt.executeQuery(sql);
					int row = 1;
					
					System.out.println("----List of top " + maxhouses + " visited houses----");
					while(rs.absolute(row))
					{
						System.out.println("#" + row + " " + rs.getString("name") + " - Visited by " + rs.getString("tot_visits") + " users");
						row++;
					}
					System.out.println();
					rs.close();
				} catch (Exception e) {

					System.out.println("cannot execute the query");
				} finally {
					try {
						if (rs != null && !rs.isClosed())
							rs.close();
					} catch (Exception e) {
						System.out.println("cannot close resultset");
					}
				}
       		}
			//most expensive houses
       	 	if (c == 2)
    		{
				System.out.println("Enter in the max number of houses to select: ");
				while ((choice = in.readLine()) == null && choice.length() == 0);
				int maxHouses = Integer.parseInt(choice);
				
				sql = "SELECT t.hid, t.name, AVG(v.cost) as avg_cost "
					+ 	"FROM TH t, Visit v "
					+	"WHERE t.hid = v.hid "
					+	"GROUP BY t.hid, t.name "
					+ 	"ORDER BY avg_cost desc "
					+ 	"limit " + maxHouses + ";";
				
				ResultSet rs = null;
				try {
					rs = Connector.stmt.executeQuery(sql);
					int row = 1;
					
					System.out.println("----List of top " + maxHouses + " most expensive houses (by average price per visit)----");
					while(rs.absolute(row))
					{
						System.out.println("#" + row + " " + rs.getString("name") + " - Average price $" + rs.getString("avg_cost"));
						row++;
					}
					System.out.println();
					rs.close();
				} catch (Exception e) {

					System.out.println("cannot execute the query");
				} finally {
					try {
						if (rs != null && !rs.isClosed())
							rs.close();
					} catch (Exception e) {
						System.out.println("cannot close resultset");
					}
				}
    		}
       	 	if (c == 3)
       	 	{
				System.out.println("Enter in the max number of houses to select: ");
				while ((choice = in.readLine()) == null && choice.length() == 0);
				int maxHouses = Integer.parseInt(choice);
				
				sql = "SELECT t.hid, t.name, AVG(feedback_score) as avg_fb "
					+ 	"FROM TH t, Feedback f "
					+ 	"WHERE t.hid = f.hid "
					+ 	"GROUP BY t.hid, t.name "
					+ 	"ORDER BY avg_fb desc "
					+ 	"limit " + maxHouses + ";";
				
				ResultSet rs = null;
				try {
					rs = Connector.stmt.executeQuery(sql);
					int row = 1;
					
					System.out.println("----List of top " + maxHouses + " rated houses (by average ratings from all users)----");
					while(rs.absolute(row))
					{
						System.out.println("#" + row + " " + rs.getString("name") + " - Average rating: " + rs.getString("avg_fb"));
						row++;
					}
					System.out.println();
					rs.close();
				} catch (Exception e) {

					System.out.println("cannot execute the query");
				} finally {
					try {
						if (rs != null && !rs.isClosed())
							rs.close();
					} catch (Exception e) {
						System.out.println("cannot close resultset");
					}
				}
			}	
	       	if (c == 4)
    	 	{
    	 		return;
			}	
		}
	}
}
