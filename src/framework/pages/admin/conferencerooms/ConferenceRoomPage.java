package framework.pages.admin.conferencerooms;

import org.openqa.selenium.By;

import framework.common.UIMethods;
import framework.pages.admin.AbstractMainMenu;

/**
 * This class represents Conference room page
 * @author Ruben Blanco
 *
 */
public class ConferenceRoomPage extends AbstractMainMenu {
	
	UIMethods UI = new UIMethods();

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
}
