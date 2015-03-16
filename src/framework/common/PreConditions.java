package framework.common;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * Description: This class is to create meetings as a precondition by UI
 * @author Asael Calizaya
 *
 */
public class PreConditions {
	private HomePage home;
	private ExcelReader excelReader;
	List<Map<String, String>> meetingData; 
	List<Map<String, String>> expectedMessages;
	
	/**
	 * This method is to initialize the listMaps to read from the excel
	 */
	public PreConditions() {
		home = new HomePage();
		try {
			excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		} catch (Exception e) {
			System.out.println("Error on Precondions: " + e.getMessage());
			e.printStackTrace();
		}
		meetingData = excelReader.getMapValues("Meetings");
		expectedMessages = excelReader.getMapValues("ExpectedMessages");
	}
	
	/**
	 * This method is to create a single meeting
	 * @return: A String with the name of the meeting created
	 */
	public String createMeetingSuccessfully() {
		String organizer = meetingData.get(0).get("Organizer");
		String subject = meetingData.get(0).get("Subject");
		String startTime = meetingData.get(0).get("Start time");
		String meridianStart = meetingData.get(0).get("MeridanStart");
		String endTime = meetingData.get(0).get("End time");
		String meridianEnd = meetingData.get(0).get("MeridianEnd");
		String attendee = meetingData.get(0).get("Attendee");
		String body = meetingData.get(0).get("Body");
		String password = meetingData.get(0).get("Password");
		String expectedMessage = expectedMessages.get(0).get("Message");
		home
			.clickSchedulePageLink()
			.setOrganizerTxtBox(organizer)
			.setSubjectTxtBox(subject)
			.setStartTimeDate(startTime, meridianStart)
			.setEndTimeDate(endTime, meridianEnd)
			.setAttendeeTxtBox(attendee)
			.setBodyTxtBox(body)
			.clickCreateBtn()
			.setPasswordTxtBox(password)
			.clickOkButton()
			.getMessagePopUpValue(expectedMessage);
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();
		return subject;
	}
	
	/**
	 * This method is to create more than one meetings, depends of the data in the excel file 
	 * @param amountOfMeetings
	 * @return: All name of meetings created on this method
	 */
	public String[] createMeetingSuccessfully(int amountOfMeetings) {
		String[] subject = new String[amountOfMeetings];
		for (int i = 0; i < amountOfMeetings; i++) {
			String organizer = meetingData.get(i).get("Organizer");
			subject[i] = meetingData.get(i).get("Subject");
			String startTime = meetingData.get(i).get("Start time");
			String meridianStart = meetingData.get(i).get("MeridanStart");
			String endTime = meetingData.get(i).get("End time");
			String meridianEnd = meetingData.get(i).get("MeridianEnd");
			String attendee = meetingData.get(i).get("Attendee");
			String body = meetingData.get(i).get("Body");
			String password = meetingData.get(i).get("Password");
			String expectedMessage = expectedMessages.get(0).get("Message");
			home
				.clickSchedulePageLink()
				.setOrganizerTxtBox(organizer)
				.setSubjectTxtBox(subject[i])
				.setStartTimeDate(startTime, meridianStart)
				.setEndTimeDate(endTime, meridianEnd)
				.setAttendeeTxtBox(attendee)
				.setBodyTxtBox(body)
				.clickCreateBtn()
				.setPasswordTxtBox(password)
				.clickOkButton()
				.getMessagePopUpValue(expectedMessage);
			SchedulePage schedule = new SchedulePage();
			schedule.clickBackBtn();
		}
		return subject;
	}
}
