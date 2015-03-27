package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC10:Verify that an enabled room is available in the tablet
 * @author Juan Carlos Guevara
 */
public class AnEnabledRoomIsAvailableInTheTablet {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");

	@Test(groups = {"FUNCTIONAL"})
	public void testAnEnabledRoomIsAvailableInTheTablet() throws InterruptedException{

		//Open tablet to see availability of an enable room
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		homeTabletPage = settingsPage.selectRoom(roomName);	
		SearchPage searchPage = homeTabletPage.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();
		searchPage.setName(roomName);

		//Assertion for TC10 
		Assert.assertTrue(searchPage.roomIsDiplayed(roomName));
	}
}
