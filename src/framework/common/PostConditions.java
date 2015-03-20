package framework.common;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import framework.pages.tablet.HomePage;
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
	HomePage home = new HomePage();
	
	/**
	 * [AC] This method is to initialize the listMaps to read from the excel
	 */
	public PostConditions() {
		schedule = new SchedulePage();
		try {
			excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		} catch (Exception e) {
			System.out.println("Error on Preconditions: " + e.getMessage());
			e.printStackTrace();
		}
		meetingData = excelReader.getMapValues("MeetingData");
	}
	
	/**
	 * [AC] This class delete a meeting
	 * @param nameMeeting: name of meeting to delete
	 * @return: This page, to use the same method repeated times
	 */
	public SchedulePage deleteMeetingFromHome(String nameMeeting, String password) {
		home.getHome().clickScheduleBtn()
		.deleteMeeting(nameMeeting, password);
		return new SchedulePage();
	}
	
	public void goHome() {
		schedule.clickBackBtn();
	}
	
}
