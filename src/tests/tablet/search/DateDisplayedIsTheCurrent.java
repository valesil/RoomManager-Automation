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
 * @title Verify if the date displayed on search page is 
 * the current date
 * @author Jose Cabrera
 */
public class DateDisplayedIsTheCurrent {
	public HomePage home = new HomePage();
	public SearchPage search = new SearchPage();
	@Test(groups = {"UI"})
	public void testDateDisplayedIsTheCurrent (){
		search = home.clickSearchBtn();
		Assert.assertTrue(search.dateIsPresent());
	}
	
	@AfterClass
	public void toHome() {
		search.clickBackBtn();
	}
	
	
}
