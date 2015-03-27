package tests.tablet.schedule;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.utils.TimeManager;

/**
 * TC38: Verify the red line in the timeline is in the current time 
 * @author Jose Cabrera
 */
public class TheRedLineInTheTimelineIsInTheCurrentTime {
	SchedulePage schedulePage;
	
	@Test(groups = "UI")
	public void testRedLineInTheTimelineIsInTheCurrentTime (){
		String currentDate = TimeManager.getCurrentDate("MMMM d YYY, HH:mm");
		HomeTabletPage homePage = new HomeTabletPage();
		schedulePage = homePage.clickScheduleBtn();
		Assert.assertTrue(schedulePage.getTimeLineDate().contains(currentDate));
	}

	@AfterClass
	public void toHome() {
		schedulePage.clickBackBtn();
	}
}
