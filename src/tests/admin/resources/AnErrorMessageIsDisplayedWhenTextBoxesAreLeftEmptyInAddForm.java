package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.MessageConstants.RESOURCE_DISPLAY_NAME_TEXTBOX_EMPTY;
import static framework.common.MessageConstants.RESOURCE_NAME_TEXTBOX_EMPTY;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.utils.readers.ExcelReader;

/**
 * TC40: Verify that an error message is displayed when {Display Name} text box is left empty on 
 * Add form
 * TC41: Verify that an error message is displayed when {Name} text box is left empty on Add form
 * @author Juan Carlos Guevara 
 */
public class AnErrorMessageIsDisplayedWhenTextBoxesAreLeftEmptyInAddForm {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");

	@Test(groups = {"NEGATIVE"})
	public void testAnErrorMessageIsDisplayedWhenNameTextBoxisLeftEmptyInAddForm() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	

		//attempt to create a resource with name TextBox empty
		ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();		
		resourceCreatePage.setResourceDisplayName(resourceDisplayName)
		.clickSaveResourceWithErrorBtn();

		//Assertion for TC41 		
		Assert.assertTrue(resourceCreatePage.verifyErrorMessage(RESOURCE_NAME_TEXTBOX_EMPTY));	
		UIMethods.refresh();

		//attempt to create a resource with display name TextBox empty		
		resourceCreatePage = resourcesPage.clickAddResourceBtn();
		resourceCreatePage.setResourceName(resourceName)
		.clearResourceDisplayName()
		.clickSaveResourceWithErrorBtn();

		//Assertion for TC40  		
		Assert.assertTrue(resourceCreatePage
				.verifyErrorMessage(RESOURCE_DISPLAY_NAME_TEXTBOX_EMPTY));	
		resourcesPage = resourceCreatePage.clickCancelResourceBtn();
	}
}
