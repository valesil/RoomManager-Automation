package tests.tablet.search;


/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SearchPage;

/**
 * @titleVerify on search page when "BACK" (represented for an arrow) button 
 * is pressed the home page is displayed
 * @author Jose Cabrera
 */
public class WhenSelectBackButtonSchedulerPageAreDisplayed {
	public HomePage home = new HomePage();
	public SearchPage search = new SearchPage();
	@Test(groups = {"UI"})
	public void testSelectBackButtonSchedulerPageAreDisplayed () {
		search = home.clickSearchBtn();
		home = search.clickBackBtn();
		Assert.assertTrue(home.isHomePageDisplayed());
	}
}
