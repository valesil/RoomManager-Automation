package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC31: Verify that all resources added are listed on {Filter by Resource:} section  in {Advanced > Search} page
 * @author Marco Llano
 */
public class ResourceCreatedUpdatesAdvancedSearchPageInTablet {
	
	//ExcelReader is used to read rooms data
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> resourcesDataList = excelReader.getMapValues("Resources");

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceCreatedUpdatesAdvancedSearchPageInTablet() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();

		//Variables declaration and initialize
		String resourceName = resourcesDataList.get(0).get("ResourceName");
		String resourceDisplayName = resourcesDataList.get(0).get("ResourceDisplayName");
		String resourceDescription = resourcesDataList.get(0).get("Description");
		String iconTitle = resourcesDataList.get(0).get("Icon");
		String quantity = resourcesDataList.get(0).get("Value");
		String roomDisplayName = resourcesDataList.get(0).get("Room Name");

		//Create a resourcesPage
		ResourceCreatePage resourceCreatePage  = resourcesPage.clickAddResourceBtn();		
		resourcesPage = resourceCreatePage.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();

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
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		homeTabletPage = settingsPage.selectRoom(roomDisplayName);
		SearchPage searchPage = homeTabletPage.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();

		//Assertion for TC31
		Assert.assertTrue(searchPage.isResourceInAdvancedSearch(resourceDisplayName));
	}

	@AfterMethod(groups = {"FUNCTIONAL"})
	public void afterMethod() throws MalformedURLException, IOException {			
		RootRestMethods.deleteResource(resourcesDataList.get(0).get("ResourceName"));
	}
}