package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.common.MeetingMethods;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * TC13: Verify a user can remove meetings
 * @author Asael Calizaya
 *
 */
public class AMeetingCanBeDeleted {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String password = meetingData.get(0).get("Password");
	private String subject;
	
	@BeforeMethod(groups = "ACCEPTANCE")
	public void creationMeetingPreCondition() {
		MeetingMethods meeting = new MeetingMethods();
		subject = meetingData.get(1).get("Subject");
		String startTime = meetingData.get(1).get("Start time");
		String endTime = meetingData.get(1).get("End time");
		String attendee = meetingData.get(1).get("Attendee");
		String body = meetingData.get(1).get("Body");
		String organizer = meetingData.get(1).get("Organizer");	
		meeting.createMeetingFromHome(organizer, subject, startTime, endTime, attendee, body, password);
	}

	@Test(groups = "ACCEPTANCE")
	public void testAMeetingCanBeDeleted() {
		HomeTabletPage homePage = new HomeTabletPage();
		SchedulePage schedulePage = homePage
				.clickScheduleBtn()
				.clickOverMeetingCreated(subject)
				.clickRemoveBtn()
				.confirmCredentials(password);

		Assert.assertTrue(schedulePage.isMessageMeetingDeletedDisplayed());
	}
}
