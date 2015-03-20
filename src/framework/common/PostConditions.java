package framework.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import framework.pages.tablet.SchedulePage;
import framework.selenium.SeleniumDriverManager;

/**
 * Description This class is to delete the meetings created
 * @author Asael Calizaya
 *
 */
public class PostConditions {
	WebDriver driver;
	SchedulePage schedule = new SchedulePage();
	
	/**
	 * [AC] Get the driver and the wait to use that in this class
	 */
	public PostConditions() {
		driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * [AC] This class delete a meeting
	 * @param nameMeeting: name of meeting to delete
	 * @return: This page, to use the same method repeated times
	 */
	public PostConditions deleteMeeting(String nameMeeting, String password) {
		schedule
				.clickOverMeetingCreated(nameMeeting)
				.clickRemoveBtn()
				.setPasswordTxtBox(password)
				.clickOkButton()
				.isMessageMeetingDeletedDisplayed();
		return this;
	}
	
	public void goHome() {
		schedule.clickBackBtn();
	}
}
