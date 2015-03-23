package suits;

import org.testng.annotations.BeforeSuite;

import framework.pages.admin.LoginPage;

public class StartTestSuiteRoomInfo {

	@BeforeSuite
	public void init() {
		LoginPage loginPage = new LoginPage();
		loginPage.clickSigninLink();
	}
}
