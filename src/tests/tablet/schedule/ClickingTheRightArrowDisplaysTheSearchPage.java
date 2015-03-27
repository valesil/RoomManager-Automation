package tests.tablet.schedule;

import java.io.IOException;
import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;

/**
 * TC43: Verify that Clicking the right arrow displays the search page
 * @author Jose Cabrera
 */
public class ClickingTheRightArrowDisplaysTheSearchPage {
	SearchPage searchPage;
	
	@Test(groups = "UI")
	public void testClickingTheRightArrowDisplaysTheSearchPage () {
		HomeTabletPage homePage = new HomeTabletPage();
		searchPage = homePage.clickScheduleBtn()
				.clickSearchBtn();
		Assert.assertTrue(searchPage.dateLblIsDisplayed());
	}
	
	@AfterClass
	public void toHome() throws MalformedURLException, IOException {
		searchPage.clickBackBtn();
	}
	
	
}
