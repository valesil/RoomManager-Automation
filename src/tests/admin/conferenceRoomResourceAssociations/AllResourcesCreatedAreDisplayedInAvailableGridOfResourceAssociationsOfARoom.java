package tests.admin.conferenceRoomResourceAssociations;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
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
 * TC05: Verify that all resources created are displayed in {Available} grid of {Resource 
 * Associations} of a room
 * @author Juan Carlos Guevara
 */
public class AllResourcesCreatedAreDisplayedInAvailableGridOfResourceAssociationsOfARoom {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");

	@Test(groups = {"FUNCTIONAL"})
	public void testAllResourcesCreatedAreDisplayedInAvailableGridOfResourceAssociationsOfARoom() 
			throws MalformedURLException, IOException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();

		for(Map<String, String> resource : testData){
			ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();

			//create a resource	
			ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();		
			newResourcePage.clickResourceIcon()
			.selectResourceIcon(resource.get("Icon"))
			.setResourceName(resource.get("ResourceName"))
			.setResourceDisplayName(resource.get("ResourceDisplayName"))
			.setResourceDescription(resource.get("Description"))
			.clickSaveResourceBtn();			


			//Associate the resource to a room
			RoomsPage confRoomsPage = resourcesPage.clickConferenceRoomsLink();
			RoomInfoPage infoPage = confRoomsPage.doubleClickOverRoomName(roomName);
			RoomResourceAssociationsPage roomResourceAssociationsPage = infoPage.
					clickResourceAssociationsLink();

			//Assertion for TC05 
			Assert.assertTrue(roomResourceAssociationsPage.searchResource(resource
					.get("ResourceDisplayName")));
			confRoomsPage = roomResourceAssociationsPage.clickCancelBtn();	

		}
	}

	@AfterClass
	public void cleanRoom() throws MalformedURLException, IOException {

		//delete resource with API rest method
		HomeAdminPage homeAdminPage = new HomeAdminPage();;	
		homeAdminPage.clickResourcesLink();
		for(Map<String, String> resource : testData){					
			RootRestMethods.deleteResource(resource.get("ResourceName"));
		}
		UIMethods.refresh();
	}
}
