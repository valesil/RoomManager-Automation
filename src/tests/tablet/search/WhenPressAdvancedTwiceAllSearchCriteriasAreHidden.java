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
 * @title Verify when filters are displayed on search page and "Advanced" button 
 * is pressed all filters are hidden
 * @author Jose Cabrera
 */
public class WhenPressAdvancedTwiceAllSearchCriteriasAreHidden {
	SearchPage search = new SearchPage();
	
	@Test(groups = "UI")
	public void testPressAdvancedTwiceAllSearchCriteriasAreHidden() {
		HomeTabletPage home = new HomeTabletPage();
		search = home.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.clickHiddenAdvancedBtn();
		Assert.assertFalse(search.filtersArePresent());
	}

	@AfterMethod
	public void toHome() {
		search.clickBackBtn();
	}
}
