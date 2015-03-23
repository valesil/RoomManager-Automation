package tests.tablet.homeUI;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;
import framework.common.AppConfigConstants;
public class HomeSuite {
	public SettingsPage settings=new SettingsPage();
	@BeforeSuite
	public void SearchSuiteStart () throws BiffException, IOException{
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String roomName = testData.get(0).get("Room Name");
		settings.selectRoom(roomName);
	}
	
	@AfterSuite
	public void cleanEnvironment() {
		settings.quit();
	}
}
