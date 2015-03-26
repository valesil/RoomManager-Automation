package tests.tablet.homeUI;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * This class retrieves the values form excel to run TCs of home suite.
 * @author Eliana
 *
 */
public class HomeTabletSettings {
	static SchedulePage schedulePage = new SchedulePage();
	static ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	static List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");

	static String password = "";
	static String organizer = "";
	static String subject = "";
	static String bodyMeeting = "";
	static String attendee = "";
	static String minStartTime = "";
	static String minEndTime = "";

	/**
	 * [EN] Create a meeting with excel values according the row number.
	 * 0 -> Current meeting values; start time: -5 min, end time: +10 min from current time. 
	 * 1 -> Next meeting values; start time: +20 min, end time: +35 min from current time. 
	 * 3 -> Past meeting values; start time: -35 min, end time: -20 min from current time. 
	 * @return Schedule Page
	 */
	public static SchedulePage createMeetingFromExcel(int rowNum) {	
		organizer = meetingData.get(rowNum).get("Organizer");
		subject = meetingData.get(rowNum).get("Subject");
		attendee = meetingData.get(rowNum).get("Attendee");
		password = meetingData.get(rowNum).get("Password");
		minStartTime = meetingData.get(rowNum).get("Start time (minutes to add)");
		minEndTime = meetingData.get(rowNum).get("End time (minutes to add)");
		
		return schedulePage.createMeeting(organizer, subject, minStartTime, 
				minEndTime, attendee, password);
	}

	/**
	 * [EN] Return the organizer concatenated with the password.
	 * @return String
	 */
	public static String getAuthenticationValue(int rowNum) {
		return meetingData.get(rowNum).get("Organizer")+ ":" + meetingData.get(rowNum).get("Password");
	}

	/**
	 * [EN] Return the password according row number.
	 * @return String
	 */
	public static String getPasswordValue(int rowNum) {
		return meetingData.get(rowNum).get("Password");
	}
	
	/**
	 * [EN] Return the subject of a meeting according row number.
	 * @return String
	 */
	public static String getMeetingSubject(int rowNum) {
		return meetingData.get(rowNum).get("Subject");
	}

	/**
	 * [EN] Return the organizer a meeting according row number.
	 * @return String
	 */
	public static String getMeetingOrganizer(int rowNum) {
		return meetingData.get(rowNum).get("Organizer");
	}

	/**
	 * [EN] Return room display name.
	 * @return String
	 */
	public static String getRoomDisplayName() {
		return meetingData.get(0).get("Room");
	}

	/**
	 * [EN] Return room code value.
	 * @return String
	 */
	public static String getRoomCodeValue() {
		return meetingData.get(0).get("Room Code");
	}

	/**
	 * [EN] Return available color configured to be displayed in {now} and {next} tiles.
	 * @return String
	 */
	public static String getAvailableColorValue() {
		return meetingData.get(0).get("Available color");
	}

	/**
	 * [EN] Return the busy color configured to be displayed in {now} tile.
	 * @return String
	 */
	public static String getNowBusyColorValue() {
		return meetingData.get(0).get("Busy now color");
	}

	/**
	 * [EN] Return the busy color configured to be displayed in {next} tile.
	 * @return String
	 */
	public static String getNextBusyColorValue() {
		return meetingData.get(0).get("Busy next color");
	}
}
