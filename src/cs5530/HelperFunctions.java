package cs5530;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class HelperFunctions 
{
	public static String addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(date);
        cal.add(Calendar.DATE, days); 
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String returnMe = myFormat.format(cal.getTime()).toString();
        return returnMe;
    }
	
	public static int differenceBetweenDays(String start, String end)
	{
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		int difference = -1;
		try {
		    java.util.Date date1 = myFormat.parse(start);
		    java.util.Date date2 = myFormat.parse(end);
		    long diff = date2.getTime() - date1.getTime();
		    difference = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return difference;
	}
	public static void trimArray(String[] arr)
	{
		for(int i = 0; i < arr.length; i++)
			arr[i] = arr[i].trim();
	}
}
