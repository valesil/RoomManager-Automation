package tests.admin.roomResourceAssociations;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.MessageConstants.RESOURCE_NAME_DUPLICATED;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
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
 * Test Case # 5 Room Resources Associations
 * @author Juan Carlos Guevara
 * Verify that all resources created are displayed in {Available} grid of {Resource Associations} 
 * of a room
 */
public class AllResourcesCreatedAreDisplayedInAvailableGridOfResourceAssociationsOfARoom {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("BackUpResources");
	String roomName = testData.get(0).get("Room Name");

	@Test(groups = {"FUNCTIONAL"})
	public void testAllResourcesCreatedAreDisplayedInAvailableGridOfResourceAssociationsOfARoom() 
			throws InterruptedException, BiffException, IOException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		for(Map<String, String> resource : testData){					

			//create a resource
			ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();
			ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();		
			resourcesPage = newResourcePage.clickResourceIcon()
					.selectResourceIcon(resource.get("Icon"))
					.setResourceName(resource.get("ResourceName"))
					.setResourceDisplayName(resource.get("ResourceDisplayName"))
					.setResourceDescription(resource.get("Description"))
					.clickSaveResourceBtn();
			if(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED)== true){
				UIMethods.refresh();
			}else {

				//Associate the resource to a room
				RoomsPage confRoomsPage = resourcesPage.clickConferenceRoomsLink();
				RoomInfoPage infoPage = confRoomsPage.doubleClickOverRoomName(roomName);
				RoomResourceAssociationsPage roomResourceAssociationsPage = infoPage.
						clickResourceAssociationsLink();

				//Verify that the resource is available
				Assert.assertTrue(roomResourceAssociationsPage.searchResource(resource
						.get("ResourceDisplayName")));
				confRoomsPage = roomResourceAssociationsPage.clickCancelBtn();	
			}
		}
	}

	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {

		//delete resource
		RoomsPage conferenceRoomPage = new RoomsPage();	
		conferenceRoomPage.clickResourcesLink();
		for(Map<String, String> resource : testData){					
			RootRestMethods.deleteResource(resource.get("ResourceName"));
		}
		UIMethods.refresh();
	}
}
