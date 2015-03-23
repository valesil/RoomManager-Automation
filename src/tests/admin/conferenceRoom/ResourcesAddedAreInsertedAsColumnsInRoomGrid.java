package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.MessageConstants.RESOURCE_NAME_DUPLICATED;

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
import framework.pages.admin.conferencerooms.RoomBaseAbstractPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * Test Case #32 Conference Rooms Sheet
 * @author Juan Carlos Guevara
 * Verify that the resources added are inserted as columns in room grid.
 */
public class ResourcesAddedAreInsertedAsColumnsInRoomGrid {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String resourceDescription = testData.get(0).get("Description");
	String iconTitle = testData.get(0).get("Icon");
	
	@BeforeClass
	public void precondition() throws InterruptedException, BiffException, IOException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	
		
		//create a resource
		ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();		
		resourcesPage = newResourcePage.clickResourceIcon()
						.selectResourceIcon(iconTitle)
						.setResourceName(resourceName)
						.setResourceDisplayName(resourceDisplayName)
						.setResourceDescription(resourceDescription)
						.clickSaveResourceBtn();
		if(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED)== true){
			UIMethods.refresh();
		}
	}
	
	@Test(groups = {"FUNCTIONAL"})
	public void testResourcesAddedAreInsertedAsColumns() throws InterruptedException,
	BiffException, IOException {
		
		//Associate resource to a room
		ResourcesPage resourcesPage = new ResourcesPage();
		RoomsPage confRoomPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = confRoomPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = infoPage
				.clickResourceAssociationsLink();
		roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName)
		.clickSaveBtn();
		UIMethods.refresh();
		
		//Verify resource name is displayed is table grid header
		Assert.assertTrue(confRoomPage.isResourcePresentInTableHeader(resourceDisplayName));
	}
	
	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		
		//delete resource
		RoomsPage confRoomPage = new RoomsPage();
		confRoomPage.clickResourcesLink();
		RootRestMethods.deleteResource(testData.get(0).get("ResourceName"));
		UIMethods.refresh();
	}
}