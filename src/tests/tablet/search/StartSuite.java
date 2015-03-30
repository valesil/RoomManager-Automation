package tests.tablet.search;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SettingsPage;
import framework.rest.RootRestMethods;
import framework.selenium.SeleniumDriverManager;
import framework.utils.readers.ExcelReader;

/**
 * 
 * @author administrator
 *
 */
public class StartSuite {
	WebDriver driver = SeleniumDriverManager.getManager().getDriver();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String roomName = meetingData.get(2).get("Room");
	
	@BeforeSuite(groups = {"ACCEPTANCE", "UI", "NEGATIVE", "FUNCTIONAL"})
	public void start() throws InterruptedException, MalformedURLException, IOException {
        String resourceFileJSON = "\\src\\tests\\Resource1.json";
        String filePath = System.getProperty("user.dir") + resourceFileJSON;
		RootRestMethods.createResource(filePath,"");
		HomeTabletPage homePage = new HomeTabletPage();
		SettingsPage settingsPage = homePage.clickSettingsBtn();
		settingsPage.selectRoom(roomName);
	}
	
	@AfterSuite(groups = {"ACCEPTANCE", "UI", "NEGATIVE", "FUNCTIONAL"})
	public void cleanEnvironment() throws MalformedURLException, IOException {
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String resourceName = testData.get(1).get("Resource");
		RootRestMethods.deleteResource(resourceName);
		driver.quit();
	}
}
