package framework.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import framework.pages.admin.LoginPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SettingsPage;
import framework.selenium.SeleniumDriverManager;

/**
 * [AC]
 * @author administrator
 *
 */
public class MeetingMethods {
	WebDriver driver;
	HomeTabletPage home = new HomeTabletPage();
	

	/**
	 * [AC] This method initializes the listMaps to read from the excel
	 */
	public MeetingMethods() {
		driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
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
		SchedulePage schedule = new SchedulePage();
		home.clickScheduleBtn()
		.setOrganizerTxtBox(organizer)
		.setSubjectTxtBox(subject)
		.setStartTimeDate(startTime)
		.setEndTimeDate(endTime)
		.setAttendeeTxtBoxPressingEnter(attendee)
		.setBodyTxtBox(body)
		.clickCreateBtn()
		.confirmCredentials(password)
		.isMessageMeetingCreatedDisplayed();
		schedule.clickBackBtn();
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
	public SchedulePage createMeeting(String organizer, String subject, String starTimeMinutes,
			String endTimeMinutes, String attendees, String body, String password) {
		home.clickScheduleBtn()
		.createMeeting(organizer, subject, starTimeMinutes, endTimeMinutes, 
				attendees, password);	
	return new SchedulePage();
	}

	/**
	 * [AC] This class deletes a meeting
	 * @param nameMeeting
	 * @return SchedulePage
	 */
	public SchedulePage deleteMeeting(String nameMeeting, String password) {
		home.getHome().clickScheduleBtn()
		.deleteMeeting(nameMeeting, password);
		return new SchedulePage();
	}

	/**
	 * [YA] This method sets the url for tablet home and choose an specific room
	 * @param roomName
	 * @return
	 */
	public HomeTabletPage getHomeForSpecificRoom(String roomName) {
		SettingsPage settings = new SettingsPage();
		settings.selectRoom(roomName);
		return new HomeTabletPage();
	}
	
	/**
	 * [AC] This method creates an Out Of Order
	 * @param startDate
	 * @param endDate
	 * @param startTime
	 * @param endTime
	 * @param title
	 * @param description
	 * @param roomName
	 */
	public void createAnOutOfOrder(String startDate, String endDate, String startTime, 
			String endTime, String title, String description, String roomName) {
		LoginPage login = new LoginPage();
		login.clickSigninLink()
		.clickConferenceRoomsLink()
		.doubleClickOverRoomName(roomName)
		.clickOutOfOrderPlanningLink()
		.setOutOfOrderPeriodInformation(startDate, endDate, startTime, 
				endTime, title, description)
		.activateOutOfOrder()
		.clickSaveBtn();
	}
}
