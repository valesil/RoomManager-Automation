package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourceDeletePage;
import framework.pages.admin.resources.ResourceInfoPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.utils.readers.ExcelReader;

/**
 * TC01: Verify that a resource can be deleted.
 * @author Marco Llano
 */
public class ResourceCanBeDeleted {	
	ResourcesPage resource;
	ResourceInfoPage resourceInfo;	

	@Test(groups = {"ACCEPTANCE"})
	public void testResourceCanBeDeleted() throws InterruptedException, BiffException, IOException {	
		HomeAdminPage home = new HomeAdminPage();
		resource = home.clickResourcesLink();
		ResourceCreatePage resourceCreate = resource.clickAddResourceBtn();	
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");

		//Variable declaration and initialize
		String resourceName = testData1.get(2).get("ResourceName");
		String resourceDisplayName = testData1.get(2).get("ResourceDisplayName");
		String resourceDescription = testData1.get(2).get("Description");

		//Create new resource
		resource = resourceCreate.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.setResourceDescription(resourceDescription)
				.clickSaveResourceBtn();	

		//Delete created resource
		ResourceDeletePage deleteResource = resource.selectResourceCheckbox(resourceName)
				.clickRemoveBtn();
		resource = deleteResource.clickConfirmRemoveBtn();

		//Assertion for TC01
		Assert.assertFalse(resource.isResourceNameDisplayedInResourcesPage(resourceName));
	}
}
