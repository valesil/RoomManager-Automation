package tests.admin.roomResourceAssociations;
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
 * Test Case # 2 Room Resource Associations
 * @author Juan Carlos Guevara
 * Verify that a resource can be associated to a room enabled.
 */
public class AResourceCanBeAssociatedToARoomEnabled {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String resourceDescription = testData.get(0).get("Description");
	String iconTitle = testData.get(0).get("Icon");
	
	@BeforeClass
	public void precondition() throws InterruptedException, BiffException, IOException {
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
		if(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED)== true){
			UIMethods.refresh();
		}

	}
	
	@Test(groups = {"ACCEPTANCE"})
	public void testAResourceCanBeAssociatedToARoomEnabled() throws InterruptedException,
	BiffException, IOException{
		
		//Associate the resource to a room
		RoomsPage confRoomsPage = new RoomsPage();
		confRoomsPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = confRoomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomsResourceAssociationsPage = infoPage
				.clickResourceAssociationsLink();
		roomsResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName);
		confRoomsPage = roomsResourceAssociationsPage.clickSaveBtn();
		UIMethods.refresh();
		confRoomsPage.clickConferenceRoomsLink();
		
		//Verify that the resource is available
		Assert.assertTrue(roomsResourceAssociationsPage.searchResource(resourceDisplayName));	
	}
	
	@AfterClass
	public void postCondition() {
		
		//Delete resource
		RoomsPage confRoomsPage = new RoomsPage();
		confRoomsPage.clickResourcesLink();
		RootRestMethods.deleteResource(resourceName);
		UIMethods.refresh();
	}
}



