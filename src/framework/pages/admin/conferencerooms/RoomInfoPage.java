package framework.pages.admin.conferencerooms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import framework.common.UIMethods;

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
	
	@FindBy(xpath = "//span[@class='select2-chosen']")
	WebElement locationCmbBox;
	
	UIMethods UI = new UIMethods();

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
	 * get the room code of specific room
	 * @param displayName is the display Name of a Room 
	 * @return
	 */
	public String getRoomCode() {
		return roomCodeTxtBox.getAttribute("value");
	}
	
	/**
	 * get the room capacity of specific Room
	 * @param displayName
	 * @return display name of room
	 */
	public String getRoomCapacity() {
		return roomCapacityTxtBox.getAttribute("value");
	}

	/**
	 * get the room location of specific room
	 * @param displayName
	 * @return the room location
	 */
	public String getRoomLocation() {
		return locationCmbBox.getText();
	}
	
	/**
	 * This method sets the location of a room
	 * @param location
	 * @return
	 */
	public RoomInfoPage setLocation(String location) {
		locationCmbBox.click();
		locationCmbBox.sendKeys(location);
		driver.findElement(By.xpath("//span[contains(text(),'"+location
				+"')and@class='select2-match']")).click();
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
