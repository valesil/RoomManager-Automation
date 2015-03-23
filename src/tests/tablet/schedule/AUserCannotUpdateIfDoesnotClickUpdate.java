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
 * Title: Verify if the user doesn't click update button the information is not updated
 * @author Asael Calizaya
 *
 */
public class AUserCannotUpdateIfDoesnotClickUpdate {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");; 
	String password = meetingData.get(0).get("Password");
	String preOrganizer = meetingData.get(1).get("Organizer");
	String preSubject = meetingData.get(1).get("Subject");
	String preStartTime = meetingData.get(1).get("Start time");
	String preEndTime = meetingData.get(1).get("End time");
	String preAttendee = meetingData.get(1).get("Attendee");
	String preBody = meetingData.get(1).get("Body");

	@BeforeMethod
	public void creationMeetingPreCondition() {
		MeetingMethods preCondition = new MeetingMethods();
		preCondition.createMeetingWithAllDataFromExcel(preOrganizer, preSubject, preStartTime, preEndTime, preAttendee, preBody, password);
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();		
	}

	@Test
	public void testAUserCannotUpdateMeetingIfDoesnotClickUpdate() {
		String subject = meetingData.get(0).get("Subject");
		String startTime = meetingData.get(0).get("Start time");
		String endTime = meetingData.get(0).get("End time");
		String attendee = meetingData.get(0).get("Attendee");
		String body = meetingData.get(0).get("Body");
		HomePage home = new HomePage();
		home
			.clickScheduleBtn()
			.clickOverMeetingCreated(subject)
			.setSubjectTxtBox(subject)
			.setStartTimeDate(startTime)
			.setEndTimeDate(endTime)
			.setAttendeeTxtBoxPressingEnter(attendee)
			.setBodyTxtBox(body)
			.clickBackBtn()
			.clickScheduleBtn()
			.clickOverMeetingCreated(preSubject);

		SchedulePage schedule = new SchedulePage();
		String actualOrganizer = schedule.getMeetingOrganizerValue();
		String actualSubject = schedule.getMeetingSubjectValue();
		String actualAttendee = schedule.getEmailAttendeeLblValue(attendee);
		//String actualBody = schedule.getBodyTxtBoxValue();

		Assert.assertEquals(preOrganizer, actualOrganizer);
		Assert.assertEquals(preSubject, actualSubject);
		Assert.assertEquals(preAttendee, actualAttendee);
		//Assert.assertEquals(preBody, actualBody);
	}

	@AfterMethod
	public void goHome() {
		SchedulePage schedule = new SchedulePage();
		schedule.deleteMeeting(preSubject, password)
		.clickBackBtn();
	}
}
