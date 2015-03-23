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
 * Test Case # 6 CRResource Associations
 * @author Juan Carlos Guevara
 * Verify that resources quantity can be modify when it is associated to a room.
 */
public class ResourcesQuantityCanBeModifyWhenItIsAssociatedToARoom {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String resourceDescription = testData.get(0).get("Description");
	String iconTitle = testData.get(0).get("Icon");	
	String value = testData.get(0).get("Value");

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
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();
		if(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED)== true){
			UIMethods.refresh();
		} else {


			//Associate resource to a room
			RoomsPage confRoomsPage = resourcesPage.clickConferenceRoomsLink();
			RoomInfoPage infoPage = confRoomsPage.doubleClickOverRoomName(roomName);
			RoomResourceAssociationsPage roomResourceAssociationsPage = infoPage.clickResourceAssociationsLink();
			roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName);
			confRoomsPage = roomResourceAssociationsPage.clickSaveBtn();
		}
	}

	@Test(groups = {"ACCEPTANCE"})
	public void testResourcesQuantityCanBeModifyWhenItIsAssociatedToARoom() 
			throws InterruptedException, BiffException, IOException {

		//change resource quantity
		UIMethods.refresh();
		RoomsPage conferenceRoomPage = new RoomsPage();
		RoomInfoPage infoPage = conferenceRoomPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage crresourceAssociationsPage = infoPage.
				clickResourceAssociationsLink()
				.changeValueForResourceFromAssociatedList(resourceDisplayName,value);
		conferenceRoomPage = crresourceAssociationsPage.clickSaveBtn();
		UIMethods.refresh();
		conferenceRoomPage.doubleClickOverRoomName(roomName);	
		crresourceAssociationsPage = infoPage.clickResourceAssociationsLink();

		//verify resource quantity was modified
		Assert.assertEquals(crresourceAssociationsPage.
				getResourceAmount(resourceDisplayName),value);
		conferenceRoomPage = crresourceAssociationsPage.clickCancelBtn();
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