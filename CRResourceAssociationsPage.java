package framework.pages.admin.conferencerooms;

import org.openqa.selenium.By;

public class CRResourceAssociationsPage extends AbstractRoomBasePage {

	/**
	 * button that allows to add an available resource to be associated to the room
	 * @param resourceName
	 * @return the same page
	 */
	public CRResourceAssociationsPage clickAddResourceToARoom(String resourceName) {
		driver.findElement(By.xpath("//span[contains(text(),'" + resourceName +
				"')]/parent::div/following-sibling::div/button")).click();
		return this;
	}

	/**
	 * method that allows to change the number of resources
	 * @param resourceName
	 * @param value
	 * @return the same page
	 */
	public CRResourceAssociationsPage changeValueForResourceFromAssociatedList(String resourceName,
			String value) {
		String locator = "//span[contains(text(),'" + resourceName + 
				"')]/ancestor::div/following-sibling::div/input"; 
		driver.findElement(By.xpath(locator)).clear();
		driver.findElement(By.xpath(locator)).sendKeys(value);
		return this;
	}

	/**
	 * method that removes one resource associated to a room
	 * @param resourceName
	 * @return the same page 
	 */
	public CRResourceAssociationsPage removeResourceFromAssociatedList(String resourceName) {
		driver.findElement(By.xpath("//span[contains(text(),'" + resourceName + 
				"')]/ancestor::div/following-sibling::div//i[@class='fa fa-minus']")).click();
		return this;
	}

	/**
	 * method that returns the quantity of a resource associated to a room
	 * @param resourceName
	 * @return
	 */
	public String getResourceAmount(String resourceName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + resourceName + 
				"')]/ancestor::div/following-sibling::div/input")).getText();
	}
	
	/**
	 * boolean method that returns true or false when locates a resource
	 * @param resourceName
	 * @return
	 */
	public boolean searchResourcequantity(String resourceName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + resourceName + 
				"')]/ancestor::div/following-sibling::div/input")).isDisplayed();
	}
	
	/**
	 * Method that returns True when a resource is found in room associations page
	 * @param resourceName
	 * @return
	 */
	public boolean searchResource(String resourceName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + resourceName +
				"')and@class='ng-binding']")).isDisplayed();	
	}
}