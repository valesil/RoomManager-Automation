package tests.admin.conferenceRoomResourceAssociations;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;
import framework.utils.readers.JsonReader;

/**
 * TC01: Verify that a resource that is deleted it is removed from resources associated in a room
 * @author Juan Carlos Guevara 
 */
public class AResourceDeletedIsRemovedFromResourcesAssociatedInARoom {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");

	//Reading json resource information
	JsonReader jsonValue = new JsonReader();
	String resourceFileJSON = "\\src\\tests\\Resource1.json";
	String filePath = System.getProperty("user.dir") + resourceFileJSON;
	String resourceDisplayName = jsonValue.readJsonFile("name" , resourceFileJSON);

	@BeforeClass
	public void precondition() throws MalformedURLException, IOException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();				
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();
		if(resourcesPage.isResourceNameDisplayedInResourcesPage(resourceDisplayName)){
			RootRestMethods.deleteResource(resourceDisplayName);
			UIMethods.refresh();
		}

		//Create resource by Rest
		RootRestMethods.createResource(filePath, "");
		UIMethods.refresh();		

		//Associate resource to a room
		RoomsPage confRoomsPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = confRoomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomsResourceAssociationsPage = infoPage
				.clickResourceAssociationsLink();
		roomsResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName);
		confRoomsPage = roomsResourceAssociationsPage.clickSaveBtn();

	}

	@Test(groups = {"FUNCTIONAL"})
	public void testAResourceDeletedIsRemovedFromResourcesAssociatedInARoom() 
			throws MalformedURLException, IOException {
		ResourcesPage resourcesPage = new ResourcesPage();
		RoomsPage conferenceRoomPage = resourcesPage.clickConferenceRoomsLink();
		resourcesPage = conferenceRoomPage.clickResourcesLink();

		//Delete resource
		RootRestMethods.deleteResource(resourceDisplayName);
		UIMethods.refresh();
		conferenceRoomPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = conferenceRoomPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage crresourceAssociationsPage = infoPage.
				clickResourceAssociationsLink();

		//Assertion for TC01  
		Assert.assertFalse(crresourceAssociationsPage.searchResource(resourceDisplayName));		
	}	
}
