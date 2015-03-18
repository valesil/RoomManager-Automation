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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import framework.selenium.SeleniumDriverManager;

/**
 * @title  SearchPage
 * @author Jose Cabrera
 * @description This page allow search by different criteria
 */
public class SearchPage {

	private WebDriver driver;
	@SuppressWarnings("rawtypes")
	private Wait wait;

	@FindBy(xpath = "//span[@ng-click='goBack()']")
	WebElement backBtn;

	@FindBy(xpath = "//span[contains(text(),'Advanced')]")
	WebElement advancedBtn;
	
	@FindBy(xpath = "//button[@ng-click='clear()']")
	WebElement clearBtn;

	@FindBy(id = "txtRoomName")
	WebElement roomNameTxtBox;

	@FindBy(id = "txtMinimumCapacity")
	WebElement minimumCapacityTxtBox;

	@FindBy(id = "listLocation")
	WebElement locationCmbBox;

	@FindBy(xpath = "//div[@class='row v-space ng-scope']/div[@class='col-xs-12']/following-sibling::div")
	WebElement listResources;

	@FindBy(xpath = "//span[@ng-bind='currentTime']")
	WebElement dateLabel;
	
	public SearchPage() {
		this.driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
		wait=SeleniumDriverManager.getManager().getWait();
	}

	public SearchPage clickClearBtn() {
		clearBtn.click();
		return this;
	}

	public HomePage clickBackBtn() {
		backBtn.click();
		return new HomePage();
	}

	@SuppressWarnings("unchecked")
	public SearchPage clickCollapseAdvancedBtn() {
		advancedBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(locationCmbBox));
		return this;
	}
	public SearchPage clickHiddenAdvancedBtn() {
		advancedBtn.click();
		return this;
	}

	public SearchPage setName(String strName) {
		roomNameTxtBox.sendKeys(strName);
		return this;
	}

	public SearchPage setMinimumCap(String strMinimumCap) {
		minimumCapacityTxtBox.clear();
		minimumCapacityTxtBox.sendKeys(strMinimumCap);  
		return this;
	}
	
	public boolean isEmptyMinimumCap() {
		return minimumCapacityTxtBox.getAttribute("value").isEmpty();
	}

	public SearchPage setLocation(String strLocation) {
		locationCmbBox.sendKeys(strLocation); 
		return this;
	}

	public SearchPage search(String strName,String strMinimumCap,String strLocation){
		return clickCollapseAdvancedBtn()
		.setName(strName)
		.setMinimumCap(strMinimumCap)
		.setLocation(strLocation);
	}
	
	public Boolean filtersArePresent(){
		return locationCmbBox.isDisplayed()&&
				minimumCapacityTxtBox.isDisplayed()&&
				roomNameTxtBox.isDisplayed()&&
				listResources.isDisplayed();

	}

	public Boolean dateIsPresent(){
		Date d = new java.util.Date(Calendar.getInstance().getTimeInMillis());
		String date=new SimpleDateFormat("MMMM d YYY").format(d).toString();
		return dateLabel.getText().replace("th","").replace("st","").replace("nd","").equals(date);
	}

	public SchedulePage selectRoom(String roomName) {
		driver.findElement(By.xpath("//button[contains(text(),'" + roomName + "')and@class='ng-scope']")).click();
		return new SchedulePage();
	}

	public SearchPage selectResource(String resourceName) {
		driver.findElement(By.xpath("//div[contains(text(),'" + resourceName + 
				"')and@class='ng-binding']/preceding-sibling::div")).click();
		return this;
	}

	public boolean roomsInList(LinkedList<String> names) {
		boolean allInList=true;
		while(!names.isEmpty()) {
			allInList=driver.findElement(By.xpath("//button[contains(text(),'" + 
					  names.remove(0) + "')and@class='ng-scope']")).isDisplayed();
		}
		return allInList;
	}

	public boolean resourceIsSelected(String resourceName) {
		return driver.findElement(By.xpath("//div[contains(text(),'" + 
			   resourceName + "')and@class='ng-binding']/preceding-sibling::div")).isSelected();
	}

	public boolean resourcesAreSelected(LinkedList<String> names) {
		boolean allSelectedInList=false;
		while(!names.isEmpty()) {
			allSelectedInList=driver.findElement(By.xpath("//div[contains(text(),'" + 
							  names.remove(0) + "')and@class='ng-binding']/preceding-sibling::div")).isSelected();
		}
		return allSelectedInList;	
	}

	public boolean resourcesInList(LinkedList<String> names) {
		boolean allInList=false;
		while(!names.isEmpty()) {
			allInList=driver.findElement(By.xpath("//div[contains(text(),'" + 
					  names.remove(0) + "')and@class='ng-binding']/preceding-sibling::div")).isDisplayed();
		}
		return allInList;	
	}
	
	/**
	 * [JC] allFiltersAreCleaned
	 * @return a boolean answer true if all elements of search are cleaned(without information)
	 */
	public boolean allFiltersAreCleaned() {
		return roomNameTxtBox.getText().equals("")
				&&minimumCapacityTxtBox.getText().equals("")
				&&!locationCmbBox.isSelected();
	}
	
	/**
	 * This method verifies if an Out Of Order is displayed in Search timeline
	 * @param title: Out Of Order Title
	 * @return
	 */
	public boolean isOutOfOrderBoxDisplayed(String title){
		return driver.findElement(By.xpath("//div[contains(text(),'" + title + "')]")).isDisplayed();
	}
}

