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
 * TC9: Verify a user cannot create a meeting if the subject is not specified
 * @author Asael Calizaya
 *
 */
public class CannotCreateMeetingWithoutSubject {
	SchedulePage schedule = new SchedulePage();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(0).get("Organizer");
	String startTime = meetingData.get(0).get("Start time");
	String endTime = meetingData.get(0).get("End time");
	String attendee = meetingData.get(0).get("Attendee");
	String body = meetingData.get(0).get("Body");
	
	@Test(groups = "ACCEPTANCE")
	public void testAUserCannotCreateMeetingWithoutSubject() {
		HomeTabletPage home = new HomeTabletPage();
		home
				.clickScheduleBtn()
				.setOrganizerTxtBox(organizer)
				.setStartTimeDate(startTime)
				.setEndTimeDate(endTime)
				.setAttendeeTxtBoxPressingEnter(attendee)
				.setBodyTxtBox(body)
				.clickCreateBtn();

		Assert.assertTrue(schedule.isErrorSubjectLblDisplayed());
	}
}
