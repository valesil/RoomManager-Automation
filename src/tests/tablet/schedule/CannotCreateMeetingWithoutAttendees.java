package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * TC10: Verify a user cannot create a meeting if the attendees are not specified
 * @author Asael Calizaya
 *
 */
public class CannotCreateMeetingWithoutAttendees {
	
	@Test(groups = "ACCEPTANCE")
	public void testAUserCannotCreateMeetingWithoutAttendees() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> meetingData  = excelReader.getMapValues("MeetingData");
		String organizer = meetingData.get(0).get("Organizer");
		String subject = meetingData.get(0).get("Subject");
		String startTime = meetingData.get(0).get("Start time");
		String endTime = meetingData.get(0).get("End time");
		String body = meetingData.get(0).get("Body");
		
		HomeTabletPage homePage = new HomeTabletPage();
		SchedulePage schedulePage = homePage
		.clickScheduleBtn()
		.setOrganizerTxtBox(organizer)
		.setSubjectTxtBox(subject)
		.setStartTimeDate(startTime)
		.setEndTimeDate(endTime)
		.setBodyTxtBox(body)
		.clickCreateBtn()
		.clickCancelButton();

		//Fails because allows create meeting without attendees
		Assert.assertTrue(schedulePage.isErrorAttendeeLblDisplayed());
	}
}