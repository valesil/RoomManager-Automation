package framework.pages.tablet;

import static framework.common.AppConfigConstants.BROWSER;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.selenium.SeleniumDriverManager;

/**
 * This class contains all locators of the schedule page and contain methods to use each one
 * 
 * @author Asael Calizaya
 *
 */
public class SchedulePage {
	private WebDriver driver;
	private WebDriverWait wait;
	
	@FindBy(xpath = "//span[contains(text(),'Scheduler')]")
	WebElement titleSchedulerLbl;
	
	@FindBy(id = "txtOrganizer")
	WebElement organizerTxtBox;
	
	@FindBy(xpath = "//small[@ng-show='formErrors.organizer']")
	WebElement errorMessageOrganizerLbl;
	
	@FindBy(id = "txtSubject")
	WebElement subjectTxtBox;
	
	@FindBy(xpath = "//small[@ng-show='formErrors.title']")
	WebElement errorMessageSubjectLbl;
	
	@FindBy(id = "txtBody")
	WebElement bodyTxtBox;
	
	@FindBy(xpath = "//small[@ng-show='formErrors.attendeesUnSet']")
	WebElement errorMessageAttendeeLbl;
	
	@FindBy(xpath = "//input[@placeholder='Press enter or semicolon to confirm']")
	WebElement attendeesTxtBox;
	
	@FindBy(xpath = "//input[@ng-change='startTimeChanged()']")
	WebElement startTimeTxtBox;
	
	@FindBy(xpath = "//input[@type='time'and@ng-change='endTimeChanged()']")
	WebElement endTimeTxtBox;
	
	@FindBy(xpath = "//span[contains(text(),'Create')]")
	WebElement createBtn;
	
	@FindBy(xpath = "//button/span[contains(text(),'Remove')]")
	WebElement removeBtn;
	
	@FindBy(xpath = "//span[contains(text(),'Update')]")
	WebElement updateBtn;
	
	@FindBy(css = "css=div.currenttime")
	WebElement currentTimeLine;
	
	@FindBy(xpath = "//span[@ng-click='goToSearch()']")
	WebElement searchBtn;
	
	@FindBy(xpath = "//span[@ng-click='goBack()']")
	WebElement backBtn;
	
	@FindBy(xpath = "//input[@ng-model='dialog.credentials.username']")
	WebElement userNameTxt;
	
	@FindBy(xpath = "//input[@ng-model='dialog.credentials.password']")
	WebElement passwordTxt;
	
	@FindBy(xpath = "//button[@ng-click='dialog.ok()']")
	WebElement okBtn;
	
	@FindBy(xpath = "//button/span[contains(text(),'Cancel')]")
	WebElement cancelBtn;
	
	public SchedulePage() {
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}
	
	public SchedulePage setOrganizerTxtBox(String organizer) {
		organizerTxtBox.clear();
		organizerTxtBox.sendKeys(organizer);
		return this;
	}
	
	public SchedulePage setSubjectTxtBox(String subject) {
		subjectTxtBox.clear();
		subjectTxtBox.sendKeys(subject);
		return this;
	}
	
	public SchedulePage setAttendeeTxtBox(String attendiee) {
		attendeesTxtBox.click();
		attendeesTxtBox.sendKeys(attendiee);
		attendeesTxtBox.sendKeys(Keys.ENTER);
		return this;
	}
	
	public SchedulePage setBodyTxtBox(String textBody) {
		bodyTxtBox.clear();
		bodyTxtBox.sendKeys(textBody);
		return this;
	}
	
	/**
	 * This method is to set the start time of a meeting on Chrome
	 * @param startTime
	 */
	private void setStartTime(String startTime, String meridian) {
		startTimeTxtBox.click();
		startTimeTxtBox.sendKeys(Keys.BACK_SPACE);
		startTimeTxtBox.sendKeys(Keys.ARROW_LEFT);
		startTimeTxtBox.sendKeys(Keys.BACK_SPACE);
		startTimeTxtBox.sendKeys(Keys.ARROW_LEFT);
		startTimeTxtBox.sendKeys(Keys.BACK_SPACE);
		startTimeTxtBox.sendKeys(startTime);
		if(meridian == "am") {
			startTimeTxtBox.sendKeys(Keys.ARROW_UP);
		} else {
			startTimeTxtBox.sendKeys(Keys.ARROW_DOWN);
		}
	}
	
