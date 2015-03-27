package tests.tablet.homeUI;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;

/**
 * @title  TC21: Verify that schedule page is displayed when Now Tape is clicked when no 
 * event is scheduled
 * @author Jose Cabrera
 */
public class SchedulePageIsDisplayedWhenNowTapeIsClicked {
		
	@Test(groups = "UI")
	public void testSchedulePageIsDisplayedWhenNowTapeIsClicked () {
		HomeTabletPage homePage = new HomeTabletPage();
		SchedulePage schedulePage = homePage.clickNowTileLbl();
		Assert.assertTrue(schedulePage.isSchedulerLblDisplayed());
	}
	
	@AfterClass
	public void toHome() {
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();
	}
}
