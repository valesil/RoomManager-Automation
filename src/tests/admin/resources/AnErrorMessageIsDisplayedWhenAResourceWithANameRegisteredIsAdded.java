package tests.admin.resources;

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
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;


/**
 * Test Cases # 35 ResourcesPage
 * @author administrator
 * Verify that an error message is displayed when a resource with a {Name} registered is added.
 */
public class AnErrorMessageIsDisplayedWhenAResourceWithANameRegisteredIsAdded {
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
		
		//create a resource
		ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();		
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

	@Test(groups = {"NEGATIVE"})
	public void testAnErrorMessageIsDisplayedWhenAResourceWithANameRegisteredIsAdded() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	

		//create a resource with the same name
		ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();		
		newResourcePage.setResourceName(resourceName)
						.setResourceDisplayName(resourceDisplayName)
						.clickSaveResourceBtn();

		//Verify if the resource exists 		
		Assert.assertTrue(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED));	
		resourcesPage = newResourcePage.clickCancelResourceBtn();
	}
	
	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		
		//delete resource
		RoomsPage confRoomPage = new RoomsPage();
		confRoomPage.clickResourcesLink();
		RootRestMethods.deleteResource(resourceName);
		UIMethods.refresh();
	}
}
