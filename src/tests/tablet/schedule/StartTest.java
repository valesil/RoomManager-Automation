package tests.tablet.schedule;

import static framework.common.AppConfigConstants.BROWSER;
import static framework.common.AppConfigConstants.ROOM_NAME;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.pages.tablet.SettingsPage;
import framework.selenium.SeleniumDriverManager;

/**
 *This class is to select a some room to work on that
 * @author Asael Calizaya
 *
 */
public class StartTest {
	WebDriver driver = SeleniumDriverManager.getManager().getDriver();
	
	@BeforeSuite
	public void start() throws InterruptedException {
		SettingsPage settings = new SettingsPage();
		if(!BROWSER.equalsIgnoreCase("ie")) {
			settings.selectRoom(ROOM_NAME);
		}		
	}
	
	@AfterSuite
	public void end() {
		driver.quit();
	}
}
