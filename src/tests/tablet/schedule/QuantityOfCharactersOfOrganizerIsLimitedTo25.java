package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * TC26: Verify that the quantity of characters in the subject text box should be limited to 255 characters
 * @author Asael Calizaya
 *
 */
public class QuantityOfCharactersOfOrganizerIsLimitedTo25 {

	@Test(groups = "NEGATIVE")
	public void testQuantityOfCharactersToOrganazerIsLimitedTo25() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData"); 
		String organizer = meetingData.get(4).get("Organizer");
		String subject = meetingData.get(4).get("Subject");
		String startTime = meetingData.get(4).get("Start time");
		String endTime = meetingData.get(4).get("End time");
		String attendee = meetingData.get(4).get("Attendee");
		String body = meetingData.get(4).get("Body");

		HomeTabletPage homePage = new HomeTabletPage();
		SchedulePage schedulePage = homePage.clickScheduleBtn();
		schedulePage
		.setOrganizerTxtBox(organizer)
		.setSubjectTxtBox(subject)
		.setAttendeeTxtBoxPressingEnter(attendee)
		.setBodyTxtBox(body)
		.setStartTimeDate(startTime)
		.setEndTimeDate(endTime)
		.clickCreateBtn()
		.clickCancelButton();

		//Fails, it should be displays an error message
		Assert.assertTrue(schedulePage.isMessageErrorPopUpDisplayed());
	}
}