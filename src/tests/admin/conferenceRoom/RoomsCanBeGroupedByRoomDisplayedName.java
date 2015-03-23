package tests.admin.conferenceRoomSuite;

import org.junit.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomsPage;

public class RoomsCanBeGroupedByRoomDisplayedName {
	@Test
	public void testRoomsCanBeGroupedByRoomDisplayedName() {
		HomeAdminPage home = new HomeAdminPage();
		home.getAdminHome();
		RoomsPage room = home.clickConferenceRoomsLink();
		room.dragAndDropColumn("Room");
		Assert.assertTrue(room.IsGroupedByRoom());
	}
}
