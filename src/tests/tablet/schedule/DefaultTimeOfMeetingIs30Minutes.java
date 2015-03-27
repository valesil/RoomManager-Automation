package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * TC30: Verify that The default time of meeting is 30 minutes
 * @author Asael Calizaya
 *
 */
public class DefaultTimeOfMeetingIs30Minutes {
	
	@Test(groups = "UI")
	public void testTheDefaultTimeOfMeetingIs30Minutes() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
		String organizer = meetingData.get(0).get("Organizer");
		String subject = meetingData.get(0).get("Subject");
		String attendee = meetingData.get(0).get("Attendee");
		String body = meetingData.get(0).get("Body");
		
		HomeTabletPage homePage = new HomeTabletPage();
		SchedulePage schedulePage = homePage.clickScheduleBtn();
		int durationMeeting = schedulePage.setOrganizerTxtBox(organizer)
						.setSubjectTxtBox(subject)
						.setAttendeeTxtBoxPressingEnter(attendee)
						.setBodyTxtBox(body)
						.getDurationOfMeetingByDefault();

		Assert.assertEquals(30, durationMeeting);
	}
}
