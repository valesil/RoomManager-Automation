package tests.tablet.search;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;

/**
 * TC17: Verify on search page when "BACK" (represented for an arrow) button 
 * is pressed the home page is displayed
 * @author Jose Cabrera
 */
public class WhenSelectBackButtonSchedulerPageAreDisplayed {
	SearchPage searchPage;
	
	@Test(groups = "UI")
	public void testSelectBackButtonSchedulerPageAreDisplayed () {
		HomeTabletPage homePage = new HomeTabletPage();
		searchPage = homePage.clickSearchBtn();
		homePage = searchPage.clickBackBtn();
		Assert.assertTrue(homePage.isHomePageDisplayed());
	}
}
