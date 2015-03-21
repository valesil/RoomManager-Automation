package framework.common;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.utils.TimeManager.getTime;

import java.util.List;
import java.util.Map;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.utils.TimeManager;
import framework.utils.readers.ExcelReader;

/**
 * Description: This class is to create meetings as a precondition by UI
 * @author Asael Calizaya
 *
 */
public class PreConditions {
	private ExcelReader excelReader;
	List<Map<String, String>> meetingData; 
	SchedulePage schedulePage;
	HomePage home;

	/**
	 * [AC] This method initializes the listMaps to read from the excel
	 */
	public PreConditions() {
		schedulePage = new SchedulePage();
		home = new HomePage();
		try {
			excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		} catch (Exception e) {
			System.out.println("Error on Preconditions: " + e.getMessage());
			e.printStackTrace();
		}
		meetingData = excelReader.getMapValues("MeetingData");
	}	
	
	/**
	 * [AC] This method creates a meeting
	 * @param organizer
	 * @param subject
	 * @param startTime
	 * @param endTime
	 * @param attendee
	 * @param body
	 * @param password
	 */
	public void createMeetingWithAllDataFromExcel(String organizer, String subject, String startTime, String endTime,
			String attendee, String body, String password) {
		HomePage home = new HomePage();
		home.clickSchedulePageBtn()
			.setOrganizerTxtBox(organizer)
			.setSubjectTxtBox(subject)
			.setStartTimeDate(startTime)
			.setEndTimeDate(endTime)
			.setAttendeeTxtBox(attendee)
			.setBodyTxtBox(body)
			.clickCreateBtn()
			.confirmCredentials(password)
			.isMessageMeetingCreatedDisplayed();
	}

	/**
	 * [AC] This method creates more than one meetings, depends of the data in the excel file 
	 * @param amountOfMeetings
	 * @return: All name of meetings created on this method
	 */
	public String[] createMeetingsSuccessfully(int amountOfMeetings) {
		String[] subject = new String[amountOfMeetings];
		home.clickSchedulePageBtn();
		for (int i = 0; i < amountOfMeetings; i++) {
			String organizer = meetingData.get(i).get("Organizer");
			subject[i] = meetingData.get(i).get("Subject");
			String startTime = meetingData.get(i).get("Start time");
			String endTime = meetingData.get(i).get("End time");
			String attendee = meetingData.get(i).get("Attendee");
			String body = meetingData.get(i).get("Body");
			String password = meetingData.get(i).get("Password");
			schedulePage
			.createMeeting(organizer, subject[i], startTime, endTime, attendee, body)		
			.confirmCredentials(password)
			.isMessageMeetingCreatedDisplayed();
		}
		schedulePage.clickBackBtn();
		return subject;
	}

	/**
	 * [EN] Create a meeting with excel values and current time calculated.
	 * start time: - 5 minutes of current time hh:mm a
	 * end time: + 10 minutes of current time hh:mm a
	 * @return subject of the meeting
	 */
	public String createCurrentMeeting() {
		String password = meetingData.get(0).get("Password");
		String organizer = meetingData.get(0).get("Organizer");
		String subject = meetingData.get(0).get("Subject");
		String startTime = getTime(-5, "hh:mm a");
		String endTime = getTime(10, "hh:mm a");
		String attendees = meetingData.get(0).get("Attendee");
		String body = meetingData.get(0).get("Body");

		schedulePage
		.createMeeting(organizer, subject, startTime,endTime, attendees, body)
		.confirmCredentials(password)
		.isMessageMeetingCreatedDisplayed();

		return subject;
	}

	/**
	 * [EN] Create a meeting with excel values and current time calculated.
	 * start time: + 20 minutes of current time
	 * end time: + 35 minutes of current time
	 * @return
	 */
	public String createNextMeeting() {
		String password = meetingData.get(0).get("Password");
		String organizer = meetingData.get(1).get("Organizer");
		String subject = meetingData.get(1).get("Subject");
		String startTime = getTime(20, "hh:mm a");
		String endTime = getTime(35, "hh:mm a");
		String attendees = meetingData.get(1).get("Attendee");
		String boddy = meetingData.get(1).get("Body");

		schedulePage
		.createMeeting(organizer, subject, startTime, endTime, attendees, boddy)
		.confirmCredentials(password)
		.isMessageMeetingCreatedDisplayed();

		return subject;
	}

	/**
	 * [EN] Get the display name of the room.
	 * @return room display name that is stored in the excel
	 */
	public String getRoomDisplayName() {
		String roomDisplayName = meetingData.get(0).get("Room");
		return roomDisplayName;
	}
	
	/**
	 * [YA]This method creates a meeting 
	 * @param organizer: Organizer name
	 * @param subject: Meeting's subject
	 * @param starTimeMinutes: Minutes to add or subtract to current time to get startTime
	 * @param endTimeMinutes: Minutes to add or subtract to current time to get endTime
	 * @param attendee: Attendees for the meeting
	 * @param body: Meeting's body message
	 * @param password: Organizer's password
	 * @throws InterruptedException 
	 */
	public void createMeeting(String organizer, String subject, String starTimeMinutes,
			String endTimeMinutes, String attendee, String body, String password) {
			
		String startTime = TimeManager.getTime(Integer.parseInt(starTimeMinutes), "hh:mm a");
		String endTime = TimeManager.getTime(Integer.parseInt(endTimeMinutes), "hh:mm a");
			home.clickSchedulePageBtn()
			.createMeeting(organizer, subject, startTime, endTime, attendee, body)		
			.confirmCredentials(password).isMessageMeetingCreatedDisplayed();
	}
}
