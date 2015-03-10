package framework.pages.admin.conferencerooms;

import org.openqa.selenium.By;

public class CRResourceAssociationsPage extends AbstractRoomBasePage {
	
	/**
	 * Enable Disable Button can be clicked to enable or disable a room 
	 * @param roomName
	 * @return the same page
	 */
	public CRResourceAssociationsPage clickEnableDisableBtn(String roomName) {
		driver.findElement(By.
				xpath("//span[contains(text(),'" + roomName + "')]//ancestor::div[@ng-click='row.toggleSelected($event)']//span")).click();
		return new CRResourceAssociationsPage();
	}
	
	/**
	 * Method that allows to get the state of the room if it is enable or disable
	 * @param roomName
	 * @return true or false if the button is enabled or disabled
	 */
	public boolean stateEnableDisableBtn(String roomName) {
		return driver.findElement(By.
				xpath("//span[contains(text(),'" + roomName + "')]//ancestor::div[@ng-click='row.toggleSelected($event)']//span")).isEnabled();
	}
	
	/**
	 * button located in Resource Associations Page that allows to add an available resource to be associated to the room
	 * @param resourceName
	 * @return the same page
	 */
	public CRResourceAssociationsPage clickAddResourceToARoom(String resourceName) {
		driver.findElement(By.
				xpath("//span[contains(text(),'" + resourceName + "')]/parent::div/following-sibling::div/button")).click();
		return new CRResourceAssociationsPage();
	}
	
	/**
	 * method that allows to change the number of resources
	 * @param resourceName
	 * @param value
	 * @return the same page
	 */
	public CRResourceAssociationsPage changeValueForResourceFromAssociatedList(String resourceName,String value) {
		String locator = "//div[@class='col-xs-6']/span[contains(text(),'" + resourceName + "')and@class='ng-binding']/ancestor::div[@class='col-xs-6']/following-sibling::div/child::input[@type='text']"; 
		driver.findElement(By.xpath(locator)).clear();
		driver.findElement(By.xpath(locator)).sendKeys(value);
		return new CRResourceAssociationsPage();
	}
	
	/**
	 * method that removes one resource associated to a room
	 * @param resourceName
	 * @return the same page 
	 */
	public CRResourceAssociationsPage removeResourceFromAssociatedList(String resourceName) {
		driver.findElement(By.
				xpath("//div[@class='col-xs-6']/span[contains(text(),'" + resourceName + "')and@class='ng-binding']/ancestor::div[@class='col-xs-6']/following-sibling::div//i[@class='fa fa-minus']")).click();
		return new CRResourceAssociationsPage();
	}

	/**
	 * method that returns the quantity of a resource associated to a room
	 * @param resourceName
	 * @return
	 */
	public String getResourceAmount(String resourceName) {
		return driver.findElement(By.
				xpath("//div[@class='col-xs-6']/span[contains(text(),'" + resourceName + "')and@class='ng-binding']/ancestor::div[@class='col-xs-6']/following-sibling::div/child::input[@type='text']")).getText();
	}
	
	/**
	 * Method that returns True when a resource is found in room associations page
	 * @param resourceName
	 * @return
	 */
	public boolean searchResource(String resourceName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + resourceName + "')and@class='ng-binding']")).isDisplayed();	
	}
}