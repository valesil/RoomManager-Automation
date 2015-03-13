package framework.pages.admin.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

	/**
	 * [ML]Click on "+Add" resource button	
	 * @return
	 */
	public ResourceInfoPage clickAddResourceBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(addResourceBtn));
		addResourceBtn.click();
		return new ResourceInfoPage();
	}

	/**
	 * [ML]Double click action in a given resource name from ResourcesPage
	 * @param resourceNameToSearch
	 * @return
	 * @throws InterruptedException
	 */
	public ResourceInfoPage openResourceInfoPage(String resourceNameToSearch) throws InterruptedException {	
		waitForMaskDesappears();
		WebElement resourceName = driver.findElement(By.xpath("//span[contains(text(),'" + resourceNameToSearch + "')]"));
		uiMethod.doubleClick(resourceName); //This is the method that is skipped.
		return new ResourceInfoPage();
	}		
	
	/**
	 * [ML]Selects a resource from ResourcesPage by click, according to the name given in parameter
	 * @param resource
	 * @return
	 * @throws InterruptedException
	 */
	public ResourcesPage selectResourceCheckbox(String resource) throws InterruptedException {
		waitForMaskDesappears();
		By resourceName = By.xpath("//*[@id='resourcesGrid']/descendant::*/span[contains(text(),'" 
				+ resource + "')]");
		if (uiMethod.isElementPresent(resourceName)) {
			driver.findElement(resourceName).click();
		}		
		return this;
	}
	
	/**
	 * [ML]Click on "remove" resource button
	 * @return
	 */
	public DeleteResourcePage clickRemoveBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(removeBtn));
		removeBtn.click();
		return new DeleteResourcePage();
	}	

	/**
	 * [ML]Returns resource displayName from ResourcesPage
	 * @param resourceDisplayName
	 * @return
	 */
	public boolean isResourceDisplayNameDisplayedInResourcesPage(String resourceDisplayName) {
		By resourceDisplayNameInResourcePage = By.xpath(".//*[@id='resourcesGrid']/descendant::*/span[contains(text(),'"
				+ resourceDisplayName + "')]");		
		return uiMethod.isElementPresent(resourceDisplayNameInResourcePage);
	}
	
	/**
	 * [ML]Returns resource name from resource page
	 * @param resourceName
	 * @return
	 */	
	public boolean isResourceNameDisplayedInResourcesPage(String resourceName) {
		By resourceNameInPage = By.xpath("//div[@id='resourcesGrid']/div[2]/descendant::*/span[contains(text(),'"
				+ resourceName + "')]");
		return uiMethod.isElementPresent(resourceNameInPage);
	}	
}
