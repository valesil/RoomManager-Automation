package tests.admin.conferenceRoomResourceAssociations;
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
 * TC02: Verify that a resource can be associated to a room enabled.
 * @author Juan Carlos Guevara
 */
public class AResourceCanBeAssociatedToARoomEnabled {
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

		//Create resource by Rest
		RootRestMethods.createResource(filePath, "");
		UIMethods.refresh();
	}

	@Test(groups = {"ACCEPTANCE"})
	public void testAResourceCanBeAssociatedToARoomEnabled() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();

		//Associate the resource to a room
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomsResourceAssociationsPage = roomInfoPage
				.clickResourceAssociationsLink();
		roomsResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName);
		roomsPage = roomsResourceAssociationsPage.clickSaveBtn();
		UIMethods.refresh();
		roomsPage.clickConferenceRoomsLink();

		//Assertion for TC02 
		Assert.assertTrue(roomsResourceAssociationsPage.searchResource(resourceDisplayName));	
	}

	@AfterClass
	public void postCondition() throws MalformedURLException, IOException {

		//Delete resource with API rest method
		RootRestMethods.deleteResource(resourceName);
		UIMethods.refresh();
	}
}



