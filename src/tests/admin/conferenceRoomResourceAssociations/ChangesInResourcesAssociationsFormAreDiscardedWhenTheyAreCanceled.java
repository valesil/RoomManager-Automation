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

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC11: Verify that changes made  in {Resources Associations} form are
 * discarded when they are canceled. 
 * @author Ruben Blanco
 *
 */
public class ChangesInResourcesAssociationsFormAreDiscardedWhenTheyAreCanceled {
	
	//reading to excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomList = excelReader.getMapValues("Resources");
	private String resourceName = roomList.get(0).get("ResourceName");
	private String resourceDisplayName = roomList.get(0).get("ResourceDisplayName");
	private String resourceDescription = roomList.get(0).get("Description");
	private String iconTitle = roomList.get(0).get("Icon");

	@BeforeClass(groups = "FUNCTIONAL")
	public void precondition() throws BiffException, IOException {
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
	}

	@Test(groups = "FUNCTIONAL")
	public void testChangesInResourcesAssociationsFormAreDiscardedWhenTheyAreCanceled() {
		
		//reading to excel to create variables
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("RoomInfo");
		String resourceName = testData.get(0).get("AssociatedResource").trim();
		String quantityToAdd = testData.get(0).get("ValueToModify").trim();
		String roomName = testData.get(0).get("DisplayName");
		
		//navigate to home admin page
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage resourceAssociation = roomInfoPage
			.clickResourceAssociationsLink();
		resourceAssociation.clickAddResourceToARoom(resourceName)
			.changeValueForResourceFromAssociatedList(resourceName, quantityToAdd)
			.clickCancelBtn();

		roomsPage = homeAdminPage.clickConferenceRoomsLink();
		roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		resourceAssociation = roomInfoPage.clickResourceAssociationsLink();
		
		//verify if changes are discarted
		boolean existChanges = resourceAssociation.verifyChanges(resourceName);
		roomInfoPage.clickCancelBtn();
		
		//Assertion for TC11
		Assert.assertTrue(existChanges);	
	}

	@AfterClass(groups = "FUNCTIONAL")
	public void cleanRoom() throws MalformedURLException, IOException {
		//Delete resource
		RootRestMethods.deleteResource(resourceName);
	}
}
