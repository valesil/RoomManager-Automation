package tests.tablet.schedule;

/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SearchPage;

/**
 * @title Verify that Clicking the right arrow displays the search page
 * @author Jose Cabrera
 */
public class ClickingTheRightArrowDisplaysTheSearchPage {
	public HomePage home=new HomePage();
	public SchedulePage schedule=new SchedulePage();
	public SearchPage search=new SearchPage();

	@Test(groups = {"UI"})
	public void testClickingTheRightArrowDisplaysTheSearchPage (){
		search = home.clickScheduleBtn()
				.clickSearchBtn();
		Assert.assertTrue(search.dateLblIsDisplayed());
	}

	@AfterClass
	public void toHome() {
		search.clickBackBtn();
		schedule.clickBackBtn();
	}
}
