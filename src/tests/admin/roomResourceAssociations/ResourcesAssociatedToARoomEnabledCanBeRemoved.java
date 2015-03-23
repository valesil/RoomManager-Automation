package tests.admin.roomResourceAssociations;

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
 * Test Case # 3 Room Resource Association
 * @author Juan Carlos Guevara  
 * Verify that resources associated to a room enabled can be removed.
 */
public class ResourcesAssociatedToARoomEnabledCanBeRemoved {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String resourceDescription = testData.get(0).get("Description");
	String iconTitle = testData.get(0).get("Icon");	

	@BeforeClass
	public void preconditions() throws InterruptedException, BiffException, IOException{

		HomeAdminPage homeAdminPage = new HomeAdminPage();
		UIMethods.refresh();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	
		ResourceCreatePage newResourcePage = new ResourceCreatePage();

		//create a resource
		newResourcePage = resourcesPage.clickAddResourceBtn();		
		resourcesPage = newResourcePage.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();
		if(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED)== true){
			UIMethods.refresh();
		} else {


			//Associating resource to a room
			RoomsPage conferenceRoomPage = resourcesPage.clickConferenceRoomsLink();
			RoomInfoPage infoPage = conferenceRoomPage.doubleClickOverRoomName(roomName);
			RoomResourceAssociationsPage crresourceAssociationsPage = infoPage.clickResourceAssociationsLink();
			crresourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName)
			.clickSaveBtn();
		}
	}

	@Test(groups = {"ACCEPTANCE"})
	public void testResourcesAssociatedToARoomEnabledCanBeRemoved() throws InterruptedException,
	BiffException, IOException{

		//Disassociate resource
		RoomsPage conferenceRoomPage = new RoomsPage();
		conferenceRoomPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = conferenceRoomPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage crresourceAssociationsPage = infoPage.clickResourceAssociationsLink();
		crresourceAssociationsPage.removeResourceFromAssociatedList(resourceDisplayName);
		conferenceRoomPage = crresourceAssociationsPage.clickSaveBtn();

		//Verify resource is Disassociate
		Assert.assertTrue(crresourceAssociationsPage.searchResource(resourceDisplayName));
	}

	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {

		//delete resource
		RoomsPage conferenceRoomPage = new RoomsPage();
		conferenceRoomPage.clickResourcesLink();
		RootRestMethods.deleteResource(testData.get(0).get("ResourceName"));
		UIMethods.refresh();	
	}
}
