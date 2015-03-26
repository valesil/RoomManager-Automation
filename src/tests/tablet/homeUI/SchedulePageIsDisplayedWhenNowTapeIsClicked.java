package tests.tablet.homeUI;

/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */

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
	public void testSchedulePageIsDisplayedWhenNowTapeIsClicked (){
		HomeTabletPage home = new HomeTabletPage();
		SchedulePage schedule = home.clickNowTileLbl();
		Assert.assertTrue(schedule.isSchedulerLblDisplayed());
	}
	
	@AfterClass
	public void toHome() {
		SchedulePage schedule = new SchedulePage();
		schedule.clickBackBtn();
	}
}
