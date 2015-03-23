package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;

/**
 * Test Case # 10
 * @author Juan Carlos Guevara
 * Verify that a enabled room is available in the tablet
 */
public class ARoomEnableIsAvailableInTheTablet {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");
	String roomName1 = testData.get(1).get("Room Name");
	
	@Test(groups = {"FUNCTIONAL"})
	public void testARoomEnableIsAvailableInTheTablet(){
		
		//Checking room status in Admin 
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		if(confRoomPage.stateEnableDisableBtn(roomName) == false) {
			confRoomPage.enableDisableIcon(roomName);
		} else {
			
		//Open tablet to see changes
		SettingsPage setting = new SettingsPage();		
		HomePage home = setting.selectRoom(roomName1);
		SearchPage searchPage = home.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();
		searchPage.setName(roomName);
		
		//Verify room status
		Assert.assertTrue(searchPage.roomIsDiplayed(roomName));
		}
	}
	
	@AfterClass
	public void postConditions() throws BiffException, IOException {
		
		//Enable room in admin
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		homeAdminPage.getAdminHome();
		RoomsPage confRoomPage = homeAdminPage.clickConferenceRoomsLink();
		if(confRoomPage.stateEnableDisableBtn(roomName) == false) {
			confRoomPage.enableDisableIcon(roomName);
		}		
	}
}
