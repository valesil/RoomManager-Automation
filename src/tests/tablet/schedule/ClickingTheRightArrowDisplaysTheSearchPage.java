package tests.tablet.schedule;

/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */
import java.io.IOException;
import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SearchPage;

/**
 * @title Verify that Clicking the right arrow displays the search page
 * @author Jose Cabrera
 */
public class ClickingTheRightArrowDisplaysTheSearchPage {
	HomeTabletPage home=new HomeTabletPage();
	SchedulePage schedule = new SchedulePage();
	SearchPage search = new SearchPage();
	
	@Test(groups = "UI")
	public void testClickingTheRightArrowDisplaysTheSearchPage () {
		search = home.clickScheduleBtn()
				.clickSearchBtn();
		Assert.assertTrue(search.dateLblIsDisplayed());
	}
	
	@AfterClass
	public void toHome() throws MalformedURLException, IOException {
		search.clickBackBtn();
	}
	
	
}
