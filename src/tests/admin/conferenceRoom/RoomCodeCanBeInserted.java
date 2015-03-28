package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC05: Verify that is allowed to insert a code for a room
 * @author Ruben Blanco
 *
 */
public class RoomCodeCanBeInserted {
	
	@Test(groups = "FUNCTIONAL")
	public void testRoomCodeCanBeInserted() {
		
		//reading to excel to create variables
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> roomList = excelReader.getMapValues("RoomInfo");
		String displayName = roomList.get(0).get("DisplayName");// "room01 changed";	  	  
		String roomCode = roomList.get(0).get("Code");

		//navigate to home admin page
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setRoomCode(roomCode)
			.clickSaveBtn();

		//get room code
		String getCode = roomsPage.doubleClickOverRoomName(displayName)
			.getRoomCode();
		roomInfoPage.clickCancelBtn();
		
		//Assertion for TC05
		Assert.assertEquals(roomCode, getCode);
	}
}
