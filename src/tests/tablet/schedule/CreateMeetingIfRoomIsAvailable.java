package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * Title: verify a user can create meeting if room is Available 
 * @author Asael Calizaya
 *
 */
public class CreateMeetingIfRoomIsAvailable {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(0).get("Organizer");
	String subject = meetingData.get(0).get("Subject");
	String startTime = meetingData.get(0).get("Start time");
	String endTime = meetingData.get(0).get("End time");
	String attendee = meetingData.get(0).get("Attendee");
	String body = meetingData.get(0).get("Body");
	String password = meetingData.get(0).get("Password");
	
	@Test
	public void testAUserCanCreateMeetingIfRoomIsAvailable() {
		HomePage home = new HomePage();
		home.clickScheduleBtn();
		SchedulePage schedule = new SchedulePage();
		boolean actual = schedule.createMeeting(organizer, subject, startTime, endTime, attendee, body)
						.confirmCredentials(password)
						.isMessageMeetingCreatedDisplayed();

		Assert.assertTrue(actual);
	}

	@AfterMethod
	public void goHome() {
		SchedulePage delete = new SchedulePage();
		delete.deleteMeeting(subject, password)
		.clickBackBtn();
	}
}
