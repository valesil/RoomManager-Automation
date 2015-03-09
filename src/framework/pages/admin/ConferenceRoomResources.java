package framework.pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.selenium.SeleniumDriverManager;

public class ConferenceRoomResources {
	
WebDriver driver;
	
	@FindBy(xpath = "//div[1]/div[3]/button")
	WebElement clickAddAvailableResourceBtn;
	
	@FindBy(xpath = "//div[2]/div/div/div[2]/div/div/div/div/div[2]/span")
	WebElement firstTextResource;
	
	@FindBy(xpath ="//div[3]/div[2]/button")
	WebElement saveBtn;
	
	public ConferenceRoomResources() {
		this.driver = SeleniumDriverManager.getManager().getDriver();	
		PageFactory.initElements(driver, this); 
	}
	public void clickAddAvailableResourceBtn() {
		clickAddAvailableResourceBtn.click();
	}
	public String getResourceName(){
		return firstTextResource.getText();
	}
	public void clickSaveBtn() {
		saveBtn.click();
	}
}
