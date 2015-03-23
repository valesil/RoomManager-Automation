package framework.common;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SettingsPage;
import framework.selenium.SeleniumDriverManager;
import framework.utils.TimeManager;
import framework.utils.readers.ExcelReader;

public class MeetingMethods {
	ExcelReader excelReader;
	List<Map<String, String>> meetingData; 
	SchedulePage schedule;
	WebDriver driver;
	HomePage home;
	SettingsPage settings;

	/**
	 * [AC] This method initializes the listMaps to read from the excel
	 */
	public MeetingMethods() {
		driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
		home = new HomePage();	
		schedule = new SchedulePage();
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
		home.clickScheduleBtn()
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
		home.clickScheduleBtn();
		for (int i = 0; i < amountOfMeetings; i++) {
			String organizer = meetingData.get(i).get("Organizer");
			subject[i] = meetingData.get(i).get("Subject");
			String startTime = meetingData.get(i).get("Start time");
			String endTime = meetingData.get(i).get("End time");
			String attendee = meetingData.get(i).get("Attendee");
			String body = meetingData.get(i).get("Body");
			String password = meetingData.get(i).get("Password");
			schedule
			.createMeeting(organizer, subject[i], startTime, endTime, attendee, body)		
			.confirmCredentials(password)
			.isMessageMeetingCreatedDisplayed();
		}
		schedule.clickBackBtn();
		return subject;
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
	 */
	public void createMeeting(String organizer, String subject, String starTimeMinutes,
			String endTimeMinutes, String attendee, String body, String password) {

		String startTime = TimeManager.getTime(Integer.parseInt(starTimeMinutes), "hh:mm a");
		String endTime = TimeManager.getTime(Integer.parseInt(endTimeMinutes), "hh:mm a");
		home.clickScheduleBtn()
		.createMeeting(organizer, subject, startTime, endTime, attendee, body)		
		.confirmCredentials(password).isMessageMeetingCreatedDisplayed();
	}

	/**
	 * [AC] This class delete a meeting
	 * @param nameMeeting
	 * @return SchedulePage
	 */
	public SchedulePage deleteMeeting(String nameMeeting, String password) {
		home.clickScheduleBtn()
		.deleteMeeting(nameMeeting, password);
		return new SchedulePage();
	}

	public void goHome() {
		schedule.clickBackBtn();
	}

	/**
	 * [YA] This method sets the url for tablet home and choose an specific room
	 * @param roomName
	 * @return
	 */
	public HomePage getHomeForSpecificRoom(String roomName) {
		home.getHome();
		if(home.isTimelineContainerPresent()) {
			home.clickSettingsBtn();
		}
		settings.selectRoom(roomName);
		return new HomePage();
	}
}
