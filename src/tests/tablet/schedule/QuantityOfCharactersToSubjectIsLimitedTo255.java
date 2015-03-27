package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC25: Verify that the quantity of characters in the subject text box should be limited to 255 characters
 * @author Asael Calizaya
 *
 */
public class QuantityOfCharactersToSubjectIsLimitedTo255 {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	
	String organizer = meetingData.get(3).get("Organizer");
	String subject = meetingData.get(3).get("Subject");
	String password = meetingData.get(3).get("Password");
	String roomName = meetingData.get(1).get("Room");
	String authentication = organizer + ":" + password;

	@Test(groups = "NEGATIVE")
	public void testQuantityOfCharactersToSubjectIsLimitedTo255() {
		String startTime = meetingData.get(3).get("Start time");
		String endTime = meetingData.get(3).get("End time");
		String attendee = meetingData.get(3).get("Attendee");
		String body = meetingData.get(3).get("Body");
		
		HomeTabletPage homePage = new HomeTabletPage();
		SchedulePage schedulePage = homePage
				.clickScheduleBtn()
				.createMeeting(organizer, subject, startTime, endTime, attendee, body, password)
				.clickOkButton();

		//Fails, It should be displays an error message
		Assert.assertTrue(schedulePage.isMessageErrorPopUpDisplayed());
	}

	@AfterMethod
	public void goHome() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}