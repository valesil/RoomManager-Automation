package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.MessageConstants.RESOURCE_DISPLAY_NAME_TEXTBOX_EMPTY;
import static framework.common.MessageConstants.RESOURCE_NAME_TEXTBOX_EMPTY;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomBaseAbstractPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.utils.readers.ExcelReader;

/**
 * Test Case # 40 and # 41 
 * @author Juan Carlos Guevara 
 * Verify that an error message is displayed when {Display Name} text box is left empty on Add form.
 * Verify that an error message is displayed when {Name} text box is left empty on Add form.
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
		ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();		
		newResourcePage.setResourceDisplayName(resourceDisplayName)
						.clickSaveResourceBtn();

		//Verify the error message 		
		Assert.assertTrue(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_TEXTBOX_EMPTY));	
		resourcesPage = newResourcePage.clickCancelResourceBtn();
	}
	
	@Test(groups = {"NEGATIVE"})
	public void testAnErrorMessageIsDisplayedWhenDisplayNameTextBoxIsLeftEmptyInAddForm() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	
		
		//attempt to create a resource with display name TextBox empty
		ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();		
		newResourcePage.setResourceDisplayName(resourceName)
						.clickSaveResourceBtn();

		//Verify the error message 		
		Assert.assertTrue(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_DISPLAY_NAME_TEXTBOX_EMPTY));	
		resourcesPage = newResourcePage.clickCancelResourceBtn();
	}
}
