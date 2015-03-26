package tests.tablet.homeUI;

import static tests.tablet.homeUI.HomeTabletSettings.createMeetingFromExcel;
import static tests.tablet.homeUI.HomeTabletSettings.getAuthenticationValue;
import static tests.tablet.homeUI.HomeTabletSettings.getMeetingSubject;
import static tests.tablet.homeUI.HomeTabletSettings.getRoomDisplayName;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;

/**
 * This TCs verify that {now} tile is set whit correct information
 * when a meeting created in the current time.
 * TC01: Meeting subject.
 * TC02: Meeting organizer.
 * TC31: Now tile is reed.
 * TC30: Next tile is green.
 * TC18: Available message is displayed in {Next} tile.
 * TC07: Interval displayed in next tile is: end time current meeting - 0:00.
 * @author Eliana Navia
 * 
 */
public class CurrentMeetingValuesVerifications {
	Logger log = Logger.getLogger(getClass());
	HomeTabletPage homeTabletPage = new HomeTabletPage();
	SchedulePage schedulePage;
	String actualMeetingSubject = getMeetingSubject(0);
	String actualMeetingOrganizer = "";
	String expectedStartTime = "";

	/**
	 * 1. Meeting in progress in the room.
	 * 2. Room free after meeting in progress.
	 */
	@BeforeClass
	public void init() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		createMeetingFromExcel(0);

		schedulePage.clickOverMeetingCreated(actualMeetingSubject);
		actualMeetingOrganizer = schedulePage.getMeetingOrganizerValue();
		expectedStartTime = schedulePage.getEndTimeTxtBoxValue().substring(0, 5);
		schedulePage.clickBackBtn();
	}

	/**
	 * [EN] Verify that meeting subject is displayed on Now Tile 
	 * when a meeting is in progress in the room.
	 * TC01
	 */
	@Test (groups = {"ACCEPTANCE"})
	public void testCurrentMeetingSubjectDisplayedOnNowTile() {

		String expectedMeetingSubject = homeTabletPage.getNowTileLbl();

		Assert.assertEquals(actualMeetingSubject, expectedMeetingSubject);
	}

	/**
	 * [EN] This test case verifies that meeting organizer is displayed on Now Tile 
	 * when a meeting is in progress in the room.
	 * TC02
	 */
	@Test (groups = {"ACCEPTANCE"})
	public void testCurrentMeetingOrganizerIsDisplayedOnNowTile() {

		String expectedMeetingOrganizer = homeTabletPage.getCurrentMeetingOrganizerLbl();

		Assert.assertEquals(actualMeetingOrganizer, expectedMeetingOrganizer);
	}

	/**
	 * [EN] Verify that now title changes the color to red 
	 * when a meeting is in progress in the room.
	 * TC31
	 */
	@Test (groups = {"ACCEPTANCE"})
	public void testBussyColorIsDisplayedOnNowTileWhenAMeetingIsInProgressInTheRoom() {

		String actualBusyColorValue = homeTabletPage.getNowTileColor();
		String expectedBusyColorValue = HomeTabletSettings.getNowBusyColorValue();

		Assert.assertEquals(actualBusyColorValue, expectedBusyColorValue);
	}

	/**
	 * [EN] This test case verifies that next title changes the color to green 
	 * when the room is free EOD after a meeting in progress in the room.
	 * TC30
	 */
	@Test (groups = {"ACCEPTANCE"})
	public void testAvailableColorIsDisplayedOnNextTileWhenAMeetingIsCurrentInTheRoomAndIsFreeEOD() {

		String actualAvailableColorValue = homeTabletPage.getNextTileColor();
		String expectedAvailableColorValue = HomeTabletSettings.getAvailableColorValue();

		Assert.assertEquals(actualAvailableColorValue, expectedAvailableColorValue);
	}

	/**
	 * [EN] This test case verifies that {Available} message is displayed on {Next} tile 
	 * when a meeting is occurring and the room has no more meetings scheduled until EOD.
	 * TC18
	 */
	@Test (groups = {"ACCEPTANCE"})
	public void testAvailableMessageDisplayedInNextTileWhenRoomIsFreeAfterAMeeting() {
		String actualNextTileValue = homeTabletPage.getNextTileLbl();
		String expectedNextTileValue = "Available";

		Assert.assertEquals(actualNextTileValue, expectedNextTileValue );
	}

	/**
	 * [EN] This test case verifies that interval of hours displayed on {Next} tape are 
	 * from end meeting time to midnight when a meeting is in progress in the room and 
	 * the room has no more meetings scheduled until EOD.
	 * TC07
	 */
	@Test (groups = {"ACCEPTANCE"})
	public void testIntervalTimeDisplayedForNextMeetingInRoomIsFromEndTimeCurrentMeetingToMidnight() {

		String actualStartTime = homeTabletPage.getStartTimeNextMeetingLbl();
		String actualEndTime = homeTabletPage.getEndTimeNextMeetingLbl();

		String expectedEndTime = "0:00";

		//Verify that actual start time of interval is equal to end time of current meeting.
		Assert.assertEquals(actualStartTime, expectedStartTime );

		//Verify that actual end time of interval is equal to 0:00.
		Assert.assertEquals(actualEndTime, expectedEndTime);
	}	

	/**
	 * Delete meeting by rest API.
	 */
	@AfterClass
	public void end() {
		PropertyConfigurator.configure("log4j.properties");
		try {
			RootRestMethods.deleteMeeting(getRoomDisplayName(), actualMeetingSubject, 
					getAuthenticationValue(0));
			log.info("The meeting:" + actualMeetingSubject + "has been deleted successfully.");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
