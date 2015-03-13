package framework.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Eliana Navia
 *
 */
public class TimeManager {

	static Calendar calendar = new GregorianCalendar();

	/**
	 * [YA]This method gets a new time based on the current time and minutes to add or subtract
	 * @param minutes: minutes to add or subtract
	 * @return new time
	 */
	private static Date getNewTime(int minutes) {
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, minutes); 
		return calendar.getTime();
	}

	/**
	 * [YA]This method changes a date to String. It could be also used for time
	 * @param date: base date to change format
	 * @param formatter: format for date (e.g. "hh:mm a")
	 * @return new date
	 */
	private static String dateToString(Date date, String formatter) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
		return simpleDateFormat.format(date);
	}

	/**
	 * [YA]This method returns a new time as a String based on current time and minutes to add/subtract
	 * It calls two methods: dateToString() and getNewTime
	 * @param value: minutes to add or subtract
	 * @return date as a String
	 */
	public static String getTime(int value) {
		return dateToString(getNewTime(value), "hh:mm a");
	}

	/**
	 * [YA]This element returns a time element: hours, minutes or meridian
	 * @param time: time to split
	 * @param element: hours, minutes or meridian
	 * @return time element
	 */
	public String getTimeElement(String time, String element) {
		String[] splittedTime = time.split(" ");
		String[] timeElement = splittedTime[0].split(":");
		switch(element) {	
		case "hours": return Integer.parseInt(timeElement[0]) + "";
		case "minutes": return timeElement[1];
		default : return splittedTime[1];
		}
	}
	
	/**
	 * [YA]This method returns the following methods of a date: year, month or day
	 * @param date: Date to split
	 * @param Element: year, month or day
	 * @return date element
	 */
	public String getDateElement(String date, String element) {
		switch(element) {
		case "year": return date.split("/")[0];
		case "month": return date.split("/")[1];
		default : return date.split("/")[2];
		}
	}


}