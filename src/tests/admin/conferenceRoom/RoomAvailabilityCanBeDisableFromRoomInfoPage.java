package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC01: Verify that a room availability can be disable from room info window
 * @author Ruben Blanco
 *
 */
public class RoomAvailabilityCanBeDisableFromRoomInfoPage {
	
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData2 = excelReader.getMapValues("RoomInfo");
	String displayName = testData2.get(0).get("DisplayName");
	String auxRoom = testData2.get(1).get("DisplayName");
	
	@Test(groups = {"FUNCTIONAL"})
	public void testRoomAvailabilityCanBeDisableFromRoomInfoPage() {
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage conferencePage = homePage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = conferencePage.doubleClickOverRoomName(displayName);
		infoPage
			.clickDisableIcon()
			.clickSaveBtn();
		
		HomeTabletPage home = new HomeTabletPage();
		SettingsPage sett = home.clickSettingsBtn();
		sett.selectRoom(auxRoom);
		SearchPage search = home.clickSearchBtn();
		Assert.assertFalse(search.roomIsDiplayed(displayName));
	}
	
	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		HomeAdminPage home = new HomeAdminPage();
		home.clickConferenceRoomsLink();
		RoomsPage confPage = new RoomsPage();
		confPage.enableDisableIcon(displayName);
		UIMethods.refresh();
	}
}
