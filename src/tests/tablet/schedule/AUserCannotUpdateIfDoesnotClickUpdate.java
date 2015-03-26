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
import framework.utils.readers.JsonReader;

/**
 * TC45: Verify if the user doesn't click update button the information is not updated
 * @author Asael Calizaya
 *
 */
public class AUserCannotUpdateIfDoesnotClickUpdate {
	String filePath = "\\src\\tests\\meeting01.json";
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	JsonReader jsonReader = new JsonReader();
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String password = meetingData.get(0).get("Password");
	String preOrganizer = meetingData.get(0).get("Organizer");
	String preSubject = meetingData.get(1).get("Subject");
	String preAttendee = meetingData.get(1).get("Attendee");
	String preBody = meetingData.get(1).get("Body");
	String roomName = meetingData.get(1).get("Room");
	String path = System.getProperty("user.dir") + EXCEL_PATH + "\\meeting01.json";
	String authentication = preOrganizer + ":" + password;

	@BeforeMethod
	public void creationMeetingPreCondition() throws MalformedURLException, IOException {
		RootRestMethods.createMeeting(roomName, path, authentication);
	}

	@Test(groups = "UI")
	public void testAUserCannotUpdateMeetingIfDoesnotClickUpdate() {
		String subject = meetingData.get(0).get("Subject");
		String startTime = meetingData.get(0).get("Start time");
		String endTime = meetingData.get(0).get("End time");
		String attendee = meetingData.get(0).get("Attendee");
		String body = meetingData.get(0).get("Body");
		HomeTabletPage home = new HomeTabletPage();
		home
			.clickScheduleBtn()
			.clickOverMeetingCreated(preSubject)
			.setSubjectTxtBox(subject)
			.setStartTimeDate(startTime)
			.setEndTimeDate(endTime)
			.setAttendeeTxtBoxPressingEnter(attendee)
			.setBodyTxtBox(body)
			.clickBackBtn()
			.clickScheduleBtn()
			.clickOverMeetingCreated(preSubject);

		SchedulePage schedule = new SchedulePage();
		String actualOrganizer = schedule.getMeetingOrganizerValue();
		String actualSubject = schedule.getMeetingSubjectValue();
		String actualAttendee = schedule.getEmailAttendeeLblValue(attendee);
		String actualBody = schedule.getBodyTxtBoxValue();

		Assert.assertEquals(preOrganizer, actualOrganizer);
		Assert.assertEquals(preSubject, actualSubject);
		Assert.assertEquals(preAttendee, actualAttendee);
		
		//Fails because it does not get the body
		Assert.assertEquals(preBody, actualBody);
	}

	@AfterMethod
	public void goHome() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, preSubject, authentication);
	}
}
