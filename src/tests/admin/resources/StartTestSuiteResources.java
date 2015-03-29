package tests.admin.resources;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.pages.admin.LoginPage;

/**
 * @author Marco Llano
 *
 */
public class StartTestSuiteResources {
	LoginPage login = new LoginPage();

	@BeforeSuite(groups = {"FUNCTIONAL","NEGATIVE","ACCEPTANCE"})
	public void init() {
	}

	@AfterSuite(groups = {"FUNCTIONAL","NEGATIVE","ACCEPTANCE"})
	public void afterSuite() {
		login.CloseWindow();
	}
}