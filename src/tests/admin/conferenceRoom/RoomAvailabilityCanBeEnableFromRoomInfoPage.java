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
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC02: Verify that a room availability can be enable from room info window
 * @author Ruben Blanco
 *
 */
public class RoomAvailabilityCanBeEnableFromRoomInfoPage {

	//reading to excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData2 = excelReader.getMapValues("RoomInfo");
	private String displayName = testData2.get(0).get("DisplayName");
	private String auxRoom = testData2.get(1).get("DisplayName");

	@BeforeClass
	public void precondition() {
		UIMethods.refresh();
		HomeAdminPage home = new HomeAdminPage();
		RoomsPage confPage = home.clickConferenceRoomsLink();
		
		//click to disable the selected room
		confPage.enableDisableIcon(displayName);
	}
	
	@Test(groups = {"FUNCTIONAL"})
	public void testRoomAvailabilityCanBeEnableFromRoomInfoPage() {
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage conferencePage = homePage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = conferencePage
			.doubleClickOverRoomName(displayName);
		infoPage
			.clickEnableIcon()
			.clickSaveBtn();
		
		HomeTabletPage home = new HomeTabletPage();
		SettingsPage sett = home.clickSettingsBtn();
		sett.selectRoom(auxRoom);
		SearchPage search = home.clickSearchBtn();
		
		//Assertion for TC 02
		Assert.assertTrue(search.roomIsDiplayed(displayName));
	}
	
}
