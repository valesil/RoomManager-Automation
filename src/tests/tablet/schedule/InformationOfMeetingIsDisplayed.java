package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.common.MeetingMethods;
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
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String password = meetingData.get(0).get("Password");
	private String organizer = meetingData.get(1).get("Organizer");
	private String subject = meetingData.get(1).get("Subject");
	private String startTime = meetingData.get(1).get("Start time");
	private String endTime = meetingData.get(1).get("End time");
	private String attendee = meetingData.get(1).get("Attendee");
	private String body = meetingData.get(1).get("Body");

	private String roomName = meetingData.get(1).get("Room");
	private String authentication = organizer + ":" + password;

	@BeforeMethod(groups = "FUNCTIONAL")
	public void creationMeetingPreCondition() {
		MeetingMethods meeting = new MeetingMethods();
		meeting.createMeetingFromHome(organizer, subject, startTime, endTime, attendee, body, password);
	}

	@Test(groups = "FUNCTIONAL")
	public void testInformationOfMeetingIsDisplayed() {
		HomeTabletPage home = new HomeTabletPage();
		SchedulePage schedule = home
				.clickScheduleBtn()
				.clickOverMeetingCreated(subject);
		String actualOrganizer = schedule.getMeetingOrganizerValue();
		String actualSubject = schedule.getMeetingSubjectValue();
		String actualAttendee = schedule.getEmailAttendeeLblValue(attendee);
		String actualBody = schedule.getBodyTxtBoxValue();

		//Assertion TC19
		Assert.assertEquals(organizer, actualOrganizer);
		Assert.assertEquals(subject, actualSubject);
		Assert.assertEquals(attendee, actualAttendee);

		//Assertion TC20
		//Fails because the application does not save the body information 
		Assert.assertEquals(body, actualBody);
	}

	@AfterMethod(groups = "FUNCTIONAL")
	public void deleteMeetings() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}
