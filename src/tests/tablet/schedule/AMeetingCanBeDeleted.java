package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.AppConfigConstants.EXCEL_PATH;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC13: Verify a user can remove meetings
 * @author Asael Calizaya
 *
 */
public class AMeetingCanBeDeleted {
	SchedulePage schedule = new SchedulePage();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");; 
	String password = meetingData.get(0).get("Password");
	String organizer = meetingData.get(1).get("Organizer");
	String subject = meetingData.get(1).get("Subject");
	String startTime = meetingData.get(1).get("Start time");
	String endTime = meetingData.get(1).get("End time");
	String attendee = meetingData.get(1).get("Attendee");
	String body = meetingData.get(1).get("Body");
	String roomName = meetingData.get(1).get("Room");
	String path = System.getProperty("user.dir") + EXCEL_PATH + "\\meeting01.json";
	String authentication = organizer + ":" + password;
	
	@BeforeMethod
	public void creationMeetingPreCondition() throws MalformedURLException, IOException {
		RootRestMethods.createMeeting(roomName, path, authentication);
	}
	
	@Test(groups = "ACCEPTANCE")
    public void testAMeetingCanBeDeleted() {
	    HomeTabletPage home = new HomeTabletPage();
	    home
	    	.clickScheduleBtn()
	    	.clickOverMeetingCreated(subject)
	    	.clickRemoveBtn()
	    	.setPasswordTxtBox(password)
	    	.clickOkButton();
	    
		Assert.assertTrue(schedule.isMessageMeetingDeletedDisplayed());
    }
}
