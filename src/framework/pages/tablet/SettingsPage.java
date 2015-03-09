package framework.pages.tablet;

/**Created by Jose Cabrera
 * 3/4/15
 * 
 */
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.selenium.SeleniumDriverManager;
import static framework.common.AppConfigConstants.URL_TABLET;;

/**
 * @title  Settings
 * @author Jose Cabrera
 * @description This page allow select a room
 */
public class SettingsPage {

	//declare the instance of Selenium Webdriver
	private WebDriver driver;
	
	//The locator of button that is used for edit Applicants
	@FindBy(xpath = "//button[1]")//cambiar
	WebElement acceptBtn;
	
	@FindBy(xpath = "//div[1]/h4")//cambiar
	WebElement roomNumber;
	
	public SettingsPage() {
		driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
		driver.get(URL_TABLET);
	}
	
	public HomePage selectroom(String roomNum) {
		driver.findElement(By.xpath("//h4[contains(text(),'" + roomNum + "')]")).click();
		acceptBtn.click();
		return new HomePage();
	}
	
	public void quit(){
		driver.quit();
	}
	
}
