package test.java.admin.conferenceroomresourceassociations;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;
import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.pages.admin.resources.ResourceCreatePage;
import main.java.pages.admin.resources.ResourcesPage;
import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SettingsPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * TC07: Verify that resources associated to the room are displayed on home page
 * @author Ruben Blanco
 *
 */
public class ResourcesAssociatedToRoomAreDisplayedOnHomePage {

	//read excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomList = excelReader.getMapValues("Resources");
	private String roomName = roomList.get(0).get("Room Name");
	private String resourceName = roomList.get(0).get("ResourceName");
	private String resourceDisplayName = roomList.get(0).get("ResourceDisplayName");
	private String resourceDescription = roomList.get(0).get("Description");
	private String iconTitle = roomList.get(0).get("Icon");	
	private String quantity = roomList.get(0).get("Value");
    	
	@BeforeClass(groups = "FUNCTIONAL")
	public void precondition() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	
		ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();
		
		//create a resource
		resourcesPage = resourceCreatePage.clickResourceIcon()
			.selectResourceIcon(iconTitle)
			.setResourceName(resourceName)
			.setResourceDisplayName(resourceDisplayName)
			.setResourceDescription(resourceDescription)
			.clickSaveResourceBtn();
		RoomsPage roomsPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage resourceAssociation = roomInfoPage
			.clickResourceAssociationsLink();
		resourceAssociation.clickAddResourceToARoom(resourceDisplayName)
			.changeValueForResourceFromAssociatedList(resourceDisplayName,quantity)
			.clickSaveBtn();
	}

	@Test(groups = {"FUNCTIONAL"})
	public void testResourcesAssociatedToRoomAreDisplayedOnHomePage() throws BiffException, IOException {
		
		//reading to excel to create variables
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData2 = excelReader.getMapValues("RoomInfo");
		String displayName = testData2.get(0).get("DisplayName").trim();		

		//navigate to tablet for select a room
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		settingsPage.selectRoom(displayName);
		
		//Assertion for TC07
		Assert.assertTrue(homeTabletPage.VerifyResourceIsAsociated(resourceDisplayName, quantity));
	}

	@AfterClass(groups = {"FUNCTIONAL"})
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		
		//Delete resource
	    RoomManagerRestMethods.deleteResource(resourceName);
	}
}
