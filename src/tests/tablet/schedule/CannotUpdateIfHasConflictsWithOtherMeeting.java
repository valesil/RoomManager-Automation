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
import framework.utils.readers.ExcelReader;

/**
 * Title: Verify an existing meeting cannot be updated if it has conflicts with another meeting
 * @author Asael Calizaya
 *
 */
public class CannotUpdateIfHasConflictsWithOtherMeeting {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData"); 
	String subject = meetingData.get(3).get("Subject");
	String startTime = meetingData.get(3).get("Start time");
	String endTime = meetingData.get(3).get("End time");
	String attendee = meetingData.get(3).get("Attendee");
	String body = meetingData.get(3).get("Body");
	String password = meetingData.get(3).get("Password");
	String[] namesMeetings;
	
	@BeforeMethod
	public void preconditionToUpdateAMeeting() {
		MeetingMethods preCondition = new MeetingMethods();
		namesMeetings = preCondition.createMeetingsSuccessfully(2);
	}

	@Test
	public void testAUserCannotUpdateIfHasConflictsWithOtherMeeting() {
		HomePage home = new HomePage();
		boolean actual = home
				.clickScheduleBtn()
				.clickOverMeetingCreated(namesMeetings[1])
				.setSubjectTxtBox(subject)
				.setStartTimeDate(startTime)
				.setEndTimeDate(endTime)
				.setAttendeeTxtBoxPressingEnter(attendee)
				.setBodyTxtBox(body)
				.clickUpdateBtn()
				.setPasswordTxtBox(password)
				.clickOkButton()
				.isMessageOfErrorDisplayed();
//		SchedulePage schedule = new SchedulePage();
//		schedule.clickCancelButton();

		Assert.assertTrue(actual);
	}

	@AfterMethod
	public void goHome() {
		SchedulePage delete = new SchedulePage();
		delete.deleteMeeting(namesMeetings[0], password);
		delete.deleteMeeting(namesMeetings[1], password);
		delete.clickBackBtn();
		
	}
}
