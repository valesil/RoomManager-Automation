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
 * TC03: Verify that a resource is created when required values are inserted in Add form
 * TC15: Verify that a resource is created when all values (required, optional), are inserted in Add form
 * @author Marco Llano
 */
public class ResourceCreatedWithRequiredAndNotRequiredValue {	
	ResourcesPage resource;
	ResourceInfoPage resourceInfo;

	@Test(groups = {"ACCEPTANCE"})
	public void testResourceCreatedWithRequiredValue() throws InterruptedException, BiffException, IOException {
		HomeAdminPage home = new HomeAdminPage();
		resource = home.clickResourcesLink();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");

		//Variable declaration and initialize
		String iconTitle = testData1.get(1).get("Icon");
		String resourceName = testData1.get(1).get("ResourceName");
		String resourceDisplayName = testData1.get(1).get("ResourceDisplayName");
		String resourceDescription = testData1.get(1).get("Description");

		//Create new resource
		ResourceCreatePage resourceCreate  = resource.clickAddResourceBtn();		
		resource = resourceCreate.clickResourceIcon()
				.selectResourceIcon(iconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();

		//Assertion for TC03 and required values from TC15
		resourceInfo = resource.openResourceInfoPage(resourceName);
		Assert.assertTrue(resourceInfo.getResourceName().contains(resourceName));
		Assert.assertTrue(resourceInfo.getResourceDisplayName().contains(resourceDisplayName));
		
		//Assertion for TC15 optional values
		Assert.assertTrue(resourceInfo.getResourceIcon(iconTitle));
		Assert.assertTrue(resourceInfo.getResourceDescription().contains(resourceDescription));
	}

	@AfterMethod
	public void afterMethod() throws InterruptedException, BiffException, IOException {
		resourceInfo.clickCancelResourceBtn();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");		
		RootRestMethods.deleteResource(testData1.get(1).get("ResourceName"));
		UIMethods.refresh();
	}
}