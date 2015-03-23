package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.common.MeetingMethods;
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * Title: Verify by clicking in the TimeLine the information of the meeting is displayed
 * Title: Verify the body content is saved in the meeting information
 * @author Asael Calizaya
 *
 */
public class InformationOfMeetingIsDisplayed {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String password = meetingData.get(0).get("Password");
	String organizer = meetingData.get(1).get("Organizer");
	String subject = meetingData.get(1).get("Subject");
	String startTime = meetingData.get(1).get("Start time");
	String endTime = meetingData.get(1).get("End time");
	String attendee = meetingData.get(1).get("Attendee");
	String body = meetingData.get(1).get("Body");

	@BeforeMethod
	public void createMeeting() {
		MeetingMethods preCondition = new MeetingMethods();
		preCondition.createMeetingWithAllDataFromExcel(organizer, subject, startTime, endTime, attendee, body, password);
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();
	}

	@Test
	public void testByClickinOverSomeMeetingTheInformationIsDisplayed() {
		HomePage home = new HomePage();
		SchedulePage schedule = new SchedulePage();
		home
			.clickScheduleBtn()
			.clickOverMeetingCreated(subject);
		String actualOrganizer = schedule.getMeetingOrganizerValue();
		String actualSubject = schedule.getMeetingSubjectValue();
		String actualAttendee = schedule.getEmailAttendeeLblValue(attendee);
		String actualBody = schedule.getBodyTxtBoxValue();
		
		//Assert TC 13
		Assert.assertEquals(organizer, actualOrganizer);
		Assert.assertEquals(subject, actualSubject);
		Assert.assertEquals(attendee, actualAttendee);
		
		//Assert TC 14
		Assert.assertEquals(body, actualBody);
	}

	@AfterMethod
	public void goHome() {
		SchedulePage delete = new SchedulePage();
		delete.deleteMeeting(subject, password)
		.clickBackBtn();
	}
}
