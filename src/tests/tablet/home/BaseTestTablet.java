package tests.tablet.home;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.utils.TimeManager.getTime12h;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * This class create meetings, update meetings with values set in a excel file and timeManager
 * also remove meetings
 * @author Eliana Navia
 */
public class BaseTest {
	static SchedulePage schedulePage = new SchedulePage();
	String meetingDataSheet = "MeetingData";
	static List<Map<String, String>> meetingDataXls; 

	private static void excelIni() throws BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		meetingDataXls = excelReader.getMapValues("MeetingData");
	}

	/**
	 * Create a project with excel values.
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public static void createCurrentMeeting() throws BiffException, IOException{
		excelIni();
		String organizer = meetingDataXls.get(0).get("Organizer");
		String subject = meetingDataXls.get(0).get("Subject");
		String startTime = getTime12h(-5);
		String endTime = getTime12h(10);
		String attendees = meetingDataXls.get(0).get("Attendees");
		String password = meetingDataXls.get(0).get("Password");
		String message = "Meeting successfully created";

		schedulePage.createMeeting(organizer, subject, startTime, 
				endTime, attendees);

		schedulePage.confirmCredentials(organizer, password);
		schedulePage.getMessagePopUpValue(message);
	}

	/**
	 * 
	 * @return room display name that is stored in the excel
	 * @throws BiffException
	 * @throws IOException
	 */
	public static String getRoomDisplayName() throws BiffException, IOException {
		excelIni();
		String roomDisplayName = meetingDataXls.get(0).get("Room");
		return roomDisplayName;
	}

	public static String getCurrentMeetingSubject() throws BiffException, IOException {
		excelIni();
		String meetingSubject = meetingDataXls.get(0).get("Subject");
		return  meetingSubject;
	}

	public static String getNextMeetingSubject() throws BiffException, IOException {
		excelIni();
		String meetingSubject = meetingDataXls.get(1).get("Subject");
		return  meetingSubject;
	}

	public static String getUpdateMeetingSubject() throws BiffException, IOException {
		excelIni();
		String meetingSubject = meetingDataXls.get(2).get("Subject");
		return  meetingSubject;
	}

	/**
	 * Create a project with excel values.
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public static void createNextMeeting() throws IOException, BiffException{
		excelIni();
		String organizer = meetingDataXls.get(0).get("Organizer");
		String subject = meetingDataXls.get(1).get("Subject");
		String startTime = getTime12h(20);
		String endTime = getTime12h(35);
		String attendees = meetingDataXls.get(0).get("Attendees");
		String password = meetingDataXls.get(0).get("Password");
		String message = "Meeting successfully created";

		schedulePage.createMeeting(organizer, subject, startTime, 
				endTime, attendees);
		schedulePage.confirmCredentials(organizer, password);
		schedulePage.getMessagePopUpValue(message);
	}

	public static void deleteMeeting(String meetingSubject) throws BiffException, IOException {	
		excelIni();
		String password = meetingDataXls.get(0).get("Password");
		String message = "Meeting successfully removed";

		schedulePage.clickOverMeetingCreated(meetingSubject);
		schedulePage.clickRemoveBtn();

		schedulePage.setPasswordTxtBox(password);
		schedulePage.clickOkButton();

		schedulePage.getMessagePopUpValue(message);
	}

	public static void updateMeetingSubject(String meetingSubject) throws BiffException, IOException {
		excelIni();
		String password = meetingDataXls.get(0).get("Password");
		String message = "Meeting successfully updated";
		String newSubject = meetingDataXls.get(2).get("Subject");

		schedulePage.clickOverMeetingCreated(meetingSubject);

		schedulePage.setSubjectTxtBox(newSubject);
		schedulePage.clickUpdateBtn();

		schedulePage.setPasswordTxtBox(password);
		schedulePage.clickOkButton();

		schedulePage.getMessagePopUpValue(message);			
	}
}
