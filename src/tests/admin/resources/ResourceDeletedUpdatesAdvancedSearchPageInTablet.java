package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.AppConfigConstants.EXCEL_PATH;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceAssociationsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourceDeletePage;
import framework.pages.admin.resources.ResourceInfoPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;
import framework.utils.readers.JsonReader;

/** 
 * TC07: Verify that a resource deleted is removed of {Filter by Resource:}  section on {Advanced > Search} page
 * @author Marco Llano
 */
public class ResourceDeletedUpdatesAdvancedSearchPageInTablet {
	ResourcesPage resource;
	ResourceInfoPage resourceInfo;
	ResourceAssociationsPage resourceAssoiation;

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceDeletedUpdatesAdvancedSearchPageInTablet() throws InterruptedException, BiffException, IOException {
		HomeAdminPage home = new HomeAdminPage();
		JsonReader value = new JsonReader();
		resource = home.clickResourcesLink();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");
		String resourceFileJSON = "\\src\\tests\\Resource1.json";
		String filePath = System.getProperty("user.dir") + "\\src\\tests\\Resource1.json";
		String resourceDisplayName = value.readJsonFile("name" , resourceFileJSON);
		String roomDisplayName = testData1.get(0).get("Room Name");
		String quantity = testData1.get(0).get("Value");
		
		//Create resource by Rest
		RootRestMethods.createResource(filePath, "");
		UIMethods.refresh();

		//Associate a resource to a room by resource display name
		RoomsPage roomsPage = home.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomDisplayName);
		RoomResourceAssociationsPage roomResourceAssociation = roomInfoPage.clickResourceAssociationsLink();
		roomsPage = roomResourceAssociation.clickAddResourceToARoom(resourceDisplayName)
				.changeValueForResourceFromAssociatedList(resourceDisplayName, quantity)
				.clickSaveBtn();

		//Open Advanced search from tablet
		HomeTabletPage homeTabletPage =  new HomeTabletPage();
		
		//Next 2 code lines are needed if in tablet did not already choose a room.
//		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();  
//		homeTabletPage = settingsPage.selectRoom(roomDisplayName);		
		SearchPage searchPage = homeTabletPage.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();

		//Delete created resource
		home = new HomeAdminPage();
		resource = home.clickResourcesLink();
		ResourceDeletePage resourceDelete = resource
				.selectResourceCheckbox(resourceDisplayName)
				.clickRemoveBtn();
		resource = resourceDelete.clickConfirmRemoveBtn();

		//Go back to tablet to verify if the resource is displayed
		homeTabletPage =  new HomeTabletPage();
		searchPage = homeTabletPage.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();

		//Assertion for TC07
		Assert.assertFalse(searchPage.isResourceInAdvancedSearch(resourceDisplayName));
	}
}