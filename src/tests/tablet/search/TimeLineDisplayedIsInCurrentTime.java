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
import framework.utils.TimeManager;

/**
 * @title Verify the red line in the timeline is in the current time 
 * @author Jose Cabrera
 */
public class TimeLineDisplayedIsInCurrentTime {
	public HomePage home = new HomePage();
	public SearchPage search = new SearchPage();
	@Test(groups = {"UI"})
	public void testTimeDisplayedIsTheCurrent (){
		String currentDate = TimeManager.getCurrentDate("MMMM d YYY, HH:mm");
		search = home.clickSearchBtn();
		System.out.println(currentDate + search.getTimeLineDate());
		Assert.assertTrue(search.getTimeLineDate().contains(currentDate));
	}
	
	@AfterClass
	public void toHome() {
		search.clickBackBtn();
	}
	
	
}
