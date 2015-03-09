package framework.pages.admin.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import framework.selenium.SeleniumDriverManager;

public class ResourceInfoPage extends AbstractResourceBasePage {	
	
	public ResourceInfoPage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}
	
	public void setResourceName(String resourceName) {
		wait.until(ExpectedConditions.visibilityOf(resourceNameTextbox));
		resourceNameTextbox.clear();
		resourceNameTextbox.sendKeys(resourceName);
	}
	
	public void setResourceDisplayName(String resourceDisplayName) {
		resourceDisplayNameTextbox.clear();
		resourceDisplayNameTextbox.sendKeys(resourceDisplayName);
	}
	
	public void setResourceDescription(String resourceDescription) {
		resourceDescriptionTextbox.clear();
		resourceDescriptionTextbox.sendKeys(resourceDescription);
	}
	
	public String getResourceNameFromResourceInfoPage() {
		wait.until(ExpectedConditions.visibilityOf(resourceNameTextbox));
		return resourceNameTextbox.getAttribute("value");
	}
	
	public String getResourceNameFromResourcePage() {
		wait.until(ExpectedConditions.visibilityOf(resourceNameTextbox));
		return resourceNameTextbox.getText();
	}
	
	public String getResourceDisplayName() {
		return resourceDisplayNameTextbox.getAttribute("value");
	}
	
	public String getResourceDescription() {
		return resourceDescriptionTextbox.getAttribute("value");
	}
	
	public String getResourceIcon(String iconTitle) {
		return driver.findElement(By.xpath(".//*[@id='resourcesGrid']/descendant::*/span[@class='fa " +
				iconTitle + "']")).getAttribute("class");
	}
	
	public ResourcesPage clickSaveResourceBtn() {
		saveResourceBtn.click();
		return new ResourcesPage();
	}
}
