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
import framework.pages.admin.resources.ResourcesPage;
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
	public void testResourcesAddedAreListedInTheTopOfRoomListPane() 
			throws InterruptedException, BiffException, IOException {
		RoomsPage confRoomPage = new RoomsPage();
		confRoomPage.clickConferenceRoomsLink();

		//Associate resource to a room
		RoomInfoPage infoPage = confRoomPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = infoPage
				.clickResourceAssociationsLink();
		roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName)
		.clickSaveBtn();
		confRoomPage = new RoomsPage();

		//Assertion for TC08 
		Assert.assertTrue(confRoomPage.searchResource(resourceDisplayName));
	}

	@AfterClass
	public void cleanRoom() throws MalformedURLException, IOException {

		//Delete resource
		HomeAdminPage homeAdminPage = new HomeAdminPage();				
		homeAdminPage.clickResourcesLink();
		RootRestMethods.deleteResource(resourceDisplayName);
		UIMethods.refresh();
	}
}
