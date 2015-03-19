package framework.pages.tablet;

import static framework.common.AppConfigConstants.URL_TABLET_HOME;

import org.openqa.selenium.By;
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

	@FindBy(xpath = "//div[@class='currenttime']")
	WebElement currentTimeLine;
	
	@FindBy(xpath = "//span[@ng-bind='currentTime']")
	WebElement currentTimeLbl;
	
	@FindBy(xpath = "//div[@ng-class='resource.icon']")
	WebElement resourceIcon;
	
	@FindBy(xpath = "//div[@ng-bind='resource.name']")
	WebElement resourceNameLbl;
	
	@FindBy(xpath = "//div[@ng-bind='resource.quantity']")
	WebElement resourceQuantityLbl;
	
	@FindBy(xpath = "//span[@ng-bind='room._customDisplayName']")
	WebElement roomDisplayNameLbl;
	
	@FindBy(xpath = "//div[@ng-click='goToSearch()']")
	WebElement searchBtn;

	@FindBy(css = "div.tile-button-quick")
	WebElement settingsBtn;

	@FindBy(xpath = "//div[@ng-click='goToSchedule()']")
	WebElement scheduleBtn;
	
	@FindBy(xpath = "//span[@ng-bind = 'room._code']")
	WebElement roomCodeValueLbl;

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
	
	public SearchPage clickSearchPageBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
		searchBtn.click();
		return new SearchPage();
	}

	public SettingsPage clickSettingsPageBtn() {
		settingsBtn.click();
		return new SettingsPage();
	}

	public SchedulePage clickSchedulePageBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(scheduleBtn));
		scheduleBtn.click();
		return new SchedulePage();
	}
	
	/**
	 * [JC] This method return a new SchedulePage
	 * @return
	 */
	public SchedulePage clickNowTileLbl() {
		nowTileLbl.click();
		return new SchedulePage();
	}
	/**
	 * [RB]This method gets the room code of selected room
	 * @return 
	 */
	public String getRoomCode() {
		return roomCodeValueLbl.getText();
	}

	/**
	 * [RB]This method verifies if an associated resource (display name and quantity
	 * is displayed in the tablet home page)
	 * @param resourceName
	 * @param amount
	 * @return
	 */
	public boolean VerifyResourceIsAsociated(String resourceName, String amount) {
		//this condition call other methods to verify if a elements are present in the tablet
		if (isAmountDisplayed(amount)&&isResourceNameDisplayed(resourceName))
			return true;
		else
			return false;
	}

	/**
	 * [RB]This method verifies the display name of an associated resource
	 * is displayed in the tablet home page
	 * @param amount
	 * @return
	 */
	private boolean isAmountDisplayed(String amount) {
		return driver.findElement(By.xpath("//div[contains(text(),'"+amount+
				"')and@ng-bind='resource.quantity']")).isDisplayed();
	}
	
	/**
	 * [RB]This method verifies the quantity of an associated resource
	 * is displayed in the tablet home page
	 * @param resourceDisplayName
	 * @return
	 */
	private boolean isResourceNameDisplayed(String resourceDisplayName) {
		return driver.findElement(By.xpath("//div[contains(text(),'"+resourceDisplayName+
				"')and@ng-bind='resource.name']")).isDisplayed();
	}

	public boolean homePageIsDisplayed() {
		return scheduleBtn.isDisplayed();
	}
	
	/**
	 * [JC] This method verify return the current date
	 * @return
	 */
	public String getTimeLineDate(){
		String time = currentTimeLine.getAttribute("title").replace("th","").replace("st","")
		.replace("nd","").replace("Current time: ","");
		return time;
	}
}
