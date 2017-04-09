package cs5530;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

public class Period
{

	int pid;
	String start;
	String end;
	/*
	 * String to; String from;
	 */

	/*
	 * public Period(int pid, String to, String from) { this.pid = pid; this.to
	 * = to; this.from = from; }
	 */

	public Period(String start, String end)
	{
		this.start = start;
		this.end = end;
		pid = -1;
	}

	public Period(int pid, String start, String end)
	{
		this.start = start;
		this.end = end;
		this.pid = pid;
	}

	public Boolean isWithinPeriod(Date stayStart, Date stayEnd)
	{
		return !Date.valueOf(start).after(stayStart) && !Date.valueOf(end).before(stayEnd);
	}

	public static Period addNewPeriod(String start, String end)
	{
		String sql = "INSERT INTO Period(fromDate, toDate) " + "VALUES(\"" + start.toString() + "\", \""
				+ end.toString() + "\");";
		ResultSet rs = null;
		Period p = null;
		try
		{
			Connector.stmt.executeUpdate(sql);
			rs = Connector.stmt.executeQuery("SELECT LAST_INSERT_ID();");
			rs.next();
			int ID = rs.getInt(1);
			p = new Period(ID, start, end);
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
		return p;
	}

	public static void removePeriod(int pid)
	{
		String sql = "DELETE FROM Period WHERE pid = '" + pid + "';";
		try
		{
			Connector.stmt.executeUpdate(sql);
		} catch (Exception e)
		{
		}
	}

	public static Period getPeriod(int pid)
	{
		Period per = null;
		String sql = "select * from Period where pid = '" + pid + "';";
		ResultSet rs = null;
		try
		{
			rs = Connector.stmt.executeQuery(sql);
			if (rs.next())
			{
				String start = rs.getString("fromDate");
				String end = rs.getString("toDate");
				per = new Period(pid, start, end);
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
		return per;
	}

}
