package cs5530;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;

public class ManagePropertyMenu
{

	// finish all TH editing
	// allow displays of user's reservations, stays
	// start search capability

	public static void displayMenu(User u) throws Exception
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{

			System.out.println("------Property Management------");
			System.out.println("1. Add a New TH");
			System.out.println("2. Update an existing TH");
			System.out.println("3. View all your listed THs");
			System.out.println("4. Update availability for a TH");
			System.out.println("5. Go back");
			System.out.println("Please enter your choice:");

			String choice;
			int c;
			while ((choice = in.readLine()) == null && choice.length() == 0);
			try
			{
				c = Integer.parseInt(choice);
			} catch (Exception e)
			{
				continue;
			}

			// invalid menu selection
			if (c < 1 | c > 5)
				continue;
			if (c == 1)
			{
				listNewTH(u, in);
			}
			if (c == 2)
			{
				updateTH(u, in);
			}
			if (c == 3)
			{
				showTHs(u, in);
			}
			if (c == 4)
			{
				updateAvailability(u, in);
			}
			if (c == 5)
			{
				return;
			}
		}
	}

	private static void updateAvailability(User u, BufferedReader in) throws Exception
	{
		ArrayList<TH> properties = showTHs(u, in);
		int index;
		String thChoice;
		System.out.println("Enter the Number of the TH to add availability to");
		while ((thChoice = in.readLine()) == null && thChoice.length() == 0);
		index = Integer.parseInt(thChoice) - 1;
		TH curr = properties.get(index);
		
		Date startDate;
		Date endDate;
		Double pricePerNight;
		String start;
		String end;
		String price;
		
		System.out.println("All Dates should be in the format YYYY-MM-DD");
		
		System.out.println("Enter Start Date of availability: ");
		while ((start = in.readLine()) == null && start.length() == 0);
		startDate = Date.valueOf(start);
		
		System.out.println("Enter End Date of availability: ");
		while ((end = in.readLine()) == null && end.length() == 0);
		endDate = Date.valueOf(end);
		
		if(!startDate.before(endDate))
		{
			System.out.println("Start date must be before end date");
			return;
		}
		
		System.out.println("Enter price per night (no leading $, only 2 decimal places max): ");
		while ((price = in.readLine()) == null && price.length() == 0);
		pricePerNight = Double.parseDouble(price);
		
		Available.addNewAvailability(curr.hid, startDate.toString(), endDate.toString(), pricePerNight);
	}

	private static void listNewTH(User u, BufferedReader in) throws Exception
	{
		String name;
		String address;
		String url;
		String category;
		String phoneNum;
		String yearBuilt;

		System.out.println("Please enter the following details for your new housing listing");

		System.out.println("Property Name:");
		while ((name = in.readLine()) == null && name.length() == 0)
			;
		if (name == "")
			name = null;

		System.out.println("Property Address:");
		while ((address = in.readLine()) == null && address.length() == 0)
			;
		if (address == "")
			address = null;

		System.out.println("URL for photos:");
		while ((url = in.readLine()) == null && url.length() == 0)
			;
		if (url == "")
			url = null;

		System.out.println("Housing Category:");
		while ((category = in.readLine()) == null && category.length() == 0)
			;
		if (category == "")
			category = null;

		System.out.println("Landlord phone number:");
		while ((phoneNum = in.readLine()) == null && phoneNum.length() == 0)
			;
		if (phoneNum == "")
			phoneNum = null;

		System.out.println("Year Built:");
		while ((yearBuilt = in.readLine()) == null && yearBuilt.length() == 0)
			;
		if (yearBuilt == "")
			yearBuilt = null;

		String keyword = null;
		ArrayList<String> keywords = new ArrayList<String>();

		System.out.println("Property Keywords:");

		while (!(keyword = in.readLine()).trim().equals(""))
		{
			keywords.add(keyword);
		}

		//TH.AddNewTH(name, address, url, category, phoneNum, yearBuilt, u.login, keywords);
	}

	private static void updateTH(User u, BufferedReader in) throws Exception
	{
		int propertyNum;
		ArrayList<TH> properties = showTHs(u, in);
		System.out.println("Enter the number for the property you would like to update: ");
		propertyNum = Integer.parseInt(in.readLine());

		TH property = properties.get(propertyNum - 1);

		String name;
		String address;
		String url;
		String category;
		String phoneNum;
		String yearBuilt = property.yearBuilt;

		System.out.println("Enter new details here, or hit enter to leave a section unchanged");

		System.out.println("Property Name:");
		if (!(name = in.readLine()).trim().equals(""))
		{
			property.name = name;
		}

		System.out.println("Property Address:");
		if (!(address = in.readLine()).trim().equals(""))
		{
			property.address = address;
		}

		System.out.println("URL for photos:");
		if (!(url = in.readLine()).trim().equals(""))
		{
			property.url = url;
		}

		System.out.println("Housing Category:");
		if (!(category = in.readLine()).trim().equals(""))
		{
			property.category = category;
		}

		System.out.println("Landlord phone number:");
		if (!(phoneNum = in.readLine()).trim().equals(""))
		{
			property.phoneNum = phoneNum;
		}

		System.out.println("Year Built:");
		if (!(yearBuilt = in.readLine()).trim().equals(""))
		{
			property.yearBuilt = yearBuilt;
		}

		//TH.updateTH(property);

		System.out.println("Would you like to edit keywords for this property? (y/n)");
		if (in.readLine().trim().toLowerCase().equals("y"))
		{
			editKeywordsForTH(property, in);
		}
		System.out.println("TH updated");

	}

	private static void editKeywordsForTH(TH property, BufferedReader in) throws IOException
	{
		System.out.println("The keywords for this property are: ");
		ArrayList<String> keywords = Keywords.getKeywordsForTH(property.hid);

		for (int i = 0; i < keywords.size(); i++)
		{
			String key = keywords.get(i);
			System.out.println((i + 1) + ". " + key);
		}
		System.out.println("Enter the indexes of keywords to remove, seperated by commas, or hit enter to skip: ");
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
					String key = keywords.get(index);
					Keywords.removeKeywordFromTH(property.hid, key);
				}
			}
		}

		// here is where we can add new keywords
		System.out.println("Enter any new keywords for this property:");
		String newKey;
		while (!(newKey = in.readLine()).trim().equals(""))
		{
			Keywords.AddNewKeyword(property.hid, newKey);
		}
	}

	private static ArrayList<TH> showTHs(User u, BufferedReader in) throws Exception
	{
		ArrayList<TH> properties = TH.listAllTHsForUser(u.login);

		for (int i = 0; i < properties.size(); i++)
		{
			TH currProp = properties.get(i);
			System.out.println((i + 1) + ". " + currProp.name);
		}
		return properties;
	}

}
