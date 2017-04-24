package cs5530;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import com.mysql.jdbc.Statement;

public class Reservation implements Serializable
{

	public String login;
	public int hid;
	public int pid;
	public double cost;

	public Period reservationPeriod;

	// NOTES
	// TH suggestions must be displayed after a new reservation has been entered
	// on creating a reservation, it should edit the days
	public Reservation(String login, int hid, Period p, double cost)
	{
		this.login = login;
		this.hid = hid;
		this.pid = p.pid;
		this.cost = cost;
		reservationPeriod = p;
	}

	public Reservation(String login, int hid, int pid, double cost)
	{
		this.login = login;
		this.hid = hid;
		this.pid = pid;
		this.cost = cost;
		reservationPeriod = null;
	}
	
	public void getPeriodFromPID()
	{
		if(reservationPeriod == null)
		{
			reservationPeriod = Period.getPeriod(pid);
		}
	}

	public static ArrayList<Reservation> getAllReservationsForUser(String login)
	{
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();

		String sql = "SELECT * FROM Reserve " + "WHERE login = '" + login + "';";
		ResultSet rs = null;
		try
		{
			rs = Connector.stmt.executeQuery(sql);
			while (rs.next())
			{
				Reservation res = new Reservation(login, rs.getInt("hid"), rs.getInt("pid"), rs.getDouble("cost"));
				reservations.add(res);
			}
			rs.close();
			for(Reservation r : reservations)
			{
				r.getPeriodFromPID();
			}
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
		return reservations;
	}

	public static Reservation getReservation(String login, int hid, int pid)
	{
		Reservation res = null;
		String sql = "SELECT * " + "FROM Reserve r " + "WHERE r.pid = '" + pid + "' " + "AND r.hid = '" + hid + "' "
				+ "AND r.login = '" + login + "';";
		ResultSet rs = null;
		try
		{
			rs = Connector.stmt.executeQuery(sql);
			res = new Reservation(login, hid, pid, rs.getDouble("cost"));

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
		return res;
	}

	public static void addNewReservation(String login, int hid, int pid, double cost)
	{
		String sql = "INSERT INTO Reserve(login, hid, pid, cost) " + "VALUES(\"" + login + "\", \"" + hid + "\", \""
				+ pid + "\", \"" + cost + "\");";
		try
		{
			ResultSet rs = null;
			Connector.stmt.executeUpdate(sql);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	// wrapper around other function
	public static void makeReservation(Reservation r) throws Exception
	{
		makeReservation(r.login, r.hid, r.reservationPeriod.start.toString(), r.reservationPeriod.end.toString(),
				r.cost);
	}

	public static void makeReservation(String login, int hid, String start, String end, double cost) throws Exception
	{
		// get the available Period that intersects with the reservation date
		Available houseAvailability = Available.getAvailabilityInRange(hid, start, end);

		if (houseAvailability == null)
			throw new Exception("Error: Invalid reservation date. Must be during a period of TH availability");

		// Get the intersecting period
		Period availablePeriod = Period.getPeriod(houseAvailability.pid);

		// HANDLING AVAILABILITY CHANGES

		// remove the available period because it will be replaced by a new
		// available date
		Available.removeAvailability(hid, availablePeriod.pid);

		// to and from dates of available period
		Date startAvailability = Date.valueOf(availablePeriod.start);
		Date endAvailability = Date.valueOf(availablePeriod.end);

		// to and from dates of reservation
		Date reserveTo = Date.valueOf(start);
		Date reserveFrom = Date.valueOf(end);

		// if the available start date is less than or equal to the reservation
		// date,
		// don't create an availability
		if (startAvailability.before(reserveTo))
		{
			Available.addNewAvailability(hid, startAvailability.toString(), HelperFunctions.addDays(reserveTo, -1),
					cost);
		}
		// if the reservation end date is less than or equal to the available
		// date
		// don't create an availability
		if (reserveFrom.before(endAvailability))
		{
			Available.addNewAvailability(hid, HelperFunctions.addDays(reserveFrom, 1).toString(),
					endAvailability.toString(), cost);
		}

		int pid = availablePeriod.pid;
		// After everything's done, add the reservation to the database
		if (!startAvailability.before(reserveTo) && !reserveFrom.before(endAvailability))
		{
			Period reservationPeriod = Period.addNewPeriod(start, end);
			pid = reservationPeriod.pid;
		}
		Reservation.addNewReservation(login, hid, pid, cost);
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
