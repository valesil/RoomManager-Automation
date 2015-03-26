package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.AppConfigConstants.EXCEL_PATH;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC19: Verify by clicking in the TimeLine the information of the meeting is displayed
 * TC20: Verify the body content is saved in the meeting information
 * @author Asael Calizaya
 *
 */
public class InformationOfMeetingIsDisplayed {
	SchedulePage schedule = new SchedulePage();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String password = meetingData.get(0).get("Password");
	String organizer = meetingData.get(1).get("Organizer");
	String subject = meetingData.get(1).get("Subject");
	String startTime = meetingData.get(1).get("Start time");
	String endTime = meetingData.get(1).get("End time");
	String attendee = meetingData.get(1).get("Attendee");
	String body = meetingData.get(1).get("Body");
	String roomName = meetingData.get(1).get("Room");
	String path = System.getProperty("user.dir") + EXCEL_PATH + "\\meeting01.json";
	String authentication = organizer + ":" + password;

	@BeforeMethod
	public void createMeeting() throws MalformedURLException, IOException {
		RootRestMethods.createMeeting(roomName, path, authentication);
	}

	@Test(groups = "FUNCTIONAL")
	public void testByClickinOverSomeMeetingTheInformationIsDisplayed() {
		HomeTabletPage home = new HomeTabletPage();
		home.clickScheduleBtn()
			.clickOverMeetingCreated(subject);
		String actualOrganizer = schedule.getMeetingOrganizerValue();
		String actualSubject = schedule.getMeetingSubjectValue();
		String actualAttendee = schedule.getEmailAttendeeLblValue(attendee);
		String actualBody = schedule.getBodyTxtBoxValue();
		
		//Assert TC 13
		Assert.assertEquals(organizer, actualOrganizer);
		Assert.assertEquals(subject, actualSubject);
		Assert.assertEquals(attendee, actualAttendee);
		
		//Assert TC 14
		//Fails because the application does not save the body information 
		Assert.assertEquals(body, actualBody);
	}

	@AfterMethod
	public void goHome() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}
