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
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;


/**
 * TC35: Verify that an error message is displayed when a resource with a {Name} registered is 
 * added.
 * @author Juan Carlos Guevara
 */
public class AnErrorMessageIsDisplayedWhenAResourceWithANameRegisteredIsAdded {

	//Reading resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String resourceName = testData.get(0).get("ResourceName");
	private String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	private String resourceDescription = testData.get(0).get("Description");
	private String iconTitle = testData.get(0).get("Icon");

	@BeforeClass(groups = "NEGATIVE")
	public void createAResource() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	

		//Create a resource
		ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();		
		resourceCreatePage.clickResourceIcon()
		.selectResourceIcon(iconTitle)
		.setResourceName(resourceName)
		.setResourceDisplayName(resourceDisplayName)
		.setResourceDescription(resourceDescription)
		.clickSaveResourceBtn();
	}

	@Test(groups = "NEGATIVE")
	public void testAnErrorMessageIsDisplayedWhenAResourceWithANameRegisteredIsAdded() {
		ResourcesPage resourcesPage = new ResourcesPage();	

		//Create a resource with the same name
		ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();		
		resourceCreatePage.setResourceName(resourceName)
		.setResourceDisplayName(resourceDisplayName)
		.clickSaveResourceWithErrorBtn();

		//Assertion for TC35  		
		Assert.assertTrue(resourceCreatePage.verifyErrorMessage(RESOURCE_NAME_DUPLICATED));	
		resourceCreatePage.clickCancelResourceBtn();
	}

	@AfterClass(groups = "NEGATIVE")
	public void deleteResource() throws MalformedURLException, IOException {	

		//Delete resource with API rest method
		RootRestMethods.deleteResource(resourceName);
	}
}
