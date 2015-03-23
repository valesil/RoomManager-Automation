package tests.admin.roomResourceAssociations;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.pages.admin.LoginPage;

public class StartTestSuiteRoomResourcesAsociations {

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
