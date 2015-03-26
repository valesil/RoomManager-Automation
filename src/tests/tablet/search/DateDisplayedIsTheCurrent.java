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
 * @title TC18:Verify if the date displayed on search page is 
 * the current date
 * @author Jose Cabrera
 */
public class DateDisplayedIsTheCurrent {
	SearchPage search = new SearchPage();
	
	@Test(groups = "UI")
	public void testDateDisplayedIsTheCurrent (){
		HomeTabletPage home = new HomeTabletPage();
		search = home.clickSearchBtn();
		Assert.assertTrue(search.dateIsPresent());
	}
	
	@AfterMethod
	public void toHome() {
		search.clickBackBtn();
	}	
}
