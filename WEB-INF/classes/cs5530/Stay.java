package cs5530;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Stay
{
	String login;
	int hid;
	int pid;
	double cost;

	Period stayPeriod;

	// NOTES
	// TH suggestions must be displayed after a new reservation has been entered
	// on creating a reservation, it should edit the days
	public Stay(String login, int hid, int pid, double cost)
	{
		this.login = login;
		this.hid = hid;
		this.pid = pid;
		this.cost = cost;

		stayPeriod = Period.getPeriod(pid);
	}
	public Stay(String login, int hid, Period p, double cost)
	{
		this.login = login;
		this.hid = hid;
		this.pid = p.pid;
		this.cost = cost;
		stayPeriod = p;
	}

	public static void RecordNewStay(Stay s)
	{
		String sql = "INSERT INTO Visit(login, hid, pid, cost) VALUES(\"" + s.login + "\", \"" + s.hid + "\", \"" + s.pid + "\", \"" + s.cost + "\");";
		ResultSet rs = null;
		try
		{
			Connector.stmt.executeUpdate(sql);
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
	
	public static ArrayList<Stay> getAllStaysForUser(String login)
	{
		ArrayList<Stay> stays = new ArrayList<Stay>();

		String sql = "SELECT * FROM Visit WHERE login = '" + login + "';";
		ResultSet rs = null;
		try
		{
			rs = Connector.stmt.executeQuery(sql);
			while (rs.next())
			{
				Stay s = new Stay(login, rs.getInt("hid"), rs.getInt("pid"), rs.getDouble("cost"));
				stays.add(s);
			}

			rs.close();
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
		return stays;
	}

	@Override
	public String toString()
	{
		Period per = Period.getPeriod(pid);
		TH house = TH.getTH(hid);
		return "House name: " + house.name + " | " + "Reservation Date: " + per.start.toString() + "-"
				+ per.end.toString() + " |  Total Cost: $" + cost;
	}
}
