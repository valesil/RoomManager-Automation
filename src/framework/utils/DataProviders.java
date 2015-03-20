package framework.utils;

import static framework.common.AppConfigConstants.EXCEL_DATA_PROVIDER;
import framework.utils.readers.ExcelReader;
import org.testng.annotations.DataProvider;

/**
 * 
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
