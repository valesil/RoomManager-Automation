package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC12: Verify that a room availability can be disable when enable/disable button is clicked in 
 * a conference room window 
 * TC13: Verify that a room availability can be enable when enable/disable button is clicked in 
 * conference room window
 * @author Juan Carlos Guevara
 */
public class RoomAvailabilityCanBeEnabledOrDisabledWithEnableDisableButton {
	
	//Read resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String roomName = testData.get(2).get("Room Name");

	@Test(groups = "ACCEPTANCE")
	public void testRoomAvailabilityCanBeEnabledOrDisabledWithEnableDisableButton() {

		//Disable a room 
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage confRoomsPage = homeAdminPage.clickConferenceRoomsLink();	
		confRoomsPage.enableDisableIcon(roomName);

		//Assertion for TC12 
		Assert.assertTrue(confRoomsPage.stateEnableDisableBtn(roomName));
		confRoomsPage.enableDisableIcon(roomName);

		//Assertion for TC13 
		Assert.assertTrue(confRoomsPage.stateEnableDisableBtn(roomName));

	}
}
