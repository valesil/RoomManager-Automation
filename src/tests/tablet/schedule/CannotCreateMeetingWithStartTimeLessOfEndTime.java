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
 * Title: Verify when put a startTime less to endTime a error message is displayed
 * @author Asael Calizaya
 *
 */
public class CannotCreateMeetingWithStartTimeLessOfEndTime {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(7).get("Organizer");
	String subject = meetingData.get(7).get("Subject");
	String startTime = meetingData.get(7).get("Start time");
	String endTime = meetingData.get(7).get("End time");
	String attendee = meetingData.get(7).get("Attendee");
	String body = meetingData.get(7).get("Body");
	String password = meetingData.get(7).get("Password");
	
	@Test
	public void testAUserCannotCreateMeetingWithStartTimeLessOfEndTime() {
		HomePage home = new HomePage();
		home.clickScheduleBtn();
		SchedulePage schedule = new SchedulePage();
		boolean actual = schedule.createMeeting(organizer, subject, startTime, endTime, attendee, body)
						.isMessageOfTimeErrorDisplayed();

		Assert.assertTrue(actual);
	}

	@AfterMethod
	public void goHome() {
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();
	}
}
