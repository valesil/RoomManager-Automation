package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

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
	ResourcesPage resource;
	ResourceInfoPage resourceInfo;
	
	@Test(groups = {"FUNCTIONAL"})
	public void testResourceCanBeUpdatedInResourceInfo() throws InterruptedException, BiffException, IOException {
		HomeAdminPage home = new HomeAdminPage();
		resource = home.clickResourcesLink();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");

		//Variables declaration and initialize
		String resourceName = testData1.get(0).get("ResourceName");
		String resourceDisplayName = testData1.get(0).get("ResourceDisplayName");
		String newResourceName = testData1.get(1).get("ResourceName");
		String newResourceDisplayName = testData1.get(1).get("ResourceDisplayName");

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
	public void afterMethod() throws InterruptedException, BiffException, IOException {	
		resourceInfo.clickCancelResourceBtn();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");		
		RootRestMethods.deleteResource(testData1.get(0).get("ResourceName"));
		UIMethods.refresh();
	}
}
