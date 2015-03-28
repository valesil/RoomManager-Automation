package tests.admin.conferenceRoomResourceAssociations;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SettingsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC08: Verify that a resource associated to a room that is deleted is removed on home page.
 * @author Ruben Blanco
 * 
 */
public class ResourceAssociatedToRoomThatIsDeletedIsRemovedOnHomePage {
	
	//reading to excel to create variables to resource creation
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String roomName = testData.get(0).get("Room Name");
	private String resourceName = testData.get(0).get("ResourceName");
	private String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	private String resourceDescription = testData.get(0).get("Description");
	private String iconTitle = testData.get(0).get("Icon");	
	private String quantity = testData.get(0).get("Value");
	
	@BeforeClass
	public void precondition() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	
		ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();
		
		//create a resource
		resourcesPage = newResourcePage
			.clickResourceIcon()
			.selectResourceIcon(iconTitle)
			.setResourceName(resourceName)
			.setResourceDisplayName(resourceDisplayName)
			.setResourceDescription(resourceDescription)
			.clickSaveResourceBtn();
		RoomsPage conferenceRoomPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = conferenceRoomPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage crresourceAssociationsPage = infoPage.clickResourceAssociationsLink();
		crresourceAssociationsPage
			.clickAddResourceToARoom(resourceDisplayName)
			.changeValueForResourceFromAssociatedList(resourceDisplayName,quantity)
			.clickSaveBtn();
	}

	@Test(groups = {"FUNCTIONAL"})
	public void testResourcesAssociatedToRoomthatIsDeletedIsRemovedOnHomePage() {
		//reading to excel to create variables of room
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData2 = excelReader.getMapValues("RoomInfo");
		String resourceName = testData2.get(0).get("AssociatedResource");
		String amount = testData2.get(0).get("Quantity");
		String displayName = testData2.get(0).get("DisplayName");
        
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage conferencePage = homePage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = conferencePage.doubleClickOverRoomName(displayName);
		RoomResourceAssociationsPage crresourceAssociationsPage = infoPage.clickResourceAssociationsLink();
		
		//remove resource of a room
		crresourceAssociationsPage
			.removeResourceFromAssociatedList(resourceName)
			.clickSaveBtn();

		HomeTabletPage home = new HomeTabletPage();
		SettingsPage sett = home.clickSettingsBtn();
		sett.selectRoom(displayName);
		
		//Assertion for TC08
		Assert.assertFalse(home.VerifyResourceIsAsociated(resourceName, amount));
	}
	
	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		//Delete resource
	    RootRestMethods.deleteResource(resourceName);
	}
}
