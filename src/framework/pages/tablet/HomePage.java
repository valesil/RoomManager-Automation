package framework.pages.tablet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.selenium.SeleniumDriverManager;

/**
 * 
 * @author Eliana Navia
 *
 */
public class HomePage {
	private WebDriver driver;
	private WebDriverWait wait;

	@FindBy(xpath = "//div[@ng-bind = 'current._title']")
	WebElement nowTileValue;

	@FindBy(xpath = "//div[@ng-bind='next._title']")
	WebElement nextTileValue; 

	@FindBy(xpath = "//div[@ng-bind='current._organizer']")
	WebElement currentMeetingOrganizerValue;

	@FindBy(xpath ="//div[@ng-bind='next._organizer']")
	WebElement nextMeetingOrganizerValue;

	@FindBy(xpath = "//div[contains(@class,'timeleft-remaining')]")
	WebElement timeLeftValue;

	@FindBy(xpath = "//span[contains(@ng-bind, 'next._start')]")
	WebElement timeNextStartValue;

	@FindBy(xpath ="//span[contains(@ng-bind, 'next._end')]")
	WebElement timeNextEndValue;

	@FindBy(xpath = "//span[@ng-bind ='currentTime']")
	WebElement currentTimeValue;

	@FindBy(xpath = "//span[@ng-bind = 'room._customDisplayName']")
	WebElement roomDisplayNameValue;

	@FindBy(css = "div.tile-button-search")
	WebElement searchBtn;

	@FindBy(css = "div.tile-button-quick")
	WebElement settingsBtn;

	@FindBy(css = "div.tile-button-schedule")
	WebElement scheduleBtn;

	public HomePage() {
		driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
		wait = SeleniumDriverManager.getManager().getWait();
	}

	public String getNowTileValue(){
		wait.until(ExpectedConditions.elementToBeClickable(nowTileValue));
		return nowTileValue.getText();
	}

	public String getTimeLeftValue(){
		return timeLeftValue.getText();
	}

	public String getNextTileValue(){
		return nextTileValue.getText();
	}

	public String getNextMeetingOrganizerNameValue(){
		return nextMeetingOrganizerValue.getText();
	}

	public String getStartTimeNextMeetingValue(){
		return timeNextStartValue.getText();
	}

	public String getEndTimeNextMeetingValue(){
		return timeNextEndValue.getText();
	}

	public String getRoomDisplayNameValue(){
		return roomDisplayNameValue.getText();
	}
	
	public SearchPage clickSearchPageLink() {
		searchBtn.click();
		return new SearchPage();
	}

	public SettingsPage clickSettingsPageLink() {
		settingsBtn.click();
		return new SettingsPage();
	}

	public SchedulePage clickSchedulePageLink() {
		scheduleBtn.click();
		return new SchedulePage();
	}

	/**
	 * Close the browser.
	 */
	public void closeBrowser(){
		SeleniumDriverManager.getManager().quitDriver();
	}
}
