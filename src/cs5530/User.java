package cs5530;

import java.awt.List;
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.jsp.JspWriter;

public class User implements Serializable
{
	public String login;
	String firstName;
	String lastName;
	ArrayList<Stay> newStays;
	ArrayList<Reservation> newReservations;

	public User(String login, String firstName, String lastName)
	{
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		newStays = new ArrayList<Stay>();
		newReservations = new ArrayList<Reservation>();
	}

	public User()
	{
		this.login = "";
		this.firstName = "";
		this.lastName = "";
		this.newStays = new ArrayList<Stay>();
		this.newReservations = new ArrayList<Reservation>();
	}

	public static ArrayList<String> getTrustRecordings(String login)
	{
		String sql = "SELECT * FROM TRUSTS WHERE login1 = \"" + login + "\";";
		ArrayList<String> trusted = new ArrayList<String>();
		ResultSet rs = null;
		try
		{
			rs = Connector.stmt.executeQuery(sql);
			while (rs.next())
			{
				String trustRating = rs.getInt("is_trusted_by") == 1 ? "trusted" : "not trusted";
				String temp = rs.getString("login2") + " (" + trustRating + ")";
				trusted.add(temp);
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

		return trusted;
	}

	public static void addTrustForUser(String myLogin, String trustLogin, Boolean isTrusted)
	{
		int trust = isTrusted ? 1 : 0;
		String sql = "INSERT INTO Trusts(login1, login2, is_trusted_by) VALUES(\"" + myLogin + "\", \"" + trustLogin
				+ "\", \"" + trust + "\");";
		try
		{
			Connector.stmt.executeUpdate(sql);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static User validateUser(String login, String password)
	{
		User validatedUser = null;
		String sql = "select * from Users where login = \"" + login + "\" and password = \"" + password + "\"";
		ResultSet rs = null;
		try
		{
			rs = Connector.stmt.executeQuery(sql);
			if (rs.next())
			{
				validatedUser = new User();
				validatedUser.firstName = rs.getString("first_name");
				validatedUser.lastName = rs.getString("last_name");
				validatedUser.login = login;
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
		return validatedUser;
	}

	public static User createUser(String login, String pw, String fname, String lname, String addr, String phone)
	{
		User createdUser;

		String sql = "INSERT INTO Users(login, password, first_name, last_name, home_address, phone_number) "
				+ "VALUES(\"" + login + "\", \"" + pw + "\", \"" + fname + "\", \"" + lname + "\", \"" + addr + "\", \""
				+ phone + "\");";
		try
		{
			Connector.stmt.executeUpdate(sql);
			createdUser = new User(login, fname, lname);
		} catch (Exception e)
		{
			createdUser = null;
		}
		return createdUser;
	}

	public static ArrayList<TH> getFavorites(String login)
	{
		String sqlGetHIDs = "SELECT hid from Favorites WHERE login = \"" + login + "\";";
		ArrayList<TH> favorites = new ArrayList<TH>();
		ArrayList<Integer> hids = new ArrayList<Integer>();
		ResultSet rsHids = null;
		try
		{
			rsHids = Connector.stmt.executeQuery(sqlGetHIDs);
			while (rsHids.next())
			{
				hids.add(rsHids.getInt("hid"));
			}
			for (int hid : hids)
			{
				TH temp = TH.getTH(hid);
				favorites.add(temp);
			}
			rsHids.close();
		} catch (Exception e)
		{

			System.out.println(e.getMessage());
		} finally
		{
			try
			{
				if (rsHids != null && !rsHids.isClosed())
					rsHids.close();
			} catch (Exception e)
			{
				System.out.println("cannot close resultset");
			}
		}
		return favorites;
	}

	public static void addFavorite(String login, int hid, String date)
	{
		String sql = "INSERT INTO Favorites(login, hid, date_favorited) VALUES(\"" + login + "\", \"" + hid + "\", \""
				+ date + "\");";
		try
		{
			Connector.stmt.executeUpdate(sql);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public ArrayList<Reservation> getReservations()
	{
		return newReservations;
	}
	@Override
	public String toString()
	{
		return "";
	}
}
