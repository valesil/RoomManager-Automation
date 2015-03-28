package tests.tablet.homeUI;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC14: This test case verifies that next meeting subject is updated in {Next} Tile 
 * when it is updated.
 * @author Eliana Navia
 * 
 */
public class UpdateOfNextMeetingSubjetWhenItIsChanged {
	Logger log = Logger.getLogger(getClass());
	HomeTabletPage homeTabletPage = new HomeTabletPage();
	SchedulePage schedulePage;

	//Data to create and use to assertions
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");	
	String organizer = meetingData.get(1).get("Organizer");
	String subject = meetingData.get(1).get("Subject");
	String attendee = meetingData.get(1).get("Attendee");
	String minStartTime = meetingData.get(1).get("Start time (minutes to add)");
	String minEndTime = meetingData.get(1).get("End time (minutes to add)");
	String password = meetingData.get(1).get("Password");
	String authentication = organizer + ":" + password;

	String expectedNewSubject = meetingData.get(2).get("Subject");

	@BeforeClass (groups = "ACCEPTANCE")
	public void init() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.createMeeting(organizer, subject, 
				minStartTime, minEndTime, attendee, password);	
	}

	@Test (groups = "ACCEPTANCE")
	public void testUpdateOfNextMeetingSubjetWhenItIsChanged() {

		schedulePage
		.clickOverMeetingCreated(subject)
		.setSubjectTxtBox(expectedNewSubject)
		.clickUpdateBtn()
		.confirmCredentials(password)
		.isMessageMeetingUpdatedDisplayed();

		schedulePage.clickBackBtn();

		String actualMeetingNameUpdated = homeTabletPage.getNextTileLbl();

		Assert.assertEquals(actualMeetingNameUpdated, expectedNewSubject);
	}

	/**
	 * Delete meeting by rest API.
	 */
	@AfterClass (groups = "ACCEPTANCE")
	public void end() {
		PropertyConfigurator.configure("log4j.properties");
		try {
			RootRestMethods.deleteMeeting(meetingData.get(0).get("Room"), expectedNewSubject , 
					authentication);
			log.info("The meeting:" + expectedNewSubject + "has been deleted successfully.");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
