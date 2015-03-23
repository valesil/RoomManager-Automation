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
import framework.utils.TimeManager;

/**
 * @title Verify the red line in the timeline is in the current time 
 * @author Jose Cabrera
 */
public class TheRedLineInTheTimelineIsInTheCurrentTime {
	public HomePage home = new HomePage();
	public SchedulePage schedule = new SchedulePage();

	@Test(groups = {"UI"})
	public void testRedLineInTheTimelineIsInTheCurrentTime (){
		String currentDate = TimeManager.getCurrentDate("MMMM d YYY, HH:mm");
		schedule = home.clickScheduleBtn();
		System.out.println(schedule.getTimeLineDate()+currentDate);
		Assert.assertTrue(schedule.getTimeLineDate().contains(currentDate));
	}

	@AfterClass
	public void toHome() {
		schedule.clickBackBtn();
	}
}
