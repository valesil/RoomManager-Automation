package tests.tablet.search;


/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;

/**
 * @title Verify on search page when "Advanced" button is pressed 
 * filters criterias are displayed(Filter by resourse, Room name, Minimum capacity, location)
 * @author Jose Cabrera
 */
public class WhenPressAdvancedallCriteriasAreDisplayed {
	SearchPage search = new SearchPage();
	
	@Test(groups = "UI")
	public void testPressAdvancedallCriteriasAreDisplayed () {
		HomeTabletPage home = new HomeTabletPage();
		search = home.clickSearchBtn()
				.clickCollapseAdvancedBtn();
		Assert.assertTrue(search.filtersArePresent());
	}

	@AfterMethod
	public void toHome() {
		search.clickBackBtn();
	}
}
