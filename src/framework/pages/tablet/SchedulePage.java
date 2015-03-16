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
 * Description: This class contains all locators of the schedule page and contain
 * methods to use each one
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
	
	@FindBy(xpath = "//button[@ng-click='goBack()']")
	WebElement backBtn;
	
	@FindBy(xpath = "//input[@ng-model='dialog.credentials.username']")
	WebElement userNameTxt;
	
	@FindBy(xpath = "//input[@ng-model='dialog.credentials.password']")
	WebElement passwordTxt;
	
	@FindBy(xpath = "//button[@ng-click='dialog.ok()']")
	WebElement okBtn;
	
	@FindBy(xpath = "//button/span[contains(text(),'Cancel')]")
	WebElement cancelBtn;
	
	/**
	 * Get the driver and the wait to use that in this class
	 */
	public SchedulePage() {
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * Clear the content of the textBox and set the new value to an organizer
	 * @param organizer: new value to set
	 * @return
	 */
	public SchedulePage setOrganizerTxtBox(String organizer) {
		organizerTxtBox.clear();
		organizerTxtBox.sendKeys(organizer);
		return this;
	}
	
	/**
	 * Clear the content of the textBox and set the new value to the subject
	 * @param subject
	 * @return
	 */
	public SchedulePage setSubjectTxtBox(String subject) {
		subjectTxtBox.clear();
		subjectTxtBox.sendKeys(subject);
		return this;
	}
	
	/**
	 * Clear the content of the textBox, set the new value and press enter
	 * to the attendee value
	 * @param attendiee
	 * @return
	 */
	public SchedulePage setAttendeeTxtBox(String attendiee) {
		attendeesTxtBox.click();
		attendeesTxtBox.sendKeys(attendiee);
		attendeesTxtBox.sendKeys(Keys.ENTER);
		return this;
	}
	
	/**
	 * Clear the content of the textBox and set the new value to the body
	 * @param textBody
	 * @return
	 */
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
		if(meridian.equalsIgnoreCase("am")) {
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
		if(meridian.equalsIgnoreCase("am")) {
			endTimeTxtBox.sendKeys(Keys.ARROW_UP);
		} else {
			endTimeTxtBox.sendKeys(Keys.ARROW_DOWN);
		}
	}
	
	/**
	 * Verify what browser is used, and according to that, chose the option to
	 * set startTime of a meeting 
	 * @param startTime
	 * @param meridian
	 * @return
	 */
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
	
	/**
	 * Verify what browser is used, and according to that, chose the option to 
	 * set endTime of a meeting
	 * @param endTime
	 * @param meridian
	 * @return
	 */
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
	
	/**
	 * This method click on Create button
	 * @return
	 */
	public SchedulePage clickCreateBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(createBtn));
		createBtn.click();
		return this;
	}
	
	/**
	 * This method click on Remove button
	 * @return
	 */
	public SchedulePage clickRemoveBtn() {
		removeBtn.click();
		return this;
	}
	
	/**
	 * This method click on Update button
	 * @return
	 */
	public SchedulePage clickUpdateBtn() {
		updateBtn.click();
		return this;
	}
	
	/**
	 * This method click on back button
	 * @return
	 */
	public HomePage clickBackBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(backBtn));
		backBtn.click();
		return new HomePage();
	}
	
	/**
	 * This method get the value of a label from organizer
	 * @return
	 */
	public String getErrorMessageOrganizerLbl() {
		return errorMessageOrganizerLbl.getText();
	}
	
	/**
	 * This method get the value of a label from subject 
	 * @return
	 */
	public String getErrorMessageSubjectLbl() {
		return errorMessageSubjectLbl.getText();
	}
	
	/**
	 * This method get the value of a label from attendee
	 * @return
	 */
	public String getErrorMessageAttendeeLbl() {
		return errorMessageAttendeeLbl.getText();
	}
	
	/**
	 * This method get the value of a label from title page
	 * @return
	 */
	public String getTitleOfPageValue() {
		return titleSchedulerLbl.getText();
	}
	
	/**
	 * This method search a meeting and return the name of that
	 * @param nameMeeting: name of a meeting to search
	 * @return
	 */
	public String getNameMeetingCreatedValue(String nameMeeting) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + "')]")).getText();
	}
	
	/**
	 * This method search a meeting and click over that
	 * @param nameMeeting
	 * @return
	 */
	public SchedulePage clickOverMeetingCreated(String nameMeeting) {
		driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + "')]")).click();
		return this;
	}
	
	/**
	 * This method search for a attendee and return his value
	 * @param emailAttendee
	 * @return
	 */
	public String getEmailAttendeeValue(String emailAttendee) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + emailAttendee + "')]")).getText();
	}
	
	/**
	 * This method obtains the value of the textBox from subject
	 * @return
	 */
	public String getNameSubjectValue() {
		return subjectTxtBox.getAttribute("value");
	}
	
	/**
	 * This method obtains the value of the textBox from organizer
	 * @return
	 */
	public String getNameOrganizerValue() {
		return organizerTxtBox.getAttribute("value");
	}
	
	/**
	 * This method obtains the value of the textBox from body
	 * @return
	 */
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
	
	/**
	 * This method clear and set the new value to user name textBox
	 * @param name
	 * @return
	 */
	public SchedulePage setUserNameTxtBox(String name) {
		userNameTxt.clear();
		userNameTxt.sendKeys(name);
		return this;
	}
	
	/**
	 * this method clear and set the new value to password textBox
	 * @param password
	 * @return
	 */
	public SchedulePage setPasswordTxtBox(String password) {
		wait.until(ExpectedConditions.visibilityOf(passwordTxt));
		passwordTxt.clear();
		passwordTxt.sendKeys(password);
		return this;
	}
	
	/**
	 * This method click on Ok button
	 * @return
	 */
	public SchedulePage clickOkButton() {
		okBtn.click();
		return this;
	}
	
	/**
	 * This method click on Cancel button
	 * @return
	 */
	public SchedulePage clickCancelButton() {
		cancelBtn.click();
		return this;
	}
}
