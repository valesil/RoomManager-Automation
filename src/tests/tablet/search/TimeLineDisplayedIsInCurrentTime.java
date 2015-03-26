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
import framework.utils.TimeManager;

/**
 * @title Verify the red line in the timeline is in the current time 
 * @author Jose Cabrera
 */
public class TimeLineDisplayedIsInCurrentTime {
	SearchPage search = new SearchPage();
	
	@Test(groups = "UI")
	public void testTimeDisplayedIsTheCurrent () {
		HomeTabletPage home = new HomeTabletPage();
		String currentDate = TimeManager.getCurrentDate("MMMM d YYY, HH:mm");
		search = home.clickSearchBtn();
		Assert.assertTrue(search.getTimeLineDate().contains(currentDate));
	}
	
	@AfterMethod
	public void toHome() {
		search.clickBackBtn();
	}
}
