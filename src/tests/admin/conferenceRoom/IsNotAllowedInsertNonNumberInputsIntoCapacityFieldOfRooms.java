package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
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
	
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	String displayName =testData1.get(0).get("DisplayName");	  	  
	String capacity = testData1.get(0).get("invalidCapacity");
	
	@BeforeClass
	private void precondition() {
		UIMethods.refresh();
	}
	
	@Test(groups = {"NEGATIVE"})
	public void testIsNotAllowedInsertNonNumberInputsIntoCapacityFieldOfRooms() {
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink()
				.clickResourcesLink().clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf.setRoomCapacity(capacity);
		String newCapacity = roomInf.getRoomCapacity();
		roomInf.clickCancelBtn();
		Assert.assertNotEquals(capacity,newCapacity);
	}
}
