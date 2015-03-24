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
 * a) Verify that a resource is created when the default icon is changed.
 * b) Verify that the icon displayed in {Icon Column} is changed when it is edited in 
 * {ResourceInfo} form
 * @author Marco Llano
 *
 */
public class ResourceIconCanBeUpdatedInResourcesPage {
	ResourcesPage resource;
	ResourceInfoPage resourceInfo;

	@Test(groups = {"FUNCTIONAL"})
	public void testResourceIconCanBeUpdatedInResourcesPage() throws InterruptedException, BiffException, IOException {		
		HomeAdminPage home = new HomeAdminPage();
		resource = home.clickResourcesLink();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");

		//Variables declaration and initialize
		String resourceName = testData1.get(2).get("ResourceName");
		String resourceDisplayName = testData1.get(2).get("ResourceDisplayName");
		String resourceIconTitle = testData1.get(2).get("Icon");
		String newIconTitle = testData1.get(1).get("Icon");
		
		//Create new resource
		ResourceCreatePage resourceCreate = resource.clickAddResourceBtn();		
		resource = resourceCreate.clickResourceIcon()
				.selectResourceIcon(resourceIconTitle)
				.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.clickSaveResourceBtn();
		
		//a) Resource created when default icon is changed		
		Assert.assertTrue(resource.getResourceIcon(resourceIconTitle));
		
		//Update resource icon
		resourceInfo = resource.openResourceInfoPage(resourceName);
		resource = resourceInfo.clickResourceIcon()
				.selectResourceIcon(newIconTitle)
				.clickSaveResourceBtn();

		//b) If the icon is updated successfully the assertion will return true.
		Assert.assertTrue(resourceInfo.getResourceIcon(newIconTitle));
	}

	@AfterMethod
	public void afterMethod() throws InterruptedException, BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("Resources");		
		RootRestMethods.deleteResource(testData1.get(2).get("ResourceName"));
		UIMethods.refresh();
	}
}
