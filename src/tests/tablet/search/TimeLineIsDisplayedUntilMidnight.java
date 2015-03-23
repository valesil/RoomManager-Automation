package tests.tablet.search;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SearchPage;
import framework.utils.TimeManager;


/**
 * @title Verify that the time line is displayed on search page from 
 * current time until midght night
 * @author Jose Cabrera
 */
public class TimeLineIsDisplayedUntilMidnight {
	public HomePage home = new HomePage();
	public SearchPage search = new SearchPage();
	@Test(groups = {"UI"})
	public void testTimeDisplayedIsTheCurrent () throws InterruptedException{
		String currentDate = TimeManager.getCurrentDate("MMMM d YYY, HH:mm");
		search = home.clickSearchBtn();
		//search.moveTimeLine(3000);
		Assert.assertTrue(search.getTimeLineDate().contains(currentDate));
	}

	@AfterClass
	public void toHome() {
		search.clickBackBtn();
	}


}

