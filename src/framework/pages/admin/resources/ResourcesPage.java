package framework.pages.admin.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.internal.thread.TestNGThread;
import framework.common.UIMethods;
import framework.pages.admin.AbstractMainMenu;

/**
 * @author Marco Llano
 *
 */
public class ResourcesPage extends AbstractMainMenu{
	UIMethods uiMethod = new UIMethods();
	@FindBy(xpath = "//div[@class='pull-left']/button[@ng-click='addResourceDialog()']") 
	WebElement addResourceBtn;

	@FindBy(xpath = "//input[@class='ngSelectionCheckbox']")
	WebElement resourceChkbox;

	@FindBy(id = "btnRemove")
	WebElement removeBtn;
	
	@FindBy(css = "button.info")
	WebElement confirmRemoveBtn;
		
	//This method click on "+Add" resource button	
	public ResourcesInfoPage clickAddResourceBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(addResourceBtn));
		addResourceBtn.click();
		return new ResourcesInfoPage();
	}
	
	/**
	 * This method makes the double click action in a given resource
	 * @throws InterruptedException 
	 */
	public ResourcesInfoPage openResourceInfoPage(String resourceNameToSearch) throws InterruptedException {		
		By resourceName = By.xpath(".//*[@id='resourcesGrid']/descendant::*/span[contains(text(),'" 
								  + resourceNameToSearch + "')]");
		TestNGThread.sleep(1000);
		uiMethod.doubleClick(driver.findElement(resourceName));
		return new ResourcesInfoPage();
	}	

	/**
	 * Required method to delete resource
	 * @throws InterruptedException 
	 */	
	public ResourcesPage deleteResource(String resource) throws InterruptedException {
		selectResourceCheckbox(resource);
		clickRemoveBtn();
		return clickConfirmRemoveBtn();
	}
	
	public ResourcesPage clickConfirmRemoveBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(confirmRemoveBtn));
		confirmRemoveBtn.click();
		return new ResourcesPage();
	}
	
	public void clickRemoveBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(removeBtn));
		removeBtn.click();
	}

	public void selectResourceCheckbox(String resource) throws InterruptedException {
		By resourceName = By.xpath("//*[@id='resourcesGrid']/descendant::*/span[contains(text(),'" 
								   + resource + "')]");
		TestNGThread.sleep(1000);
		driver.findElement(resourceName).click();
	}
	
	/**
	 * Below are the methods for assertions, the first return the displayName from ResourcesPage
	 */
	public boolean isResourceDisplayNameDisplayedInResourcesPage(String resourceDisplayName) {
			By resourceDisplayNameInResourcePage = By.xpath(".//*[@id='resourcesGrid']/descendant::*/span[contains(text(),'"
															+ resourceDisplayName + "')]");		
			return uiMethod.isElementPresent(resourceDisplayNameInResourcePage);
	}

	/*This method returns resource name from resource page*/
	public boolean isResourceNameDisplayedInResourcesPage(String resourceName) {
		By resourceNameInPage = By.xpath("//div[@id='resourcesGrid']/div[2]/descendant::*/span[contains(text(),'"
									  + resourceName + "')]");
		return uiMethod.isElementPresent(resourceNameInPage);
	}	
}
