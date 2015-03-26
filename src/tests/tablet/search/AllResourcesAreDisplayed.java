package tests.tablet.search;

/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.rest.RootRestMethods;

/**
 * @title Verify that all resources are displayed on search page 
 * on "filter by resource" field 
 * @author Jose Cabrera
 */
public class AllResourcesAreDisplayed {
	SearchPage search = new SearchPage();
	
	@Test(groups = "ACCEPTANCE")
	public void testAllResourcesAreDisplayed () throws JSONException, MalformedURLException, IOException {
		HomeTabletPage home = new HomeTabletPage();
		LinkedList<String> condition = RootRestMethods.getAllNameResources();
		search = home.clickSearchBtn()
				.clickCollapseAdvancedBtn();
		Assert.assertTrue(search.resourcesInList(condition));
	}
	
	@AfterMethod
	public void toHome() {
		search.clickBackBtn();
	}
	
	
	
}
