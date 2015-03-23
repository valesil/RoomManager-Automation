package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.MessageConstants.RESOURCE_NAME_DUPLICATED;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomBaseAbstractPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * Test case # 27
 * @author Juan Carlos Guevara
 * Verify that resource quantity and icon are displayed as columns in {Room} grid.
 */
public class ResourceQuantityIsDisplayedInTheResourceAssociatedColumnInRoomGrid {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String resourceDescription = testData.get(0).get("Description");
	String iconTitle = testData.get(0).get("Icon");
	String value = testData.get(0).get("Value");

	@BeforeClass
	public void precondition() throws InterruptedException, BiffException, IOException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();				

		//create a resource
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();
		ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();		
		newResourcePage.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();
		if(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED)== true){
			UIMethods.refresh();
		}
	}

	@Test(groups = {"FUNCTIONAL"})
	public void testAResourceIsDisplayedAsAColumnWhenItsIconIsSelectedInTheTopOfRoomGrid() throws BiffException, IOException {
		
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage confRoomsPage = homeAdminPage.clickConferenceRoomsLink();
		
		//Associate resource to a room
		RoomInfoPage infoPage = confRoomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = infoPage.clickResourceAssociationsLink();
		roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName);
		roomResourceAssociationsPage.changeValueForResourceFromAssociatedList(resourceDisplayName, value);
		confRoomsPage = roomResourceAssociationsPage.clickSaveBtn();
		confRoomsPage.clickResourceIcon(resourceDisplayName);
		
		//verify that the quantity of the resource is reflected in rooms greed
		Assert.assertEquals(confRoomsPage.getResourceQuantity(roomName),"x " + value);
	}

	@AfterClass
	public void postConditions() throws InterruptedException, BiffException, IOException {
		
		//Delete resource created
		RoomsPage conferenceRoomPage = new RoomsPage();	
		conferenceRoomPage.clickResourcesLink();
		RootRestMethods.deleteResource(testData.get(0).get("ResourceName"));
		UIMethods.refresh();
	}
}
