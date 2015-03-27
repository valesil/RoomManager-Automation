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
 * TC06: Verify that resource information is displayed in {ResourceInfo} form when a 
 * resource is selected
 * @author Marco Llano
 */
public class ResourcInformationIsDisplayedInResourceInfoPage {
	private ResourcesPage resource;
	private ResourceInfoPage resourceInfo;
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");

	@Test(groups = {"FUNCTIONAL"})
	public void testResourcInformationIsDisplayedInResourceInfoPage() throws InterruptedException {
		HomeAdminPage home = new HomeAdminPage();		
		resource = home.clickResourcesLink();	

		//Variables declaration and initialize
		String resourceName = testData.get(0).get("ResourceName");
		String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
		String resourceDescription = testData.get(0).get("Description");
		String iconTitle = testData.get(0).get("Icon");

		//Create new resource
		ResourceCreatePage resourceCreate = resource.clickAddResourceBtn();
		resource = resourceCreate.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();

		//Assertion for TC06
		resourceInfo = resource.openResourceInfoPage(resourceName);
		Assert.assertTrue(resourceInfo.getResourceName().contains(resourceName));
		Assert.assertTrue(resourceInfo.getResourceDisplayName().contains(resourceDisplayName));
		Assert.assertTrue(resourceInfo.getResourceDescription().contains(resourceDescription));
		Assert.assertTrue(resourceInfo.getResourceIcon(iconTitle));		
	}	

	@AfterMethod
	public void afterMethod() throws MalformedURLException, IOException {
		resource = resourceInfo.clickCancelResourceBtn();		
		RootRestMethods.deleteResource(testData.get(0).get("ResourceName"));
		UIMethods.refresh();
	}
}