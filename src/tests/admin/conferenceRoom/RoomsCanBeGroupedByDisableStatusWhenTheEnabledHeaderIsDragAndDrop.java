package tests.admin.conferenceRoomSuite;

import org.junit.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomsPage;

public class RoomsCanBeGroupedByDisableStatusWhenTheEnabledHeaderIsDragAndDrop {
	@Test
	public void testRoomsCanBeGroupedByDisableStatusWhenTheEnabledHeaderIsDragAndDrop() {
		
		HomeAdminPage home = new HomeAdminPage();
		RoomsPage room = home.clickConferenceRoomsLink();
		room.dragAndDropColumn("Enabled");
		Assert.assertTrue(room.IsGroupedByDisableStatus());
		
		/**
		 * TC to verify that rooms can be grouped by enable status when
		 * the [Enabled] header is drag a dropped into the group
		 * panel field
		 */
		Assert.assertTrue(room.IsGroupedByEnableStatus());
	}
}
