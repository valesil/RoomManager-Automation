package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

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
	private ResourcesPage resourcesPage;
	private ResourceInfoPage resourceInfoPage;
	
	//ExcelReader is used to read rooms data
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> resourcesDataList = excelReader.getMapValues("Resources");

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceCanBeUpdatedInResourceInfo() throws InterruptedException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		resourcesPage = homeAdminPage.clickResourcesLink();

		//Variables declaration and initialize
		String resourceName = resourcesDataList.get(0).get("ResourceName");
		String resourceDisplayName = resourcesDataList.get(0).get("ResourceDisplayName");
		String newResourceName = resourcesDataList.get(1).get("ResourceName");
		String newResourceDisplayName = resourcesDataList.get(1).get("ResourceDisplayName");

		//Create a resourcesPage
		ResourceCreatePage resourceCreatePage  = resourcesPage.clickAddResourceBtn();		
		resourcesPage = resourceCreatePage.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.clickSaveResourceBtn();

		//Update the created resourcesPage then click on cancel to verify that changes are not saved
		resourceInfoPage = resourcesPage.openResourceInfoPage(resourceName);
		resourcesPage = resourceInfoPage
				.setResourceName(newResourceName)
				.setResourceDisplayName(newResourceDisplayName)
				.clickCancelResourceBtn();

		//Assertion for TC17
		resourceInfoPage = resourcesPage.openResourceInfoPage(resourceName);
		Assert.assertFalse(resourceInfoPage.getResourceName().contains(newResourceName));
		Assert.assertFalse(resourceInfoPage.getResourceDisplayName().contains(newResourceDisplayName));
	}	

	@AfterMethod(groups = {"FUNCTIONAL"})
	public void afterMethod() throws MalformedURLException, IOException {	
		resourcesPage = resourceInfoPage.clickCancelResourceBtn();		
		RootRestMethods.deleteResource(resourcesDataList.get(0).get("ResourceName"));
	}
}