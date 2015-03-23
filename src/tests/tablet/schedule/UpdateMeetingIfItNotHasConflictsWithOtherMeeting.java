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
 * Title: Verify an existing meeting can be updated if it doesn't have a conflict with another meeting
 * @author Asael Calizaya
 *
 */
public class UpdateMeetingIfItNotHasConflictsWithOtherMeeting {	
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String password;
	String preSubject;
	String newSubject;

	@BeforeClass
	public void preconditionToUpdateAMeeting() {
		password = meetingData.get(0).get("Password");
		String organizer = meetingData.get(1).get("Organizer");
		preSubject = meetingData.get(1).get("Subject");
		String startTime = meetingData.get(1).get("Start time");
		String endTime = meetingData.get(1).get("End time");
		String attendee = meetingData.get(1).get("Attendee");
		String body = meetingData.get(1).get("Body");
		MeetingMethods preCondition = new MeetingMethods();
		preCondition.createMeetingWithAllDataFromExcel(organizer, preSubject, startTime, endTime, attendee, body, password);
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();
	}

	@Test
	public void testAUserCanUpdateAMeetingIfItNotHasConflictsWithOtherMeeting() {
		newSubject = meetingData.get(4).get("Subject");
		String startTime = meetingData.get(4).get("Start time");
		String endTime = meetingData.get(4).get("End time");
		String attendee = meetingData.get(4).get("Attendee");
		String body = meetingData.get(4).get("Body");
		HomePage home = new HomePage();
		boolean actual = home
				.clickScheduleBtn()
				.clickOverMeetingCreated(preSubject)
				.setSubjectTxtBox(newSubject)
				.setStartTimeDate(startTime)
				.setEndTimeDate(endTime)
				.setAttendeeTxtBoxPressingEnter(attendee)
				.setBodyTxtBox(body)
				.clickUpdateBtn()
				.setPasswordTxtBox(password)
				.clickOkButton()
				.isMessageMeetingUpdatedDisplayed();

		Assert.assertTrue(actual);
	}

	@AfterMethod
	public void goHome() {
		SchedulePage delete = new SchedulePage();
		delete.deleteMeeting(newSubject, password)
		.clickBackBtn();
	}
}
