package framework.pages.tablet;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.selenium.SeleniumDriverManager;

/**
 * 
 * @author Asael Calizaya
 *
 */
public class SchedulePage {
	private WebDriver driver;
	
	@FindBy(xpath = "//legend/div[1]/span[2]") //cambiar
	WebElement titleSchedulerLbl;
	
	@FindBy(id = "txtOrganizer")
	WebElement organizerTxtBox;
	
	@FindBy(xpath = "//small[@ng-show='formErrors.organizer']")
	WebElement errorMessageOrganizer;
	
	@FindBy(id = "txtSubject")
	WebElement subjectTxtBox;
	
	@FindBy(xpath = "//small[@ng-show='formErrors.title']")
	WebElement errorMessageSubject;
	
	@FindBy(id = "txtBody")
	WebElement bodyTxtBox;
	
	@FindBy(xpath = "//small[@ng-show='formErrors.attendeesUnSet']")
	WebElement errorMessageAttendee;
	
	@FindBy(xpath = "//input[@placeholder='Press enter or semicolon to confirm']")
	WebElement attendeesTxtBox;
	
	@FindBy(xpath = "//input[@ng-change='startTimeChanged()']")
	WebElement startTimeTxtBox;
	
	@FindBy(xpath = "//input[@type='time'and@ng-change='endTimeChanged()']")
	WebElement endTimeTxtBox;
	
	@FindBy(xpath = "//button[@ng-click='createMeeting()']")
	WebElement createBtn;
	
	@FindBy(xpath = "//button[@ng-click='cancelMeeting()']")
	WebElement removeBtn;
	
	@FindBy(xpath = "//button[@ng-click='updateMeeting()']")
	WebElement updateBtn;
	
	@FindBy(css = "css=div.currenttime")
	WebElement currentTimeLine;
	
	@FindBy(xpath = "//span[@ng-click='goToSearch()']")
	WebElement searchBtn;
	
	@FindBy(xpath = "//span[@ng-click='goBack()']")
	WebElement backBtn;
	
	@FindBy(xpath = "//div[contains(text(),'Meeting successfully created')]")
	WebElement successfullCreatedMessagePopUp;
	
	@FindBy(xpath = "//div[contains(text(),'Meeting successfully removed')]")
	WebElement successfullRemovedMessagePopUp;
	
	@FindBy(xpath = "//div[contains(text(),'Exist a conflict with another meeting')]")
	WebElement errorMessagePopUp;
	
	@FindBy(xpath = "//div[contains(text(),'Meeting successfully updated')]")
	WebElement successfullUpdatedMessagePopUp;
	
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
	
	public SchedulePage setStartTimeDate(String startTime) {
		startTimeTxtBox.clear();
		startTimeTxtBox.sendKeys(startTime);
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
	
	public SchedulePage setEndTimeDate(String endTime) {
		endTimeTxtBox.clear();
		endTimeTxtBox.sendKeys(endTime);
		return this;
	}

	public boolean getSuccessfulCreatedMessagePopUp() {
		return successfullCreatedMessagePopUp.isEnabled();
	}
	
	public boolean getSuccessfulRemovedMessagePopUp() {
		return successfullRemovedMessagePopUp.isEnabled();
	}
	
	public boolean getSuccessfulUpdatedMessagePopUp() {
		return successfullUpdatedMessagePopUp.isEnabled();
	}
	
	public boolean getErrorMessagePopUp() {
		return errorMessagePopUp.isEnabled();
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
	
	public String getMeetingCreated(String nameMeeting) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + "')]")).getText();
	}
	
	public SchedulePage clickOnMeetingToUpdate(String nameMeeting) {
		driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + "')]")).click();
		return this;
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
	
}
