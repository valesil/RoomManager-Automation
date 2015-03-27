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
 * TC8: Verify a user cannot create a meeting if the organizer is not specified
 * @author Asael Calizaya
 *
 */
public class CannotCreateMeetingWithoutOrganizer {
	
	@Test(groups = "ACCEPTANCE")
	public void testAUserCannotCreateMeetingWithoutOrganizer() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
		String subject = meetingData.get(0).get("Subject");
		String startTime = meetingData.get(0).get("Start time");
		String endTime = meetingData.get(0).get("End time");
		String attendee = meetingData.get(0).get("Attendee");
		String body = meetingData.get(0).get("Body");
		
		HomeTabletPage homePage = new HomeTabletPage();
		SchedulePage schedulePage = homePage
				.clickScheduleBtn()
				.setSubjectTxtBox(subject)
				.setStartTimeDate(startTime)
				.setEndTimeDate(endTime)
				.setAttendeeTxtBoxPressingEnter(attendee)
				.setBodyTxtBox(body)
				.clickCreateBtn();

		Assert.assertTrue(schedulePage.isErrorOrganizerLblDisplayed());
	}
}
