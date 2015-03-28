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

import framework.common.UIMethods;
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
	private List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	private String displayName =testData1.get(0).get("DisplayName");// "room01 changed";	  	  
	private String capacity = testData1.get(0).get("Capacity");
	private String location = testData1.get(0).get("Location");
	private String roomCode = testData1.get(0).get("Code");
	private String empty = "";
	
	@Test(groups = {"FUNCTIONAL"})
	public void testSaveButtonSaveChangesMadeInRoomInfoPage() throws JSONException, 
	MalformedURLException, IOException {
		HomeAdminPage homePage = new HomeAdminPage();
		UIMethods.refresh();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf
			.setDisplayName(displayName)
			.setRoomCapacity(capacity)
			.setRoomCode(roomCode)
			.setLocation(location)
			.clickSaveBtn();
		
		//Assertion for TC23
		Assert.assertTrue(confRoomPage.verifyChangesMade(displayName));
	}
	
	@AfterTest
	public void postcondition() {
		RoomsPage rooms = new RoomsPage();
		RoomInfoPage roomInf = rooms.doubleClickOverRoomName(displayName);
		
		//clean display name and capacity with empty value
		roomInf
			.setDisplayName(displayName)
			.setRoomCode(empty)
			.setRoomCapacity(empty)
			.clickSaveBtn();
		UIMethods.refresh();
	}
}
