package cs5530;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ShoppingCartMenu 
{
	public static void displayMenu(User u) throws Exception
	{	
		ArrayList<Reservation> reservationList = u.getReservations();
		ArrayList<Stay> stayList = u.newStays;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			
			System.out.println("------Shopping Cart------");
			if (reservationList.size() > 0 || stayList.size() > 0) {
				System.out.println("Reservations:");
				for (int i = 0; i < reservationList.size(); i++) {
					System.out.println("Reservation #" + (i + 1) + ": " + reservationList.get(i).toString());
				} 
				System.out.println("Stays:");
				for (int i = 0; i < stayList.size(); i++) {
					System.out.println("Stay #" + (i + 1) + ": " + stayList.get(i).toString());
				} 
			}
			else
			{
				System.out.println("EMPTY");
			}
			System.out.println("------End of Shopping Cart------");
			System.out.println("1. Remove a Reservation");
			System.out.println("2. Remove a Stay");
			System.out.println("3. Confirm");
			System.out.println("4. Go back");
			System.out.println("Please enter your choice:");
			String choice;
			int c;
			while ((choice = in.readLine()) == null && choice.length() == 0);
			try 
			{
				c = Integer.parseInt(choice);
			} 
			catch (Exception e) 
			{
				continue;
			}

			// invalid menu selection
			if (c < 1 || c > 4)
				continue;
			if(c == 1)
			{
				System.out.println("Type the reservation numbers you wish to remove, seperated by commas:");
				String input;
				if (!(input = in.readLine()).trim().equals(""))
				{
					String[] removalIndexes = input.trim().split(",");
					if (removalIndexes.length > 0)
					{
						// now work on removing keywords
						for (String indexStr : removalIndexes)
						{
							int index = Integer.parseInt(indexStr) - 1;
							reservationList.remove(index);
						}
					}
				}
				
			}
			if(c == 2)
			{
				System.out.println("Type the stay numbers you wish to remove, seperated by commas:");
				String input;
				if (!(input = in.readLine()).trim().equals(""))
				{
					String[] removalIndexes = input.trim().split(",");
					if (removalIndexes.length > 0)
					{
						// now work on removing keywords
						for (String indexStr : removalIndexes)
						{
							int index = Integer.parseInt(indexStr) - 1;
							stayList.remove(index);
						}
					}
				}
				
			}
			if(c == 3)
			{
				for (int i = 0; i < reservationList.size(); i++) 
				{
					Reservation.makeReservation(reservationList.get(i));
					System.out.println("Reservation #" + (i+1) + " created sucessfully");
				}
				reservationList.clear();
				for (int i = 0; i < stayList.size(); i++) 
				{
					Stay.RecordNewStay(stayList.get(i));
					System.out.println("Stay #" + (i+1) + " created sucessfully");
				}
				stayList.clear();
				continue;
			}
			if(c == 4)
			{
				return;
			}
		}
	}

}
