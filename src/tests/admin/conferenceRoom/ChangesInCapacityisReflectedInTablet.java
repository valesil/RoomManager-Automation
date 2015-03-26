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
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC08: Verify that the changes on Capacity of rooms are reflected tablet app 
 * @author Ruben Blanco
 *
 */
public class ChangesInCapacityisReflectedInTablet {

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	String displayName =testData1.get(0).get("DisplayName");
	String capacity = testData1.get(0).get("Capacity");
	String empty = "";

	@Test(groups = {"FUNCTIONAL"})
	public void testChangesInCapacityisReflectedInTablet() {
		
		UIMethods.refresh();
		
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		System.out.println("entrando a room -"+displayName);
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		System.out.println("cambio de capacida");
		roomInf
			.setRoomCapacity(capacity)
			.clickSaveBtn();
		
		System.out.println("cambio de page");
		HomeTabletPage home = new HomeTabletPage();
		SettingsPage sett = home.clickSettingsBtn();
		sett.selectRoom(displayName);
        SearchPage searchPage = home
        		.clickSearchBtn()
        		.clickCollapseAdvancedBtn()
        		.setMinimumCap(capacity);
        Assert.assertTrue(searchPage.roomIsDiplayed(displayName));
	}

	@AfterClass
	public void postcondition() {
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf
			.setRoomCapacity(empty)
			.clickSaveBtn();
	}
}
