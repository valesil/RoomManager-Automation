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
 * TC06: Verify that resource information is displayed in {ResourceInfo} form when a 
 * resource is selected
 * @author Marco Llano
 */
public class ResourcInformationIsDisplayedInResourceInfoPage {
	ResourcesPage resource;
	ResourceInfoPage resourceInfo;

	@Test(groups = {"FUNCTIONAL"})
	public void testResourcInformationIsDisplayedInResourceInfoPage() throws InterruptedException, BiffException, IOException {
		HomeAdminPage home = new HomeAdminPage();		
		resource = home.clickResourcesLink();	
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");

		//Variables declaration and initialize
		String resourceName = testData1.get(0).get("ResourceName");
		String resourceDisplayName = testData1.get(0).get("ResourceDisplayName");
		String resourceDescription = testData1.get(0).get("Description");
		String iconTitle = testData1.get(0).get("Icon");

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
	public void afterMethod() throws InterruptedException, BiffException, IOException {
		resource = resourceInfo.clickCancelResourceBtn();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");		
		RootRestMethods.deleteResource(testData1.get(0).get("ResourceName"));
		UIMethods.refresh();
	}
}