package tests.tablet.schedule;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;

/**
 * TC44: Verify that Clicking back button goes to the previous page
 * @author Jose Cabrera
 */
public class ClickingBackButtonGoesToThePreviousPage { 

	@Test(groups = "UI")
	public void testClickingBackButtonGoesToThePreviousPage () {
		HomeTabletPage homePage = new HomeTabletPage();
		SchedulePage schedulePage = homePage.clickScheduleBtn();
		homePage = schedulePage.clickBackBtn();
		Assert.assertTrue(homePage.isHomePageDisplayed());
	}
}
