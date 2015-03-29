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

	//Reading resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String resourceName = testData.get(0).get("ResourceName");
	private String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	private String resourceUpdateName = testData.get(1).get("ResourceName");

	@BeforeClass(groups = "NEGATIVE")
	public void createResources() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();

		//Create resources
		for(Map<String, String> resource : testData){
			ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();
			ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();		
			resourceCreatePage.clickResourceIcon()
			.selectResourceIcon(resource.get("Icon"))
			.setResourceName(resource.get("ResourceName"))
			.setResourceDisplayName(resource.get("ResourceDisplayName"))
			.setResourceDescription(resource.get("Description"))
			.clickSaveResourceBtn();				
		}
	}

	@Test(groups = "NEGATIVE")
	public void testAnErrorMessageIsDisplayedWhenAResourceIsUpdatedWithANameAlreadyRegistered() 
			throws InterruptedException {
		ResourcesPage resourcesPage = new ResourcesPage();	

		//Create a resource with the same name
		ResourceInfoPage resourceInfoPage = resourcesPage.openResourceInfoPage(resourceUpdateName);		
		resourceInfoPage.setResourceName(resourceName)
		.setResourceDisplayName(resourceDisplayName)
		.clickSaveResourceWithErrorBtn();

		//Assertion for TC37 		
		Assert.assertTrue(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED));	
		resourceInfoPage.clickCancelResourceBtn();
	}

	@AfterClass(groups = "NEGATIVE")
	public void deleteResource() throws MalformedURLException, IOException {
		
		//Delete resource with API rest method
		for(Map<String, String> resource : testData){					
			RootRestMethods.deleteResource(resource.get("ResourceName"));
		}
	}
}
