package cs5530;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SingleTHMenu
{
	public static void displayMenu(User u, TH th) throws Exception
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{
			System.out.println("------TH Options------");
			System.out.println("1. Show TH details");
			System.out.println("2. Book a visit");
			System.out.println("3. See all feedback for this TH");
			System.out.println("4. See top m most useful feedbacks");
			System.out.println("5. Leave feedback");
			System.out.println("6. Add as a favorite");
			System.out.println("7. Go back");
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
			if (c < 1 | c > 7)
				continue;
			if (c == 1)
			{
				System.out.println(th.toString());
			}
			if (c == 2)
			{
				makeReservation(th, u, in);
			}
			if (c == 3)
			{
				displayAndRateFeedback(th, u, in);
			}
			if (c == 4)
			{
			}
			if (c == 5)
			{
				if (Feedback.canUserLeaveFeedback(th.hid, u.login))
				{
					leaveFeedback(u, th, in);
				} else
				{
					System.out.println("You have already left feedback for this TH");
				}

			}
			if (c == 6)
			{
				User.addFavorite(u.login, th.hid, new Date(System.currentTimeMillis()).toString());
				System.out.println(th.name + " added to favorites!");
			}
			if (c == 7)
			{
				return;
			}
		}
	}

	private static void makeReservation(TH th, User u, BufferedReader in) throws Exception
	{
			Date stayStart;
			Date stayEnd;
			String start;
			String end;

			System.out.println("All Dates should be in the format YYYY-MM-DD");

			System.out.println("Enter Start Date of your stay: ");
			while ((start = in.readLine()) == null && start.length() == 0);
			stayStart = Date.valueOf(start);

			System.out.println("Enter End Date of your stay: ");
			while ((end = in.readLine()) == null && end.length() == 0);
			stayEnd = Date.valueOf(end);

			if (stayStart.after(stayEnd))
			{
				System.out.println("Start date must be before end date");
				return;
			}
			Period p = Period.addNewPeriod(start, end);
			Available rentalPeriod = Available.getAvailabilityInRange(th.hid, stayStart.toString(), stayEnd.toString());
			if (rentalPeriod == null)
			{
				System.out.println("This TH is not available during your stay");
			}
			else
			{
				int daysInStay = HelperFunctions.differenceBetweenDays(start, end);
				double cost = daysInStay * rentalPeriod.pricePerNight;
				Reservation r = new Reservation(u.login, th.hid, p, cost);
				u.newReservations.add(r);
			}
	}

	private static void leaveFeedback(User u, TH th, BufferedReader in) throws IOException
	{
		// !(keyword = in.readLine()).trim().equals("")
		System.out.println("Enter the following Feedback Details:");
		String scoreString;
		String message;

		System.out.println("Score (0 = terrible, 10 = excellent):");
		while ((scoreString = in.readLine()) == null && scoreString.length() == 0)
			;
		int score = Integer.parseInt(scoreString);

		System.out.println("Message:");
		while ((message = in.readLine()) == null)
			;

		String currDate = new Date(System.currentTimeMillis()).toString();

		Feedback temp = new Feedback(-1, score, message, currDate, th.hid, u.login);
		Feedback.leaveFeedback(temp);

	}

	private static void displayAndRateFeedback(TH th, User u, BufferedReader in)
			throws NumberFormatException, IOException
	{
		// get all feedback for TH
		ArrayList<Feedback> reviews = Feedback.getAllFeedbackForTH(th.hid);
		for (int i = 0; i < reviews.size(); i++)
		{
			System.out.println((i + 1) + ". " + reviews.get(i).toString());
		}
		System.out.println("To rate the usefullness of feedback enter the feedback number below.");
		System.out.println("Otherwise press enter to return to TH menu.");
		String giveFeedbackReview;
		if (!(giveFeedbackReview = in.readLine()).trim().equals(""))
		{
			int feedbackChoice = Integer.parseInt(giveFeedbackReview) - 1;
			Feedback fb = reviews.get(feedbackChoice);
			if (fb.login != u.login)
			{
				int rating;
				String ratingEntry;
				System.out.println("Enter a rating for usefulness (0 = useless, 1 = useful, 2 = very useful");
				while ((ratingEntry = in.readLine()) == null && ratingEntry.length() == 0)
					;
				rating = Integer.parseInt(ratingEntry);
				Feedback.rateUsefulnessOfFeedback(rating, fb.fid, u.login);
			} else
			{
				System.out.println("You cannot rate your own feedback");
			}
		}
	}
}
