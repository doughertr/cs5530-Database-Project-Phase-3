package cs5530;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

public class Available
{
	int hid;
	int pid;
	public double pricePerNight;

	public Available(int hid, int pid, double pricePerNight)
	{
		this.hid = hid;
		this.pid = pid;
		this.pricePerNight = pricePerNight;
	}

	public static Available getAvailablity(int hid, int pid)
	{
		Available selectedAvailablity = null;
		String sql = "select * from Available where hid = \"" + hid + "\" and pid = \"" + pid + "\";";
		ResultSet rs = null;
		try
		{
			rs = Connector.stmt.executeQuery(sql);
			if (rs.next())
			{
				selectedAvailablity = new Available(rs.getInt("hid"), rs.getInt("pid"),
						rs.getDouble("price_per_night"));
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
		return selectedAvailablity;

	}

	public static Available getAvailabilityInRange(int hid, String start, String end)
	{
		Available availability = null;
		String sql = "SELECT * " + "FROM Available" + " WHERE hid = " + hid + " " + " AND pid = ANY (SELECT p.pid"
				+ "			 	   FROM Period p WHERE p.fromDate <= \'" + start + "\' "
				+ "				 	  AND p.toDate >= \'" + end + "\');";
		ResultSet rs = null;
		try
		{
			rs = Connector.stmt.executeQuery(sql);
			if (rs.next())
			{
				availability = new Available(rs.getInt("hid"), rs.getInt("pid"), rs.getDouble("price_per_night"));
			}
			rs.close();
		} catch (Exception e)
		{
			//System.out.println("cannot execute the query");
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
		return availability;

	}

	public static void addNewAvailability(int hid, String startAvailability, String endAvailability,
			double pricePerNight)
	{
		try
		{
			// adding new period matching to and from date
			Period p = Period.addNewPeriod(startAvailability, endAvailability);

			// getting id of newly created period
			/*
			 * ResultSet rs =
			 * Connector.stmt.executeQuery("SELECT LAST_INSERT_ID();");
			 * rs.next(); int pid = rs.getInt(1);
			 */

			String sql = "INSERT INTO Available(hid, pid, price_per_night) " + "VALUES('" + hid + "', '" + p.pid + "', '"
					+ pricePerNight + "');";
			// adding into database
			Connector.stmt.executeUpdate(sql);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void removeAvailability(int hid, int pid)
	{
		// remove the period
		Period.removePeriod(pid);

		String sql = "DELETE FROM Available WHERE pid = '" + pid + "' AND hid = '" + hid + "';";
		try
		{
			// remove the Availability
			Connector.stmt.executeUpdate(sql);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public double getPricePerNight()
	{
		return pricePerNight;
	}
}
