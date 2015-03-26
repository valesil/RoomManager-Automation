package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.MessageConstants.RESOURCE_NAME_DUPLICATED;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomBaseAbstractPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourceInfoPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC37: Verify that an error message is displayed when a resource is updated with a {Name} 
 * registered.
 * @author Juan Carlos Guevara
 */
public class AnErrorMessageIsDisplayedWhenAResourceIsUpdatedWithANameAlreadyRegistered {

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String resourceUpdateName = testData.get(1).get("ResourceName");

	@BeforeClass
	public void preconditions() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();

		//create resources
		for(Map<String, String> resource : testData){
			ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();
			ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();		
			newResourcePage.clickResourceIcon()
			.selectResourceIcon(resource.get("Icon"))
			.setResourceName(resource.get("ResourceName"))
			.setResourceDisplayName(resource.get("ResourceDisplayName"))
			.setResourceDescription(resource.get("Description"))
			.clickSaveResourceBtn();				
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
		.clickSaveResourceWithErrorBtn();

		//Assertion for TC37 		
		Assert.assertTrue(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED));	
		resourcesPage = resourceInfoPage.clickCancelResourceBtn();
	}

	@AfterClass
	public void cleanRoom() throws MalformedURLException, IOException {

		//delete resource
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		homeAdminPage.clickResourcesLink();
		for(Map<String, String> resource : testData){					
			RootRestMethods.deleteResource(resource.get("ResourceName"));
		}
		UIMethods.refresh();
	}
}
