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
 * TC18: Verify that the tool allows blank space between text in displayed name of room
 * TC19: Verify that the tool allows blank space between text in code of room
 * @author Marco Llano
 */
public class RoomAllowsBlankSpaceBetweenTextForDisplayNameAndCode {
	private RoomsPage roomsPage;
	private RoomInfoPage infoPage;
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String roomDisplayName = testData.get(0).get("Room Name");

	@Test(groups = {"NEGATIVE"})
	public void testRoomAllowsBlankSpaceBetweenTextForDisplayNameAndCode() {
		HomeAdminPage home = new HomeAdminPage();
		roomsPage = home.clickConferenceRoomsLink();			

		//Insert blank space between text in room display name and room code
		roomsPage.clickConferenceRoomsLink();
		infoPage = roomsPage.doubleClickOverRoomName(roomDisplayName);
		roomsPage = infoPage.setDisplayName(roomDisplayName + " " + roomDisplayName)
				.setRoomCode(roomDisplayName + " " + roomDisplayName)
				.clickSaveBtn();
		infoPage = roomsPage.doubleClickOverRoomName(roomDisplayName + " " + roomDisplayName);

		//Assertion for TC18		
		Assert.assertTrue(infoPage.getRoomDisplayName().contains(roomDisplayName + " " + roomDisplayName));

		//Assertion for TC19
		Assert.assertEquals(infoPage.getRoomCode(), roomDisplayName + " " + roomDisplayName);
	}

	@AfterMethod
	public void afterMethod() {	
		roomsPage = infoPage.setDisplayName(roomDisplayName).clickSaveBtn();
	}
}