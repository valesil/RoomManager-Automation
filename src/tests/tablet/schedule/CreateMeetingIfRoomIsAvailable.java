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
 * TC7: verify a user can create meeting if room is Available 
 * @author Asael Calizaya
 *
 */
public class CreateMeetingIfRoomIsAvailable {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	
	private String organizer = meetingData.get(0).get("Organizer");
	private String subject = meetingData.get(0).get("Subject");
	private String password = meetingData.get(0).get("Password");
	private String roomName = meetingData.get(1).get("Room");
	private String authentication = organizer + ":" + password;
	
	@Test(groups = "ACCEPTANCE")
	public void testCreateMeetingIfRoomIsAvailable() {
		String startTime = meetingData.get(0).get("Start time");
		String endTime = meetingData.get(0).get("End time");
		String attendee = meetingData.get(0).get("Attendee");
		String body = meetingData.get(0).get("Body");
		
		HomeTabletPage homePage = new HomeTabletPage();
		SchedulePage schedulePage = homePage.clickScheduleBtn();
		schedulePage.createMeeting(organizer, subject, startTime, endTime, attendee, body, password);

		Assert.assertTrue(schedulePage.isMessageMeetingCreatedDisplayed());
	}

	@AfterMethod(groups = "ACCEPTANCE")
	public void deleteMeetings() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}
