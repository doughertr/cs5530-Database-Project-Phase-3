package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Keywords
{

	public static void removeKeywordFromTH(int hid, String keyword)
	{
		ResultSet widSet = null;
		try
		{
			// get wid from Keyword
			int wid;
			String sqlSelectWID = "select wid from Keywords where word = \"" + keyword + "\";";
			widSet = Connector.stmt.executeQuery(sqlSelectWID);
			
			if (widSet.next())
			{
				wid = widSet.getInt(1);
			} else
			{
				widSet.close();
				System.out.println("Something went wrong removing this keyword");
				return;
			}

			// now have wid for keyword, remove the relationship from the table
			String sqlInsertNewHasKeyword = "Delete FROM Has_Keywords WHERE hid = \"" + hid + "\" and wid = \"" + wid
					+ "\";";
			Connector.stmt.executeUpdate(sqlInsertNewHasKeyword);

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}finally
		{
			try
			{
				if (widSet != null && !widSet.isClosed())
					widSet.close();
			} catch (Exception e)
			{
				System.out.println("cannot close resultset");
			}
		}

	}

	public static void AddNewKeyword(int hid, String keyword)
	{
		ResultSet widSet = null;
		try
		{
			// get wid from Keyword
			int wid;
			String sqlSelectWID = "select wid from Keywords where word = \"" + keyword + "\";";
			widSet = Connector.stmt.executeQuery(sqlSelectWID);
			if (widSet.next())
			{
				wid = widSet.getInt(1);
			} else
			{
				// this keyword isn't in the database
				// insert it, get ID
				widSet.close();
				String sqlInsertNewKeyword = "INSERT INTO Keywords(word) VALUES(\"" + keyword + "\");";
				Connector.stmt.executeUpdate(sqlInsertNewKeyword);
				widSet = Connector.stmt.executeQuery("SELECT LAST_INSERT_ID();");
				widSet.next();
				wid = widSet.getInt(1);
			}

			// now have wid for keyword, insert relationship into table
			String sqlInsertNewHasKeyword = "INSERT INTO Has_Keywords(hid,wid) VALUES(\"" + hid + "\", \"" + wid
					+ "\");";
			Connector.stmt.executeUpdate(sqlInsertNewHasKeyword);

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}finally
		{
			try
			{
				if (widSet != null && !widSet.isClosed())
					widSet.close();
			} catch (Exception e)
			{
				System.out.println("cannot close resultset");
			}
		}
	}

	public static ArrayList<String> getKeywordsForTH(int hid)
	{
		String sqlGetWords = "SELECT word FROM Has_Keywords, Keywords Where Keywords.wid = Has_Keywords.wid AND hid = \"" + hid + "\";";
		ArrayList<String> Keywords = new ArrayList<String>();
		ResultSet rsKeyword = null;
		try
		{
			rsKeyword = Connector.stmt.executeQuery(sqlGetWords);
			while (rsKeyword.next())
			{
				String keyword = rsKeyword.getString(1);
				Keywords.add(keyword);
			}
			rsKeyword.close();
		} catch (Exception e)
		{

			System.out.println(e.getMessage());
		} finally
		{
			try
			{
				if (rsKeyword != null && !rsKeyword.isClosed())
					rsKeyword.close();
			} catch (Exception e)
			{
				System.out.println("cannot close resultset");
			}
		}
		return Keywords;
	}
}
