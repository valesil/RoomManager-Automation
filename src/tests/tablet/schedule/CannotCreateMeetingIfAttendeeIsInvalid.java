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
 * Title: Verify that Attendees text box doesn't allow a different format than email
 * @author Asael Calizaya
 *
 */
public class CannotCreateMeetingIfAttendeeIsInvalid {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(8).get("Organizer");
	String subject = meetingData.get(8).get("Subject");
	String startTime = meetingData.get(8).get("Start time");
	String endTime = meetingData.get(8).get("End time");
	String attendee = meetingData.get(8).get("Attendee");
	String body = meetingData.get(8).get("Body");
	String password = meetingData.get(8).get("Password");
	
	@Test
	public void testAUserCannotCreateMeetingIfAttendeeIsInvalid() {
		HomePage home = new HomePage();
		home.clickScheduleBtn();
		SchedulePage schedule = new SchedulePage();
		boolean actual = schedule.createMeeting(organizer, subject, startTime, endTime, attendee, body)
						.isErrorAttendeeInvalidLblDisplayed();

		Assert.assertTrue(actual);
	}

	@AfterMethod
	public void goHome() {
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();
	}
}
