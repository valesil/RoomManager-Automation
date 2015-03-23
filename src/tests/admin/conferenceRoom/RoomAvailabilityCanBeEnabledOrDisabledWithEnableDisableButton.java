package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * Test Case # 12 and # 13 Conference Rooms
 * @author Juan Carlos Guevara
 * Verify that a room availability can be disable when enable/disable button is clicked in 
 * a conference room window
 * Verify that a room availability can be enable when enable/disable button is clicked in 
 * conference room window
 */
public class RoomAvailabilityCanBeEnabledOrDisabledWithEnableDisableButton {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");

	@Test(groups = {"ACCEPTANCE"})
	public void testRoomAvailabilityCanBeDisabled() throws InterruptedException,
	BiffException, IOException {

		//disabling a room 
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage confRoomsPage = homeAdminPage.clickConferenceRoomsLink();
		if(confRoomsPage.stateEnableDisableBtn(roomName) == true) {
			confRoomsPage.enableDisableIcon(roomName);
		} else {

			//Verify that room is disable
			Assert.assertTrue(confRoomsPage.stateEnableDisableBtn(roomName));
		}
	}

	@Test(groups = {"ACCEPTANCE"})
	public void testRoomAvailabilityCanBeEnabled() throws InterruptedException,
	BiffException, IOException {

		//Enabling room
		RoomsPage confRoomsPage = new RoomsPage();
		if(confRoomsPage.stateEnableDisableBtn(roomName) == false) {
			confRoomsPage.enableDisableIcon(roomName);
		} else {

			//Verify that room is enabled
			Assert.assertTrue(confRoomsPage.stateEnableDisableBtn(roomName));
		}
	}
}
