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
import framework.common.AppConfigConstants;
/**
 * @title  Settings
 * @author Jose Cabrera
 * @description This page allow select a room
 */
public class SettingsPage {

	//declare the instance of Selenium Webdriver
	private WebDriver driver;
	
	
	@FindBy(xpath = "//button[@ng-click='saveSelectedRoom()']")//cambiar
	WebElement acceptBtn;
	
	
	public SettingsPage() {
		driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
		driver.get(AppConfigConstants.URL_TABLET);
	}
	
	public HomePage selectRoom(String roomNum) {
		driver.findElement(By.xpath("//h4[contains(text(),'" + roomNum + "')]")).click();
		acceptBtn.click();
		return new HomePage();
	}
	
	public void quit(){
		driver.quit();
	}
	
}
