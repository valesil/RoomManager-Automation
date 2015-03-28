package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC06: Verify that is allowed to insert a code for a room
 * @author Ruben Blanco
 *
 */
public class RoomCapacityCanBeInserted {

	@Test(groups = {"FUNCTIONAL"})
	public void testRoomCapacityCanBeInserted()  {
		//reading to excel to create variables
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
		String displayName =testData1.get(0).get("DisplayName");// "room01 changed";	  	  
		String capacity = testData1.get(0).get("Capacity");

		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf
			.setRoomCapacity(capacity)
			.clickSaveBtn();

		String roomCapacity = confRoomPage
			.doubleClickOverRoomName(displayName)
			.getRoomCapacity();
		roomInf.clickCancelBtn();
		
		//Assertion fot TC06
		Assert.assertEquals(capacity, roomCapacity);
	}
}
