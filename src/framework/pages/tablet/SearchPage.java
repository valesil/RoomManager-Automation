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
 * @title  SearchPage
 * @author Jose Cabrera
 * @description This page allow search by different criteria
 */
public class SearchPage {

	//declare the instance of Selenium Webdriver
	WebDriver driver;
	
	//The locator of button that is used for edit Applicants
	@FindBy(xpath = "//button[@type='button']")
	WebElement advancedBtn;
	@FindBy(id = "txtRoomName")
	WebElement roomNameTxtBox;
	@FindBy(id = "txtMinimumCapacity")
	WebElement minimumCapacityTxtBox;
	@FindBy(id = "listLocation")
	WebElement locationCmbBox;

	public SearchPage() {
		this.driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
	}
	
	public void setName(String strName) {
		roomNameTxtBox.sendKeys(strName);  
	}
	
	public void setMinimumCap(String strMinimumCap) {
		minimumCapacityTxtBox.sendKeys(strMinimumCap);  
	}
	
	public void setlocation(String strLocation) {
		locationCmbBox.sendKeys(strLocation);  
	}
	
	public void search(String strName,String strMinimumCap,String strLocation)
	{
		advancedBtn.click();
		setName(strName);
		setMinimumCap(strMinimumCap);
		setlocation(strLocation);
	}
}

