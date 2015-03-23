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
 * @title Verify that a message is displayed if the start time is later than end time
 * @author Jose Cabrera
 */
public class AMessageIsDisplayedWhenStartTimeIsLaterThanEndTime {
	public HomePage home=new HomePage();
	public SchedulePage schedule=new SchedulePage();
	public SearchPage search=new SearchPage();

	@Test(groups = {"UI"})
	public void testMessageIsDisplayedStartTimeIsLaterThanEndTime (){
		schedule = home.clickScheduleBtn();
		schedule.setStartTimeDate("10:00 pm")
				.setEndTimeDate("9:00 pm")
				.clickCreateBtn();
		Assert.assertTrue(schedule.isMessageOfTimeErrorDisplayed());
	}

	@AfterClass
	public void toHome() {
		schedule.clickBackBtn();
	}
}
