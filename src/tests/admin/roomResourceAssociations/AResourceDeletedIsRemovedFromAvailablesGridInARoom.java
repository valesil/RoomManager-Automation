package tests.admin.roomResourceAssociations;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.MessageConstants.RESOURCE_NAME_DUPLICATED;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomBaseAbstractPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * Test Case # 4 Room Resources Associations 
 * @author Juan Carlos Guevara 
 * Verify that a deleted resource is removed from {Availables} grid in a room
 */
public class AResourceDeletedIsRemovedFromAvailablesGridInARoom {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String description = testData.get(0).get("Description");
	String iconTitle = testData.get(0).get("Icon");

	@BeforeClass
	public void precondition() throws BiffException, IOException {

		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	
		ResourceCreatePage newResourcePage = new ResourceCreatePage();

		//create a resource
		newResourcePage = resourcesPage.clickAddResourceBtn();		
		resourcesPage = newResourcePage.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(description)
				.clickSaveResourceBtn();
		if(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED)== true){
			UIMethods.refresh();
		}
	}

	@Test(groups = {"FUNCTIONAL"})
	public void testAResourceDeletedIsRemovedFromAvailablesGridInARoom() throws InterruptedException,
	BiffException, IOException{

		//Delete Resource
		ResourcesPage resourcesPage = new ResourcesPage();
		RoomsPage confRoomsPage = resourcesPage.clickConferenceRoomsLink();
		resourcesPage = confRoomsPage.clickResourcesLink();
		RootRestMethods.deleteResource(testData.get(0).get("ResourceName"));
		UIMethods.refresh();
		confRoomsPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = confRoomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = infoPage.clickResourceAssociationsLink();

		//Verify that the resource is not available
		Assert.assertFalse(roomResourceAssociationsPage.searchResource(resourceDisplayName));
		confRoomsPage = roomResourceAssociationsPage.clickCancelBtn();
	}
}
