package suits;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

public class SaveButtonSaveChangesMadeInRoomInfoPage {

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	String displayName =testData1.get(0).get("DisplayName");// "room01 changed";	  	  
	String capacity = testData1.get(0).get("Capacity");
	String location = testData1.get(0).get("Location");
	String roomCode = testData1.get(0).get("Code");

	@Test
	public void testSaveButtonSaveChangesMadeInRoomInfoPage() {

		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf.setDisplayName(displayName)
		.setRoomCapacity(capacity)
		.setRoomCode(roomCode)
		.setLocation(location)
		.clickSaveBtn();
		Assert.assertTrue(confRoomPage.changesWasSaved(displayName));
	}
	
	@AfterTest
	public void postcondition() {
		
		RoomsPage rooms = new RoomsPage();
		RoomInfoPage roomInf = rooms.doubleClickOverRoomName(displayName);
		roomInf.setDisplayName(displayName)
		.setRoomCode("")
		.setRoomCapacity("")
		.clickSaveBtn();
	}
}
