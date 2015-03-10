package framework.pages.admin.conferencerooms;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
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

	@FindBy(xpath = "//div/span[2]") //cambiar
	WebElement roomNameLbl;

	@FindBy(xpath = "//div[3]/input") //cambiar
	WebElement roomCodeTxtBox;

	@FindBy(xpath = "//input[@ng-model='selectedRoom.capacity']")
	WebElement roomCapacityTxtBox;

	@FindBy(xpath = "//div[@id='s2id_autogen1']")
	WebElement RoomLocationCmbBox;

	@FindBy(css="button.btn-clear") 
	WebElement cancelBtn;

	public RoomInfoPage doubleClickOverRoomName() throws InterruptedException {
		doubleClick(roomNameLbl);
		return new RoomInfoPage();
	}

	public void doubleClick(WebElement webElement) {
		action.doubleClick(roomNameLbl);
		action.perform();
	}

	/**
	 * 
	 * @param displayName
	 * @return
	 * @throws IOException
	 */
	public String getRoomName(String displayName) throws IOException {
		return getRoomNameAux(displayName);
	}

	public String getRoomCode() {
		doubleClick(roomNameLbl);
		return roomCodeTxtBox.getAttribute("value");
	}

	public String getRoomCapacity() {
		doubleClick(roomNameLbl);
		return roomCapacityTxtBox.getAttribute("value");
	}

	public String getRoomLocation() {
		doubleClick(roomNameLbl);
		return RoomLocationCmbBox.getText();
	}

	public ConferenceRoomPage clickCancelButton() {
		cancelBtn.click();
		return new ConferenceRoomPage();
	}

	public String getRoomNameAux(String roomName) throws IOException {
		return driver.findElement(By.xpath("//span[contains(text(),'" + roomName + "')and@class='ng-binding']")).getText();
	}

	public String getListRooms() {
		return driver.findElement(By.xpath("//div[@ng-class='col.colIndex()' and @class='ngHeaderCell ng-scope col2 colt2']")).getText();
	}

	public void dragAndDropResourceHeader(String resourceHeader) {
		(new Actions(driver)).dragAndDrop(driver.findElement(By.cssSelector(".ngHeaderText.ng-binding.colt4")), 
				driver.findElement(By.cssSelector(".ngTopPanel.ng-scope"))).perform();
	}

	public void dragAndDrop(String resourceDisplayName) {
		WebElement elementToMove = driver.findElement(By.xpath("//div[@class='ngHeaderSortColumn customHeaderClass']//div[contains(text(),'"+resourceDisplayName+"')]"));
		WebElement moveToElement = driver.findElement(By.xpath("//div[@class='ngGroupPanel']"));
        Actions dragAndDrop = new Actions(driver);
        Action action = dragAndDrop.dragAndDrop(elementToMove, moveToElement).build();
        action.perform();
	}

	public boolean searchResourceInTableHeader(String resourceName) {
		return driver.findElement(By.xpath("//div[@class='ngHeaderScroller']//div[contains(text(),'" + resourceName + "')]")).isDisplayed();
	}

}
