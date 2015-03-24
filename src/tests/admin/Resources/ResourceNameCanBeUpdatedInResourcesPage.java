package tests.admin.Resources;

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
 * Verify that the name displayed in {Name} column is changed when it is edited in 
 * {ResourceInfo} form
 * @author Marco Llano
 *
 */
public class ResourceNameCanBeUpdatedInResourcesPage {
	ResourcesPage resource;
	ResourceInfoPage resourceInfo;

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceNameCanBeUpdatedInResourcesPage() throws InterruptedException, BiffException, IOException {		
		HomeAdminPage home = new HomeAdminPage();
		resource = home.clickResourcesLink();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");

		//Variables declaration and initialize
		String resourceName = testData1.get(0).get("ResourceName");
		String newResourceName = testData1.get(1).get("ResourceName");
		String resourceDisplayName = testData1.get(0).get("ResourceDisplayName");

		//Create new resource
		ResourceCreatePage resourceCreate = resource.clickAddResourceBtn();		
		resource = resourceCreate.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.clickSaveResourceBtn();

		//Update resourceName field
		resourceInfo = resource.openResourceInfoPage(resourceName);
		resource = resourceInfo.setResourceName(newResourceName)
				.clickSaveResourceBtn();

		//If the resource name is found, will return true and pass the TC.
		Assert.assertTrue(resource.isResourceNameDisplayedInResourcesPage(newResourceName));
	}

	@AfterMethod
	public void afterMethod() throws InterruptedException, BiffException, IOException {	
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");		
		RootRestMethods.deleteResource(testData1.get(1).get("ResourceName"));
		UIMethods.refresh();
	}
}
