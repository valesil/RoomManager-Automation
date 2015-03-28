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
 * TC06: Verify that is allowed to insert a code for a room
 * @author Ruben Blanco
 *
 */
public class RoomCapacityCanBeInserted {

	@Test(groups = "FUNCTIONAL")
	public void testRoomCapacityCanBeInserted()  {
		
		//reading to excel to create variables
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> roomList = excelReader.getMapValues("RoomInfo");
		String displayName = roomList.get(0).get("DisplayName");// "room01 changed";	  	  
		String capacity = roomList.get(0).get("Capacity");

		//navigate to home admin page
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setRoomCapacity(capacity)
			.clickSaveBtn();

		//get room capacity 
		String roomCapacity = roomsPage.doubleClickOverRoomName(displayName)
			.getRoomCapacity();
		roomInfoPage.clickCancelBtn();
		
		//Assertion fot TC06
		Assert.assertEquals(capacity, roomCapacity);
	}
}
