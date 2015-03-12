package framework.pages.admin.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import framework.common.UIMethods;
import framework.pages.admin.AbstractMainMenu;

public class ResourcesPage extends AbstractMainMenu{
	UIMethods uiMethod = new UIMethods();
	@FindBy(xpath = "//div[@class='pull-left']/button[@ng-click='addResourceDialog()']") 
	WebElement addResourceBtn;

	@FindBy(xpath = "//input[@class='ngSelectionCheckbox']")
	WebElement resourceChkbox;

	@FindBy(id = "btnRemove")
	WebElement removeBtn;

	//This method click on "+Add" resource button	
	public ResourceInfoPage clickAddResourceBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(addResourceBtn));
		addResourceBtn.click();
		return new ResourceInfoPage();
	}

	/**
	 * This method makes the double click action in a given resource
	 * @throws InterruptedException 
	 */
	public ResourceInfoPage openResourceInfoPage(String resourceNameToSearch) throws InterruptedException {	
		uiMethod.waitForMaskDesappears();
		WebElement resourceName = driver.findElement(By.xpath("//span[contains(text(),'" + resourceNameToSearch + "')]"));
		uiMethod.doubleClick(resourceName); //This is the method that is skipped.
		return new ResourceInfoPage();
	}		
	
	/**
	 * Required method to delete resource
	 * @throws InterruptedException 
	 */	
	public ResourcesPage selectResourceCheckbox(String resource) throws InterruptedException {
		uiMethod.waitForMaskDesappears();
		By resourceName = By.xpath("//*[@id='resourcesGrid']/descendant::*/span[contains(text(),'" 
				+ resource + "')]");
		if (uiMethod.isElementPresent(resourceName)) {
			driver.findElement(resourceName).click();
		}		
		return this;
	}
	
	public DeleteResourcePage clickRemoveBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(removeBtn));
		removeBtn.click();
		return new DeleteResourcePage();
	}	

	/**
	 * Below are the methods for assertions, the first return the resource displayName from ResourcesPage
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
