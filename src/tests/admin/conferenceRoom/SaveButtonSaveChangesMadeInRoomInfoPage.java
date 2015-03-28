package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.junit.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC23: Verify that the save button save the changes made in room info window
 * @author Ruben Blanco
 * 
 */
public class SaveButtonSaveChangesMadeInRoomInfoPage {

	//reading to excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomList = excelReader.getMapValues("RoomInfo");
	private String displayName =roomList.get(0).get("DisplayName");// "room01 changed";	  	  
	private String capacity = roomList.get(0).get("Capacity");
	private String location = roomList.get(0).get("Location");
	private String roomCode = roomList.get(0).get("Code");
	private String empty = "";
	
	@Test(groups = "FUNCTIONAL")
	public void testSaveButtonSaveChangesMadeInRoomInfoPage() throws JSONException, 
	MalformedURLException, IOException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setDisplayName(displayName)
			.setRoomCapacity(capacity)
			.setRoomCode(roomCode)
			.setLocation(location)
			.clickSaveBtn();
		
		//Assertion for TC23
		Assert.assertTrue(roomsPage.verifyChangesMade(displayName));
	}
	
	@AfterTest(groups = "FUNCTIONAL")
	public void postcondition() {
		RoomsPage roomsPage = new RoomsPage();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		
		//clean display name and capacity with empty value
		roomInfoPage.setDisplayName(displayName)
			.setRoomCode(empty)
			.setRoomCapacity(empty)
			.clickSaveBtn();
	}
}
