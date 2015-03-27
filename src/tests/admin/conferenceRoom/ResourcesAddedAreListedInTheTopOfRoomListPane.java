package tests.admin.conferenceRoom;

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
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;
import framework.utils.readers.JsonReader;

/**
 * TC08: Verify that resources added are listed in the top of room list pane
 * @author Juan Carlos Guevara
 */
public class ResourcesAddedAreListedInTheTopOfRoomListPane {
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
	public void precondition() throws MalformedURLException, IOException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();				
		homeAdminPage.clickResourcesLink();
		
		//Create resource by Rest
		RootRestMethods.createResource(filePath, "");
		UIMethods.refresh();		
	}

	@Test(groups = {"FUNCTIONAL"})
	public void testResourcesAddedAreListedInTheTopOfRoomListPane() 
			throws InterruptedException, BiffException, IOException {
		RoomsPage roomsPage = new RoomsPage();
		roomsPage.clickConferenceRoomsLink();

		//Associate resource to a room
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage
				.clickResourceAssociationsLink();
		roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName)
		.clickSaveBtn();

		//Assertion for TC08 
		Assert.assertTrue(roomsPage.searchResource(resourceDisplayName));
	}

	@AfterClass
	public void cleanRoom() throws MalformedURLException, IOException {

		//Delete resource
		HomeAdminPage homeAdminPage = new HomeAdminPage();				
		homeAdminPage.clickResourcesLink();
		RootRestMethods.deleteResource(resourceName);
		UIMethods.refresh();
	}
}
