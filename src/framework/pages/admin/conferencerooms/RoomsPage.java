package framework.pages.admin.conferencerooms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import framework.common.UIMethods;
import framework.pages.admin.AbstractMainMenu;

/**
 * This class represents Conference room page
 * @author Ruben Blanco
 *
 */
public class RoomsPage extends AbstractMainMenu {
	UIMethods UI = new UIMethods();
	
	@FindBy(id = "roomsGrid")
	WebElement roomsGrid;
	
	@FindBy (xpath = "//div[@class='toast-message']/div")
	WebElement messagePopUp;

	/**
	 * Click over Room 
	 * @param displayName is the display name of a Room
	 * @return
	 */
	public RoomInfoPage doubleClickOverRoomName(String displayName) {
		UI.doubleClick(driver.findElement(By.xpath("//span[contains(text(),'" 
				+ displayName + "')and@class='ng-binding']")));
		return new RoomInfoPage();
	}

	/**
	 * Get the display name of a Room
	 * @param displayName
	 * @return 
	 */
	public String getRoomDisplayName(String roomName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" 
				+ roomName + "')and@class='ng-binding']")).getText();
	}
	
	/**
	 * This method disables a selected room
	 * @param roomDisplayName
	 * @return ConferenceRoomPage object
	 */
	public Object enableDisableIcon(String roomDisplayName) {
		driver.findElement(By.xpath("//span[contains(text(),'"+roomDisplayName
				+"')]//ancestor::div[@ng-click='row.toggleSelected($event)']//span")).click();
		return this;
	}

	/**
	 * [YA]This method returns the icon is displayed in Out Of Order Column when an Out Of Order Period
	 * is established
	 * @param roomDisplayName
	 * @return
	 */
	public String getOutOfOrderIcon(String roomDisplayName) {
		wait.until(ExpectedConditions.visibilityOf(messagePopUp));
		messagePopUp.click();
		WebElement outOfOrderIcon = driver.findElement(By.xpath("//span[contains(text(),'" + roomDisplayName 
				+ "')]//ancestor::div[@ng-click='row.toggleSelected($event)']//out-of-order-icon//span"));
		return outOfOrderIcon.getAttribute("class");
	}
	
	/**
	 * [YA]This method verifies if a message is displayed and clicks on the message to make it disappear.
	 * @return
	 */
	public boolean messageIsPresent() {
		boolean messageDisplayed = messagePopUp.isDisplayed();
		if (messageDisplayed == true) {
			messagePopUp.click();
		}
		return messageDisplayed;
	}
	
	/**
	 * [YA] This method returns the text of the message displayed after creating or updating an Out Of Order Period
	 * @return
	 */
	public String getMessageValue() {
		wait.until(ExpectedConditions.visibilityOf(messagePopUp));
		messagePopUp.click();
		return messagePopUp.getText();
		
	}
}