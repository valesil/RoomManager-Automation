package framework.pages.admin.conferencerooms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This class represents Room Info page
 * @author Ruben Blanco
 *
 */
public class RoomInfoPage extends AbstractRoomBasePage{
	@FindBy(xpath = "//input[@ng-model='selectedRoom.customDisplayName']") 
	WebElement displayNameTxtBox;
	
	@FindBy(xpath = "//input[@ng-model='selectedRoom.code']")
	WebElement roomCodeTxtBox;
	
	@FindBy(xpath = "//input[@ng-model='selectedRoom.capacity']")
	WebElement roomCapacityTxtBox;

	@FindBy(css = "button.info") 
	WebElement saveBtn;
	
	@FindBy(xpath = "//div[@id='s2id_autogen1']")
	WebElement locationCmbBox;

	/**
	 * This method sets the display Name of a room
	 * @param newDisplayName
	 * @return RoomInfoPage object
	 */
	public RoomInfoPage setDisplayName(String newDisplayName) {		  
		displayNameTxtBox.clear();
		displayNameTxtBox.sendKeys(newDisplayName);	
		return this;
	}
	
	/**
	 * This method sets the code of a room
	 * @param roomCode is the code of a Room
	 * @return
	 */
	public RoomInfoPage setRoomCode(String roomCode) {
		roomCodeTxtBox.clear();
		roomCodeTxtBox.sendKeys(roomCode);
		return new RoomInfoPage();
	}
	
	/**
	 * This method sets the location of a room
	 * @param location
	 * @return
	 */
	public RoomInfoPage setLocation(String location) {
		locationCmbBox.click();
		locationCmbBox.sendKeys(location);
		return new RoomInfoPage();
	}
	
	/**
	 * This method sets the room's capacity
	 * @param Amount is the capacity of a Room
	 * @return RoomInfoPageObject
	 */
	public RoomInfoPage setRoomCapacity(String roomCapacity) {
		roomCapacityTxtBox.clear();
		roomCapacityTxtBox.sendKeys(roomCapacity);
		return new RoomInfoPage();
	}
}
