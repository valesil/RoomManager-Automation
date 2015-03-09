package framework.pages.admin.conferencerooms;

import org.openqa.selenium.By;

public class CRResourceAssociationsPage extends AbstractRoomBasePage {
	
	public CRResourceAssociationsPage clickEnableDisableBtn(String roomName) {
		driver.findElement(By.xpath("//span[contains(text(),'" + roomName + "')]//ancestor::div[@ng-click='row.toggleSelected($event)']//span")).click();
		return new CRResourceAssociationsPage();
	}
	
	public CRResourceAssociationsPage addResourceToARoom(String resourceName) {
		driver.findElement(By.
				xpath("//div[@class='col-xs-8']/span[contains(text(),'"+ resourceName +"')and@class='ng-binding']/ancestor::div[@class='col-xs-8']/following-sibling::div//i[@class='fa fa-plus']")).click();
		return new CRResourceAssociationsPage();
	}
	//span[contains(text(),'newroom005')]//ancestor::div[@ng-click='row.toggleSelected($event)']//out-of-order-icon//span
	//span[contains(text(),'newroom005')]//ancestor::div[@ng-click='row.toggleSelected($event)']//span
	
	public String getNameOfResourceFromAssociatedList() {
		return driver.findElement(By.
				xpath("//div[class='col-xs-6']/span[contains(text(),'LCD')and@class='ng-binding']")).getText();
	}
	
	public CRResourceAssociationsPage changeValueForResourceFromAssociatedList(String name,String value) {
		driver.findElement(By.
				xpath("//div[@class='col-xs-6']/span[contains(text(),'LCD')and@class='ng-binding']/ancestor::div[@class='col-xs-6']/following-sibling::div/child::input[@type='text']")).sendKeys(value);
		return new CRResourceAssociationsPage();
	}
	public CRResourceAssociationsPage removeResourceFromAssociatedList(String name) {
		driver.findElement(By.
				xpath("//div[@class='col-xs-6']/span[contains(text(),'"+name+"')and@class='ng-binding']/ancestor::div[@class='col-xs-6']/following-sibling::div//i[@class='fa fa-minus']")).click();
		return new CRResourceAssociationsPage();
	}

	public String getResourceAmount(String resourceName) {
		return driver.findElement(By.
				xpath("//div[@class='col-xs-6']/span[contains(text(),'"+resourceName+"')and@class='ng-binding']/ancestor::div[@class='col-xs-6']/following-sibling::div/child::input[@type='text']")).getText();
	}
}
	
