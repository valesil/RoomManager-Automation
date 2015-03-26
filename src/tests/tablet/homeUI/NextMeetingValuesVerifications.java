package tests.tablet.homeUI;

import static tests.tablet.homeUI.HomeTabletSettings.createMeetingFromExcel;


import static tests.tablet.homeUI.HomeTabletSettings.getAuthenticationValue;
import static tests.tablet.homeUI.HomeTabletSettings.getMeetingSubject;
import static tests.tablet.homeUI.HomeTabletSettings.getRoomDisplayName;
import static framework.utils.TimeManager.differenceBetweenTimes;
import static framework.utils.TimeManager.getCurrentDate;

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
 * TC06 -> Interval hours of next meeting.
 * TC03 -> Time left.
 * TC32 -> Busy color of next tile.
 * @author Eliana Navia
 */
public class NextMeetingValuesVerifications {
	Logger log = Logger.getLogger(getClass());
	HomeTabletPage homeTabletPage = new HomeTabletPage();
	SchedulePage schedulePage;
	String nextMeetingSubject = getMeetingSubject(1);
	String expectedStartTime = "";
	String expectedEndTime = ""; 

	/**
	 * Meeting scheduled in the room.
	 */
	@BeforeClass
	public void init() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		createMeetingFromExcel(1);
		schedulePage.clickOverMeetingCreated(nextMeetingSubject);
		expectedStartTime =  schedulePage.getStartTimeTxtBoxValue().substring(0, 5);
		expectedEndTime =  schedulePage.getEndTimeTxtBoxValue().substring(0, 5);
		schedulePage.clickBackBtn();	
	}

	/**
	 * [EN] This test case verifies that interval of beginning-end time 
	 * of next meeting scheduled for the room is displayed in the {Next} Tape.
	 * TC06
	 */
	@Test (groups = {"ACCEPTANCE"})
	public void testIntervalTimeDisplayedForNextMeetingInRoom() {

		String actualStartTime = homeTabletPage.getStartTimeNextMeetingLbl();	
		String actualEndTime = homeTabletPage.getEndTimeNextMeetingLbl();

		Assert.assertEquals(actualStartTime, expectedStartTime );
		Assert.assertEquals(actualEndTime, expectedEndTime );
	}

	/**
	 * [EN] This test case verifies that time left displayed in {Now}Tape 
	 * is from current time until next meeting when any meeting is in progress.
	 * TC03
	 */
	@Test (groups = {"ACCEPTANCE"})
	public void testTimeLeftDisplayTimeUntilNextMeetingWhenRoomIsFreeBeforeInNowTape() {

		String expectedTimeLeft = differenceBetweenTimes(getCurrentDate("HH:mm"),
				expectedStartTime);
		String actualTimeLeft = homeTabletPage.getTimeLeftLbl();

		//Current time - start time of next meeting
		Assert.assertEquals(actualTimeLeft, expectedTimeLeft );
	}

	/**
	 * [EN] Verify that next title changes the color 
	 * when a meeting is set to occur after some minutes of current time in the room.
	 * TC32
	 */
	@Test (groups = {"ACCEPTANCE"})
	public void testBussyColorIsDisplayedOnNextTileWhenAMeetingIsSetInTheRoom() {

		String actualBusyColorValue = homeTabletPage.getNextTileColor();
		String expectedBusyColorValue = HomeTabletSettings.getNextBusyColorValue();

		Assert.assertEquals(actualBusyColorValue, expectedBusyColorValue);
	}

	/**
	 * Delete meeting by rest API.
	 */
	@AfterClass
	public void end() {
		PropertyConfigurator.configure("log4j.properties");
		try {
			RootRestMethods.deleteMeeting(getRoomDisplayName(), nextMeetingSubject, 
					getAuthenticationValue(0));
			log.info("The meeting:" + nextMeetingSubject + "has been deleted successfully.");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
