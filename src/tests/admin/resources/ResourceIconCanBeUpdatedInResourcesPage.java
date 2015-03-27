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
 * TC16: Verify that a resource is created when the default icon is changed.
 * TC29: Verify that the icon displayed in {Icon Column} is changed when it is edited in 
 * {ResourceInfo} form
 * @author Marco Llano
 */
public class ResourceIconCanBeUpdatedInResourcesPage {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceIconCanBeUpdatedInResourcesPage() throws InterruptedException {		
		HomeAdminPage home = new HomeAdminPage();
		ResourcesPage resource = home.clickResourcesLink();

		//Variables declaration and initialize
		String resourceName = testData.get(2).get("ResourceName");
		String resourceDisplayName = testData.get(2).get("ResourceDisplayName");
		String resourceIconTitle = testData.get(2).get("Icon");
		String newIconTitle = testData.get(1).get("Icon");

		//Create new resource
		ResourceCreatePage resourceCreate = resource.clickAddResourceBtn();		
		resource = resourceCreate.clickResourceIcon()
				.selectResourceIcon(resourceIconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.clickSaveResourceBtn();

		//Assertion for TC16	
		Assert.assertTrue(resource.getResourceIcon(resourceIconTitle));

		//Update resource icon
		ResourceInfoPage resourceInfo = resource.openResourceInfoPage(resourceName);
		resource = resourceInfo.clickResourceIcon()
				.selectResourceIcon(newIconTitle)
				.clickSaveResourceBtn();

		//Assertion for TC29
		Assert.assertTrue(resourceInfo.getResourceIcon(newIconTitle));
	}

	@AfterMethod
	public void afterMethod() throws MalformedURLException, IOException {		
		RootRestMethods.deleteResource(testData.get(2).get("ResourceName"));
		UIMethods.refresh();
	}
}