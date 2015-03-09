package framework.pages.admin.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import framework.pages.admin.AbstractMainMenu;

public class ResourcesPage extends AbstractMainMenu{
	private Actions action = new Actions(driver);
	@FindBy(xpath = "//div[2]/div[2]/div[1]/div/div/button[1]") 
	WebElement addResourceBtn;

	@FindBy(xpath = "//input[@class='ngSelectionCheckbox']")
	WebElement resourceCheckbox;

	@FindBy(xpath = "//button[@id='btnRemove']")
	WebElement removeBtn;
	
	@FindBy(css = "button.info")
	WebElement confirmRemoveBtn;
	
	@FindBy(xpath = "//div[@id='resourcesGrid']/div[2]/div/div/div[3]")
	WebElement resourceName;
	
	/**
	 * Required methods to create resource
	 */	
	public ResourceInfoPage clickAddResourceBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(addResourceBtn));
		addResourceBtn.click();
		return new ResourceInfoPage();
	}
	
	/**
	 * 
	 */
	public ResourceInfoPage openResourceInfoPage(String resourceNameToSearch) {
		wait.until(ExpectedConditions.visibilityOf(resourceName));
		action.doubleClick(driver.findElement(By.xpath(".//*[@id='resourcesGrid']/descendant::*/span[contains(text(),'" +
				resourceNameToSearch + "')]")));
		action.perform();
		return new ResourceInfoPage();
	}

	/*This method returns resource name from resource page*/
	public String getResourceName(String newResourceName) {
		String resourceFound;
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='resourcesGrid']/div[2]/descendant::*/span[contains(text(),'"
					+ newResourceName + "')]")));
			resourceFound = driver.findElement(By.xpath("//div[@id='resourcesGrid']/div[2]/descendant::*/span[contains(text(),'"
					+ newResourceName + "')]")).getText();
			if (resourceFound=="") {
				return null;
			}		
			return resourceFound;			
	}
	
	/*This method returns resource displayed name from resource page*/
	public String getResourceDisplayName(String resourceDisplayName) {
			return driver.findElement(By.xpath("//div[@id='resourcesGrid']/descendant::*/span[contains(text(),'"
					+ resourceDisplayName + "')]")).getText();		
	}

	/**
	 * Required method to delete resource
	 */	
	public ResourcesPage clickConfirmRemoveBtn() {
		wait.until(ExpectedConditions.visibilityOf(confirmRemoveBtn));
		confirmRemoveBtn.click();
		return new ResourcesPage();
	}
	
	public void clickRemoveBtn() {
		removeBtn.click();
	}

	public void selectResourceCheckbox(String resource) {
		driver.findElement(By.xpath(".//*[@id='resourcesGrid']/descendant::*/span[contains(text(),'" +
				resource + "')]")).click();
	}
}
