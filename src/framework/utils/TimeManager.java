package framework.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This class manage Time elements: hours, minutes, meridian 
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
	public static String getTime12h(int value) {
		return dateToString(getNewTime(value), "hh:mm a");
	}

	/**
	 * [EN]This method returns a new time as a String based on current time 
	 * and minutes to add/subtract
	 * It calls two methods: dateToString() and getNewTime
	 * @param value: minutes to add or subtract
	 * @return date as a String in format HH:mm
	 */
	public static String getTime24h(int value) {
		return dateToString(getNewTime(value), "HH:mm");
	}

	/**
	 * [EN] This method return the current time.
	 * @return current time format HH:mm
	 */
	public static String getCurrentTime() {
		calendar.setTime(new Date());
		return dateToString(calendar.getTime(), "HH:mm");
	}

	/**
	 * [YA]This element returns a time element: hours, minutes or meridian
	 * @param time: time to split
	 * @param element: hours, minutes or meridian
	 * @return time element
	 */
	public static String getTimeElement(String time, String element) {
		String[] splittedTime = time.split(" ");
		String[] timeElement = splittedTime[0].split(":");
		String[] meridian = splittedTime[1].split(" ");
		switch(element) {	
		case "hours": return Integer.parseInt(timeElement[0]) + "";
		case "minutes": return timeElement[1];
		case "meridian": return meridian[0];
		default : return splittedTime[0];
		}
	}

	/**
	 * [EN]This methods split a string in hours and minutes.
	 * @param strDate HH:mm
	 * @param parameter hour,minute
	 * @return integer of hours and minutes
	 */
	public static int getTimeElementInt(String strDate, String parameter) {
		String[] dataArray = strDate.split(":");
		int value = (parameter.equalsIgnoreCase("hour")) ? 
				Integer.parseInt(dataArray[0]): 
					Integer.parseInt(dataArray[1]);
				return value;
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