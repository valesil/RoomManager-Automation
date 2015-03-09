package framework.pages.tablet;

/**Created by Jose Cabrera
 * 3/4/15
 * 
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
	private WebDriver driver;
	
	@FindBy(xpath = "//button[@type='button']")
	WebElement advancedBtn;
	
	@FindBy(id = "txtRoomName")
	WebElement roomNameTxtBox;
	
	@FindBy(id = "txtMinimumCapacity")
	WebElement minimumCapacityTxtBox;
	
	@FindBy(id = "listLocation")
	WebElement locationCmbBox;
	
	@FindBy(xpath = "//*[@id='collapseExample']/div[1]/div[2]") //cambiar
	WebElement listResources;
	
	@FindBy(xpath = "//rm-timeline/div/div[5]/div[1]/div") //cambiar
	WebElement listRooms;
	
	@FindBy(xpath = "//span/rm-clock/span[1]") //cambiar
	WebElement dateLabel;
	
	@FindBy(xpath = ".//*[@id='54f642c6bfc087d40f69ac93']") //cambiar
	WebElement roomLink;
	
	public SearchPage() {
		this.driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
	}
	public void clickAdvancedBtn() {
		advancedBtn.click();		
	}
	
	public void setName(String strName) {
		roomNameTxtBox.sendKeys(strName);  
	}
	
	public void setMinimumCap(String strMinimumCap) {
		minimumCapacityTxtBox.clear();
		minimumCapacityTxtBox.sendKeys(strMinimumCap);  
	}
	
	public void setlocation(String strLocation) {
		locationCmbBox.sendKeys(strLocation);  
	}
	
	public void search(String strName,String strMinimumCap,String strLocation) {
		clickAdvancedBtn();
		setName(strName);
		setMinimumCap(strMinimumCap);
		setlocation(strLocation);
	}
	
	public Boolean filtersArePresent(){
		return locationCmbBox.isDisplayed()&&
				minimumCapacityTxtBox.isDisplayed()&&
				roomNameTxtBox.isDisplayed()&&
				listResources.isDisplayed();
	}
	
	public Boolean dateArePresent(){
		Calendar ca1 = Calendar.getInstance();
        Date d = new java.util.Date(ca1.getTimeInMillis());
        String date=new SimpleDateFormat("MMMM d YYY").format(d).toString();
        System.out.println(date);
        return dateLabel.getText().replace("th","")
								  .replace("st","")
								  .replace("nd","")
								  .equals(date);
	}
	public SchedulePage selectRoom() {
		roomLink.click();
		return new SchedulePage();
	}
	
}

