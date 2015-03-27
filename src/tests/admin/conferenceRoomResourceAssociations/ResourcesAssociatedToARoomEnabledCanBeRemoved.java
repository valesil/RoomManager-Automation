package tests.admin.conferenceRoomResourceAssociations;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
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
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;
import framework.utils.readers.JsonReader;

/**
 * TC03: Verify that resources associated to a room enabled can be removed.
 * @author Juan Carlos Guevara  
 */
public class ResourcesAssociatedToARoomEnabledCanBeRemoved {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("APIResources");
	String roomName = testData.get(0).get("Room Name");

	//Reading json resource information
	JsonReader jsonValue = new JsonReader();
	String resourceFileJSON = "\\src\\tests\\Resource1.json";
	String filePath = System.getProperty("user.dir") + resourceFileJSON;
	String resourceName = jsonValue.readJsonFile("name" , resourceFileJSON);	
	String resourceDisplayName = jsonValue.readJsonFile("customName" , resourceFileJSON);
	
	@BeforeClass
	public void preconditions() throws MalformedURLException, IOException {
		UIMethods.refresh();
		HomeAdminPage homeAdminPage = new HomeAdminPage();				
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();

		//Create resource by Rest
		RootRestMethods.createResource(filePath, "");
		UIMethods.refresh();		

		//Associating resource to a room
		RoomsPage roomsPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage
				.clickResourceAssociationsLink();
		roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName)
		.clickSaveBtn();
	}

	@Test(groups = {"ACCEPTANCE"})
	public void testResourcesAssociatedToARoomEnabledCanBeRemoved() throws InterruptedException,
	BiffException, IOException{

		//Disassociate resource
		RoomsPage roomsPage = new RoomsPage();
		roomsPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage
				.clickResourceAssociationsLink();
		roomResourceAssociationsPage.removeResourceFromAssociatedList(resourceDisplayName);
		roomsPage = roomResourceAssociationsPage.clickSaveBtn();

		//Assertion for TC03
		Assert.assertTrue(roomResourceAssociationsPage.searchResource(resourceDisplayName));
	}

	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {

		//delete resource with API rest method
		HomeAdminPage homeAdminPage = new HomeAdminPage();;	
		homeAdminPage.clickResourcesLink();
		RootRestMethods.deleteResource(resourceName);
		UIMethods.refresh();	
	}
}
