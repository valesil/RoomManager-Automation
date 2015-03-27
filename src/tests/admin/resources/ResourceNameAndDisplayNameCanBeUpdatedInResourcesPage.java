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
 * TC34: Verify that the display name in {Display name} column is changed when it is edited in 
 * {ResourceInfo} form.
 * TC30: Verify that the name displayed in {Name} column is changed when it is edited in 
 * {ResourceInfo} form
 * @author Marco Llano
 */
public class ResourceNameAndDisplayNameCanBeUpdatedInResourcesPage {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceDisplayNameCanBeUpdatedInResourcePage() throws InterruptedException {
		HomeAdminPage home = new HomeAdminPage();
		ResourcesPage resource = home.clickResourcesLink();

		//Variable declaration and initialize
		String resourceName = testData.get(1).get("ResourceName");
		String resourceDisplayName = testData.get(1).get("ResourceDisplayName");
		String newResourceDisplayName = testData.get(2).get("ResourceDisplayName");

		//Create new resource
		ResourceCreatePage resourceCreate = resource.clickAddResourceBtn();		
		resource = resourceCreate.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.clickSaveResourceBtn();

		//Update resourceDisplayName field
		ResourceInfoPage resourceInfo = resource.openResourceInfoPage(resourceName);		
		resource = resourceInfo.setResourceDisplayName(newResourceDisplayName)
				.clickSaveResourceBtn();

		//Assertion for TC34
		Assert.assertTrue(resource.isResourceDisplayNameDisplayedInResourcesPage(newResourceDisplayName));

		//Assertion for TC30
		Assert.assertTrue(resource.isResourceNameDisplayedInResourcesPage(resourceName));
	}

	@AfterMethod
	public void afterMethod() throws MalformedURLException, IOException {	
		RootRestMethods.deleteResource(testData.get(1).get("ResourceName"));
		UIMethods.refresh();
	}
}