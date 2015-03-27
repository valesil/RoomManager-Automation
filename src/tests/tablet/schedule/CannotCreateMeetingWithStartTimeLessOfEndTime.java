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
 * TC48: Verify when put a startTime less to endTime a error message is displayed
 * @author Asael Calizaya
 *
 */
public class CannotCreateMeetingWithStartTimeLessOfEndTime {
	
	@Test(groups = "FUNCTIONAL")
	public void testAUserCannotCreateMeetingWithStartTimeLessOfEndTime() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
		String organizer = meetingData.get(8).get("Organizer");
		String subject = meetingData.get(8).get("Subject");
		String startTime = meetingData.get(8).get("Start time");
		String endTime = meetingData.get(8).get("End time");
		String attendee = meetingData.get(8).get("Attendee");
		String body = meetingData.get(8).get("Body");
		
		HomeTabletPage homePage = new HomeTabletPage();
		SchedulePage schedulePage = homePage.clickScheduleBtn()
			.setOrganizerTxtBox(organizer)
			.setSubjectTxtBox(subject)
			.setStartTimeDate(startTime)
			.setEndTimeDate(endTime)
			.setAttendeeTxtBoxPressingEnter(attendee)
			.setBodyTxtBox(body)
			.clickCreateBtn();
		
		Assert.assertTrue(schedulePage.isMessageOfTimeErrorDisplayed());
	}
}
