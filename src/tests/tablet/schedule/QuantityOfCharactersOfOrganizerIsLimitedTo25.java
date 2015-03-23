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
public class QuantityOfCharactersOfOrganizerIsLimitedTo25 {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData"); 
	String organizer = meetingData.get(4).get("Organizer");
	String subject = meetingData.get(4).get("Subject");
	String startTime = meetingData.get(4).get("Start time");
	String endTime = meetingData.get(4).get("End time");
	String attendee = meetingData.get(4).get("Attendee");
	String body = meetingData.get(4).get("Body");
	String password = meetingData.get(4).get("Password");
	
	@Test
	public void testQuantityOfCharactersToOrganazerIsLimitedTo25() {
		HomePage home = new HomePage();
		boolean actual = home.clickScheduleBtn()
			.setOrganizerTxtBox(organizer)
			.setSubjectTxtBox(subject)
			.setAttendeeTxtBoxPressingEnter(attendee)
			.setBodyTxtBox(body)
			.setStartTimeDate(startTime)
			.setEndTimeDate(endTime)
			.clickCreateBtn()
			.clickCancelButton()
			.isMessageOfErrorDisplayed();
		
		Assert.assertTrue(actual);
	}
	
	@AfterMethod
	public void goHome() {
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();
	}
}