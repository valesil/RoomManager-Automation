package tests.tablet.homeUI;

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

public class SchedulePageIsDisplayedWhenNowTapeIsClicked {
	public HomePage home = new HomePage();
	public SchedulePage schedule = new SchedulePage();
	@Test(groups = {"UI"})
	public void testSchedulePageIsDisplayedWhenNowTapeIsClicked (){
		schedule = home.clickNowTileLbl();
		Assert.assertTrue(schedule.isSchedulerLblDisplayed());
	}
	@AfterClass
	public void toHome() {
		schedule.clickBackBtn();
	}
	

}
