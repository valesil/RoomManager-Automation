package framework.pages.admin.conferencerooms;

import static framework.common.MessageConstants.OUT_OF_ORDER_SUCCESSFULLY_CREATED;

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
	 * [RB]Click over Room 
	 * @param displayName is the display name of a Room
	 * @return
	 */
	public RoomInfoPage doubleClickOverRoomName(String displayName) {
		UI.doubleClick(driver.findElement(By.xpath("//span[contains(text(),'" 
				+ displayName + "')and@class='ng-binding']")));
		return new RoomInfoPage();
	}

	/**
	 * [RB]Get the display name of a Room
	 * @param displayName
	 * @return 
	 */
	public String getRoomDisplayName(String roomName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" 
				+ roomName + "')and@class='ng-binding']")).getText();
	}
	
	/**
	 * [RB]This method disables a selected room
	 * @param roomDisplayName
	 * @return ConferenceRoomPage object
	 */
	public Object enableDisableIcon(String roomDisplayName) {
		driver.findElement(By.xpath("//span[contains(text(),'"+roomDisplayName
				+ "')]//ancestor::div[@ng-click='row.toggleSelected($event)']//span")).click();
		return this;
	}

	/**
	 * [YA] this method verifies if the icon of Out Of Order is displayed
	 * @param roomName
	 * @return
	 */
	public boolean isOutOfOrderIconDisplayed(String roomName) {
		return 	getOutOfOrderIcon(roomName).isDisplayed();
	}
	
	/**
	 * [YA]This method returns the icon is displayed in Out Of Order Column when an Out Of Order 
	 * Period is established
	 * @param roomDisplayName
	 * @return
	 */
	public String getOutOfOrderIconClass(String roomName) {
		WebElement outOfOrderIcon = getOutOfOrderIcon(roomName);
		return outOfOrderIcon.getAttribute("class");
	}
	
	private WebElement getOutOfOrderIcon(String roomName) {
		wait.until(ExpectedConditions.visibilityOf(messagePopUp));
		messagePopUp.click();
		return driver.findElement(By.xpath("//span[contains(text(),'" 
				+ roomName + "')]//ancestor::div[@ng-click='row.toggleSelected($event)']"
				+ "//out-of-order-icon//span"));
	} 

	/**
	 * [YA]This method verifies if a message is displayed and clicks on the message to make it 
	 * disappear.
	 * @return
	 */
	public boolean isMessagePresent() {
		boolean messageDisplayed = messagePopUp.isDisplayed();
		if (messageDisplayed == true) {
			messagePopUp.click();
		}
		return messageDisplayed;
	}
	
	/**
	 * [YA]This method verifies if a message is correct
	 * @return boolean
	 */
	private boolean isMessageCorrect(String message) {
		return driver.findElement(By.xpath("//div[contains(text(),'" 
				+ message + "')]")).isDisplayed();
	}
	
	/**
	 * [YA]This method verifies that a message that says: "Out of order was created successfully"
	 * is displayed
	 * @return boolean
	 */
	public boolean isOutOfOrderSuccessfullyCreatedMessageDisplayed() {
		return isMessageCorrect(OUT_OF_ORDER_SUCCESSFULLY_CREATED);
	}
}
