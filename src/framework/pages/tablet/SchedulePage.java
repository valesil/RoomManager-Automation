package framework.pages.tablet;

import static framework.common.AppConfigConstants.BROWSER;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.selenium.SeleniumDriverManager;

/**
 * This class contains all locators of the schedule page and contain methods to use each one
 * 
 * @author Asael Calizaya
 *
 */
public class SchedulePage {
	private WebDriver driver;
	
	@FindBy(xpath = "//span[contains(text(), 'Scheduler')]")
	WebElement titleSchedulerLbl;
	
	@FindBy(id = "txtOrganizer")
	WebElement organizerTxtBox;
	
	@FindBy(xpath = "//small[@ng-show = 'formErrors.organizer']")
	WebElement errorMessageOrganizer;
	
	@FindBy(id = "txtSubject")
	WebElement subjectTxtBox;
	
	@FindBy(xpath = "//small[@ng-show = 'formErrors.title']")
	WebElement errorMessageSubject;
	
	@FindBy(id = "txtBody")
	WebElement bodyTxtBox;
	
	@FindBy(xpath = "//small[@ng-show = 'formErrors.attendeesUnSet']")
	WebElement errorMessageAttendee;
	
	@FindBy(xpath = "//input[@placeholder = 'Press enter or semicolon to confirm']")
	WebElement attendeesTxtBox;
	
	@FindBy(xpath = "//input[@ng-change = 'startTimeChanged()']")
	WebElement startTimeTxtBox;
	
	@FindBy(xpath = "//input[@type = 'time'and@ng-change='endTimeChanged()']")
	WebElement endTimeTxtBox;
	
	@FindBy(xpath = "//button[@ng-click = 'createMeeting()']")
	WebElement createBtn;
	
	@FindBy(xpath = "//button[@ng-click = 'cancelMeeting()']")
	WebElement removeBtn;
	
	@FindBy(xpath = "//button[@ng-click = 'updateMeeting()']")
	WebElement updateBtn;
	
	@FindBy(css = "div.currenttime")
	WebElement currentTimeLine;
	
	@FindBy(xpath = "//span[@ng-click = 'goToSearch()']")
	WebElement searchBtn;
	
	@FindBy(xpath = "//span[@ng-click = 'goBack()']")
	WebElement backBtn;
	
	public SchedulePage() {
		driver = SeleniumDriverManager.getManager().getDriver();
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
	
	private void setStartTime(String startTime) {
		startTimeTxtBox.click();
		startTimeTxtBox.sendKeys(Keys.BACK_SPACE);
		startTimeTxtBox.sendKeys(Keys.ARROW_LEFT);
		startTimeTxtBox.sendKeys(Keys.BACK_SPACE);
		startTimeTxtBox.sendKeys(Keys.ARROW_LEFT);
		startTimeTxtBox.sendKeys(Keys.BACK_SPACE);
		startTimeTxtBox.sendKeys(startTime);
		startTimeTxtBox.sendKeys(Keys.ARROW_UP);
	}
	
	private void setEndTime(String endTime) {
		endTimeTxtBox.click();
		endTimeTxtBox.sendKeys(Keys.BACK_SPACE);
		endTimeTxtBox.sendKeys(Keys.ARROW_LEFT);
		endTimeTxtBox.sendKeys(Keys.BACK_SPACE);
		endTimeTxtBox.sendKeys(Keys.ARROW_LEFT);
		endTimeTxtBox.sendKeys(Keys.BACK_SPACE);
		endTimeTxtBox.sendKeys(endTime);
		endTimeTxtBox.sendKeys(Keys.ARROW_UP);
	}
	
	public SchedulePage setStartTimeDate(String startTime) {
		if(BROWSER.equalsIgnoreCase("ie")) {
			startTimeTxtBox.clear();
			startTimeTxtBox.sendKeys(startTime);			
		} else if (BROWSER.equalsIgnoreCase("firefox")) {
			startTimeTxtBox.clear();
			startTimeTxtBox.sendKeys(startTime);
		} else {
			setStartTime(startTime);
		}
		return this;
	}
	
	public SchedulePage setEndTimeDate(String endTime) {
		if(BROWSER.equalsIgnoreCase("ie")) {
			endTimeTxtBox.clear();
			endTimeTxtBox.sendKeys(endTime);			
		} else if (BROWSER.equalsIgnoreCase("firefox")) {
			endTimeTxtBox.clear();
			endTimeTxtBox.sendKeys(endTime);
		} else {
			setEndTime(endTime);
		}
		return this;
	}
	
	public SchedulePage clickCreateBtn() {
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
		backBtn.click();
		return new HomePage();
	}
	
	public String getErrorMessageOrganizerLabel() {
		return errorMessageOrganizer.getText();
	}
	
	public String getErrorMessageSubjectLabel() {
		return errorMessageSubject.getText();
	}
	
	public String getErrorMessageAttendeeLabel() {
		return errorMessageAttendee.getText();
	}

	public String getTitleOfPage() {
		return titleSchedulerLbl.getText();
	}
	
	public String getNameMeetingCreated(String nameMeeting) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + "')]")).getText();
	}
	
	public SchedulePage clickOverMeetingCreated(String nameMeeting) {
		driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + "')]")).click();
		return this;
	}
	
	public String getEmailAttedee(String emailAttendee) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + emailAttendee + "')]")).getText();
	}
	
	public String getNameSubject() {
		return subjectTxtBox.getAttribute("value");
	}
	
	public String getNameOrganizer() {
		return organizerTxtBox.getAttribute("value");
	}
	
	public String getTextBody() {
		return bodyTxtBox.getAttribute("value");
	}
	
	public String getMessagePopUp(String message) {
		return driver.findElement(By.xpath("//div[contains(text(),'" + message + "')]")).getText();
	}
}
