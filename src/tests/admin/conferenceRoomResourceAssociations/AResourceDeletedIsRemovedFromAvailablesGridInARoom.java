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
 * TC04: Verify that a deleted resource is removed from {Availables} grid in a room 
 * @author Juan Carlos Guevara 
 */
public class AResourceDeletedIsRemovedFromAvailablesGridInARoom {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("APIResources");
	String roomName = testData.get(0).get("Room Name");
	String quantity = testData.get(0).get("Value");

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
	}

	@Test(groups = {"FUNCTIONAL"})
	public void testAResourceDeletedIsRemovedFromAvailablesGridInARoom() 
			throws MalformedURLException, IOException {

		//Delete Resource
		ResourcesPage resourcesPage = new ResourcesPage();
		RoomsPage confRoomsPage = resourcesPage.clickConferenceRoomsLink();
		resourcesPage = confRoomsPage.clickResourcesLink();
		RootRestMethods.deleteResource(testData.get(0).get("ResourceName"));
		UIMethods.refresh();
		confRoomsPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = confRoomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = infoPage
				.clickResourceAssociationsLink();

		//Assertion for TC04 
		Assert.assertFalse(roomResourceAssociationsPage.searchResource(resourceDisplayName));
		confRoomsPage = roomResourceAssociationsPage.clickCancelBtn();
	}
}
