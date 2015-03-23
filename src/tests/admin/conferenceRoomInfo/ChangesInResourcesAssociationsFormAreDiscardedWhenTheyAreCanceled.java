package tests.admin.suits;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

public class ChangesInResourcesAssociationsFormAreDiscardedWhenTheyAreCanceled {

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String resourceDescription = testData.get(0).get("Description");
	String iconTitle = testData.get(0).get("Icon");
	
	@BeforeTest
	public void precondition() throws BiffException, IOException {
		
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	
		ResourceCreatePage newResourcePage = new ResourceCreatePage();
		
		//create a resource
		newResourcePage = resourcesPage.clickAddResourceBtn();		
		resourcesPage = newResourcePage.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();
	}

	@Test
	public void testChangesInResourcesAssociationsFormAreDiscardedWhenTheyAreCanceled() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("RoomInfo");

		String resourceName = testData.get(0).get("AssociatedResource").trim();
		String quantityToAdd = testData.get(0).get("ValueToModify").trim();

		String roomName = testData.get(0).get("DisplayName");
		HomeAdminPage home = new HomeAdminPage();
		RoomsPage conferenceRoomPage = home.clickConferenceRoomsLink();
		RoomInfoPage infoPage = conferenceRoomPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage crresourceAssociationsPage = infoPage.clickResourceAssociationsLink();
		crresourceAssociationsPage
			.clickAddResourceToARoom(resourceName)
			.changeValueForResourceFromAssociatedList(resourceName, quantityToAdd)
			.clickCancelBtn();
		
		conferenceRoomPage = home.clickConferenceRoomsLink();
		infoPage = conferenceRoomPage.doubleClickOverRoomName(roomName);
		crresourceAssociationsPage = infoPage.clickResourceAssociationsLink();
		boolean existChanges = crresourceAssociationsPage.verifyChanges(resourceName);
		infoPage.clickCancelBtn();
		Assert.assertTrue(existChanges);	
	}
	
	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		//Delete resource
	    RootRestMethods.deleteResource(resourceName);
		UIMethods.refresh();
	}
}
