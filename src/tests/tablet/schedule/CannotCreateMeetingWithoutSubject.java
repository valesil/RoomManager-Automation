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
 * Title: Verify a user cannot create a meeting if the subject is not specified
 * @author Asael Calizaya
 *
 */
public class CannotCreateMeetingWithoutSubject {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(0).get("Organizer");
	String startTime = meetingData.get(0).get("Start time");
	String endTime = meetingData.get(0).get("End time");
	String attendee = meetingData.get(0).get("Attendee");
	String body = meetingData.get(0).get("Body");
	
	@Test
	public void testAUserCannotCreateMeetingWithoutSubject() {
		HomePage home = new HomePage();
		boolean actual = home
				.clickScheduleBtn()
				.setOrganizerTxtBox(organizer)
				.setStartTimeDate(startTime)
				.setEndTimeDate(endTime)
				.setAttendeeTxtBoxPressingEnter(attendee)
				.setBodyTxtBox(body)
				.clickCreateBtn()
				.isErrorSubjectDisplayed();

		Assert.assertTrue(actual);
	}

	@AfterMethod
	public void goHome() {
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();
	}
}
