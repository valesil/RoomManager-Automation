package framework.pages.tablet;

import static framework.common.AppConfigConstants.BROWSER;
import static framework.common.MessageConstants.MEETING_ATTENDEES_REQUIRED;
import static framework.common.MessageConstants.MEETING_CREATED;
import static framework.common.MessageConstants.MEETING_ERROR;
import static framework.common.MessageConstants.MEETING_ORGANIZER_REQUIRED;
import static framework.common.MessageConstants.MEETING_REMOVED;
import static framework.common.MessageConstants.MEETING_SUBJECT_REQUIERED;
import static framework.common.MessageConstants.MEETING_TIME_STARTEND;
import static framework.common.MessageConstants.MEETING_UPDATED;
import static framework.utils.TimeManager.getTimeElement;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.common.UIMethods;
import framework.selenium.SeleniumDriverManager;
import framework.utils.TimeManager;

/**
 * Description: This class contains all locators of the schedule page and contain
 * methods to use each one
 * @author Asael Calizaya
 *
 */
public class SchedulePage {
	private WebDriver driver;
	private WebDriverWait wait;
	UIMethods uiMethods = new UIMethods();

	@FindBy(xpath = "//span[contains(text(),'Scheduler')]")
	WebElement schedulerLbl;

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

	@FindBy(xpath = "//div[@class='currenttime']")
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
	 * [AC] Get the driver and the wait to use that in this class
	 */
	public SchedulePage() {
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}

	/**
	 * [AC] Clear the content of the textBox and set the new value to an organizer
	 * @param organizer: new value to set
	 * @return
	 */
	public SchedulePage setOrganizerTxtBox(String organizer) {
		organizerTxtBox.clear();
		organizerTxtBox.sendKeys(organizer);
		return this;
	}

	/**
	 * [AC] Clear the content of the textBox and set the new value to the subject
	 * @param subject
	 * @return
	 */
	public SchedulePage setSubjectTxtBox(String subject) {
		subjectTxtBox.clear();
		subjectTxtBox.sendKeys(subject);
		return this;
	}

	/**
	 * [AC] Clear the content of the textBox, set the new value and press enter
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
	 * [AC] Clear the content of the textBox and set the new value to the body
	 * @param textBody
	 * @return
	 */
	public SchedulePage setBodyTxtBox(String textBody) {
		bodyTxtBox.clear();
		bodyTxtBox.sendKeys(textBody);
		return this;
	}

	/**
	 * [AC] This method sets the start time of a meeting on Chrome
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
	 * [AC] This method sets the end time of a meeting on Chrome
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
	 * [AC] Verify what browser is used, and according to that, chose the option to
	 * set startTime of a meeting 
	 * @param startTime
	 * @param meridian
	 * @return
	 */
	public SchedulePage setStartTimeDate(String startTime) {
		String from = getTimeElement(startTime, "hourMin");
		String fromMeridian = getTimeElement(startTime, "meridian");		
		if(BROWSER.equalsIgnoreCase("ie")) {
			startTimeTxtBox.clear();
			startTimeTxtBox.sendKeys(startTime);			
		} else if (BROWSER.equalsIgnoreCase("firefox")) {
			startTimeTxtBox.clear();
			startTimeTxtBox.sendKeys(startTime);
		} else {
			setStartTime(from, fromMeridian);
		}
		return this;
	}

	/**
	 * [AC] Verify what browser is used, and according to that, chose the option to 
	 * set endTime of a meeting
	 * @param endTime
	 * @param meridian
	 * @return
	 */
	public SchedulePage setEndTimeDate(String endTime) {
		String to = getTimeElement(endTime, "hourMin");
		String toMeridian = getTimeElement(endTime, "meridian");
		if(BROWSER.equalsIgnoreCase("ie")) {
			endTimeTxtBox.clear();
			endTimeTxtBox.sendKeys(endTime);			
		} else if (BROWSER.equalsIgnoreCase("firefox")) {
			endTimeTxtBox.clear();
			endTimeTxtBox.sendKeys(endTime);
		} else {
			setEndTime(to, toMeridian);
		}
		return this;
	}

