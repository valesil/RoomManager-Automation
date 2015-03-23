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
}
