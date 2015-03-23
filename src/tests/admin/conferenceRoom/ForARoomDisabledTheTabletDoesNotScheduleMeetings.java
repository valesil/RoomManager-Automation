package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;

/**
 * Test Case # 24
 * @author Juan Carlos Guevara
 * Verify that when a room status is disabled, the tablet does not schedule meetings
 */
public class ForARoomDisabledTheTabletDoesNotScheduleMeetings {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(1).get("Room Name");

	@Test(groups = {"FUNCTIONAL"})
	public void testForARoomDisabledTheTabletDoesNotScheduleMeetings(){

		//Disabling room in Admin 
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		if(confRoomPage.stateEnableDisableBtn(roomName) == true) {
			confRoomPage.enableDisableIcon(roomName);
		} else {

			//Open tablet to see changes
			SettingsPage setting = new SettingsPage();		
			HomePage home = setting.selectRoom(testData.get(0).get("Room Name"));
			SearchPage searchPage = home.clickSearchBtn();
			searchPage.clickCollapseAdvancedBtn();
			searchPage.setName(roomName);

			//Verify room status
			Assert.assertFalse(searchPage.roomIsDiplayed(roomName));
		}
	}

	@AfterClass
	public void postConditions() throws BiffException, IOException {

		//Enable room in admin
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		homeAdminPage.getAdminHome();
		UIMethods.refresh();
		RoomsPage confRoomsPage = homeAdminPage.clickConferenceRoomsLink();
		if(confRoomsPage.stateEnableDisableBtn(roomName) == false) {
			confRoomsPage.enableDisableIcon(roomName);
		}		
	}
}
