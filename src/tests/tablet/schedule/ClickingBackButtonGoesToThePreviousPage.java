package tests.tablet.schedule;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;

/**
 * @title TC44: Verify that Clicking back button goes to the previous page
 * @author Jose Cabrera
 */
public class ClickingBackButtonGoesToThePreviousPage {
	HomeTabletPage home = new HomeTabletPage();
	SchedulePage schedule =new SchedulePage();

	@Test(groups = "UI")
	public void testClickingBackButtonGoesToThePreviousPage (){
		schedule = home.clickScheduleBtn();
		home = schedule.clickBackBtn();
		Assert.assertTrue(home.isHomePageDisplayed());
	}
}
