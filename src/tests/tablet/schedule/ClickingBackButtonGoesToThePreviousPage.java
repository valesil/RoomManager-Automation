package tests.tablet.schedule;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SearchPage;

/**
 * @title Verify that Clicking back button goes to the previous page
 * @author Jose Cabrera
 */
public class ClickingBackButtonGoesToThePreviousPage {
	public HomePage home = new HomePage();
	public SchedulePage schedule =new SchedulePage();
	public SearchPage search = new SearchPage();

	@Test(groups = {"UI"})
	public void testClickingBackButtonGoesToThePreviousPage (){
		schedule = home.clickScheduleBtn();
		home = schedule.clickBackBtn();
		Assert.assertTrue(home.isHomePageDisplayed());
	}
}
