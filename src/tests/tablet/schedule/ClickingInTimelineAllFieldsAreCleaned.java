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
 * Title: Verify that Clicking in the timeline erases all the content in Organizer, 
 * Subject, Attendees and body textboxes
 * @author Asael Calizaya
 *
 */
public class ClickingInTimelineAllFieldsAreCleaned {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");; 
	String password = meetingData.get(0).get("Password");
	String organizer = meetingData.get(1).get("Organizer");
	String subject = meetingData.get(1).get("Subject");
	String startTime = meetingData.get(1).get("Start time");
	String endTime = meetingData.get(1).get("End time");
	String attendee = meetingData.get(1).get("Attendee");
	String body = meetingData.get(1).get("Body");
	
	@BeforeMethod
	public void creationMeetingPreCondition() {
		MeetingMethods preCondition = new MeetingMethods();
		preCondition.createMeetingWithAllDataFromExcel(organizer, subject, startTime, endTime, attendee, body, password);
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();	
	}
	
	@Test
    public void testClickingInTimeLineAllFiledsAreCleaned() {
	    HomePage home = new HomePage();
	    home
	    	.clickScheduleBtn()
	    	.clickOverMeetingCreated(subject)
	    	.clickOverTimeline();
	    SchedulePage schedule = new SchedulePage();
	    String actualOrganizer = schedule.getMeetingOrganizerValue();
		String actualSubject = schedule.getMeetingSubjectValue();
		String actualAttendee = schedule.getEmailAttendeeTxtBoxValue();
		String actualBody = schedule.getBodyTxtBoxValue();
		
		Assert.assertEquals("", actualOrganizer);
		Assert.assertEquals("", actualSubject);
		Assert.assertEquals("", actualAttendee);
		Assert.assertEquals("", actualBody);
    }
	
	@AfterMethod
	public void goHome() {
		SchedulePage delete = new SchedulePage();
		delete.deleteMeeting(subject, password)
		.clickBackBtn();
	}
}
