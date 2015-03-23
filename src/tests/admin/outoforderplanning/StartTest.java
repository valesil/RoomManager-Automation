package tests.admin.outoforderplanning;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.pages.admin.LoginPage;
import framework.selenium.SeleniumDriverManager;


public class StartTest {
	WebDriver driver = SeleniumDriverManager.getManager().getDriver();

	@BeforeSuite
	public void start() {
		LoginPage login = new LoginPage();
		login.clickSigninLink();
	}
	
	@AfterSuite
	public void end() {
		driver.quit();
	}
}
