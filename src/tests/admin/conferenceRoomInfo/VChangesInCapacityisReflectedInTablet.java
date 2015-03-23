package suits;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;

public class VChangesInCapacityisReflectedInTablet {

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	String displayName =testData1.get(0).get("DisplayName");
	String capacity = testData1.get(0).get("Capacity");

	@Test
	public void testChangesInCapacityisReflectedInTablet() throws InterruptedException, BiffException, IOException {

		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf.setRoomCapacity(capacity)
		.clickSaveBtn();
		SettingsPage setting = new SettingsPage();
		HomePage home = setting.selectRoom(displayName);
		SearchPage searchPage = home.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();
		searchPage.setMinimumCap(capacity);	  
		Assert.assertTrue(searchPage.roomIsDiplayed(displayName));
	}

	@AfterClass
	public void postcondition() {
		HomeAdminPage homePage = new HomeAdminPage();
		homePage.getAdminHome();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf.setRoomCapacity("")
			.clickSaveBtn();
	}
}