	/**
	 * This method is to set the end time of a meeting on Chrome
	 * @param endTime
	 */
	private void setEndTime(String endTime, String meridian) {
		endTimeTxtBox.click();
		endTimeTxtBox.sendKeys(Keys.BACK_SPACE);
		endTimeTxtBox.sendKeys(Keys.ARROW_LEFT);
		endTimeTxtBox.sendKeys(Keys.BACK_SPACE);
		endTimeTxtBox.sendKeys(Keys.ARROW_LEFT);
		endTimeTxtBox.sendKeys(Keys.BACK_SPACE);
		endTimeTxtBox.sendKeys(endTime);
		if(meridian == "am") {
			endTimeTxtBox.sendKeys(Keys.ARROW_UP);
		} else {
			endTimeTxtBox.sendKeys(Keys.ARROW_DOWN);
		}
	}
	
	public SchedulePage setStartTimeDate(String startTime, String meridian) {
		if(BROWSER.equalsIgnoreCase("ie")) {
			startTimeTxtBox.clear();
			startTimeTxtBox.sendKeys(startTime);			
		} else if (BROWSER.equalsIgnoreCase("firefox")) {
			startTimeTxtBox.clear();
			startTimeTxtBox.sendKeys(startTime);
		} else {
			setStartTime(startTime, meridian);
		}
		return this;
	}
	
	public SchedulePage setEndTimeDate(String endTime, String meridian) {
		if(BROWSER.equalsIgnoreCase("ie")) {
			endTimeTxtBox.clear();
			endTimeTxtBox.sendKeys(endTime);			
		} else if (BROWSER.equalsIgnoreCase("firefox")) {
			endTimeTxtBox.clear();
			endTimeTxtBox.sendKeys(endTime);
		} else {
			setEndTime(endTime, meridian);
		}
		return this;
	}
	
	public SchedulePage clickCreateBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(createBtn));
		createBtn.click();
		return this;
	}
	
	public SchedulePage clickRemoveBtn() {
		removeBtn.click();
		return this;
	}
	
	public SchedulePage clickUpdateBtn() {
		updateBtn.click();
		return this;
	}
	
	public HomePage clickBackBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(backBtn));
		backBtn.click();
		return new HomePage();
	}
	
	public String getErrorMessageOrganizerLbl() {
		return errorMessageOrganizerLbl.getText();
	}
	
	public String getErrorMessageSubjectLbl() {
		return errorMessageSubjectLbl.getText();
	}
	
	public String getErrorMessageAttendeeLbl() {
		return errorMessageAttendeeLbl.getText();
	}

	public String getTitleOfPageValue() {
		return titleSchedulerLbl.getText();
	}
	
	public String getNameMeetingCreatedValue(String nameMeeting) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + "')]")).getText();
	}
	
	public SchedulePage clickOverMeetingCreated(String nameMeeting) {
		driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + "')]")).click();
		return this;
	}
	
	public String getEmailAttendeeValue(String emailAttendee) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + emailAttendee + "')]")).getText();
	}
	
	public String getNameSubjectValue() {
		return subjectTxtBox.getAttribute("value");
	}
	
	public String getNameOrganizerValue() {
		return organizerTxtBox.getAttribute("value");
	}
	
	public String getTextBodyValue() {
		return bodyTxtBox.getAttribute("value");
	}
	
	/**
	 * This method is to found the message pop up that appears after do something
	 * @param message
	 * @return
	 */
	public String getMessagePopUpValue(String message) {
		WebElement messageLbl = driver.findElement(By.xpath("//div[contains(text(),'" + message + "')]"));
		wait.until(ExpectedConditions.visibilityOf(messageLbl));
		return messageLbl.getText();
	}
	
	public SchedulePage setUserNameTxtBox(String name) {
		userNameTxt.clear();
		userNameTxt.sendKeys(name);
		return this;
	}
	
	public SchedulePage setPasswordTxtBox(String password) {
		passwordTxt.clear();
		passwordTxt.sendKeys(password);
		return this;
	}
	
	public SchedulePage clickOkButton() {
		okBtn.click();
		return this;
	}
	
	public SchedulePage clickCancelButton() {
		cancelBtn.click();
		return this;
	}
	
	/**
	 * This method is to set credentials to can create a meeting
	 * @param name
	 * @param password
	 * @return
	 */
	public SchedulePage setCredentials(String name, String password) {
		setUserNameTxtBox(name);
		setPasswordTxtBox(password);
		return clickOkButton();
	}
}