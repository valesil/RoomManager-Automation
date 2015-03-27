package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
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
	private RoomInfoPage infoPage;

	@Test(groups = {"NEGATIVE"})
	public void testRoomDoesNotAllowsBlankSpaceBetweenTextForCapacity() {
		HomeAdminPage home = new HomeAdminPage();
		roomsPage = home.clickConferenceRoomsLink();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Resources");
		String roomDisplayName = testData.get(2).get("Room Name");
		String roomCapacity = testData.get(2).get("Value");

		//Insert blank space between text in room capacity
		roomsPage.clickConferenceRoomsLink();
		infoPage = roomsPage.doubleClickOverRoomName(roomDisplayName);
		roomsPage = infoPage.setRoomCapacity(roomCapacity + " " + roomCapacity)
				.clickSaveBtn();

		//Verify if exist blank space between text for room display name
		infoPage = roomsPage.doubleClickOverRoomName(roomDisplayName);
		Assert.assertNotSame(infoPage.getRoomCode(), roomCapacity + " " + roomCapacity);
	}

	@AfterMethod
	public void afterMethod() {	
		roomsPage = infoPage.clickCancelBtn();
	}
}