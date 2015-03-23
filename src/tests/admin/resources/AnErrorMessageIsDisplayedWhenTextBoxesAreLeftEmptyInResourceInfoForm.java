package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.MessageConstants.RESOURCE_DISPLAY_NAME_TEXTBOX_EMPTY;
import static framework.common.MessageConstants.RESOURCE_NAME_TEXTBOX_EMPTY;
import static framework.common.MessageConstants.RESOURCE_NAME_DUPLICATED;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomBaseAbstractPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourceInfoPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * Test Case # 42 and # 43 
 * @author Juan Carlos Guevara 
 * Verify that an error message is displayed when {Display Name} text box is left empty on Resource
 * Info form.
 * Verify that an error message is displayed when {Name} text box is left empty on Resource Info 
 * form.
 */
public class AnErrorMessageIsDisplayedWhenTextBoxesAreLeftEmptyInResourceInfoForm {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	String roomName = testData.get(0).get("Room Name");
	String resourceName = testData.get(0).get("ResourceName");
	String resourceDisplayName = testData.get(0).get("ResourceDisplayName");
	String resourceDescription = testData.get(0).get("Description");
	String iconTitle = testData.get(0).get("Icon");
	
	@BeforeClass
	public void precondition() throws InterruptedException, BiffException, IOException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	
		
		//create a resource
		ResourceCreatePage newResourcePage = resourcesPage.clickAddResourceBtn();		
		resourcesPage = newResourcePage.clickResourceIcon()
						.selectResourceIcon(iconTitle)
						.setResourceName(resourceName)
						.setResourceDisplayName(resourceDisplayName)
						.setResourceDescription(resourceDescription)
						.clickSaveResourceBtn();
		if(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_DUPLICATED)== true){
			UIMethods.refresh();
		}
	}

	@Test(groups = {"NEGATIVE"})
	public void testAnErrorMessageIsDisplayedWhenNameTextBoxisLeftEmptyInResourceInfoForm() 
			throws InterruptedException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	

		//Cleaning resource name textbox 
		ResourceInfoPage resourceInfoPage = resourcesPage.openResourceInfoPage(resourceName);		
		resourceInfoPage.clearResourceName()
						.clickSaveResourceBtn();

		//Verify that error is displayed 		
		Assert.assertTrue(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_NAME_TEXTBOX_EMPTY));	
		UIMethods.refresh();
	}
	
	@Test(groups = {"NEGATIVE"})
	public void testAnErrorMessageIsDisplayedWhenDisplayNameTextBoxIsLeftEmptyInResourceInfoForm() 
			throws InterruptedException {	
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();
		
		//Cleaning resource display name textbox 
		ResourceInfoPage resourceInfoPage = resourcesPage.openResourceInfoPage(resourceName);		
		resourceInfoPage.clearResourceDisplayName()
						.clickSaveResourceBtn();

		//Verify that error is displayed 		
		Assert.assertTrue(RoomBaseAbstractPage.isErrorMessageCorrect(RESOURCE_DISPLAY_NAME_TEXTBOX_EMPTY));	
		UIMethods.refresh();
	}
	
	@AfterClass
	public void cleanRoom() throws InterruptedException, BiffException, IOException {

		//Delete resource
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		homeAdminPage.clickResourcesLink();
		RootRestMethods.deleteResource(resourceName);
		UIMethods.refresh();
	}
}
