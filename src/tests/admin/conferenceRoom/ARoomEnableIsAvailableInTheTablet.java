package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC10:Verify that a enabled room is available in the tablet
 * @author Juan Carlos Guevara
 */
public class ARoomEnableIsAvailableInTheTablet {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");
	String roomName1 = testData.get(1).get("Room Name");

	@Test(groups = {"FUNCTIONAL"})
	public void testARoomEnableIsAvailableInTheTablet() throws InterruptedException{

		//Checking room status in Admin 
		HomeAdminPage homePage = new HomeAdminPage();

		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		if(!confRoomPage.stateEnableDisableBtn(roomName)) {
			confRoomPage.enableDisableIcon(roomName);
		} else {

			//Open tablet to see changes
			HomeTabletPage homeTablet = new HomeTabletPage();
			SettingsPage settingsPage = homeTablet.clickSettingsBtn();
			homeTablet = settingsPage.selectRoom(roomName);	
			SearchPage searchPage = homeTablet.clickSearchBtn();
			searchPage.clickCollapseAdvancedBtn();
			searchPage.setName(roomName);

			//Assertion for TC10 
			Assert.assertTrue(searchPage.roomIsDiplayed(roomName));
		}
	}

	@AfterClass
	public void postConditions() {

		//Enable room in admin
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage confRoomPage = homeAdminPage.clickConferenceRoomsLink();
		if(!confRoomPage.stateEnableDisableBtn(roomName)) {
			confRoomPage.enableDisableIcon(roomName);
		}		
	}
}
