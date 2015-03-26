package tests.tablet.search;

/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */
import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;

/**
 * @titleVerify on search page when "BACK" (represented for an arrow) button 
 * is pressed the home page is displayed
 * @author Jose Cabrera
 */
public class WhenSelectBackButtonSchedulerPageAreDisplayed {
	SearchPage search = new SearchPage();
	
	@Test(groups = "UI")
	public void testSelectBackButtonSchedulerPageAreDisplayed () {
		HomeTabletPage home = new HomeTabletPage();
		search = home.clickSearchBtn();
		home = search.clickBackBtn();
		Assert.assertTrue(home.isHomePageDisplayed());
	}
}
