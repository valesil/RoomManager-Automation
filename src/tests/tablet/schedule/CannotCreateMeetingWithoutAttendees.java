package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * Title: Verify a user cannot create a meeting if the attendees are not specified
 * @author Asael Calizaya
 *
 */
public class CannotCreateMeetingWithoutAttendees {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData  = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(0).get("Organizer");
	String subject = meetingData.get(0).get("Subject");
	String startTime = meetingData.get(0).get("Start time");
	String endTime = meetingData.get(0).get("End time");
	String body = meetingData.get(0).get("Body");
	
	@Test
	public void testAUserCannotCreateMeetingWithoutAttendees() {
		HomePage home = new HomePage();
		boolean actual = home
				.clickScheduleBtn()
				.setOrganizerTxtBox(organizer)
				.setSubjectTxtBox(subject)
				.setStartTimeDate(startTime)
				.setEndTimeDate(endTime)
				.setBodyTxtBox(body)
				.clickCreateBtn()
				.clickCancelButton()
				.isErrorAttendeeLblDisplayed();

		Assert.assertTrue(actual);
	}

	@AfterMethod
	public void goHome() {
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();
	}
}
