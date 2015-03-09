package framework.pages.admin.conferencerooms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RoomInfoPage extends AbstractRoomBasePage{
	@FindBy(xpath="(//input[@type='text'])[4]") //cambiar
	WebElement displayNameTxtBox;
	
	@FindBy(xpath = "//div[3]/input") //cambiar
	WebElement codeRoomTxtBox;

	@FindBy(css="button.info") 
	WebElement saveBtn;

	public RoomInfoPage changeDisplayName(String newDisplayName) {		  
		displayNameTxtBox.clear();
		displayNameTxtBox.sendKeys(newDisplayName);		
		return this;
	}
	
	public RoomInfoPage setRoomCode(String roomCode) {
		codeRoomTxtBox.clear();
		codeRoomTxtBox.sendKeys(roomCode);
		return new RoomInfoPage();
	}

	public ConferenceRoomPage clickSaveButton() {
		saveBtn.click();
		return new ConferenceRoomPage();
	}
}
