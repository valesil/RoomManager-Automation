package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.AppConfigConstants.EXCEL_PATH;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC35: Verify that Clicking in the timeline erases all the content in Organizer, 
 * Subject, Attendees and body textboxes
 * @author Asael Calizaya
 *
 */
public class ClickingInTimelineAllFieldsAreCleaned {
	SchedulePage schedule = new SchedulePage();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");; 
	String password = meetingData.get(0).get("Password");
	String organizer = meetingData.get(1).get("Organizer");
	String subject = meetingData.get(1).get("Subject");
	String roomName = meetingData.get(1).get("Room");
	String path = System.getProperty("user.dir") + EXCEL_PATH + "\\meeting01.json";
	String authentication = organizer + ":" + password;
	
	@BeforeMethod
	public void creationMeetingPreCondition() throws MalformedURLException, IOException {
		RootRestMethods.createMeeting(roomName, path, authentication);
	}
	
	@Test(groups = "UI")
    public void testClickingInTimeLineAllFiledsAreCleaned() {
	    HomeTabletPage home = new HomeTabletPage();
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
	public void goHome() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}
