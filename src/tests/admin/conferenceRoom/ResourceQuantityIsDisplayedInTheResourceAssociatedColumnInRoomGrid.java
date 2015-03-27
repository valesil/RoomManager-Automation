package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

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
 * TC27: Verify that resource quantity and icon are displayed as columns in Room grid.
 * TC32: Verify that resources added are inserted as columns in Room grid. 
 * @author Juan Carlos Guevara
 */
public class ResourceQuantityIsDisplayedInTheResourceAssociatedColumnInRoomGrid {
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

		//Create resource by Rest
		RootRestMethods.createResource(filePath, "");
		UIMethods.refresh();		
	}

	@Test(groups = {"FUNCTIONAL"})
	public void testAResourceIsDisplayedAsAColumnWhenItsIconIsSelectedInTheTopOfRoomGrid() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();

		//Associate resource to a room
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage
				.clickResourceAssociationsLink();
		roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName);
		roomResourceAssociationsPage.changeValueForResourceFromAssociatedList(resourceDisplayName,
				quantity);
		roomsPage = roomResourceAssociationsPage.clickSaveBtn();
		roomsPage.clickResourceIcon(resourceDisplayName);

		//Assertion for TC27 
		Assert.assertEquals(roomsPage.getResourceQuantity(roomName),"x " + quantity);

		//Assertion for TC32 
		Assert.assertTrue(roomsPage.isResourcePresentInTableHeader(resourceDisplayName));

		//Assertion for TC08 
		Assert.assertTrue(roomsPage.searchResource(resourceDisplayName));
	}

	@AfterClass
	public void postConditions() throws MalformedURLException, IOException {

		//Delete resource with API rest method
		RootRestMethods.deleteResource(resourceDisplayName);
		UIMethods.refresh();
	}
}
