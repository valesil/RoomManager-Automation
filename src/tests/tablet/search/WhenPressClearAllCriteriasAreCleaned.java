package tests.tablet.search;


/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */

import java.util.LinkedList;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SearchPage;
import framework.rest.RootRestMethods;

/**
 * @title Verify on search page that when "Clear" button  is pressed 
 * all fields are cleaned
 * @author Jose Cabrera
 */
public class WhenPressClearAllCriteriasAreCleaned {
	public HomePage home = new HomePage();
	public SearchPage search = new SearchPage();
	public LinkedList<String> condition = RootRestMethods.getNamesAssociatedResources("room 7");
	@Test(groups = {"UI"})
	public void testPressClearAllCriteriasAreCleaned () {
		search = home.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.clickClearBtn();
		Assert.assertFalse(search.resourcesAreSelected(condition)&&
				search.allFiltersAreCleaned());
	}

	@AfterClass
	public void toHome() {
		search.clickBackBtn();
	}
}
