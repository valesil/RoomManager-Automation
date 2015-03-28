package tests.tablet.schedule;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;

/**
 * TC24: This test case verifies that double clicking a period in timeline 
 * creates a meeting box and sets the time for a new meeting with default duration.
 * @author Eliana Navia
 *
 */
public class DoubleClickingATimelinePeriodCreatesAMeetingBoxAndSetNewMeetingTime {
	HomeTabletPage homeTabletPage = new HomeTabletPage();
	SchedulePage schedulePage;

	@Test (groups = "UI")
	public void testDoubleClickingATimelinePeriodCreatesAMeetingBoxAndSetNewMeetingTime() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.doubleClickTimeLineGroup();
		
		//Verify that the duration time displayed in From and to are 30 that is the default value.
        Assert.assertEquals(schedulePage.getDurationOfMeetingByDefault(), 30);
	}
}
