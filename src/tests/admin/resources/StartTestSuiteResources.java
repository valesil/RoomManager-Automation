package tests.admin.resources;

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
	
	@BeforeSuite
	public void init() {
	}
	
	@AfterSuite
	public void afterSuite() {
		login.CloseWindow();
	}
}
