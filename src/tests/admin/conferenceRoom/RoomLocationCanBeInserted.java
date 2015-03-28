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
 * TC07: Verify that is allowed to insert a code for a room
 * @author Ruben Blanco
 *
 */
public class RoomLocationCanBeInserted {
	
	@Test(groups = {"FUNCTIONAL"})
	public void testRoomLocationCanBeInserted() {
		//reading to excel to create variables
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
		String displayName =testData1.get(0).get("DisplayName");	  	  
		String location = testData1.get(0).get("Location");
		
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf
			.setLocation(location)
			.clickSaveBtn();
		
		String roomLocation = confRoomPage
			.doubleClickOverRoomName(displayName)
			.getRoomLocation();  
		roomInf.clickCancelBtn();
		
		//Assertion for TC07
		Assert.assertEquals(location, roomLocation);
	}
}
