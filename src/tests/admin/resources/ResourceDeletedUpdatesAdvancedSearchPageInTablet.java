package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceDeletePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;
import framework.utils.readers.JsonReader;

/** 
 * TC07: Verify that a resourcesPage deleted is removed of {Filter by Resource:}  section on {Advanced > Search} page
 * @author Marco Llano
 */
public class ResourceDeletedUpdatesAdvancedSearchPageInTablet {
	@Test(groups = {"FUNCTIONAL"})
	public void testResourceDeletedUpdatesAdvancedSearchPageInTablet() throws MalformedURLException,
	IOException, InterruptedException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	
		
		//ExcelReader is used to read resources data from excel		
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");
		String roomDisplayName = testData1.get(0).get("Room Name");
		String quantity = testData1.get(0).get("Value");
		
		//JsonReader is used to read resources data
		JsonReader value = new JsonReader();
		String resourceJsonFilePath = "\\src\\tests\\Resource1.json";
		String resourceDisplayName = value.readJsonFile("name" , resourceJsonFilePath);	
		
		//Create resourcesPage by Rest
		String resourceFilePath = System.getProperty("user.dir") + "\\src\\tests\\Resource1.json";
		RootRestMethods.createResource(resourceFilePath, "");
		UIMethods.refresh();

		//Associate a resourcesPage to a room by resourcesPage display name
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomDisplayName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage.clickResourceAssociationsLink();
		roomsPage = roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName)
				.changeValueForResourceFromAssociatedList(resourceDisplayName, quantity)
				.clickSaveBtn();

		//Open Advanced search from tablet
		HomeTabletPage homeTabletPage =  new HomeTabletPage();

		//Next 2 code lines are needed if in tablet did not already choose a room.
//		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();  
//		homeTabletPage = settingsPage.selectRoom(roomDisplayName);		
		SearchPage searchPage = homeTabletPage.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();

		//Delete created resourcesPage
		homeAdminPage = new HomeAdminPage();
		resourcesPage = homeAdminPage.clickResourcesLink();
		ResourceDeletePage resourceDeletePage = resourcesPage
				.selectResourceCheckbox(resourceDisplayName)
				.clickRemoveBtn();
		resourcesPage = resourceDeletePage.clickConfirmRemoveBtn();

		//Go back to tablet to verify if the resourcesPage is displayed
		homeTabletPage =  new HomeTabletPage();
		searchPage = homeTabletPage.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();

		//Assertion for TC07
		Assert.assertFalse(searchPage.isResourceInAdvancedSearch(resourceDisplayName));
	}
}