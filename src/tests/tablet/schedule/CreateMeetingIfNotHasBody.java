package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC18: Verify a meeting can be created if the body is empty
 * TC28: Verify that pressing enter confirms the name of an attendee
 * TC29: Verify that Pressing semicolon confirms the name of an attendee
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
	String attendee2 = meetingData.get(0).get("Attendee");
	String password = meetingData.get(0).get("Password");
	String roomName = meetingData.get(1).get("Room");
	String authentication = organizer + ":" + password;
	
	@Test(groups = {"FUNCTIONAL", "UI", "UI"})
	public void testCreateMeetingIfNotHasBody() {
		HomeTabletPage home = new HomeTabletPage();
		SchedulePage schedule = new SchedulePage();
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
		
		schedule.clickCreateBtn()
				.setPasswordTxtBox(password)
				.clickOkButton();
		
		//TC's 18
		Assert.assertTrue(schedule.isMessageMeetingCreatedDisplayed());
	}

	@AfterMethod
	public void goHome() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}
