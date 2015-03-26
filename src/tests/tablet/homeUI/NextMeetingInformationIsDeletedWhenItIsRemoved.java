package tests.tablet.homeUI;

import static tests.tablet.homeUI.HomeTabletSettings.createMeetingFromExcel;
import static tests.tablet.homeUI.HomeTabletSettings.getPasswordValue;
import static tests.tablet.homeUI.HomeTabletSettings.getMeetingSubject;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;

/**
 * TC N° 16 -> This test case verifies that the next meeting information is removed of Next Tile 
 * when it is deleted.
 * @author Eliana Navia
 * 
 */
public class NextMeetingInformationIsDeletedWhenItIsRemoved {
	HomeTabletPage homeTabletPage = new HomeTabletPage();
	SchedulePage schedulePage;
	String nextMeetingSubject = getMeetingSubject(1);
	String password = getPasswordValue(1);

	@Test (groups = {"ACCEPTANCE"})
	public void testNextMeetingInformationIsDeletedWhenItIsRemoved() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		createMeetingFromExcel(1);

		schedulePage.clickBackBtn();
		String expectedMeetingValue = homeTabletPage.getNextTileLbl();

		//Verify that next meeting subject is displayed in {next} to be sure that was created.
		Assert.assertEquals(nextMeetingSubject, expectedMeetingValue);

		homeTabletPage.clickScheduleBtn();
		schedulePage.deleteMeeting(nextMeetingSubject, password);
		schedulePage.clickBackBtn();

		expectedMeetingValue = homeTabletPage.getNextTileLbl();

		//Verify that the next meeting subject is not displayed in {next} tile. 
		Assert.assertNotEquals(nextMeetingSubject, expectedMeetingValue);	
	}
}
