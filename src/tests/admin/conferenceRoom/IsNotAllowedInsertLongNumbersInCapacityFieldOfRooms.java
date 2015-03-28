package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC13: Verify that is not allowed to insert long numbers in capacity field of rooms
 * @author Ruben Blanco
 *
 */
public class IsNotAllowedInsertLongNumbersInCapacityFieldOfRooms {
	
	//reading to excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	private String displayName =testData1.get(0).get("DisplayName");	  	  
	private String capacity = testData1.get(0).get("longValues");
	private String empty = "";
	
	@Test(groups = {"NEGATIVE"})
	public void testIsNotAllowedInsertLongNumbersInCapacityFieldOfRooms() {
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf
			.setRoomCapacity(capacity)
			.clickSaveBtn();
		confRoomPage.doubleClickOverRoomName(displayName);
		String newCapacity = roomInf.getRoomCapacity();
		
		//Assertion for TC13
		Assert.assertTrue(roomInf.capacityIsLong(newCapacity));
	}
	
	@AfterClass
	public void postcondition() {
		RoomInfoPage roomInf = new RoomInfoPage();
		
		//clean room capacity
		roomInf
			.setRoomCapacity(empty)
			.clickSaveBtn();
		UIMethods.refresh();
	}
	
}
