package framework.pages.tablet;

import static framework.common.AppConfigConstants.URL_TABLET_HOME;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.selenium.SeleniumDriverManager;

/**
 * This class represent the home page of tablet.
 * This page is divided in the following sections:
 * Now tile,  New tile, Now tape, New tape, Time line.
 * Also has the buttons: Settings, Schedule, Search.
 * @author Eliana Navia 
 */
public class HomePage {
	private WebDriver driver;
	private WebDriverWait wait;

	@FindBy(xpath = "//div[@ng-bind='current._title']")
	WebElement nowTileLbl;

	@FindBy(xpath = "//div[@ng-bind='next._title']")
	WebElement nextTileLbl; 

	@FindBy(xpath = "//div[@ng-bind='current._organizer']")
	WebElement currentMeetingOrganizerLbl;
    
	@FindBy(xpath = "//div[@ng-bind='next._organizer']")
	WebElement nextMeetingOrganizerLbl;
	
	@FindBy(xpath = "//div[contains(@class,'timeleft-remaining')]")
	WebElement timeLeftLbl;

	@FindBy(xpath = "//span[contains(@ng-bind,'next._start')]")
	WebElement timeNextStartLbl;

	@FindBy(xpath = "//span[contains(@ng-bind,'next._end')]")
	WebElement timeNextEndLbl;

	@FindBy(xpath = "//span[@ng-bind='currentTime']")
	WebElement currentTimeLbl;
	
	@FindBy(xpath = "//i[@ng-class='resource.icon']")
	WebElement resourceIcon;
	
	@FindBy(xpath = "//div[@ng-bind='resource.name']")
	WebElement resourceNameLbl;
	
	@FindBy(xpath = "//div[@ng-bind='resource.quantity']")
	WebElement resourceQuantityLbl;
	
	@FindBy(xpath = "//span[@ng-bind='room._customDisplayName']")
	WebElement roomDisplayNameLbl;
	
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

	public void getHome(){
		driver.get(URL_TABLET_HOME);
	}
	
	public String getNowTileLbl() {
		wait.until(ExpectedConditions.elementToBeClickable(nowTileLbl));
		return nowTileLbl.getText();
	}

	public String getTimeLeftLbl() {
		return timeLeftLbl.getText();
	}

	public String getNextTileLbl() {
		wait.until(ExpectedConditions.elementToBeClickable(nextTileLbl));
		return nextTileLbl.getText();
	}

	public String getNextMeetingOrganizerNameLbl() {
		return nextMeetingOrganizerLbl.getText();
	}
	
	public String getStartTimeNextMeetingLbl() {
		return timeNextStartLbl.getText();
	}
	
	public String getEndTimeNextMeetingLbl() {
		return timeNextEndLbl.getText();
	}
	
	public String getRoomDisplayNameLbl() {
		wait.until(ExpectedConditions.visibilityOf(roomDisplayNameLbl));
		return roomDisplayNameLbl.getText();
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
		wait.until(ExpectedConditions.elementToBeClickable(nextTileLbl));
		scheduleBtn.click();
		return new SchedulePage();
	}
}
