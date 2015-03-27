package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC18: Verify that {total items} information displays resources quantity below of {Resource grid} 
 * according {page size & items > 1- 50} setting.
 * @author Juan Carlos Guevara
 */
public class TotalItemsInformationLabelDisplaysResourcesQuantity {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");

	@Test(groups = {"FUNCTIONAL"})
	public void testTotalItemsInformationLabelDisplaysResourcesQuantity() 
			throws InterruptedException {
		int counter = 0;
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();
		for(Map<String, String> resource : testData){	

			//create a resource
			ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();		
			resourceCreatePage.clickResourceIcon()
			.selectResourceIcon(resource.get("Icon"))
			.setResourceName(resource.get("ResourceName"))
			.setResourceDisplayName(resource.get("ResourceDisplayName"))
			.setResourceDescription(resource.get("Description"));
			resourcesPage = resourceCreatePage.clickSaveResourceBtn();
			counter = counter + 1;

			//Assertion for TC18 
			Assert.assertTrue(resourcesPage.searchTotalItemsValue(Integer.toString(counter)));			
		}
	}

	@AfterClass
	public void cleanRoom() throws MalformedURLException, IOException  {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		homeAdminPage.clickResourcesLink();
		for(Map<String, String> resource : testData){					
			RootRestMethods.deleteResource(resource.get("ResourceName"));
		}
		UIMethods.refresh();
	}
}
