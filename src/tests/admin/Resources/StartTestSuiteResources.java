package tests.admin.Resources;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.LoginPage;

/**
 * @author Marco Llano
 *
 */
public class StartTestSuiteResources {
	LoginPage login = new LoginPage();
	HomeAdminPage home = new HomeAdminPage();
	
	@BeforeSuite
	public void init() {
		home = login.clickSigninLink();
	}
	
	@AfterSuite
	public void afterSuite() {
		login.CloseWindow();
	}
}
