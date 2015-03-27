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
import framework.utils.readers.ExcelReader;

/**
 * TC:24 Verify that when a room status is disabled, the tablet does not schedule meetings
 * @author Juan Carlos Guevara
 */
public class ForARoomDisabledTheTabletDoesNotScheduleMeetings {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(1).get("Room Name");

	@Test(groups = {"FUNCTIONAL"})
	public void testForARoomDisabledTheTabletDoesNotScheduleMeetings() {

		//Disabling room in Admin 
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();		
		roomsPage.enableDisableIcon(roomName);		

		//Open tablet to see changes
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SearchPage searchPage = homeTabletPage.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();
		searchPage.setName(roomName);

		//Assertion for TC24 
		Assert.assertFalse(searchPage.roomIsDiplayed(roomName));		
	}

	@AfterClass
	public void postConditions() {

		//Enable room in admin
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();	
		roomsPage.enableDisableIcon(roomName);
	}
}
