package tests.admin.Resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceAssociationsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourceInfoPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * Verify that rooms name associated to a resource are displayed on {Name} column in 
 * {ResourceInfo>Resource Associations} form when it is selected
 * @author Marco Llano
 *
 */
public class AssociatedRoomIsDisplayedInResourceAssociationPage {

	ResourceAssociationsPage resourceAssociation;

	@Test(groups = {"FUNCTIONAL"})
	public void testAssociatedRoomIsDisplayedInResourceAssociationPage() throws InterruptedException, BiffException, IOException {
		HomeAdminPage home = new HomeAdminPage();
		ResourceInfoPage resourceInfo;
		ResourcesPage resource = home.clickResourcesLink();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Resources");

		//Variables declaration and initialize
		String resourceName = testData.get(0).get("ResourceName");
		String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
		String quantity = testData.get(0).get("Value");
		String roomDisplayName = testData.get(0).get("Room Name");
		String roomDisplayName1 = testData.get(1).get("Room Name");
		String roomDisplayName2 = testData.get(2).get("Room Name");

		//create a resource					
		ResourceCreatePage resourceCreate = resource.clickAddResourceBtn();		
		resource = resourceCreate.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.clickSaveResourceBtn();

		//Associate a resource to a group of rooms by resource display name
		RoomsPage roomsPage = home.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomDisplayName);
		RoomResourceAssociationsPage roomResourceAssociation = roomInfoPage.clickResourceAssociationsLink();
		roomsPage = roomResourceAssociation.clickAddResourceToARoom(resourceDisplayName)
				.changeValueForResourceFromAssociatedList(resourceDisplayName, quantity)
				.clickSaveBtn();

		roomInfoPage = roomsPage.doubleClickOverRoomName(roomDisplayName1);
		roomResourceAssociation = roomInfoPage.clickResourceAssociationsLink();
		roomsPage = roomResourceAssociation.clickAddResourceToARoom(resourceDisplayName)
				.changeValueForResourceFromAssociatedList(resourceDisplayName, quantity)
				.clickSaveBtn();

		roomInfoPage = roomsPage.doubleClickOverRoomName(roomDisplayName2);
		roomResourceAssociation = roomInfoPage.clickResourceAssociationsLink();
		roomsPage = roomResourceAssociation.clickAddResourceToARoom(resourceDisplayName)
				.changeValueForResourceFromAssociatedList(resourceDisplayName, quantity)
				.clickSaveBtn();

		//Open ResourceAssociationsPage
		resource = home.clickResourcesLink();
		resourceInfo = resource.openResourceInfoPage(resourceDisplayName);
		resourceAssociation = resourceInfo.clickResourceAssociationLink();

		//Recover all rooms associated to a resource in a LinkedList
		LinkedList<String> list = RootRestMethods.getRoomNamesByResource(resourceName);

		//Verify that room names are displayed in ResourceAssociationsPage
		for (String l : list) {
			Assert.assertTrue(resourceAssociation.getRoomDisplayNameFromResourceAssociationPage(l));
		}
	}

	@AfterMethod
	public void afterMethod() throws InterruptedException, BiffException, IOException {	
		resourceAssociation.clickCancelResourceBtn();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");		
		RootRestMethods.deleteResource(testData1.get(0).get("ResourceName"));
		UIMethods.refresh();
	}
}
