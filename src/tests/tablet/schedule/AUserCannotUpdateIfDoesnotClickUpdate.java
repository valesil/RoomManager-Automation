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
 * TC45: Verify if the user doesn't click update button the information is not updated
 * @author Asael Calizaya
 *
 */
public class AUserCannotUpdateIfDoesnotClickUpdate {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");

	//data for precondition and to do asserts
	private String organizer = meetingData.get(1).get("Organizer");
	private String subject = meetingData.get(1).get("Subject");
	private String startTime = meetingData.get(1).get("Start time");
	private String endTime = meetingData.get(1).get("End time");
	private String attendee = meetingData.get(1).get("Attendee");
	private String body = meetingData.get(1).get("Body");

	private String password = meetingData.get(1).get("Password");
	private String roomName = meetingData.get(1).get("Room");
	private String authentication = organizer + ":" + password;

	@BeforeMethod(groups = "UI")
	public void creationMeetingPreCondition() {
		MeetingMethods meeting = new MeetingMethods();
		meeting.createMeetingFromHome(organizer, subject, startTime, endTime, attendee, body, password);
	}

	@Test(groups = "UI")
	public void testAUserCannotUpdateIfDoesnotClickUpdate() {
		String newSubject = meetingData.get(0).get("Subject");
		String newStartTime = meetingData.get(0).get("Start time");
		String newEndTime = meetingData.get(0).get("End time");
		String newAttendee = meetingData.get(0).get("Attendee");
		String newBody = meetingData.get(0).get("Body");

		HomeTabletPage home = new HomeTabletPage();
		SchedulePage schedule = home
				.clickScheduleBtn()
				.clickOverMeetingCreated(subject)
				.setSubjectTxtBox(newSubject)
				.setStartTimeDate(newStartTime)
				.setEndTimeDate(newEndTime)
				.setAttendeeTxtBoxPressingEnter(newAttendee)
				.setBodyTxtBox(newBody)
				.clickBackBtn()
				.clickScheduleBtn()
				.clickOverMeetingCreated(subject);

		String actualOrganizer = schedule.getMeetingOrganizerValue();
		String actualSubject = schedule.getMeetingSubjectValue();
		String actualAttendee = schedule.getEmailAttendeeLblValue(newAttendee);
		String actualBody = schedule.getBodyTxtBoxValue();

		Assert.assertEquals(organizer, actualOrganizer);
		Assert.assertEquals(subject, actualSubject);
		Assert.assertEquals(attendee, actualAttendee);

		//Fails because it does not get the body
		Assert.assertEquals(body, actualBody);
	}

	@AfterMethod(groups = "UI")
	public void deleteMeetings() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}
