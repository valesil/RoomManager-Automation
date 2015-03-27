package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.AppConfigConstants.EXCEL_PATH;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;
import framework.utils.readers.JsonReader;

/**
 * TC15: Verify a user cannot create a meeting that has conflicts with the schedule of another meeting
 * @author Asael Calizaya
 *
 */
public class CannotCreateMeetingIfHasConfilctsWithAnotherMeeting {
	String filePath = EXCEL_PATH + "\\meeting01.json";
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	JsonReader jsonReader = new JsonReader();
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");

	String preOrganizer = jsonReader.readJsonFile("organizer", filePath);
	String password = meetingData.get(0).get("Password");
	String preSubject = jsonReader.readJsonFile("title", filePath);
	String roomName = meetingData.get(1).get("Room");	
	String authentication = preOrganizer + ":" + password;

	@BeforeClass
	public void preconditionToUpdateAMeeting() throws MalformedURLException, IOException {
		String path = System.getProperty("user.dir") + filePath;
		RootRestMethods.createMeeting(roomName, path, authentication);
	}

	@Test(groups = "FUNCTIONAL")
	public void testAUserCannotCreateAMeetingIfHasConflictsWithAnotherMeeting() {
		String organizer = meetingData.get(2).get("Organizer");
		String subject = meetingData.get(2).get("Subject");
		String startTime = meetingData.get(2).get("Start time");
		String endTime = meetingData.get(2).get("End time");
		String attendee = meetingData.get(2).get("Attendee");
		String body = meetingData.get(2).get("Body");
		String password = meetingData.get(2).get("Password");
		HomeTabletPage homePage = new HomeTabletPage();
		SchedulePage schedulePage = homePage
				.clickScheduleBtn()
				.createMeeting(organizer, subject, startTime, endTime, attendee, body, password)
				.clickCancelButton();

		//Fails because the message displayed is incorrect
		Assert.assertTrue(schedulePage.isMessageErrorCreationMeetingPopUpDisplayed());
	}

	@AfterMethod
	public void goHome() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, preSubject, authentication);
	}
}
