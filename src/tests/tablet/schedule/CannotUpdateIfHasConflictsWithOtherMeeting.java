package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.common.MeetingMethods;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC12: Verify an existing meeting cannot be updated if it has conflicts with another meeting
 * @author Asael Calizaya
 *
 */
public class CannotUpdateIfHasConflictsWithOtherMeeting {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String nameMeeting1 = meetingData.get(0).get("Subject");
	private String organizer = meetingData.get(0).get("Organizer");
	private String nameMeeting2 = meetingData.get(1).get("Subject");

	private String password = meetingData.get(2).get("Password");
	private String roomName = meetingData.get(1).get("Room");
	private String authentication = organizer + ":" + password;

	@BeforeMethod(groups = "ACCEPTANCE")
	public void creationMeetingsPreCondition() {
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
	public void testCannotUpdateIfHasConflictsWithOtherMeeting() {
		String subject = meetingData.get(2).get("Subject");
		String startTime = meetingData.get(2).get("Start time");
		String endTime = meetingData.get(2).get("End time");
		String attendee = meetingData.get(2).get("Attendee");
		String body = meetingData.get(2).get("Body");

		HomeTabletPage homePage = new HomeTabletPage();
		SchedulePage schedulePage = homePage.clickScheduleBtn();
		schedulePage
		.clickOverMeetingCreated(nameMeeting1)
		.setSubjectTxtBox(subject)
		.setStartTimeDate(startTime)
		.setEndTimeDate(endTime)
		.setAttendeeTxtBoxPressingEnter(attendee)
		.setBodyTxtBox(body)
		.clickUpdateBtn()
		.confirmCredentials(password);

		//Fails because the message that is displayed is incorrect
		Assert.assertTrue(schedulePage.isMessageErrorUpdateMeetingPopUpDisplayed());
	}

	@AfterMethod(groups = "ACCEPTANCE")
	public void deleteMeetings() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, nameMeeting2, authentication);
		RootRestMethods.deleteMeeting(roomName, nameMeeting1, authentication);
	}
}
