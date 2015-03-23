package tests.tablet.search;


/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SearchPage;

/**
 * @title Verify when filters are displayed on search page and "Advanced" button 
 * is pressed all filters are hidden
 * @author Jose Cabrera
 */
public class WhenPressAdvancedTwiceAllSearchCriteriasAreHidden {
	public HomePage home = new HomePage();
	public SearchPage search = new SearchPage();
	@Test(groups = {"UI"})
	public void testPressAdvancedTwiceAllSearchCriteriasAreHidden () {
		search = home.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.clickHiddenAdvancedBtn();
		Assert.assertFalse(search.filtersArePresent());
	}

	@AfterClass
	public void toHome() {
		search.clickBackBtn();
	}
}
