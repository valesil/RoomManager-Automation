package framework.pages.admin.reources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import framework.pages.admin.AbstractMainMenu;

public class ResourcesPage extends AbstractMainMenu{
	
	@FindBy(xpath="//div[2]/div[2]/div[1]/div/div/button[1]") 
	WebElement addResourceBtn;
	
	/**
	 * Required methods to create resource
	 */	
	public NewResourcesPage clickAddResourceBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(addResourceBtn));
		addResourceBtn.click();
		return new NewResourcesPage();
	}
	
	/**
	 * This method return the resource name for assert
	 * @param resourceName
	 * @return
	 */
	public String getResource(String resourceName) {
		return driver.findElement(By.xpath(".//*[@id='resourcesGrid']/div[2]/descendant::*/span[contains(text(),'" +
									 resourceName + "']")).getText();		
	}
	
	/**
	 * Required method to delete resource
	 */
	
	public void deleteResourceByName(String resourceName) {
		int index = 0;
		driver.findElement(By.xpath(".//*[@id='resourcesGrid']/div[2]/div/div[" + index + "]/" +
									"descendant::*/span[contains(text(),'" + resourceName + "')]"));
	}
}
