package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.common.MeetingMethods;
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * Title: Verify a meeting cannot be created when an Out Of Order is ative
 * @author Asael Calizaya
 *
 */
public class CannotCreateAMeetingWhenOutOfOrderIsActive {
	HomePage home = new HomePage();
	SchedulePage schedule = new SchedulePage();
	MeetingMethods meeting = new MeetingMethods();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	List<Map<String, String>> outOfOrderData = excelReader.getMapValues("OutOfOrderPlanning");

	//data from excel to create an out of order
	String roomName = outOfOrderData.get(11).get("Room Name");
	String startDate = outOfOrderData.get(11).get("Start date");
	String endDate = outOfOrderData.get(11).get("End date");
	String startTime = outOfOrderData.get(11).get("Start time (minutes to add)");
	String endTime = outOfOrderData.get(11).get("End time (minutes to add)");
	String title = outOfOrderData.get(11).get("Title");
	String description = outOfOrderData.get(11).get("Description");

	//data from excel to create a meeting
	String meetingOrganizer = meetingData.get(10).get("Organizer");
	String meetingSubject = meetingData.get(10).get("Subject");
	String meetingStartTime = meetingData.get(10).get("Start time");
	String meetingEndTime = meetingData.get(10).get("End time");
	String meetingAttendee = meetingData.get(10).get("Attendee");
	String meetingBody = meetingData.get(10).get("Body");
	String password = meetingData.get(10).get("Password");

	@BeforeMethod
	public void createOutOfOrder() {
		meeting.createAnOutOfOrder(startDate, endDate, meetingStartTime, endTime, title, description, roomName);
	}

	@Test
	public void testCannotCreateAMeetingWhenOutOfOrderIsActive() {
		home.getHome().clickScheduleBtn();
		meeting.createMeeting(meetingOrganizer, meetingSubject, meetingStartTime, meetingEndTime, meetingAttendee, meetingBody, password);
		boolean actual = schedule.confirmCredentials(password)
		.isMessageOfErrorDisplayed();

		Assert.assertTrue(actual);
	}

	@AfterMethod
	public void deleteData() {
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
