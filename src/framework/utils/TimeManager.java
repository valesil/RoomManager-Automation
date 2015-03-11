package framework.utils;

/**
 * 
 * @author Eliana Navia
 *
 */
public class TimeManager {

	public String getYear(String date) {
		System.out.println(date.split("/")[0]);
		return date.split("/")[0];
	}

	public String getMonth(String date) {
		System.out.println(date.split("/")[1]);
		return date.split("/")[1];
	}

	public String getDay(String date) {
		System.out.println(date.split("/")[2]);
		return date.split("/")[2];
	}
	
	public String getHour(String time) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMinute(String time) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMeridian(String time) {
		// TODO Auto-generated method stub
		return null;
	}
}