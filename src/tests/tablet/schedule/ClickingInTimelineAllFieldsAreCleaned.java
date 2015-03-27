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
import framework.utils.readers.JsonReader;

/**
 * TC35: Verify that Clicking in the timeline erases all the content in Organizer, 
 * Subject, Attendees and body textboxes
 * @author Asael Calizaya
 *
 */
public class ClickingInTimelineAllFieldsAreCleaned {
	String filePath = EXCEL_PATH + "\\meeting01.json";
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	JsonReader jsonReader = new JsonReader();
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData"); 
	
	String subject = jsonReader.readJsonFile("title", filePath);
	String roomName = meetingData.get(1).get("Room");	
	String password = meetingData.get(0).get("Password");
	String organizer = jsonReader.readJsonFile("organizer", filePath);
	String path = System.getProperty("user.dir") + filePath;
	String authentication = organizer + ":" + password;
	
	@BeforeMethod
	public void creationMeetingPreCondition() throws MalformedURLException, IOException {
		RootRestMethods.createMeeting(roomName, path, authentication);
	}
	
	@Test(groups = "UI")
    public void testClickingInTimeLineAllFiledsAreCleaned() {
	    HomeTabletPage homePage = new HomeTabletPage();
	    SchedulePage schedulePage = homePage
	    	.clickScheduleBtn()
	    	.clickOverMeetingCreated(subject)
	    	.clickOverTimeline();
	    
	    String actualOrganizer = schedulePage.getMeetingOrganizerValue();
		String actualSubject = schedulePage.getMeetingSubjectValue();
		String actualAttendee = schedulePage.getEmailAttendeeTxtBoxValue();
		String actualBody = schedulePage.getBodyTxtBoxValue();
		
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
