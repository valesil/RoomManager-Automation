package tests.admin.ConferenceRoom;

import org.testng.annotations.Test;

import framework.pages.admin.ConferenceRoomPage;

public class RoomAvailabilityCanBeEnabled {

	@Test(groups = {"Acceptance"})
	public void testRoomAvailabilityCanBeEnabled() {
	ConferenceRoomPage conferenceRoomPage = new ConferenceRoomPage();
	conferenceRoomPage.clickEnableDisableBtn();
	}
}
