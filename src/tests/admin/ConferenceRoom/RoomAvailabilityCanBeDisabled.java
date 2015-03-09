package tests.admin.ConferenceRoom;

import org.testng.annotations.Test;

import framework.pages.admin.ConferenceRoomPage;

/**
 * 
 * @author administrator
 * Verify that a room availability can be disable when enable/disable button is clicked in 
 * aconference room window
 */
public class RoomAvailabilityCanBeDisabled {

	@Test(groups = {"Acceptance"})
	public void testRoomAvailabilityCanBeDisabled() {
	ConferenceRoomPage conferenceRoomPage = new ConferenceRoomPage();
	conferenceRoomPage.clickEnableDisableBtn();
	}
}
