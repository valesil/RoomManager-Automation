package framework.pages.admin.conferencerooms;

import org.openqa.selenium.By;

/**
 * 
 * @author Carlos Guevara
 *
 */
public class CRResourceAssociationsPage extends AbstractRoomBasePage {

	/**
	 * [CG]Button that allows to add an available resource to be associated to the room
	 * @param resourceName
	 * @return the same page
	 */
	public CRResourceAssociationsPage clickAddResourceToARoom(String resourceName) {
		driver.findElement(By.xpath("//span[contains(text(),'" + resourceName +
				"')]/parent::div/following-sibling::div/button")).click();
		return this;
	}

	/**
	 * [CG]Method that allows to change the number of resources associated to a room
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
	 * [CG]Method that removes one resource associated to a room
	 * @param resourceName
	 * @return the same page 
	 */
	public CRResourceAssociationsPage removeResourceFromAssociatedList(String resourceName) {
		driver.findElement(By.xpath("//span[contains(text(),'" + resourceName + 
				"')]/ancestor::div/following-sibling::div//i[@class='fa fa-minus']")).click();
		return this;
	}

	/**
	 * [CG]Method that returns the quantity of a resource associated to a room
	 * @param resourceName
	 * @return
	 */
	public String getResourceAmount(String resourceName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + resourceName + 
				"')]/ancestor::div/following-sibling::div/input")).getAttribute("value");
	}
	
	/**
	 * [CG]Boolean method that returns true or false when locates a resource
	 * @param resourceName
	 * @return
	 */
	public boolean searchResourcequantity(String resourceName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + resourceName + 
				"')]/ancestor::div/following-sibling::div/input")).isDisplayed();
	}
	
	/**
	 * [CG]Method that returns True when it finds a resource associated to a room
	 * @param resourceName
	 * @return
	 */
	public boolean searchResource(String resourceName) {
		try {
			return driver.findElement(By.xpath("//span[contains(text(),'" + resourceName +
				"')and@class='ng-binding']")).isDisplayed();	
		}
		catch (Exception e) {
			return false;
		}
	}
}