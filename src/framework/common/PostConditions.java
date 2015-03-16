package framework.common;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * Description This class is to delete the meetings created
 * @author Asael Calizaya
 *
 */
public class PostConditions {
	private SchedulePage schedule;
	private ExcelReader excelReader;
	private List<Map<String, String>> meetingData;
	private List<Map<String, String>> expectedMessages;
	
	/**
	 * [AC] This method is to initialize the listMaps to read from the excel
	 */
	public PostConditions() {
		schedule = new SchedulePage();
		try {
			excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		} catch (Exception e) {
			System.out.println("Error on Precondions: " + e.getMessage());
			e.printStackTrace();
		}
		expectedMessages = excelReader.getMapValues("ExpectedMessages");
		meetingData = excelReader.getMapValues("Meetings");
	}
	
	/**
	 * [AC] This class delete a meeting
	 * @param nameMeeting: name of meeting to delete
	 * @return: This page, to use the same method repeated times
	 */
	public PostConditions deleteMeeting(String nameMeeting) {
		String password = meetingData.get(0).get("Password");
		String expectedMessage = expectedMessages.get(1).get("Message");
		schedule
				.clickOverMeetingCreated(nameMeeting)
				.clickRemoveBtn()
				.setPasswordTxtBox(password)
				.clickOkButton()
				.getMessagePopUpValue(expectedMessage);
		return this;
	}
	
	public void goHome() {
		schedule.clickBackBtn();
	}
}
