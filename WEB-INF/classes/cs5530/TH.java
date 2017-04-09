package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//Goals For Today
//Get keywords working
//Display all keywords
//Let them enter a list of integers, seperated by commas, of the numbers of keywords to be removed
//let them add keywords using code from before

//Searching
//Add a search menu class
//Give options to search by category, keywords, etc.
//at each menu they can choose which field to search by, and give values, then give another option to add a new parameter
//have them enter and/or
//then bring back to the top of the menu
//construct a list of parameters and values for them to search by, and conjunctions for it
//then go into some crazy intense SQL code for that

public class TH
{

	int hid;
	String name;
	String address;
	String url;
	String category;
	String phoneNum;
	String yearBuilt;
	String ownerLogin;
	//ArrayList<String> keywords;

	public TH(int hid, String name, String address, String url, String category, String phoneNum, String yearBuilt,
			String login)
	{
		this.hid = hid;
		this.name = name;
		this.address = address;
		this.url = url;
		this.category = category;
		this.phoneNum = phoneNum;
		this.yearBuilt = yearBuilt;
		this.ownerLogin = login;
		//keywords = Keywords.getKeywordsForTH(hid);
	}

	@Override
	public String toString()
	{
		String returnMe = "Name: " + name + " | Address: " + address + " | Photo URL: " + url + " | Owner Phone Number: " + phoneNum
				+ " | Year Built: " + yearBuilt + " | Category: " + category;
		return returnMe;
	}

	public static TH getTH(int hid)
	{
		TH selectedTH = null;
		String sql = "select * from TH where hid = \"" + hid + "\";";
		ResultSet rs = null;
		try
		{
			rs = Connector.stmt.executeQuery(sql);
			if (rs.next())
			{
				selectedTH = new TH(rs.getInt("hid"), rs.getString("name"), rs.getString("address"),
						rs.getString("url"), rs.getString("category"), rs.getString("phone_number"),
						rs.getString("year_built"), rs.getString("login"));
			}
			rs.close();
			//selectedTH.keywords = Keywords.getKeywordsForTH(hid);
		} catch (Exception e)
		{
			System.out.println("cannot execute the query");
		} finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			} catch (Exception e)
			{
				System.out.println("cannot close resultset");
			}
		}
		return selectedTH;
	}
	public static ArrayList<TH> getAllTH()
	{
		ArrayList<TH> allTH = new ArrayList<TH>();
		String sql = "select * from TH;";
		ResultSet rs = null;
		try
		{
			rs = Connector.stmt.executeQuery(sql);
			while (rs.next())
			{
				allTH.add(new TH(rs.getInt("hid"), rs.getString("name"), rs.getString("address"),
						rs.getString("url"), rs.getString("category"), rs.getString("phone_number"),
						rs.getString("year_built"), rs.getString("login")));
			}
			rs.close();
			//allTH.keywords = Keywords.getKeywordsForTH(hid);
		} catch (Exception e)
		{
			System.out.println("cannot execute the query");
		}
		return allTH;
	}

	public static void AddNewTH(String name, String address, String url, String category, String phoneNum,
			String yearBuilt, String login, ArrayList<String> keywords)
	{
		String sql = "INSERT INTO TH(name, address, url, category, phone_number, year_built, login) " + "VALUES(\""
				+ name + "\", \"" + address + "\", \"" + url + "\", \"" + category + "\", \"" + phoneNum + "\", \""
				+ yearBuilt + "\", \"" + login + "\");";
		ResultSet rs = null;
		try
		{
			Connector.stmt.executeUpdate(sql);
			rs = Connector.stmt.executeQuery("SELECT LAST_INSERT_ID();");
			rs.next();
			int ID = rs.getInt(1);
			for (String keyword : keywords)
			{
				Keywords.AddNewKeyword(ID, keyword);
			}
			System.out.println("Added new TH, ID =" + ID);

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		} finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			} catch (Exception e)
			{
				System.out.println("cannot close resultset");
			}
		}
	}

	public static ArrayList<TH> listAllTHsForUser(String login)
	{
		String sql = "select * from TH where login = \"" + login + "\";";
		ArrayList<TH> properties = new ArrayList<TH>();
		Statement stmt2 = Connector.stmt;
		ResultSet rs = null;
		try
		{
			int row = 1;
			rs = stmt2.executeQuery(sql);
			while (rs.absolute(row))
			{
				int hid = rs.getInt("hid");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String url = rs.getString("url");
				String category = rs.getString("category");
				String phoneNum = rs.getString("phone_number");
				String yearBuilt = rs.getString("year_built");
				TH property = new TH(hid, name, address, url, category, phoneNum, yearBuilt, login);
				properties.add(property);
				row++;
			}
			rs.close();
		} catch (Exception e)
		{

			System.out.println(e.getMessage());
		} finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			} catch (Exception e)
			{
				System.out.println("cannot close resultset");
			}
		}
		return properties;
	}

	public static void updateTH(TH property)
	{
		String sql = "UPDATE TH SET name = \"" + property.name + "\", address = \"" + property.address + "\", url = \""
				+ property.url + "\", category = \"" + property.category + "\", phone_number = \"" + property.phoneNum
				+ "\", year_built = \"" + property.yearBuilt + "\" WHERE hid = " + property.hid + ";";
		try
		{
			Connector.stmt.executeUpdate(sql);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	public static ArrayList<TH> getRecommendedTHs(int hid)
	{
		ArrayList<TH> recommendedations = new ArrayList<TH>();
		String sql 	= 	"SELECT t.name, vt.visit_count "
					+	"FROM TH t, "
					+		"(SELECT v3.hid, COUNT(*) as visit_count "
					+    		"FROM Visit v3 "
					+       	"GROUP BY v3.hid) as vt "
					+	"WHERE t.hid != '" + hid + "' AND t.hid = vt.hid AND "
					+	"EXISTS( "
					+		"SELECT v1.login "
					+		"FROM Visit v1, Visit v2 "
					+    	"WHERE v1.login = v2.login " 
					+        	"AND v1.hid = '" + hid + "' " 
					+           "AND v2.hid = t.hid) "
					+	"ORDER BY vt.visit_count desc;";
			
		ResultSet rs = null;
		try {
			rs = Connector.stmt.executeQuery(sql);
			int row = 1;
			while(rs.absolute(row))
			{
				recommendedations.add(new TH(rs.getInt("hid"), rs.getString("name"), rs.getString("address"), rs.getString("url"),
												rs.getString("category"), rs.getString("phone_number"), rs.getString("year_built"), 
												rs.getString("login")));
				row++;
			}
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
		return recommendedations;
	}

}
