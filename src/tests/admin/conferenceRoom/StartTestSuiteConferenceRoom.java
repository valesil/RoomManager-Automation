package tests.admin.conferenceRoom;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.pages.admin.LoginPage;

public class StartTestSuiteConferenceRoom {
	LoginPage loginPage = new LoginPage();
	
	@BeforeSuite
	public void init() {
		loginPage.clickSigninLink();
	}
	
	@AfterSuite
	public void afterSuite() {
		loginPage.CloseWindow();
	}
}
