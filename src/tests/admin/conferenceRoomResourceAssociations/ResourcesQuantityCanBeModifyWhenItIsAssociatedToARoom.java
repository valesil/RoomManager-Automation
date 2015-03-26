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
 * TC06: Verify that resources quantity can be modify when it is associated to a room.
 * @author Juan Carlos Guevara
 */
public class ResourcesQuantityCanBeModifyWhenItIsAssociatedToARoom {
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

		//Associate resource to a room
		RoomsPage confRoomsPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = confRoomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = infoPage
				.clickResourceAssociationsLink();
		roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName);
		confRoomsPage = roomResourceAssociationsPage.clickSaveBtn();

	}

	@Test(groups = {"ACCEPTANCE"})
	public void testResourcesQuantityCanBeModifyWhenItIsAssociatedToARoom() {

		//change resource quantity
		UIMethods.refresh();
		RoomsPage conferenceRoomPage = new RoomsPage();
		RoomInfoPage infoPage = conferenceRoomPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage crresourceAssociationsPage = infoPage.
				clickResourceAssociationsLink()
				.changeValueForResourceFromAssociatedList(resourceDisplayName,quantity);
		conferenceRoomPage = crresourceAssociationsPage.clickSaveBtn();
		UIMethods.refresh();
		conferenceRoomPage.doubleClickOverRoomName(roomName);	
		crresourceAssociationsPage = infoPage.clickResourceAssociationsLink();

		//Assertion for TC06 
		Assert.assertEquals(crresourceAssociationsPage.
				getResourceAmount(resourceDisplayName),quantity);
		conferenceRoomPage = crresourceAssociationsPage.clickCancelBtn();
	}

	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {

		//delete resource with API rest method
		HomeAdminPage homeAdminPage = new HomeAdminPage();;	
		homeAdminPage.clickResourcesLink();
		RootRestMethods.deleteResource(resourceDisplayName);
		UIMethods.refresh();
	}
}