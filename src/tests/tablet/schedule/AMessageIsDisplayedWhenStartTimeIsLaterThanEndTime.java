package tests.tablet.schedule;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SearchPage;

/**
 * @title  TC41: Verify that a message is displayed if the start time is later than end time
 * @author Jose Cabrera
 */
public class AMessageIsDisplayedWhenStartTimeIsLaterThanEndTime {
	SchedulePage schedulePage;
	SearchPage searchPage;

	@Test(groups = "UI")
	public void testMessageIsDisplayedStartTimeIsLaterThanEndTime() {
		HomeTabletPage homePage = new HomeTabletPage();
		schedulePage = homePage.clickScheduleBtn();
		schedulePage.setStartTimeDate("10:00 pm")
				.setEndTimeDate("9:00 pm")
				.clickCreateBtn();
		Assert.assertTrue(schedulePage.isMessageOfTimeErrorDisplayed());
	}

	@AfterClass
	public void toHome() {
		schedulePage.clickBackBtn();
	}
}
