package framework.pages.admin;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * 
 * @author Administrator
 *
 */
public class HomePage {

	@FindBy(linkText="SIGN-ON") WebElement conferenceRoomLink;
	
	/**
	 * 
	 * @return
	 */
	public  RoomInfoPage clickConferenceRoomLink() {
				
		return new RoomInfoPage();
	}
	
}
