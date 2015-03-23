package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * Title: Verify that The default time of meeting is 30 minutes
 * @author Asael Calizaya
 *
 */
public class DefaultTimeOfMeetingIs30Minutes {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(0).get("Organizer");
	String subject = meetingData.get(0).get("Subject");
	String attendee = meetingData.get(0).get("Attendee");
	String body = meetingData.get(0).get("Body");
	
	@Test
	public void testTheDefaultTimeOfMeetingIs30Minutes() {
		HomePage home = new HomePage();
		home.clickScheduleBtn();
		SchedulePage schedule = new SchedulePage();
		int durationMeeting = schedule.setOrganizerTxtBox(organizer)
						.setSubjectTxtBox(subject)
						.setAttendeeTxtBoxPressingEnter(attendee)
						.setBodyTxtBox(body)
						.getDurationOfMeetingByDefault();
		System.out.println(durationMeeting);

		Assert.assertEquals(30, durationMeeting);
	}

	@AfterMethod
	public void goHome() {
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();
	}
}
