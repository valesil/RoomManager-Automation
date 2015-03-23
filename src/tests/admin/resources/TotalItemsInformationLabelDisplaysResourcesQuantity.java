package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.MessageConstants.RESOURCE_NAME_DUPLICATED;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomBaseAbstractPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * Test Case #18 Resources
 * @author Juan Carlos Guevara
 * Verify that {total items} information displays resources quantity below of {Resource grid} 
 * according {page size & items > 1- 50} setting.
 */
public class TotalItemsInformationLabelDisplaysResourcesQuantity {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("BackUpResources");

	@Test(groups = {"FUNCTIONAL"})
	public void testTotalItemsInformationLabelDisplaysResourcesQuantity() 
			throws InterruptedException, BiffException, IOException {
		
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();
		int counter = 0;
		for(Map<String, String> resource : testData){					
			
			//create a resource
			ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();		
			resourcesPage = newResourcePage.clickResourceIcon()
							.selectResourceIcon(resource.get("Icon"))
							.setResourceName(resource.get("ResourceName"))
							.setResourceDisplayName(resource.get("ResourceDisplayName"))
							.setResourceDescription(resource.get("Description"))
							.clickSaveResourceBtn();
			if(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED)== true){
				UIMethods.refresh();
			} else{
			counter = counter + 1;
			}
			Assert.assertTrue(resourcesPage.searchTotalItemsValue(Integer.toString(counter)));			
		}
	}
	
	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		for(Map<String, String> resource : testData){					
			RootRestMethods.deleteResource(resource.get("ResourceName"));
		}
		UIMethods.refresh();
	}
}
