package tests.admin.conferenceRoomResourceAssociations;

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

/**
 * TC09: Verify that a resource can be associated to a room disabled.
 * @author Ruben Blanco
 *
 */
public class ResourceCanBeAssociatedToDisabledRoom {

	//reading to excel to create variables for resource creation
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String roomName = testData.get(0).get("Room Name");
	private String resourceName = testData.get(0).get("ResourceName");
	private String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	private String resourceDescription = testData.get(0).get("Description");
	private String iconTitle = testData.get(0).get("Icon");	
	
	@BeforeClass
	public void precondition() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	
		ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();
		
		//create a resource
		resourcesPage = newResourcePage
			.clickResourceIcon()
			.selectResourceIcon(iconTitle)
			.setResourceName(resourceName)
			.setResourceDisplayName(resourceDisplayName)
			.setResourceDescription(resourceDescription)
			.clickSaveResourceBtn();
	}

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceCanBeAssociatedToDisabledRoom() {
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confPage = homePage.clickConferenceRoomsLink();
		confPage.enableDisableIcon(roomName);
		RoomInfoPage infoPage = confPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage resourceAssociation = infoPage.clickResourceAssociationsLink();
		
		//Associate resource to room
		resourceAssociation.clickAddResourceToARoom(resourceDisplayName);
		confPage = resourceAssociation.clickSaveBtn();
		
		//Assertion for TC09
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
