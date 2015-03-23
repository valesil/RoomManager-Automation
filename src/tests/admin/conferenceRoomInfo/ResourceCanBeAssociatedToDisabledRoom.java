package suits;package tests.admin.suits;
import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

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
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

public class ResourceCanBeAssociatedToDisabledRoom {

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String resourceDescription = testData.get(0).get("Description");
	String iconTitle = testData.get(0).get("Icon");	
	
	@BeforeClass
	public void precondition() {
		
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
	public void testResourceCanBeAssociatedToDisabledRoom() {
		
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confPage = homePage.clickConferenceRoomsLink();
		confPage.enableDisableIcon(roomName);
		RoomInfoPage infoPage = confPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage resourceAssociation = infoPage.clickResourceAssociationsLink();
		resourceAssociation.clickAddResourceToARoom(resourceDisplayName);
		confPage = resourceAssociation.clickSaveBtn();
		confPage.clickConferenceRoomsLink();
		Assert.assertTrue(resourceAssociation.searchResource(resourceDisplayName));	  
	}
	
	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		//Delete resource
		RoomsPage confPage = new RoomsPage();
		confPage.enableDisableIcon(roomName);
	    RootRestMethods.deleteResource(resourceName);
		UIMethods.refresh();
	}
}
