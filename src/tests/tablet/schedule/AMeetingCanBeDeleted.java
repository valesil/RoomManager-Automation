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
import framework.utils.readers.JsonReader;

/**
 * TC13: Verify a user can remove meetings
 * @author Asael Calizaya
 *
 */
public class AMeetingCanBeDeleted {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	JsonReader jsonReader = new JsonReader();
	String filePath = EXCEL_PATH + "\\meeting01.json";
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String password = meetingData.get(0).get("Password");

	@BeforeMethod
	public void creationMeetingPreCondition() throws MalformedURLException, IOException {
		String roomName = meetingData.get(1).get("Room");
		String organizer = jsonReader.readJsonFile("organizer", filePath);
		String path = System.getProperty("user.dir") + filePath;
		String authentication = organizer + ":" + password;
		RootRestMethods.createMeeting(roomName, path, authentication);
	}
	
	@Test(groups = "ACCEPTANCE")
    public void testAMeetingCanBeDeleted() {
		String subject = jsonReader.readJsonFile("title", filePath);
		
	    HomeTabletPage homePage = new HomeTabletPage();
	    SchedulePage schedulePage = homePage.clickScheduleBtn()
	    	.clickOverMeetingCreated(subject)
	    	.clickRemoveBtn()
	    	.confirmCredentials(password);
	    
		Assert.assertTrue(schedulePage.isMessageMeetingDeletedDisplayed());
    }
}
