package framework.pages.admin.conferencerooms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This class represents Room Info page
 * @author Ruben Blanco
 *
 */
public class RoomInfoPage extends RoomBaseAbstractPage{
	@FindBy(xpath = "//input[@ng-model='selectedRoom.customDisplayName']") 
	WebElement displayNameTxtBox;

	@FindBy(xpath = "//input[@ng-model='selectedRoom.code']")
	WebElement roomCodeTxtBox;

	@FindBy(xpath = "//input[@ng-model='selectedRoom.capacity']")
	WebElement roomCapacityTxtBox;
	
	@FindBy(xpath = "//div/input[@ng-model='selectedRoom.customDisplayName']")
	WebElement roomDisplayNameTxtBox;

	@FindBy(xpath = "//span[@class='select2-chosen']")
	WebElement locationCmbBox;
	
	@FindBy(xpath = "//small[@class='inline-error']")
	WebElement errorDisplayName;
	
	@FindBy(xpath = "//button[@ng-click='enableDisableRoom(selectedRoom)']")
	WebElement disableIcon;

	/**
	 * [RB]This method sets the display Name of a room
	 * @param newDisplayName
	 * @return RoomInfoPage object
	 */
	public RoomInfoPage setDisplayName(String newDisplayName) {		  
		displayNameTxtBox.clear();
		displayNameTxtBox.sendKeys(newDisplayName);	
		return this;
	}

	/**
	 * [RB]This method sets the code of a room
	 * @param roomCode is the code of a Room
	 * @return
	 */
	public RoomInfoPage setRoomCode(String roomCode) {
		roomCodeTxtBox.clear();
		roomCodeTxtBox.sendKeys(roomCode);
		return new RoomInfoPage();
	}

	/**
	 * [RB]get the room code of specific room
	 * @param displayName is the display Name of a Room 
	 * @return
	 */
	public String getRoomCode() {
		return roomCodeTxtBox.getAttribute("value");
	}

	/**
	 * [RB]get the room capacity of specific Room
	 * @param displayName
	 * @return display name of room
	 */
	public String getRoomCapacity() {
		return roomCapacityTxtBox.getAttribute("value");
	}

	/**
	 * [RB]get the room location of specific room
	 * @param displayName
	 * @return the room location
	 */
	public String getRoomLocation() {
		return locationCmbBox.getText();
	}

	/**
	 * [RB]This method sets the location of a room
	 * @param location
	 * @return
	 */
	public RoomInfoPage setLocation(String location) {
		locationCmbBox.click();
		locationCmbBox.sendKeys(location);
		driver.findElement(By.xpath("//span[contains(text(),'" + location
				+ "')and@class='select2-match']")).click();
		return new RoomInfoPage();
	}

	/**
	 * [RB]This method sets the room's capacity
	 * @param Amount is the capacity of a Room
	 * @return RoomInfoPageObject
	 */
	public RoomInfoPage setRoomCapacity(String roomCapacity) {
		roomCapacityTxtBox.clear();
		roomCapacityTxtBox.sendKeys(roomCapacity);
		return new RoomInfoPage();
	}
	
	/**
	 * [ML]This method gets error message from RoomInfoPage when blank text is 
	 * inserted into room display name 
	 * @return error message for display name when empty value is inserted
	 */
	public String getErrorMessageDisplayName() {
		return errorDisplayName.getText();
	}
	
	/**
	 * [ML]This method gets the room display name from RoomInfoPage
	 * @return room display name
	 */
	public String getRoomDisplayName() {
		return roomDisplayNameTxtBox.getAttribute("value");
	}

	/**
	 * [RB] THis method verify default values of room
	 * @param newdisplayname
	 * @return
	 */
	public boolean verifyRoomDefaultValues(String newdisplayname) {

		String capacity = getRoomCapacity();
		String code = getRoomCode();
		String displayname = getRoomDisplayName();
		
		if (displayname.equals(newdisplayname) && capacity.equals("")
				&& code.equals("")){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * [RB]This method verify if a error message is displayed when a empty value for 
	 * displayname is inserted and saved 
	 * @return
	 */
	public boolean isDisplaynameErrorMessageDisplayed() {
		return driver.findElement(By.xpath("//small[contains(text(),'not be empty')"
				+ "and@ng-show='textInputs.customName']")).isDisplayed();
	}

	/**
	 * [RB]This method verify if the capacity of room is long
	 * @param newCapacity
	 * @return
	 */
	public boolean capacityIsLong(String newCapacity) {
		if (newCapacity.length() > 9)
			return true;
		else
			return false;
	}

	/**
	 * [RB]This method disables a room
	 */
	public RoomInfoPage clickDisableIcon() {
		disableIcon.click();
		return this;
	}
}
