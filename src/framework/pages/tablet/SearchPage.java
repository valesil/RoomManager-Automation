package framework.pages.tablet;

/**Created by Jose Cabrera
 * 3/4/15
 * 
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import org.openqa.selenium.By;
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

	@FindBy(xpath = "//span[@ng-click='goBack()']")
	WebElement backBtn;

	@FindBy(xpath = "//span[contains(text(),'Advanced')]")
	WebElement advancedBtn;

	@FindBy(id = "txtRoomName")
	WebElement roomNameTxtBox;

	@FindBy(id = "txtMinimumCapacity")
	WebElement minimumCapacityTxtBox;

	@FindBy(id = "listLocation")
	WebElement locationCmbBox;

	@FindBy(xpath = "//div[@class='row v-space ng-scope']/div/child::div[@class='pull-left ng-scope']/parent::div[@class='col-xs-12']")
	WebElement listResources;

	@FindBy(xpath = "//span[@ng-bind='currentTime']")
	WebElement dateLabel;

	public SearchPage() {
		this.driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
	}

	public void clickClearBtn() {

	}

	public HomePage clickBackBtn() {
		backBtn.click();
		return new HomePage();
	}

	public void clickAdvancedBtn() throws InterruptedException {
		advancedBtn.click();
		Thread.sleep(300);
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

	public void search(String strName,String strMinimumCap,String strLocation) throws InterruptedException
	{
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
		return dateLabel.getText().replace("th","")
				.replace("st","")
				.replace("nd","")
				.equals(date);
	}

	public SchedulePage selectRoom(String name) throws InterruptedException {
		driver.findElement(By.xpath("//button[contains(text(),'"+name+"')and@class='ng-scope']")).click();
		Thread.sleep(300);
		return new SchedulePage();
	}

	public void selectResource(String name) {
		driver.findElement(By.xpath("//div[contains(text(),'"+name+"')and@class='ng-binding']/preceding-sibling::div")).click();
	}

	public boolean roomsInList(LinkedList<String> names) {
		boolean allInList=false;
		while(!names.isEmpty()) {
			allInList=driver.findElement(By.xpath("//button[contains(text(),'"+names.remove(0)+"')and@class='ng-scope']")).isDisplayed();
		}
		return allInList;
	}

	public boolean resourceIsSelected(String name) {
		return driver.findElement(By.xpath("//div[contains(text(),'"+name+"')and@class='ng-binding']/preceding-sibling::div")).isSelected();
	}

	public boolean resourcesAreSelected(LinkedList<String> names) {
		boolean allSelectedInList=false;
		while(!names.isEmpty()) {
			allSelectedInList=driver.findElement(By.xpath("//div[contains(text(),'"+names.remove(0)+"')and@class='ng-binding']/preceding-sibling::div")).isSelected();
		}
		return allSelectedInList;	
	}

	public boolean resourcesInList(LinkedList<String> names) {
		boolean allInList=false;
		while(!names.isEmpty()) {
			allInList=driver.findElement(By.xpath("//div[contains(text(),'"+names.remove(0)+"')and@class='ng-binding']/preceding-sibling::div")).isDisplayed();
		}
		return allInList;	
	}

	public boolean allResourcesAreClean() {
		return roomNameTxtBox.getText().equals("")
				&&minimumCapacityTxtBox.getText().equals("")
				&&!locationCmbBox.isSelected();
	}
}

