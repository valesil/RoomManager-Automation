package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.common.MeetingMethods;
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * Title: Verify a user cannot create a meeting that has conflicts with the schedule of another meeting
 * @author Asael Calizaya
 *
 */
public class CannotCreateMeetingIfHasConfilctsWithAnotherMeeting {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");; 
	String password = meetingData.get(0).get("Password");
	String organizer = meetingData.get(1).get("Organizer");
	String subject = meetingData.get(1).get("Subject");
	String startTime = meetingData.get(1).get("Start time");
	String endTime = meetingData.get(1).get("End time");
	String attendee = meetingData.get(1).get("Attendee");
	String body = meetingData.get(1).get("Body");
	
	@BeforeClass
	public void preconditionToUpdateAMeeting() {
		MeetingMethods preCondition = new MeetingMethods();
		preCondition.createMeetingWithAllDataFromExcel(organizer, subject, startTime, endTime, attendee, body, password);
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();
	}

	@Test
	public void testAUserCannotCreateAMeetingIfHasConflictsWithAnotherMeeting() {
		String organizer = meetingData.get(2).get("Organizer");
		String subject = meetingData.get(2).get("Subject");
		String startTime = meetingData.get(2).get("Start time");
		String endTime = meetingData.get(2).get("End time");
		String attendee = meetingData.get(2).get("Attendee");
		String body = meetingData.get(2).get("Body");
		String password = meetingData.get(2).get("Password");
		HomePage home = new HomePage();
		boolean actual = home
				.clickScheduleBtn()
				.setOrganizerTxtBox(organizer)
				.setSubjectTxtBox(subject)
				.setStartTimeDate(startTime)
				.setEndTimeDate(endTime)
				.setAttendeeTxtBoxPressingEnter(attendee)
				.setBodyTxtBox(body)
				.clickCreateBtn()
				.setPasswordTxtBox(password)
				.clickOkButton()
				.isMessageOfErrorDisplayed();
		SchedulePage schedule = new SchedulePage();
		schedule.clickCancelButton();

		Assert.assertTrue(actual);
	}

	@AfterMethod
	public void goHome() {
		SchedulePage delete = new SchedulePage();
		delete.deleteMeeting(subject, password)
		.clickBackBtn();
	}
}
