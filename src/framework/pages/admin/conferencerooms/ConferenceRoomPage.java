package framework.pages.admin.conferencerooms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import framework.common.UIMethods;
import framework.pages.admin.AbstractMainMenu;

/**
 * This class represents Conference room page
 * @author Ruben Blanco
 *
 */
public class ConferenceRoomPage extends AbstractMainMenu {
	UIMethods UI = new UIMethods();

	@FindBy(id = "roomsGrid")
	WebElement roomsGrid;

	/**
	 * Click over a Room 
	 * @param displayName is the display name of a Room
	 * @return
	 */
	public RoomInfoPage doubleClickOverRoomName(String displayName) {
		UI.doubleClick(driver.findElement(By.xpath("//span[contains(text(),'" 
				+ displayName + "')and@class='ng-binding']")));
		return new RoomInfoPage();
	}

	/**
	 * Get the display name of a Room
	 * @param displayName
	 * @return 
	 */
	public String getRoomDisplayName(String roomName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" 
				+ roomName + "')and@class='ng-binding']")).getText();
	}

	/**
	 * [YA]This method returns the icon is displayed in Out Of Order Column when an Out Of Order Period
	 * is established
	 * @param roomDisplayName
	 * @return
	 */
	public String getOutOfOrderIcon(String roomDisplayName) {
		WebElement outOfOrderIcon = driver.findElement(By.xpath("//span[contains(text(),'" + roomDisplayName 
				+ "')]//ancestor::div[@ng-click='row.toggleSelected($event)']//out-of-order-icon//span"));
		System.out.println(outOfOrderIcon.getAttribute("class"));
		return outOfOrderIcon.getAttribute("class");
	}

}
