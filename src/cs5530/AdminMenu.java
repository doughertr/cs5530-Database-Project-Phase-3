package cs5530;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AdminMenu
{
	public static void displayMenu(User u) throws Exception
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{
			System.out.println("		Admin Menu");
			System.out.println("1. View most Trusted Users");
			System.out.println("2. View most Useful Users");
			System.out.println("3. Go back");
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
			if (c < 1 | c > 3)
				continue;
			//most trusted users
			if (c == 1)
			{
				System.out.println("Enter in the max number of users to select: ");
				while ((choice = in.readLine()) == null && choice.length() == 0);
				int maxUsrs = Integer.parseInt(choice);
				
				String sql 	= "SELECT t1.login2 as login, (num_likes - num_hates) as trust_score "
							+	"FROM " 
							+	"(SELECT t.login2, COUNT(is_trusted_by) as num_likes "
						    +    	"FROM Users u, Trusts t "
						    +    	"WHERE u.login = t.login2 AND t.is_trusted_by = 1 "
						    +    	"GROUP BY t.login2) as t1, "
							+	"(SELECT t1.login2, COUNT(is_trusted_by) as num_hates "
						    +    	"FROM Users u1, Trusts t1 "
						    +    	"WHERE u1.login = t1.login2 AND t1.is_trusted_by = 0 "
						    +     	"GROUP BY t1.login2) as t2 "
							+	"WHERE t1.login2 = t2.login2 "
							+	"ORDER BY trust_score desc "
							+	"limit " + maxUsrs + ";";
				ResultSet rs = null;
				try {
					rs = Connector.stmt.executeQuery(sql);					
					System.out.println("----List of top " + maxUsrs + " trusted users----");
					
					int row = 1;
					while(rs.absolute(row))
					{
						System.out.println("#" + row + " " + rs.getString("login") + " - Trust score: " + rs.getString("trust_score"));
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
			//most useful users
			if (c == 2)
			{
				System.out.println("Enter in the max number of users to select: ");
				while ((choice = in.readLine()) == null && choice.length() == 0);
				int maxUsrs = Integer.parseInt(choice);
				
				String sql 	= "SELECT f.login, AVG(fr.rating) as avg_fb_rating "
							+	"FROM Feedback f, Feedback_Ratings fr "
							+   "WHERE fr.fid = f.fid "
							+	"GROUP BY f.login "
							+   "ORDER BY avg_fb_rating desc "
							+	"limit " + maxUsrs + ";";
				ResultSet rs = null;
				try {
					rs = Connector.stmt.executeQuery(sql);					
					System.out.println("----List of top " + maxUsrs + " useful users----");
					
					int row = 1;
					while(rs.absolute(row))
					{
						System.out.println("#" + row + " " + rs.getString("login") + " - Average feedback score: " + rs.getString("avg_fb_rating"));
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
			//exit 
			if (c == 3)
			{
				return;
			}
		}
	}
}
