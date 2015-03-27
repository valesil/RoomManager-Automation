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
 * TC15: Verify that the tool allows blank text for Room Capacity
 * TC16: Verify that the tool allows blank text for Room Code
 * @author Marco Llano
 */
public class RoomAllowsBlankTextForRoomCodeAndCapacity {
	private RoomsPage roomsPage;
	private RoomInfoPage infoPage;

	@Test(groups = {"NEGATIVE"})
	public void testRoomAllowsBlankTextForRoomCodeAndCapacity() {
		HomeAdminPage home = new HomeAdminPage();
		roomsPage = home.clickConferenceRoomsLink();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Resources");
		String roomName = testData.get(0).get("Room Name");

		//Set room capacity to blank text
		roomsPage.clickConferenceRoomsLink();
		infoPage = roomsPage.doubleClickOverRoomName(roomName);
		roomsPage = infoPage.setRoomCapacity("").setRoomCode("").clickSaveBtn();
		infoPage = roomsPage.doubleClickOverRoomName(roomName);

		//Assertion for TC15
		Assert.assertEquals(infoPage.getRoomCapacity(), "");

		//Assertion for TC16
		Assert.assertEquals(infoPage.getRoomCode(), "");		
	}

	@AfterMethod
	public void afterMethod() {	
		roomsPage = infoPage.clickCancelBtn();
	}
}
