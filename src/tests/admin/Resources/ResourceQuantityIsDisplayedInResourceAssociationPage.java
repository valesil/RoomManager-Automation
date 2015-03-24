package tests.admin.Resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import java.io.IOException;
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
 * Verify that resource quantity associated to a room is displayed on {Quantity} column
 * in {ResourceInfo>Resource Associations} form when it is selected
 * @author Marco Llano
 *
 */
public class ResourceQuantityIsDisplayedInResourceAssociationPage {
	ResourcesPage resource;
	ResourceInfoPage resourceInfo;
	ResourceAssociationsPage resourceAssoiation;

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceQuantityIsDisplayedInResourceAssociationPage() throws InterruptedException, BiffException, IOException {
		HomeAdminPage home = new HomeAdminPage();
		resource = home.clickResourcesLink();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");

		//Variables declaration and initialize
		String resourceName = testData1.get(2).get("ResourceName");
		String resourceDisplayName = testData1.get(2).get("ResourceDisplayName");
		String resourceDescription = testData1.get(2).get("Description");
		String iconTitle = testData1.get(2).get("Icon");
		String quantity = testData1.get(2).get("Value");
		String roomDisplayName = testData1.get(2).get("Room Name");

		//Create a resource
		ResourceCreatePage resourceCreate  = resource.clickAddResourceBtn();		
		resource = resourceCreate.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();
		
		//Associate a resource to a room by resource display name
		RoomsPage roomsPage = home.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomDisplayName);
		RoomResourceAssociationsPage roomResourceAssociation = roomInfoPage.clickResourceAssociationsLink();
		roomsPage = roomResourceAssociation.clickAddResourceToARoom(resourceDisplayName)
							   .changeValueForResourceFromAssociatedList(resourceDisplayName, quantity)
							   .clickSaveBtn();
		
		//Verify if quantity is displayed in ResourceAssociationsPage by room display name
		resource = home.clickResourcesLink();
		resourceInfo = resource.openResourceInfoPage(resourceDisplayName);
		resourceAssoiation = resourceInfo.clickResourceAssociationLink();
		String result = resourceAssoiation.getResourceQuantityByRoomDisplayName(roomDisplayName);
		Assert.assertEquals(result, "x " + quantity);
	}
	
	@AfterMethod
	public void afterMethod() throws InterruptedException, BiffException, IOException {	
		resourceAssoiation.clickCancelResourceBtn();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");		
		RootRestMethods.deleteResource(testData1.get(2).get("ResourceName"));
		UIMethods.refresh();
	}
}