	/**
	 * [AC] This method clicks on Create button
	 * @return
	 */
	public SchedulePage clickCreateBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(createBtn));
		createBtn.click();
		return this;
	}

	/**
	 * [AC] This method clicks on Remove button
	 * @return
	 */
	public SchedulePage clickRemoveBtn() {
		removeBtn.click();
		return this;
	}

	/**
	 * [AC] This method clicks on Update button
	 * @return
	 */
	public SchedulePage clickUpdateBtn() {
		updateBtn.click();
		return this;
	}

	/**
	 * [AC] This method clicks on back button
	 * @return
	 */
	public HomePage clickBackBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(backBtn));
		backBtn.click();
		return new HomePage();
	}

	/**
	 * [AC] This method clicks on search page
	 * @return
	 */
	public SearchPage clickSearchBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
		searchBtn.click();
		return new SearchPage();
	}

	/**
	 * [AC] This method get the value of a label from title page
	 * @return
	 */
	public String getTitleOfPageValue() {
		return schedulerLbl.getText();
	}

	/**
	 * [AC] This method search a meeting and return the name of that
	 * @param nameMeeting: name of a meeting to search
	 * @return
	 */
	public String getNameMeetingCreatedValue(String nameMeeting) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + "')]")).getText();
	}

	/**
	 * [JC] This method move the Time Line left or right(depend of the value)
	 * @param nameMeeting
	 * @return
	 */
	public SchedulePage moveTimeLine(int value){
		Actions builder = new Actions(driver);
		WebElement elem = driver.findElement(By.xpath("//div[@id='timelinePanel']"
				+ "/descendant::div[contains(@class,'vispanel center')]")); 
		builder.clickAndHold(elem)
				.moveByOffset(value, 0)
				.release().perform();
		return this;
	}
	
	/**
	 * [JC] This method move the Meeting selected left or right(depend of the value)
	 * @param nameMeeting
	 * @return
	 */
	public SchedulePage moveMeeting(String nameMeeting, int value){
		Actions builder = new Actions(driver);
		WebElement elem = driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + "')]"));
		builder.clickAndHold(elem)
				.moveByOffset(value, 0)
				.release().perform();
		return this;
	}
	
	/**
	 * [JC] This method search a meeting and click over Left Side of this meeting
	 * @param nameMeeting
	 * @return
	 */
	public SchedulePage resizeMeetingLeft(String nameMeeting){
		Actions builder = new Actions(driver);
		WebElement elem = driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + 
				"')]/parent::div/following-sibling::div[@class='drag-left']"));
		builder.clickAndHold(elem)
				.moveByOffset(-600, 0)
				.release().perform();
		return this;
	}
	
	/**
	 * [JC] This method search a meeting and click over Right Side of this meeting
	 * @param nameMeeting
	 * @return
	 */
	public SchedulePage resizeMeetingRight(String nameMeeting){
		Actions builder = new Actions(driver);
		WebElement elem = driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + 
				"')]/parent::div/following-sibling::div[@class='drag-right']"));
		builder.clickAndHold(elem)
				.moveByOffset(600, 0)
				.release().perform();
		return this;
	}
	
	
	/**
	 * [AC] This method search a meeting and click over that
	 * @param nameMeeting
	 * @return
	 */
	public SchedulePage clickOverMeetingCreated(String nameMeeting) {
		driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + "')]")).click();
		return this;
	}

	/**
	 * [AC] This method search for a attendee and return his value
	 * @param emailAttendee
	 * @return
	 */
	public String getEmailAttendeeValue(String emailAttendee) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + emailAttendee + "')]")).getText();
	}

	/**
	 * [AC] This method obtains the value of the textBox from subject
	 * @return
	 */
	public String getMeetingSubjectValue() {
		return subjectTxtBox.getAttribute("value");
	}

	/**
	 * [AC] This method obtains the value of the textBox from organizer
	 * @return
	 */
	public String getNameOrganizerValue() {
		return organizerTxtBox.getAttribute("value");
	}

	/**
	 * [AC] This method obtains the value of the textBox from body
	 * @return
	 */
	public String getBodyTxtBoxValue() {
		return bodyTxtBox.getAttribute("value");
	}

	/**
	 * [AC] This method founds the message pop up that appears after do something
	 * @param message
	 * @return
	 */
	private boolean getMessagePopUpValue(String message) {
		WebElement messageLbl = driver.findElement(By.xpath("//div[contains(text(),'" + message + "')]"));
		wait.until(ExpectedConditions.visibilityOf(messageLbl));
		return messageLbl.isDisplayed();
	}

	private boolean getAnyErrorMessageLbl(String message) {
		return driver.findElement(By.xpath("//small[contains(text(),'" + message + "')]")).isDisplayed();
	}

	/**
	 * [AC] This method gets the successfully message when meeting is created  
	 * @return boolean
	 */
	public boolean isMessageMeetingCreatedDisplayed() {
		return getMessagePopUpValue(MEETING_CREATED);
	}

	/**
	 * [AC] This method gets the successfully message when meeting is updated
	 * @return boolean
	 */
	public boolean isMessageMeetingUpdatedDisplayed() {
		return getMessagePopUpValue(MEETING_UPDATED);
	}

	/**
	 * [AC] This method gets the successfully message when meeting is deleted
	 * @return boolean
	 */
	public boolean isMessageMeetingDeletedDisplayed() {
		return getMessagePopUpValue(MEETING_REMOVED);
	}

	/**
	 * [JC] This method gets the error message when start time is less than end time
	 * @return boolean
	 */
	public boolean isMessageOfTimeErrorDisplayed() {
		return getAnyErrorMessageLbl(MEETING_TIME_STARTEND);
	}

	/**
	 * [AC] This method gets the error message when something bad happens
	 * @return boolean
	 */
	public boolean isMessageOfErrorDisplayed() {
		return getMessagePopUpValue(MEETING_ERROR);
	}

	/**
	 * [AC] This method gets the error label when does not put a subject
	 * @return
	 */
	public boolean isErrorSubjectDisplayed() {
		return getAnyErrorMessageLbl(MEETING_SUBJECT_REQUIERED);
	}

	/**
	 * [AC] This method gets the error label when does not put a organizer
	 * @return
	 */
	public boolean isErrorOrganizerDisplayed() {
		return getAnyErrorMessageLbl(MEETING_ORGANIZER_REQUIRED);
	}

	/**
	 * [AC] This method gets the error label when does not put attendees
	 * @return
	 */
	public boolean isErrorAttendeeDisplayed() {
		return getAnyErrorMessageLbl(MEETING_ATTENDEES_REQUIRED);
	}

	/**
	 * [AC]This method clears and set the new value to user name textBox
	 * @param name
	 * @return
	 */
	public SchedulePage setUserNameTxtBox(String name) {
		userNameTxt.clear();
		userNameTxt.sendKeys(name);
		return this;
	}

	/**
	 * [AC] This method clears and set the new value to password textBox
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
	 * [AC] This method click on OK button
	 * @return
	 */
	public SchedulePage clickOkButton() {
		okBtn.click();
		return this;
	}

	/**
	 * [AC] This method click on Cancel button
	 * @return
	 */
	public SchedulePage clickCancelButton() {
		cancelBtn.click();
		return this;
	}

	/**
	 * [YA]This method verifies Out Of Order is displayed in Scheduler's Timeline
	 * @param title: Out Of Order's Title
	 * @return
	 */
	public boolean isOutOfOrderBoxDisplayed(String title) {
		By outOfORderBoxLocator = By.xpath("//span[contains(text(),'" + title + "')]");
		return UIMethods.isElementPresent(outOfORderBoxLocator);	
	}

	/**
	 * [EN] This method confirm the credentials inserted by the user
	 * @param name
	 * @param password
	 * @return
	 */
	public SchedulePage confirmCredentials(String password) {
		setPasswordTxtBox(password);
		return clickOkButton();
	}

	/**
	 * [EN] Return start time value of a meeting selected
	 * @return start time value displayed on "From" text box.
	 */
	public String getStartTimeTxtBoxValue() {
		return startTimeTxtBox.getAttribute("value");
	}

	/**
	 * [EN] Return end time value of a meeting selected
	 * @return end time value displayed on "To" text box.
	 */
	public String getEndTimeTxtBoxValue() {
		return endTimeTxtBox.getAttribute("value");
	}

	/**
	 * [EN] This method setting the values to created a meeting.
	 * @param organizer
	 * @param subject
	 * @param startTime hh:mm a
	 * @param endTime  hh:mm a
	 * @param attendees
	 * @param bodyMeeting
	 * @return
	 */
	public SchedulePage createMeeting(String organizer, String subject, String startTime, 
			String endTime, String attendees, String bodyMeeting) {
		setOrganizerTxtBox(organizer);
		setSubjectTxtBox(subject);
		setStartTimeDate(startTime);
		setEndTimeDate(endTime);
		setAttendeeTxtBox(attendees);
		setBodyTxtBox(bodyMeeting);
		return clickCreateBtn();
	}

	
	/**
	 * [JC] This method verify if the label scheduler is displayed
	 * @return
	 */
	public boolean isSchedulerLblDisplayed() {
		return schedulerLbl.isDisplayed();
	}

	/**
	 * [JC] This method verify return the current date
	 * @return
	 */
	public String getTimeLineDate() {
		System.out.println(currentTimeLine.getAttribute("title"));
		String time = currentTimeLine.getAttribute("title").replace("th","").replace("st","")
				.replace("nd","").replace("Current time: ","");
		return time;
	}

	/**
	 * [YA]This method verifies if Meeting Box is present
	 * @param nameMeeting
	 * @return
	 */
	public boolean isMeetingBoxDisplayed(String nameMeeting) {
		By meetingBoxLocator = By.xpath("//span[contains(text(),'" + nameMeeting + "')]");
		return UIMethods.isElementPresent(meetingBoxLocator);
	}
	
	/**
	 * [AC] This method waits until the mask disappears
	 * @return
	 */
	public SchedulePage waitForMaskDisappears() {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='Modal-backdrop ng-scope']")));
		return this;
	} 
	
	/**
	 * [AC] This class delete a meeting
	 * @param nameMeeting: name of meeting to delete
	 * @return: This page, to use the same method repeated times
	 */
	public SchedulePage deleteMeeting(String nameMeeting, String password) {
				clickOverMeetingCreated(nameMeeting);
				clickRemoveBtn();
				confirmCredentials(password);
				isMessageMeetingDeletedDisplayed();
				return this;
	}
	
	/**
	 * [EN] Overload of createMetting method, where body meeting is optional.
	 * @param organizer
	 * @param subject
	 * @param minutesFrom minutes number to add/subtract of current time to set in {From} text box.
	 * @param minutesTo minutes number to add/subtract of current time to set in {To} text box.
	 * @param attendee
	 * @param password
	 * @return
	 */
	public SchedulePage createMeeting(String organizer, String subject, int minutesFrom, 
			int minutesTo, String attendee, String password) {

		String startTime = TimeManager.getTime(minutesFrom, "hh:mm a");
		String endTime = TimeManager.getTime(minutesTo, "hh:mm a");

		setOrganizerTxtBox(organizer);
		setSubjectTxtBox(subject);
		setStartTimeDate(startTime);
		setEndTimeDate(endTime);
		setAttendeeTxtBox(attendee);
		clickCreateBtn();	
		confirmCredentials(password);
		isMessageMeetingCreatedDisplayed();
		return this;
	}
}