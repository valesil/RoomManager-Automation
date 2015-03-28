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
 * TC11: Verify that room information (name, display name) by default 
 * are displayed in conference room window
 * @author Ruben Blanco
 * 
 */
public class DefaultRoomInformationAreDisplayedInConferenceRoomPage {
	
	@Test(groups = {"FUNCTIONAL"})
	public void testDefaultRoomInfomationAreDisplayedInConferenceRoomPage() {
		//reading to excel to create variables
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
		String displayName = testData1.get(0).get("DisplayName");
		
		HomeAdminPage homeAdminPage = new HomeAdminPage();	
		RoomsPage roomsPage = homeAdminPage.clickResourcesLink()
			.clickConferenceRoomsLink()
			.clickResourcesLink()
			.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		
		//Assertion for TC11
		Assert.assertTrue(roomInfoPage.verifyRoomDefaultValues(displayName));
	}
	
	@AfterClass(groups = {"FUNCTIONAL"})
	public void postcondition() {
		RoomInfoPage roomInfoPage = new RoomInfoPage();
		roomInfoPage.clickCancelBtn();
		UIMethods.refresh();		
	}
	
}
