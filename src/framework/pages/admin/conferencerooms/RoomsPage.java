package framework.pages.admin.conferencerooms;

import static framework.common.MessageConstants.OUT_OF_ORDER_SUCCESSFULLY_CREATED;
import lib.DragAndDrop;
import lib.DragAndDrop.Position;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import framework.common.UIMethods;
import framework.pages.admin.AbstractMainMenu;
import framework.rest.RootRestMethods;

/**
 * This class represents Conference room page
 * @author Ruben Blanco
 *
 */
public class RoomsPage extends AbstractMainMenu {

	@FindBy(id = "roomsGrid")
	WebElement roomsGrid;

	@FindBy (xpath = "//div[@class='toast-message']/div")
	WebElement messagePopUp;
	
	@FindBy(xpath = "//div[@class='ngGroupPanel']")
	WebElement container;

	@FindBy (xpath = "//span[@class='ngAggregateText ng-binding']")
	WebElement disableRoomsLbl;

	@FindBy (xpath = "//span[contains(text(),'true')]")
	WebElement enabledRoomsLbl;

	/**
	 * [RB]Click over Room 
	 * @param displayName is the display name of a Room
	 * @return
	 */
	public RoomInfoPage doubleClickOverRoomName(String displayName) {
		UIMethods.doubleClick(driver.findElement(By.xpath("//span[contains(text(),'" 
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
		return 	findOutOfOrderIcon(roomName).isDisplayed();
	}

	/**
	 * [YA]This method returns the icon is displayed in Out Of Order Column when an Out Of Order 
	 * Period is established
	 * @param roomDisplayName
	 * @return
	 */
	public String getOutOfOrderIconClass(String roomName) {
		WebElement outOfOrderIcon = findOutOfOrderIcon(roomName);
		return outOfOrderIcon.getAttribute("class");
	}

	/**
	 * [YA]This method finds Out Of Order Icon
	 * @param roomName
	 * @return WebElement
	 */
	private WebElement findOutOfOrderIcon(String roomName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" 
				+ roomName + "')]//ancestor::div[@ng-click='row.toggleSelected($event)']"
				+ "//out-of-order-icon//span"));
	} 

	/**
	 * [YA]This method clicks outOfOrderIcon
	 * @param roomName
	 * @return
	 */
	public RoomsPage clickOutOfOrderIcon(String roomName) {
		WebElement outOfOrderIcon = findOutOfOrderIcon(roomName);
		String outOfOrderClass = getOutOfOrderIconClass(roomName);
		String action;
		if(outOfOrderClass.contains("calendar")) {
			action = "waiting";
		} else {
			action = "running";
		}
		outOfOrderIcon.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[contains(text(),'" + roomName + "')]//ancestor::div[@ng-click="
						+ "'row.toggleSelected($event)']//div[@ng-switch-when='" + action + "']")));
		return this;
	}

	/**
	 * [YA]This method verifies if a message is displayed and clicks on the message to make it 
	 * disappear.
	 * @return boolean
	 */
	public boolean isMessagePresent() {
		boolean messageDisplayed = messagePopUp.isDisplayed();
		if (messageDisplayed == true) {
			messagePopUp.click();
		}
		return messageDisplayed;
	}

	/**
	 * [YA]This method that verifies if a message is correct
	 * @return boolean
	 */
	private boolean isMessageCorrect(String message) {
		return UIMethods.isElementPresent(By.xpath("//div[contains(text(),'" 
				+ message + "')]"));
	}

	/**
	 * [YA]This method verifies that a message that says: "Out of order was created successfully"
	 * is displayed
	 * @return boolean
	 */
	public boolean isOutOfOrderSuccessfullyCreatedMessageDisplayed() {
		return isMessageCorrect(OUT_OF_ORDER_SUCCESSFULLY_CREATED);
	}

	/**
	 * [CG]Method that returns true when the search of a resource in the top of conference rooms 
	 * page icons is successful  
	 * @param resourceName
	 * @return
	 */
	public boolean searchResource(String resourceName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + resourceName 
				+ "')and@class='ng-binding']")).isDisplayed();	
	}

	/**
	 * [CG]Method that clicks the resource icon in the top of rooms page
	 * @param resourceName
	 * @return
	 */
	public RoomsPage clickResourceIcon(String resourceName) {
		driver.findElement(By.xpath("//span[contains(text(),'" + resourceName 
				+ "')and@class='ng-binding']")).click();
		return this;
	}

	/**
	 * [CG]Method that returns true when the search of a resource in the top of rooms table 
	 * header is successful
	 * @param resourceName
	 * @return
	 */
	public boolean isResourcePresentInTableHeader(String resourceName) {
		String locator = "//div[@class='ngHeaderScroller']//div[contains(text(),'" 
				+ resourceName + "')]";
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(locator), 
				resourceName));
		return driver.findElement(By.xpath(locator)).isDisplayed();
	}

	/**
	 * [CG]Method that allows to get the state of the room if it is enable or disable
	 * @param roomName
	 * @return true or false if the button is enabled or disabled
	 */
	public boolean stateEnableDisableBtn(String roomName) {
		String locator = "//span[contains(text(),'" + roomName +
				"')]//ancestor::div[@ng-click='row.toggleSelected($event)']//span";
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
		return driver.findElement(By.xpath(locator)).isEnabled();
	}

	/**
	 * [YA] This method waits for any message to be displayed and clicks it
	 * @return RoomsPage
	 */
	public RoomsPage waitForMessage() {
		wait.until(ExpectedConditions.visibilityOf(messagePopUp));
		messagePopUp.click();
		return this;
	}

	/**
	 * [CG]Method that returns the resource value from resources grid
	 * @param resourceName
	 * @return
	 */
	public String getResourceQuantity(String resourceName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + resourceName +  "')]"
				+ "/ancestor::div/following-sibling::div[@class='ngCell centeredColumn col3 colt3']"
				+ "//span[@class='ng-binding']/parent::div")).getText();
	}

	/**
	 * This method use the rootRestClass to verify the made changes
	 * @return true if the roomDisplay was modified 
	 */
	public boolean verifyChangesMade(String ChangedDisplayName) {
		boolean flag = false;
		for (String displayName : RootRestMethods.getAllDisplayNameRooms()) {
			if (ChangedDisplayName.equals(displayName)) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * [RB]This method verify if room was grouped after drag and drop
	 * @return
	 */
	public boolean IsGroupedByRoom() {
		boolean flag = false;
		for (String room : RootRestMethods.getAllDisplayNameRooms()) {
			if (driver.findElement(By.xpath("//*[@id='roomsGrid']/div[@class='ngViewport ng-scope']//descendant::span[contains(text(),'"
					+room+"')]")).isDisplayed()) {
				flag = true;
			}
			else{
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * [RB]This method does the drag and drop of room column
	 */
	public RoomsPage dragAndDropColumn(String columnHeaderName) {
		moveElement(driver.findElement(By.xpath("//div[contains(text(),'"+columnHeaderName+"')]"
				+ "/ancestor::div[@class='ngHeaderSortColumn customHeaderClass']")));
		return this;
	}

	/**
	 * [RB]This method move the element to target using the Drag and Drop library 
	 */
	private void moveElement(WebElement elementToMove) {
		DragAndDrop.html5_DragAndDrop(driver, elementToMove, container, Position.Center, Position.Center);
	}

	/**
	 * [RB]This method verifies if rooms are grouped by disable status 
	 * @return
	 */
	public boolean IsGroupedByDisableStatus() {
		boolean flag = false;
		int totalRooms = RootRestMethods.getAllDisplayNameRooms().size();
		int displayedEnableRooms = getEnbledRooms();
		int result = totalRooms - displayedEnableRooms;
		if (result == getDisabledRooms()) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * [RB]This method verifies if rooms are grouped by enable status 
	 * @return
	 */
	public boolean IsGroupedByEnableStatus() {
		boolean flag = false;
		int totalRooms = RootRestMethods.getAllDisplayNameRooms().size();
		int displayedDisableRooms = getDisabledRooms();
		int result = totalRooms - displayedDisableRooms;
		
		if (result == getEnbledRooms()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * [RB]This method gets the number of disabled rooms
	 * @return
	 */
	public int getDisabledRooms(){
		String word = disableRoomsLbl.getText();
		return getAmount(word);
	}

	/**
	 * [RB]This method gets the number of enabled rooms
	 * @return
	 */
	public int getEnbledRooms() {
		String word = enabledRoomsLbl.getText();
		return getAmount(word);
	}

	/**
	 * [RB]This method filter only the numbers that contains a string 
	 * @return the number of a string
	 */
	private int getAmount(String string){
		String cadena = "";
		for (int i=0; i<string.length(); i++){
			String subCadena = string.substring(i, i+1);
			if (subCadena.matches("[0-9]")){
				cadena += subCadena;
			}
		}
		return Integer.parseInt(cadena);
	}
}
