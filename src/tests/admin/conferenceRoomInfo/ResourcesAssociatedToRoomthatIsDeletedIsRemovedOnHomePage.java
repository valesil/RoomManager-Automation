package tests.admin.suits;

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
import framework.pages.admin.LoginPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SettingsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;
import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

public class ResourcesAssociatedToRoomthatIsDeletedIsRemovedOnHomePage {
	
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String resourceDescription = testData.get(0).get("Description");
	String iconTitle = testData.get(0).get("Icon");	
	String quantity = testData.get(0).get("Value");
	
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
		RoomsPage conferenceRoomPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = conferenceRoomPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage crresourceAssociationsPage = infoPage.clickResourceAssociationsLink();
		crresourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName)
		.changeValueForResourceFromAssociatedList(resourceDisplayName,quantity)
		.clickSaveBtn();
	}

	@Test
	public void testResourcesAssociatedToRoomthatIsDeletedIsRemovedOnHomePage() {

		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData2 = excelReader.getMapValues("RoomInfo");
		String resourceName = testData2.get(0).get("AssociatedResource");
		String amount = testData2.get(0).get("Quantity");
		String displayName = testData2.get(0).get("DisplayName");

		LoginPage loginPage = new LoginPage();
		HomeAdminPage homePage = loginPage.clickSigninLink();
		RoomsPage conferencePage = homePage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = conferencePage.doubleClickOverRoomName(displayName);
		RoomResourceAssociationsPage crresourceAssociationsPage = infoPage.clickResourceAssociationsLink();
		crresourceAssociationsPage
		.removeResourceFromAssociatedList(resourceName)
		.clickSaveBtn();

		SettingsPage setting = new SettingsPage();
		HomePage home = setting.selectRoom(displayName);
		Assert.assertFalse(home.VerifyResourceIsAsociated(resourceName, amount));
	}
	
	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		//Delete resource
	    RootRestMethods.deleteResource(resourceName);
		UIMethods.refresh();
	}
}
