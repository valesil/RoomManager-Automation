package framework.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.selenium.SeleniumDriverManager;

/**
 * Description This class is to delete the meetings created
 * @author Asael Calizaya
 *
 */
public class PostConditions {
	private WebDriver driver;
	private SchedulePage schedule = new SchedulePage();
	HomePage home = new HomePage();
	
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
	public SchedulePage deleteMeetingFromHome(String nameMeeting, String password) {
		home.getHome().clickScheduleBtn()
		.deleteMeeting(nameMeeting, password);
		return new SchedulePage();
	}
	
	public void goHome() {
		schedule.clickBackBtn();
	}
}
