package framework.utils;

import static framework.common.AppConfigConstants.EXCEL_DATA_PROVIDER;

import org.testng.annotations.DataProvider;

import framework.utils.readers.ExcelReader;

/**
 * This class contains data providers for tests
 * @author Yesica Acha
 *
 */
public class DataProviders {
		
	@DataProvider(name = "OutOfOrderAndMeeting")
	public static Object[][] stageData() {
		ExcelReader excelFile = new ExcelReader(EXCEL_DATA_PROVIDER);
		return excelFile.getObjectValues("OutOfOrderAndMeeting");		
	}
	
	/**
	 * [EN] Reads the data of a excel file.
	 * @return Object[][] where the column contain the following values: 
	 * Organizer, Subject, minutesFrom, minutesTo, Attendee, Password
	 */
	@DataProvider(name="CurrentMeetingDataprovider")
	public static Object[][] getCurrentMeetingDataProvider() {
		ExcelReader excelFile = new ExcelReader(EXCEL_DATA_PROVIDER);
		return excelFile.getObjectValues("CurrentMeetingProvider");
	}
	
	/**
	 * Reads the data of a excel file.
	 * @return Object[][] Object[][] where the column contain the following values: 
	 * Organizer, Subject, minutesFrom, minutesTo, Attendee, Password
	 */
	@DataProvider(name="NextMeetingDataprovider")
	public static Object[][] getNextMeetingDataProvider() {
		ExcelReader excelFile = new ExcelReader(EXCEL_DATA_PROVIDER);
		return excelFile.getObjectValues("NextMeetingProvider");
	}
}
