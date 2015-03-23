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
 * Test Case # 1 Room Resource Associations
 * @author Juan Carlos Guevara 
 * Verify that a resource that is deleted it is removed from resources associated in a room
 */
public class AResourceDeletedIsRemovedFromResourcesAssociatedInARoom {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String description = testData.get(0).get("Description");
	String iconTitle = testData.get(0).get("Icon");
	
	@BeforeClass
	public void precondition() throws InterruptedException, BiffException, IOException {
		
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
		} else{
		
		//Associate resource to a room
		RoomsPage confRoomsPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = confRoomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomsResourceAssociationsPage = infoPage
				.clickResourceAssociationsLink();
		roomsResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName);
		confRoomsPage = roomsResourceAssociationsPage.clickSaveBtn();
		}
	}
	
	@Test(groups = {"FUNCTIONAL"})
	public void testAResourceDeletedIsRemovedFromResourcesAssociatedInARoom() 
			throws InterruptedException, BiffException, IOException {
		ResourcesPage resourcesPage = new ResourcesPage();
		RoomsPage conferenceRoomPage = resourcesPage.clickConferenceRoomsLink();
		resourcesPage = conferenceRoomPage.clickResourcesLink();
		
		//Delete resource
		RootRestMethods.deleteResource(testData.get(0).get("ResourceName"));
		UIMethods.refresh();
		conferenceRoomPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = conferenceRoomPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage crresourceAssociationsPage = infoPage.
				clickResourceAssociationsLink();
		
		//Verify that the resource is not available 
		Assert.assertFalse(crresourceAssociationsPage.searchResource(resourceDisplayName));		
	}	
}
