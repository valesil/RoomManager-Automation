package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * Title: Verify that the quantity of characters in the subject text box should be limited to 255 characters
 * @author Asael Calizaya
 *
 */
public class QuantityOfCharactersToSubjectIsLimitedTo255 {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(2).get("Organizer");
	String subject = meetingData.get(2).get("Subject");
	String startTime = meetingData.get(2).get("Start time");
	String endTime = meetingData.get(2).get("End time");
	String attendee = meetingData.get(2).get("Attendee");
	String body = meetingData.get(2).get("Body");
	String password = meetingData.get(2).get("Password");
	
	@Test
	public void testQuantityOfCharactersToSubjectIsLimitedTo255() {
		HomePage home = new HomePage();
		boolean actual = home.clickScheduleBtn()
			.setOrganizerTxtBox(organizer)
			.setSubjectTxtBox(subject)
			.setAttendeeTxtBoxPressingEnter(attendee)
			.setBodyTxtBox(body)
			.setStartTimeDate(startTime)
			.setEndTimeDate(endTime)
			.clickCreateBtn()
			.setPasswordTxtBox(password)
			.clickOkButton()
			.isMessageOfErrorDisplayed();
		
		Assert.assertTrue(actual);
	}
	
	@AfterMethod
	public void goHome() {
		SchedulePage delete = new SchedulePage();
		delete.deleteMeeting(subject, password)
		.clickBackBtn();
	}
}