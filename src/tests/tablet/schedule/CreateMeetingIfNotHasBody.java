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
 * Title: Verify a user can create a meeting if the body is empty 
 * @author Asael Calizaya
 *
 */
public class CreateMeetingIfNotHasBody {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(0).get("Organizer");
	String subject = meetingData.get(0).get("Subject");
	String startTime = meetingData.get(0).get("Start time");
	String endTime = meetingData.get(0).get("End time");
	String attendee1 = meetingData.get(0).get("Attendee");
	String attendee2 = meetingData.get(1).get("Attendee");
	String password = meetingData.get(0).get("Password");
	
	@Test
	public void testCreateMeetingIfNotHasBody() {
		HomePage home = new HomePage();
		SchedulePage schedule = new SchedulePage();
		boolean actual;
		home.clickScheduleBtn()
			.setOrganizerTxtBox(organizer)
			.setSubjectTxtBox(subject)
			.setStartTimeDate(startTime)
			.setEndTimeDate(endTime);
		
		//TC's 28
		schedule.setAttendeeTxtBoxPressingEnter(attendee1);
		Assert.assertEquals(schedule.getEmailAttendeeLblValue(attendee1), attendee1);
		
		//TC's 29
		schedule.setAttendeeTxtBoxPressingSemicolon(attendee2);
		Assert.assertEquals(schedule.getEmailAttendeeLblValue(attendee2), attendee2);		
		
		actual = schedule.clickCreateBtn()
				.setPasswordTxtBox(password)
				.clickOkButton()
				.isMessageMeetingCreatedDisplayed();
		
		//TC's 18
		Assert.assertTrue(actual);
	}

	@AfterMethod
	public void goHome() {
		SchedulePage delete = new SchedulePage();
		delete.deleteMeeting(subject, password)
		.clickBackBtn();
	}
}
