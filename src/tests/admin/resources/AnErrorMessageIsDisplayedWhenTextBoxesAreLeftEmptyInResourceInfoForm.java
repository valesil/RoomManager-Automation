package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.MessageConstants.RESOURCE_DISPLAY_NAME_TEXTBOX_EMPTY;
import static framework.common.MessageConstants.RESOURCE_NAME_TEXTBOX_EMPTY;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourceInfoPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC42:Verify that an error message is displayed when {Display Name} text box is left empty on 
 * Resource
 * Info form.
 * TC43: Verify that an error message is displayed when {Name} text box is left empty on Resource 
 * Info form.
 * @author Juan Carlos Guevara 
 */
public class AnErrorMessageIsDisplayedWhenTextBoxesAreLeftEmptyInResourceInfoForm {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String resourceDescription = testData.get(0).get("Description");
	String iconTitle = testData.get(0).get("Icon");

	@BeforeClass
	public void precondition() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	

		//create a resource
		ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();		
		resourceCreatePage.clickResourceIcon()
		.selectResourceIcon(iconTitle)
		.setResourceName(resourceName)
		.setResourceDisplayName(resourceDisplayName)
		.setResourceDescription(resourceDescription)
		.clickSaveResourceBtn();
	}

	@Test(groups = {"NEGATIVE"})
	public void testAnErrorMessageIsDisplayedWhenNameTextBoxisLeftEmptyInResourceInfoForm()
			throws InterruptedException {
		ResourcesPage resourcesPage = new ResourcesPage();	

		//Cleaning resource name textbox 
		ResourceInfoPage resourceInfoPage = resourcesPage.openResourceInfoPage(resourceName);		
		resourceInfoPage.clearResourceName()
		.clickSaveResourceWithErrorBtn();

		//Verify that error is displayed 		
		Assert.assertTrue(resourceInfoPage.verifyErrorMessage(RESOURCE_NAME_TEXTBOX_EMPTY));	
		resourceInfoPage.clickCancelResourceBtn();

		//Cleaning resource display name textbox 		
		resourceInfoPage = resourcesPage.openResourceInfoPage(resourceName);
		resourceInfoPage.setResourceName(resourceName)
		.clearResourceDisplayName()
		.clickSaveResourceWithErrorBtn();

		//Assertion for TC42  		
		Assert.assertTrue(resourceInfoPage
				.verifyErrorMessage(RESOURCE_DISPLAY_NAME_TEXTBOX_EMPTY));
		resourceInfoPage.clickCancelResourceBtn();
	}

	@AfterClass
	public void cleanRoom() throws MalformedURLException, IOException {

		//Delete resource
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		homeAdminPage.clickResourcesLink();
		RootRestMethods.deleteResource(resourceName);
		UIMethods.refresh();
	}
}
