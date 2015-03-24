package tests.admin.Resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import jxl.read.biff.BiffException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceAssociationsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourceInfoPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * Verify that a resource deleted is removed of {Filter by Resource:}  section on {Advanced > Search} page
 * @author Marco Llano
 *
 */
public class ResourceDeletedIsRemovedFromAdvancedSearchInTablet {
	ResourcesPage resource;
	ResourceInfoPage resourceInfo;
	ResourceAssociationsPage resourceAssoiation;
	HomeAdminPage home = new HomeAdminPage();

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceCanBeUpdatedInResourceInfo() throws InterruptedException, BiffException, IOException {
		resource = home.clickResourcesLink();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");

		//Variables declaration and initialize
		String resourceName = testData1.get(0).get("ResourceName");
		String resourceDisplayName = testData1.get(0).get("ResourceDisplayName");
		String resourceDescription = testData1.get(0).get("Description");
		String iconTitle = testData1.get(0).get("Icon");
		String quantity = testData1.get(0).get("Value");
		String roomDisplayName = testData1.get(0).get("Room Name");

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
		
		//Open tablet to see changes
		SettingsPage setting = new SettingsPage();		
		HomePage homeTablet = setting.selectRoom(testData1.get(0).get("Room Name"));
		SearchPage searchPage = homeTablet.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();
		if (searchPage.isResourceInAdvancedSearch(resourceDisplayName)) {
			RootRestMethods.deleteResource(testData1.get(0).get("ResourceName"));
		}
		Assert.assertFalse(searchPage.isResourceInAdvancedSearch(resourceDisplayName));
	}
	
	@AfterMethod
	public void afterMethod() throws InterruptedException, BiffException, IOException {
		//Go back to AdminHomePage
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		homeAdminPage.getAdminHome();
	}
}
