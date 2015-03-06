package framework.pages.tablet;

/**Created by Jose Cabrera
 * 3/4/15
 * 
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.selenium.SeleniumDriverManager;

/**
 * @title  Settings
 * @author Jose Cabrera
 * @description This page allow select a room
 */
public class SettingsPage {

	//declare the instance of Selenium Webdriver
	private WebDriver driver;
	
	//The locator of button that is used for edit Applicants
	@FindBy(xpath = "//button[1]")
	WebElement acceptBtn;
	@FindBy(xpath = "//div[1]/h4")
	WebElement roomNumber;
	
	public SettingsPage() {
		driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
		driver.get("http://172.20.208.177:4055/tablet");
	}
	
	public HomePage selectroom(String roomNum)
	{
		roomNumber.click();
		acceptBtn.click();
		return new HomePage();
	}
	
}
