package tests.tablet.homeUI;

import static framework.utils.TimeManager.getCurrentDate;
import static framework.utils.TimeManager.differenceBetweenTimes;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.utils.TimeManager;

/**
 * This TCs verify that the information displayed is correct in the home page when it is loaded.
 * TC N°: 27 -> Room display name.
 * TC N°: 28 -> Room code.
 * TC N°: 33 -> Current time.
 * TC N°: 19 -> Available message in {Now} tile.
 * TC N°: 29 -> Color of {Now} tile.
 * TC N°: 17 -> End of day message in {Next} tile.
 * TC N°: 05 -> Time left display the time remaining form current time to midnight.
 * @author Eliana Navia
 * 
 */
public class HomePageInformation {

	/**
	 * [EN] This test case verifies that room display name is displayed 
	 * in the main container window.
	 * TC N°: 27
	 */
	@Test (groups = {"UI"})
	public void testRoomNameDisplayedInMainWindow() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();

		String actualRoomDisplayName = homeTabletPage.getRoomDisplayNameLbl();
		String expectedRoomDisplayName = HomeTabletSettings.getRoomDisplayName();

		//Verify that actual room display name is equal to expected.
		Assert.assertEquals(actualRoomDisplayName, expectedRoomDisplayName );
	}	

	/**
	 *[EN] This test case verifies that the code of a room is displayed in the main window.
	 * TC N°: 28
	 */
	@Test (groups = {"UI"})
	public void testRoomCodeDisplayedInMainWindow() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();

		String actualRoomCode = homeTabletPage.getRoomCodeLbl();
		String expectedRoomCode = HomeTabletSettings.getRoomCodeValue();

		//Verify that actual room code is equal to expected.
		Assert.assertEquals(actualRoomCode, expectedRoomCode);
	}	

	/**
	 * [EN] This test case verifies that current time is  displayed 
	 * in the main container window.
	 * TC N°: 33
	 */
	@Test (groups = {"UI"})
	public void testCurrentTimeIsDisplayedInMainWindow() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();

		String actualCurrentTime = homeTabletPage.getCurrentTimeLbl();
		String expectedCurentTime = TimeManager.getCurrentDate("HH:mm");

		//Verify that current time is displayed.
		Assert.assertEquals(actualCurrentTime, expectedCurentTime );
	}

	/**
	 * [EN] This test case verifies that "Available" message is displayed 
	 * in {Now} tile when any meeting is in progress in the room.
	 * TC N°: 19
	 */
	@Test (groups = {"UI"})
	public void testAvailableMessageDisplayedWhenRoomIsFreeEOD() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();

		String actualNowTileValue = homeTabletPage.getNowTileLbl();
		String expectedNowTileValue = "Available";

		Assert.assertEquals(actualNowTileValue, expectedNowTileValue );
	}

	/**
	 * [EN] This test case verifies that now title is green color 
	 * when any room is in progress in the room.
	 * TC N°: 29
	 */
	@Test (groups = {"UI"})
	public void testAvailableColorIsDisplayedInNowTileWhenAnyMeetingIsInProgress() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();

		String actualTimeLeft = homeTabletPage.getTimeLeftLbl();
		String expectedTimeLeft = differenceBetweenTimes(getCurrentDate("HH:mm"), "23:59");

		//Verify that the difference of current time - midnight is correct.
		Assert.assertEquals(actualTimeLeft, expectedTimeLeft );
	}

	/**
	 * [EN] This test case verifies that "End of day" message is displayed 
	 * in {Next} tile when any meeting is in progress and the room is free EOD.
	 * TC N°: 17
	 */
	@Test (groups = {"UI"})
	public void testEndOfDayMessageIsDisplayedWhenRoomIsFreeEOD() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();

		String actualNextTileValue = homeTabletPage.getNextTileLbl();
		String expectedNextTileValue = "End of day";

		Assert.assertEquals(actualNextTileValue, expectedNextTileValue );
	}

	/**
	 * [EN] This test case verifies that time left displayed in {Now} Tape 
	 * is the amount of hours reaming until 23:59 when any meeting is in 
	 * progress and the room is free EOD.
	 * TC N°: 05
	 */
	@Test (groups = {"UI"})
	public void testTimeLeftUntilMidnightWhenRoomIsFreeAfter() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();

		String actualTimeLeft = homeTabletPage.getTimeLeftLbl();
		String expectedTimeLeft = differenceBetweenTimes(getCurrentDate("HH:mm"), "23:59");

		//Verify that the difference of current time - midnight is correct.
		Assert.assertEquals(actualTimeLeft, expectedTimeLeft );
	}
}
