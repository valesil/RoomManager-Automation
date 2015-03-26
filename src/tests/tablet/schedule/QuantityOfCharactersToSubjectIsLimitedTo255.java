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
	SchedulePage schedule = new SchedulePage();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(3).get("Organizer");
	String subject = meetingData.get(3).get("Subject");
	String startTime = meetingData.get(3).get("Start time");
	String endTime = meetingData.get(3).get("End time");
	String attendee = meetingData.get(3).get("Attendee");
	String body = meetingData.get(3).get("Body");
	String password = meetingData.get(3).get("Password");
	String roomName = meetingData.get(1).get("Room");
	String authentication = organizer + ":" + password;

	@Test(groups = "NEGATIVE")
	public void testQuantityOfCharactersToSubjectIsLimitedTo255() {
		HomeTabletPage home = new HomeTabletPage();
		home.clickScheduleBtn()
		.setOrganizerTxtBox(organizer)
		.setSubjectTxtBox(subject)
		.setAttendeeTxtBoxPressingEnter(attendee)
		.setBodyTxtBox(body)
		.setStartTimeDate(startTime)
		.setEndTimeDate(endTime)
		.clickCreateBtn()
		.setPasswordTxtBox(password)
		.clickOkButton();

		//Fails, It should be displays an error message
		Assert.assertTrue(schedule.isMessageErrorPopUpDisplayed());
	}

	@AfterMethod
	public void goHome() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}