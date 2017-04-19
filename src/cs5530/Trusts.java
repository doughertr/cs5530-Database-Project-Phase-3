package cs5530;

import java.sql.ResultSet;

public class Trusts 
{
	String usr1;
	boolean trusts;
	String usr2;
	
	public Trusts(String usr1, boolean trusts, String usr2)
	{
		this.usr1 = usr1;
		this.trusts = trusts;
		this.usr2 = usr2;
	}
	public static boolean getTrust(String usr1, String usr2)
	{
		String sql = "SELECT t.is_trusted_by "
				   + "FROM Trusts t "
				   + "WHERE t.login1 = '" + usr1 + "' "
				   + "AND   t.login2 = '" + usr2 + "';";
		
		ResultSet rs = null;
		boolean trusted = false;
		
		try {
			rs = Connector.stmt.executeQuery(sql);
			trusted = rs.getBoolean("is_trusted_by");
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
		return trusted;
	}
}
