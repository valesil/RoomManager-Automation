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
import framework.pages.admin.resources.ResourceInfoPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * Test case # 37
 * @author JC
 * Verify that an error message is displayed when a resource is updated with a {Name} registered.
 */
public class AnErrorMessageIsDisplayedWhenAResourceIsUpdatedWithANameAlreadyRegistered {

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("BackUpResources");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String resourceUpdateName = testData.get(1).get("ResourceName");

	@BeforeClass
	public void preconditions() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	

		//create resources
		for(Map<String, String> resource : testData){					
			ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();		
			resourcesPage = newResourcePage.clickResourceIcon()
					.selectResourceIcon(resource.get("Icon"))
					.setResourceName(resource.get("ResourceName"))
					.setResourceDisplayName(resource.get("ResourceDisplayName"))
					.setResourceDescription(resource.get("Description"))
					.clickSaveResourceBtn();
			if(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED)== true){
				UIMethods.refresh();
			}
		}
	}

	@Test(groups = {"NEGATIVE"})
	public void testAnErrorMessageIsDisplayedWhenAResourceIsUpdatedWithANameAlreadyRegistered() 
			throws InterruptedException {
		ResourcesPage resourcesPage = new ResourcesPage();	

		//create a resource with the same name
		ResourceInfoPage resourceInfoPage = resourcesPage.openResourceInfoPage(resourceUpdateName);		
		resourceInfoPage.setResourceName(resourceName)
						.setResourceDisplayName(resourceDisplayName)
						.clickSaveResourceBtn();

		//Verify if the resource exists 		
		Assert.assertTrue(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED));	
		resourcesPage = resourceInfoPage.clickCancelResourceBtn();
	}
	
	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		
		//delete resource
		RoomsPage conferenceRoomPage = new RoomsPage();	
		conferenceRoomPage.clickResourcesLink();
		for(Map<String, String> resource : testData){					
			RootRestMethods.deleteResource(resource.get("ResourceName"));
		}
		UIMethods.refresh();
	}
}
