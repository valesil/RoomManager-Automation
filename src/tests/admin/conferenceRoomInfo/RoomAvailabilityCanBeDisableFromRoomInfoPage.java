package tests.admin.suits;

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

public class RoomAvailabilityCanBeDisableFromRoomInfoPage {
	
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData2 = excelReader.getMapValues("RoomInfo");
	String displayName = testData2.get(0).get("DisplayName");
	String auxRoom = testData2.get(1).get("DisplayName");
	
	@Test
	public void testRoomAvailabilityCanBeDisableFromRoomInfoPage() {
		
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage conferencePage = homePage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = conferencePage.doubleClickOverRoomName(displayName);
		infoPage
			.clickDisableIcon()
			.clickSaveBtn();
		SettingsPage setting = new SettingsPage();
		HomePage home = setting.selectRoom(auxRoom);
		SearchPage search = home.clickSearchBtn();
		Assert.assertFalse(search.roomIsDiplayed(displayName));
	}
	
	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		HomeAdminPage home = new HomeAdminPage();
		home.getAdminHome()
			.clickConferenceRoomsLink();
		RoomsPage confPage = new RoomsPage();
		confPage.enableDisableIcon(displayName);
	}
}
