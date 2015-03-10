package framework.pages.admin.conferencerooms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RoomInfoPage extends AbstractRoomBasePage{
	@FindBy(xpath="(//input[@type='text'])[4]") //cambiar
	WebElement displayNameTxtBox;
	
	@FindBy(xpath = "//div[3]/input") //cambiar
	WebElement roomCodeTxtBox;
	
	@FindBy(xpath = "//input[@ng-model='selectedRoom.capacity']")
	WebElement roomCapacityTxtBox;

	@FindBy(css="button.info") 
	WebElement saveBtn;
	
	@FindBy(xpath = "//div[@id='s2id_autogen1']")
	WebElement locationCmbBox;

	public RoomInfoPage setDisplayName(String newDisplayName) {		  
		displayNameTxtBox.clear();
		displayNameTxtBox.sendKeys(newDisplayName);	
		return this;
	}
	
	public RoomInfoPage setRoomCode(String roomCode) {
		roomCodeTxtBox.clear();
		roomCodeTxtBox.sendKeys(roomCode);
		return new RoomInfoPage();
	}
	
	public RoomInfoPage setLocation(String location) {
		locationCmbBox.click();
		locationCmbBox.sendKeys(location);
		return new RoomInfoPage();
	}
	
	public RoomInfoPage setRoomCapacity(String Amount) {
		roomCapacityTxtBox.clear();
		roomCapacityTxtBox.sendKeys(Amount);
		return new RoomInfoPage();
	}

	public ConferenceRoomPage clickSaveButton() {
		saveBtn.click();
		return new ConferenceRoomPage();
	}
	
}
