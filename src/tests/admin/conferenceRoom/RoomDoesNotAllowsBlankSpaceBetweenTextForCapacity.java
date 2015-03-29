package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC20: Verify that the tool does not allows blank space between text in capacity of room
 * @author Marco Llano
 */
public class RoomDoesNotAllowsBlankSpaceBetweenTextForCapacity {
	private RoomsPage roomsPage;
	private RoomInfoPage roomInfoPage;

	@Test(groups = "NEGATIVE")
	public void testRoomDoesNotAllowsBlankSpaceBetweenTextForCapacity() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		roomsPage = homeAdminPage.clickConferenceRoomsLink();
		
		//ExcelReader is used to read rooms data
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> roomsDataList = excelReader.getMapValues("Resources");
		String roomName = roomsDataList.get(2).get("Room Name");
		String roomCapacity = roomsDataList.get(2).get("Value");

		//Insert blank space between text in room capacity
		roomsPage.clickConferenceRoomsLink();
		roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		roomsPage = roomInfoPage.setRoomCapacity(roomCapacity + " " + roomCapacity)
				.clickSaveBtn();

		//Verify if exist blank space between text for room display name
		roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		Assert.assertNotSame(roomInfoPage.getRoomCode(), roomCapacity + " " + roomCapacity);
	}
}