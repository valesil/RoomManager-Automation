package framework.pages.admin.conferencerooms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import framework.common.UIMethods;
import framework.pages.admin.AbstractMainMenu;

/**
 * 
 * @author Ruben Blanco
 *
 */
public class ConferenceRoomPage extends AbstractMainMenu {
	@FindBy(id = "roomsGrid")
	WebElement roomsGrid;

	@FindBy(xpath = "selectedRoom.displayName")
	WebElement roomCodeTxtBox; 

	@FindBy(xpath = "//input[@ng-model='selectedRoom.capacity']")
	WebElement roomCapacityTxtBox;

	@FindBy(xpath = "//div[@id='s2id_autogen1']")
	WebElement RoomLocationCmbBox;

	@FindBy(css="button.btn-clear") 
	WebElement cancelBtn;

	UIMethods UI = new UIMethods();

	/**
	 * Click over a Room 
	 * @param displayName is the display name of a Room
	 * @return
	 */
	public RoomInfoPage doubleClickOverRoomName(String displayName) {
		UI.doubleClick(driver.findElement(By.xpath("//span[contains(text(),'"+displayName+"')and@class='ng-binding']")));
		return new RoomInfoPage();
	}

	/**
	 * Get the display name of a Room
	 * @param displayName
	 * @return 
	 */
	public String getRoomDisplayName(String roomName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" +
				roomName + "')and@class='ng-binding']")).getText();
	}

	/**
	 * get the room code of specific room
	 * @param displayName is the display Name of a Room 
	 * @return
	 */
	public String getRoomCode(String displayName) {
		UI.doubleClick(driver.findElement(By.xpath("//span[contains(text(),'"+displayName+"')and@class='ng-binding']")));
		return roomCodeTxtBox.getAttribute("value");
	}

	/**
	 * get the room capacity of specific Room
	 * @param displayName
	 * @return display name of room
	 */
	public String getRoomCapacity(String displayName) {
		UI.doubleClick(driver.findElement(By.xpath("//span[contains(text(),'"+displayName+"')and@class='ng-binding']")));
		return roomCapacityTxtBox.getAttribute("value");
	}

	/**
	 * get the room location of specific room
	 * @param displayName
	 * @return the room location
	 */
	public String getRoomLocation(String displayName) {
		UI.doubleClick(driver.findElement(By.xpath("//span[contains(text(),'"+displayName+"')and@class='ng-binding']")));
		return RoomLocationCmbBox.getText();
	}

	/**
	 * Click on cancel button
	 * @return
	 */
	public ConferenceRoomPage clickCancelButton() {
		cancelBtn.click();
		return new ConferenceRoomPage();
	}

}
