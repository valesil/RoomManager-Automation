package tests.tablet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.common.AppConfigConstants;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SettingsPage;
import framework.selenium.SeleniumDriverManager;
import framework.utils.readers.ExcelReader;
import framework.rest.RootRestMethods;
import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

/**
 * 
 * @author administrator
 *
 */
public class StartSuite {
	WebDriver driver = SeleniumDriverManager.getManager().getDriver();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Search");
	String roomName = testData.get(0).get("Room Name");
	
	@BeforeSuite
	public void start() throws InterruptedException, MalformedURLException, IOException {
        String resourceFileJSON = "\\src\\tests\\Resource1.json";
        String filePath = System.getProperty("user.dir") + resourceFileJSON;
		RootRestMethods.createResource(filePath,"");
		HomeTabletPage homePage = new HomeTabletPage();
		SettingsPage settingsPage = homePage.clickSettingsBtn();
		settingsPage.selectRoom(roomName);
	}
	
	@AfterSuite
	public void cleanEnvironment() throws MalformedURLException, IOException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String resourceName = testData.get(1).get("Resource");
		RootRestMethods.deleteResource(resourceName);
		driver.quit();
	}
}
