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
 * @title Verify that all resources are displayed on search page 
 * on "filter by resource" field 
 * @author Jose Cabrera
 */
public class AllResourcesAreDisplayed {
	public HomePage home = new HomePage();
	public SearchPage search = new SearchPage();
	public LinkedList<String> condition = RootRestMethods.getAllNameResources();
	
	@Test(groups = {"ACCEPTANCE"})
	public void testAllResourcesAreDisplayed () {
		search = home.clickSearchBtn()
				.clickCollapseAdvancedBtn();
		Assert.assertTrue(search.resourcesInList(condition));
	}
	
	@AfterClass
	public void toHome() {
		search.clickBackBtn();
	}
	
	
	
}
