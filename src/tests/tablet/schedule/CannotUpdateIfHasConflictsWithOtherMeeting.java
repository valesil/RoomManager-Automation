package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.common.MeetingMethods;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * TC12: Verify an existing meeting cannot be updated if it has conflicts with another meeting
 * @author Asael Calizaya
 *
 */
public class CannotUpdateIfHasConflictsWithOtherMeeting {
	SchedulePage schedule = new SchedulePage();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String nameMeeting1 = meetingData.get(0).get("Subject");
	String nameMeeting2 = meetingData.get(1).get("Subject");
	
	String subject = meetingData.get(2).get("Subject");
	String startTime = meetingData.get(2).get("Start time");
	String endTime = meetingData.get(2).get("End time");
	String attendee = meetingData.get(2).get("Attendee");
	String body = meetingData.get(2).get("Body");
	String password = meetingData.get(2).get("Password");
	
	@BeforeMethod
	public void preconditionToUpdateAMeeting() {
		MeetingMethods preCondition = new MeetingMethods();
		
		//Meeting 01
		preCondition.createMeetingFromHome(meetingData.get(0).get("Organizer"),
				meetingData.get(0).get("Subject"), meetingData.get(0).get("Start time"),
				meetingData.get(0).get("End time"), meetingData.get(0).get("Attendee"),
				meetingData.get(0).get("Body"), meetingData.get(0).get("Password"));
		
		//Meeting 02
		preCondition.createMeetingFromHome(meetingData.get(1).get("Organizer"),
				meetingData.get(1).get("Subject"), meetingData.get(1).get("Start time"),
				meetingData.get(1).get("End time"), meetingData.get(1).get("Attendee"),
				meetingData.get(1).get("Body"), meetingData.get(1).get("Password"));
	}

	@Test(groups = "ACCEPTANCE")
	public void testAUserCannotUpdateIfHasConflictsWithOtherMeeting() {
		HomeTabletPage home = new HomeTabletPage();
		home
				.clickScheduleBtn()
				.clickOverMeetingCreated(nameMeeting1)
				.setSubjectTxtBox(subject)
				.setStartTimeDate(startTime)
				.setEndTimeDate(endTime)
				.setAttendeeTxtBoxPressingEnter(attendee)
				.setBodyTxtBox(body)
				.clickUpdateBtn()
				.setPasswordTxtBox(password)
				.clickOkButton();
		
		//Fails because the message that is displayed is incorrect
		Assert.assertTrue(schedule.isMessageErrorUpdateMeetingPopUpDisplayed());
	}

	@AfterMethod
	public void goHome() {
		schedule.deleteMeeting(nameMeeting2, password);
		schedule.deleteMeeting(nameMeeting1, password);
		schedule.clickBackBtn();
		
	}
}
