package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.AppConfigConstants.EXCEL_PATH;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC11: Verify an existing meeting can be updated if it doesn't have a conflict with another meeting
 * @author Asael Calizaya
 *
 */
public class UpdateMeetingIfItNotHasConflictsWithOtherMeeting {	
	SchedulePage schedule = new SchedulePage();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String password = meetingData.get(0).get("Password");
	String preOrganizer = meetingData.get(1).get("Organizer");
	String preSubject = meetingData.get(1).get("Subject");
	String newSubject;
	String roomName = meetingData.get(1).get("Room");
	String path = System.getProperty("user.dir") + EXCEL_PATH + "\\meeting01.json";
	String authentication = preOrganizer + ":" + password;

	@BeforeMethod
	public void preconditionToUpdateAMeeting() throws MalformedURLException, IOException {
		RootRestMethods.createMeeting(roomName, path, authentication);
	}

	@Test(groups = "ACCEPTANCE")
	public void testAUserCanUpdateAMeetingIfItNotHasConflictsWithOtherMeeting() {
		newSubject = meetingData.get(4).get("Subject");
		String startTime = meetingData.get(4).get("Start time");
		String endTime = meetingData.get(4).get("End time");
		String attendee = meetingData.get(4).get("Attendee");
		String body = meetingData.get(4).get("Body");
		HomeTabletPage home = new HomeTabletPage();
		home
				.clickScheduleBtn()
				.clickOverMeetingCreated(preSubject)
				.setSubjectTxtBox(newSubject)
				.setStartTimeDate(startTime)
				.setEndTimeDate(endTime)
				.setAttendeeTxtBoxPressingEnter(attendee)
				.setBodyTxtBox(body)
				.clickUpdateBtn()
				.setPasswordTxtBox(password)
				.clickOkButton();
		
		Assert.assertTrue(schedule.isMessageMeetingUpdatedDisplayed());
	}

	@AfterMethod
	public void goHome() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, newSubject, authentication);
	}
}
