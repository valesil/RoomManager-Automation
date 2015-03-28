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

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC10: Verify that resources associated can be removed from a room disabled.
 * @author Ruben Blanco
 *
 */
public class ResourceAssociatedCanBeRemovedFromDisabledRoom {

	//reading to excel to create variables for resource creation
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String roomName = testData.get(0).get("Room Name");
	private String resourceName = testData.get(0).get("ResourceName");
	private String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	private String resourceDescription = testData.get(0).get("Description");
	private String iconTitle = testData.get(0).get("Icon");	

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
		
		//Associate resource to room
		crresourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName)
			.clickSaveBtn();
	}

	@Test(groups = {"FUNCTIONAL"})
	public void testResourcesAssociatedCanBeRemovedFromDisabledRoom() {
		//reading to excel to create variables
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("RoomInfo");
		String roomDisplayName = roomName;
		String resourceName = testData.get(0).get("AssociatedResource");
		
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confPage = homePage.clickConferenceRoomsLink();
		confPage.enableDisableIcon(roomDisplayName);
		RoomInfoPage infoPage = confPage.doubleClickOverRoomName(roomDisplayName);
		RoomResourceAssociationsPage resourceAssociation = infoPage.clickResourceAssociationsLink();
		resourceAssociation
			.removeResourceFromAssociatedList(resourceName)
			.clickSaveBtn();
		
		//Assertion for TC10
		Assert.assertTrue(resourceAssociation.searchResource(resourceName));
	}

	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		RoomsPage confPage = new RoomsPage();
		confPage.enableDisableIcon(roomName);
			
		//Delete resource
		RootRestMethods.deleteResource(resourceName);
		UIMethods.refresh();
	}
}
