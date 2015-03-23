package framework.pages.admin.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import framework.common.UIMethods;
import framework.selenium.SeleniumDriverManager;

public class ResourceAssociationsPage extends ResourceBaseAbstractPage {
	
	public ResourceAssociationsPage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}

	/**
	 * [ML]Method to get the quantity of resources associated to a given room
	 * @param roomDisplayName
	 */
	public String getResourceQuantityByRoomDisplayName(String roomDisplayName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + roomDisplayName + "')]/parent::div/parent::div"
				+ "/parent::div/following-sibling::div")).getText();
	}

	/**
	 * [ML]Method to find roomDisplayName in ResourceAssociationPage
	 * @param roomDisplayName
	 * @return boolean
	 */
	public boolean getRoomDisplayNameFromResourceAssociationPage(
			String roomDisplayName) {
		By name = By.xpath("//span[contains(text(),'" + roomDisplayName + "')]");
		return UIMethods.isElementPresent(name);
	}
}
