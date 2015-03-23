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
 * Title: Verify a user can remove meetings
 * @author Asael Calizaya
 *
 */
public class AMeetingCanBeDeleted {
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
    public void testAMeetingCanBeDeleted() {
	    HomePage home = new HomePage();
	    boolean actual = home
	    	.clickScheduleBtn()
	    	.clickOverMeetingCreated(subject)
	    	.clickRemoveBtn()
	    	.setPasswordTxtBox(password)
	    	.clickOkButton()
	    	.isMessageMeetingDeletedDisplayed();
	    
	    Assert.assertTrue(actual);
    }
	
	@AfterMethod
	public void goHome() {
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();
	}
}
