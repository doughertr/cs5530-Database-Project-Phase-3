package cs5530;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Feedback
{
	int fid;
	int feedbackScore;
	String message;
	String dateProvided;
	int hid;
	String login;

	public Feedback(int fid, int feedbackScore, String message, String dateProvided, int hid, String login)
	{
		super();
		this.fid = fid;
		this.feedbackScore = feedbackScore;
		this.message = message;
		this.dateProvided = dateProvided;
		this.hid = hid;
		this.login = login;
	}
	
	@Override
	public String toString()
	{
		return "Review From: " + login + " Rating: " + feedbackScore + "\n" + message;
	}

	public static void leaveFeedback(Feedback f)
	{
		String sql = "INSERT INTO Feedback (feedback_score, message, date_provided, hid, login) VALUES(\""
				+ f.feedbackScore + "\", \"" + f.message + "\", \"" + f.dateProvided + "\", \"" + f.hid + "\", \""
				+ f.login + "\");";
		try
		{
			Connector.stmt.executeUpdate(sql);

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void rateUsefulnessOfFeedback(int rating, int fid, String login)
	{
		String sql = "INSERT INTO Feedback_Ratings (login, fid, rating) VALUES(\"" + login + "\", \"" + fid + "\", \""
				+ rating + "\");";
		try
		{
			Connector.stmt.executeUpdate(sql);

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static Boolean canUserLeaveFeedback(int hid, String login)
	{
		// Need to check if feedback exists that if for this HID with this user
		// login
		String sql = "SELECT * From Feedback WHERE login = \"" + login + "\" AND hid = \"" + hid + "\";";
		ResultSet rs = null;
		try
		{
			rs = Connector.stmt.executeQuery(sql);
			return !rs.next();

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static ArrayList<Feedback> getAllFeedbackForTH(int hid)
	{
		ArrayList<Feedback> reviews = new ArrayList<Feedback>();
		String sql = "select * from Feedback where hid = \"" + hid + "\"";
		ResultSet rs = null;
		try
		{
			rs = Connector.stmt.executeQuery(sql);
			while (rs.next())
			{
				Feedback f = new Feedback(rs.getInt("fid"), rs.getInt("feedback_score"), rs.getString("message"),
						rs.getString("date_provided"), rs.getInt("hid"), rs.getString("login"));
				reviews.add(f);
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
		return reviews;
	}
}
