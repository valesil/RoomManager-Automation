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
 * TC14: Verify that is not allowed to insert non-number inputs into the 
 * capacity field of rooms
 * @author Ruben Blanco
 *
 */
public class IsNotAllowedInsertNonNumberInputsIntoCapacityFieldOfRooms {
	
	//reading to excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	private String displayName =testData1.get(0).get("DisplayName");	  	  
	private String capacity = testData1.get(0).get("invalidCapacity");
	
	@Test(groups = {"NEGATIVE"})
	public void testIsNotAllowedInsertNonNumberInputsIntoCapacityFieldOfRooms() {
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink()
			.clickResourcesLink().clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf.setRoomCapacity(capacity);
		
		//get the new room capacity
		String newCapacity = roomInf.getRoomCapacity();
		roomInf.clickCancelBtn();
		
		//Assertion for TC14
		Assert.assertNotEquals(capacity, newCapacity);
	}
	
	@AfterClass
	private void precondition() {
		UIMethods.refresh();
	}
}
