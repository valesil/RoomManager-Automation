package tests.admin.conferenceRoom;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.pages.admin.LoginPage;

public class StartTestSuiteConferenceRoom {

	@BeforeSuite
	public void init() {
		LoginPage loginPage = new LoginPage();
		loginPage.clickSigninLink();
	}
	
	@AfterSuite
	public void quit() {
		LoginPage loginPage = new LoginPage();
		loginPage.CloseWindow();
	}
}
