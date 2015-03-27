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
import framework.utils.readers.JsonReader;

/**
 * TC17: Verify that is allowed to change the display name of rooms for special characters. 
 * @author Marco Llano
 */
public class RoomAllowsSpecialCharactersForDisplayName {
	private RoomsPage roomsPage;
	private RoomInfoPage infoPage;
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String roomName = testData.get(0).get("Room Name");

	@Test(groups = {"NEGATIVE"})
	public void testRoomDoesNotAllowsBlankTextForDisplayName() {
		HomeAdminPage home = new HomeAdminPage();
		roomsPage = home.clickConferenceRoomsLink();
		JsonReader value = new JsonReader();
		String resourceFileJSON = "\\src\\tests\\Resource2.json";		
		String specialCharacters = value.readJsonFile("customName" , resourceFileJSON);

		//Set room capacity to blank text
		roomsPage.clickConferenceRoomsLink();
		infoPage = roomsPage.doubleClickOverRoomName(roomName);
		roomsPage = infoPage.setDisplayName(specialCharacters)
				.clickSaveBtn();
		infoPage = roomsPage.doubleClickOverRoomName(specialCharacters);

		//Assertion for TC17
		Assert.assertTrue(roomsPage.getRoomDisplayName(specialCharacters).contains(specialCharacters));		
	}

	@AfterMethod
	public void afterMethod() {	
		roomsPage = infoPage.setDisplayName(roomName)
				.clickSaveBtn();
	}
}