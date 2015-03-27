package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourceInfoPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC17: Verify that changed in {Resources Info} are discarded when it is canceled.
 * @author Marco Llano
 */
public class ResourceCanNotBeUpdatedInResourceInfo {
	private ResourcesPage resource;
	private ResourceInfoPage resourceInfo;
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceCanBeUpdatedInResourceInfo() throws InterruptedException {
		HomeAdminPage home = new HomeAdminPage();
		resource = home.clickResourcesLink();

		//Variables declaration and initialize
		String resourceName = testData.get(0).get("ResourceName");
		String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
		String newResourceName = testData.get(1).get("ResourceName");
		String newResourceDisplayName = testData.get(1).get("ResourceDisplayName");

		//Create a resource
		ResourceCreatePage resourceCreate  = resource.clickAddResourceBtn();		
		resource = resourceCreate.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.clickSaveResourceBtn();

		//Update the created resource then click on cancel to verify that changes are not saved
		resourceInfo = resource.openResourceInfoPage(resourceName);
		resource = resourceInfo
				.setResourceName(newResourceName)
				.setResourceDisplayName(newResourceDisplayName)
				.clickCancelResourceBtn();

		//Assertion for TC17
		resourceInfo = resource.openResourceInfoPage(resourceName);
		Assert.assertFalse(resourceInfo.getResourceName().contains(newResourceName));
		Assert.assertFalse(resourceInfo.getResourceDisplayName().contains(newResourceDisplayName));
	}	

	@AfterMethod
	public void afterMethod() throws MalformedURLException, IOException {	
		resource = resourceInfo.clickCancelResourceBtn();		
		RootRestMethods.deleteResource(testData.get(0).get("ResourceName"));
		UIMethods.refresh();
	}
}