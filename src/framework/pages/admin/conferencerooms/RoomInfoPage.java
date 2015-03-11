package framework.pages.admin.conferencerooms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RoomInfoPage extends AbstractRoomBasePage{
	@FindBy(xpath="(//input[@type='text'])[4]") //cambiar
	WebElement displayNameTxtBox;
	
	@FindBy(xpath = "//input[@ng-model='selectedRoom.code']")
	WebElement roomCodeTxtBox;
	
	@FindBy(xpath = "//input[@ng-model='selectedRoom.capacity']")
	WebElement roomCapacityTxtBox;

	@FindBy(css="button.info") 
	WebElement saveBtn;
	
	@FindBy(xpath = "//div[@id='s2id_autogen1']")
	WebElement locationCmbBox;

	/**
	 * 
	 * @param newDisplayName
	 * @return RoomInfoPage object
	 */
	public RoomInfoPage setDisplayName(String newDisplayName) {		  
		displayNameTxtBox.clear();
		displayNameTxtBox.sendKeys(newDisplayName);	
		return this;
	}
	
	/**
	 * 
	 * @param roomCode is the code of a Room
	 * @return
	 */
	public RoomInfoPage setRoomCode(String roomCode) {
		roomCodeTxtBox.clear();
		roomCodeTxtBox.sendKeys(roomCode);
		return new RoomInfoPage();
	}
	
	/**
	 * 
	 * @param location
	 * @return
	 */
	public RoomInfoPage setLocation(String location) {
		locationCmbBox.click();
		locationCmbBox.sendKeys(location);
		return new RoomInfoPage();
	}
	
	/**
	 * 
	 * @param Amount is the capacity of a Room
	 * @return RoomInfoPageObject
	 */
	public RoomInfoPage setRoomCapacity(String Amount) {
		roomCapacityTxtBox.clear();
		roomCapacityTxtBox.sendKeys(Amount);
		return new RoomInfoPage();
	}
}
