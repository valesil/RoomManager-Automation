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
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceCreatedUpdatesAdvancedSearchPageInTablet() {
		HomeAdminPage home = new HomeAdminPage();
		ResourcesPage resource = home.clickResourcesLink();

		//Variables declaration and initialize
		String resourceName = testData.get(0).get("ResourceName");
		String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
		String resourceDescription = testData.get(0).get("Description");
		String iconTitle = testData.get(0).get("Icon");
		String quantity = testData.get(0).get("Value");
		String roomDisplayName = testData.get(0).get("Room Name");

		//Create a resource
		ResourceCreatePage resourceCreate  = resource.clickAddResourceBtn();		
		resource = resourceCreate.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();

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
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		homeTabletPage = settingsPage.selectRoom(roomDisplayName);
		SearchPage searchPage = homeTabletPage.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();

		//Assertion for TC31
		Assert.assertTrue(searchPage.isResourceInAdvancedSearch(resourceDisplayName));
	}

	@AfterMethod
	public void afterMethod() throws MalformedURLException, IOException {			
		RootRestMethods.deleteResource(testData.get(0).get("ResourceName"));
	}
}