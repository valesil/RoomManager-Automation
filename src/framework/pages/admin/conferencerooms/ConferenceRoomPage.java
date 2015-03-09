package framework.pages.admin.conferencerooms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import framework.pages.admin.AbstractMainMenu;

/**
 * 
 * @author Ruben Blanco
 *
 */
public class ConferenceRoomPage extends AbstractMainMenu {
	private Actions action = new Actions(driver);
	@FindBy(id = "roomsGrid")
	WebElement roomsGrid;
	
	@FindBy(xpath = "//span[contains(text(),'room01')and@class='ng-binding']") //cambiar
	WebElement roomNameLbl;

	@FindBy(xpath = "//div[3]/input") //cambiar
	WebElement codeRoomTxtBox;
	
	@FindBy(css="button.btn-clear") 
	WebElement cancelBtn;

	public RoomInfoPage doubleClickOverRoomName() throws InterruptedException {
		doubleClick(roomNameLbl);
		return new RoomInfoPage();
	}
	
	private void doubleClick(WebElement webElement) {
		action.doubleClick(roomNameLbl);
		action.perform();
	}
	
	public String getRoomName() {
		return roomNameLbl.getText();
	}
	
	public String getRoomCode() {
		doubleClick(roomNameLbl);
		codeRoomTxtBox.click();
		return codeRoomTxtBox.getText();
	}
	
	public ConferenceRoomPage clickCancelButton() {
		cancelBtn.click();
		return new ConferenceRoomPage();
	}

}
