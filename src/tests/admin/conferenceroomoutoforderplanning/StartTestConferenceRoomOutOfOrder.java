package tests.admin.conferenceroomoutoforderplanning;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.selenium.SeleniumDriverManager;


public class StartTestConferenceRoomOutOfOrder {
	WebDriver driver = SeleniumDriverManager.getManager().getDriver();

	@BeforeSuite
	public void start() {
	}
	
	@AfterSuite
	public void end() {
		driver.quit();
	}
}
