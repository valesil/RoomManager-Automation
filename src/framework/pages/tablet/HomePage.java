package framework.pages.tablet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.selenium.SeleniumDriverManager;

public class HomePage {
	private WebDriver driver;
	
	@FindBy(xpath = "//div[@ng-bind = 'current._title']")
	WebElement meetingTitleText;
	@FindBy(xpath = "//div[contains(@class,'timeleft-remaining')]")
	WebElement timeLeft;
	@FindBy(xpath = "//div[@ng-bind='next._title']")
	WebElement infoNextTile;
	@FindBy(xpath = "//span[contains(@ng-bind, 'next._start')]")
	WebElement timeNextStart;
	@FindBy(xpath ="//span[contains(@ng-bind, 'next._end')]")
	WebElement timeNextEnd;
	@FindBy(xpath ="//div[@id='timeline-container']/div")
	WebElement timelineContainer;
	@FindBy(xpath ="//div[@ng-bind='current._organizer']")
	WebElement currentOrganizerText;
	@FindBy(css="div.tile-button-search")
	WebElement	searchBtn;
	@FindBy(css="div.tile-button-quick")
	WebElement settingsBtn;
	@FindBy(css="div.tile-button-schedule")
	WebElement scheduleBtn;
	
	public HomePage() {
		driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
	}
	
	public SearchPage searchPageLink() {
		searchBtn.click();
		return new SearchPage();
	}
	
	public SettingsPage SettingsPageLink() {
		settingsBtn.click();
		return new SettingsPage();
	}
	
	public SchedulePage SchedulePageLink() {
		scheduleBtn.click();
		return new SchedulePage();
	}
}
