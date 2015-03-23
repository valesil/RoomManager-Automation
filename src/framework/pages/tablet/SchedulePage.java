package framework.pages.tablet;

import static framework.common.AppConfigConstants.BROWSER;
import static framework.common.MessageConstants.MEETING_ATTENDEES_INVALID;
import static framework.common.MessageConstants.MEETING_ATTENDEES_REQUIRED;
import static framework.common.MessageConstants.MEETING_CREATED;
import static framework.common.MessageConstants.MEETING_ERROR;
import static framework.common.MessageConstants.MEETING_ORGANIZER_REQUIRED;
import static framework.common.MessageConstants.MEETING_REMOVED;
import static framework.common.MessageConstants.MEETING_SUBJECT_REQUIERED;
import static framework.common.MessageConstants.MEETING_TIME_STARTEND;
import static framework.common.MessageConstants.MEETING_UPDATED;
import static framework.common.MessageConstants.MEETING_PAST_CREATED_ERROR;
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

	@FindBy(xpath = "//div[@class='item range meeting']")
	WebElement itemRangeMeeting;

	@FindBy(xpath = "//div[@class='vispanel center']")
	WebElement timeLine;

	@FindBy(css = "div.Modal-holder.ng-scope")
	WebElement mask;

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
	 * @return SchedulePage
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
	 * @return SchedulePage
	 */
	public SchedulePage setAttendeeTxtBoxPressingEnter(String attendiee) {
		attendeesTxtBox.click();
		attendeesTxtBox.sendKeys(attendiee);
		attendeesTxtBox.sendKeys(Keys.ENTER);
		return this;
	}

	/**
	 * [AC] Clear the content of the textBox, set the new value and press semicolon
	 * to the attendee value
	 * @param attendiee
	 * @return SchedulePage
	 */
	public SchedulePage setAttendeeTxtBoxPressingSemicolon(String attendiee) {
		attendeesTxtBox.click();
		attendeesTxtBox.sendKeys(attendiee);
		attendeesTxtBox.sendKeys(Keys.SEMICOLON);
		return this;
	}

	/**
	 * [AC] Clear the content of the textBox and set the new value to the body
	 * @param textBody
	 * @return SchedulePage
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
	 * @return SchedulePage
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
	 * @return SchedulePage
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
	 * @return SchedulePage
	 */
	public SchedulePage clickCreateBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(createBtn));
		createBtn.click();
		return this;
	}

	/**
	 * [AC] This method clicks on Remove button
	 * @return SchedulePage
	 */
	public SchedulePage clickRemoveBtn() {
		removeBtn.click();
		return this;
	}

	/**
	 * [AC] This method clicks on Update button
	 * @return SchedulePage
	 */
	public SchedulePage clickUpdateBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(updateBtn));
		updateBtn.click();
		return this;
	}

	/**
	 * [AC] This method clicks on back button
	 * @return HomePage
	 */
	public HomePage clickBackBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(backBtn));
		backBtn.click();
		return new HomePage();
	}

	/**
	 * [AC] This method clicks on search page
	 * @return SearchPage
	 */
	public SearchPage clickSearchBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
		searchBtn.click();
		return new SearchPage();
	}

	/**
	 * [AC] This method get the value of a label from title page
	 * @return String
	 */
	public String getTitleOfPageValue() {
		return schedulerLbl.getText();
	}

	/**
	 * [AC] This method search a meeting and return the name of that
	 * @param nameMeeting: name of a meeting to search
	 * @return String
	 */
	public String getNameMeetingCreatedValue(String nameMeeting) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + "')]")).getText();
	}

	/**
	 * [AC] This method search a meeting and click over that
	 * @param nameMeeting
	 * @return SchedulePage
	 */
	public SchedulePage clickOverMeetingCreated(String nameMeeting) {
		WebElement meeting = driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + "')]"));
		wait.until(ExpectedConditions.elementToBeClickable(meeting));
		meeting.click();
		return this;
	}

	/**
	 * [AC] This method search for a attendee and return his value
	 * @param emailAttendee
	 * @return String
	 */
	public String getEmailAttendeeLblValue(String emailAttendee) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + emailAttendee + "')]")).getText();
	}

	/**
	 * [AC] This method gets the value of the attendee text box
	 * @return String
	 */
	public String getEmailAttendeeTxtBoxValue() {
		return attendeesTxtBox.getText();
	}

	/**
	 * [AC] This method obtains the value of the textBox from subject
	 * @return String
	 */
	public String getMeetingSubjectValue() {
		return subjectTxtBox.getAttribute("value");
	}

	/**
	 * [AC] This method obtains the value of the textBox from organizer
	 * @return String
	 */
	public String getMeetingOrganizerValue() {
		return organizerTxtBox.getAttribute("value");
	}

	/**
	 * [AC] This method obtains the value of the textBox from body
	 * @return String
	 */
	public String getBodyTxtBoxValue() {
		return bodyTxtBox.getAttribute("value");
	}

	/**
	 * [AC] This method founds the message pop up that appears after do something
	 * @param message
	 * @return boolean
	 */
	private boolean findMessagePopUpValue(String message) {
		WebElement messageLbl = driver.findElement(By.xpath("//div[contains(text(),'" + message + "')]"));
		wait.until(ExpectedConditions.visibilityOf(messageLbl));
		return messageLbl.isDisplayed();
	}

	/**
	 * [AC] This method obtains any message of error label
	 * @param message
	 * @return boolean
	 */
	private boolean getAnyErrorMessageLbl(String message) {
		return driver.findElement(By.xpath("//small[contains(text(),'" + message + "')]")).isDisplayed();
	}

	/**
	 * [AC] This method gets the successfully message when meeting is created  
	 * @return boolean
	 */
	public boolean isMessageMeetingCreatedDisplayed() {
		return findMessagePopUpValue(MEETING_CREATED);
	}

	/**
	 * [AC] This method gets the successfully message when meeting is updated
	 * @return boolean
	 */
	public boolean isMessageMeetingUpdatedDisplayed() {
		return findMessagePopUpValue(MEETING_UPDATED);
	}

	/**
	 * [AC] This method gets the successfully message when meeting is deleted
	 * @return boolean
	 */
	public boolean isMessageMeetingDeletedDisplayed() {
		return findMessagePopUpValue(MEETING_REMOVED);
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
		return findMessagePopUpValue(MEETING_ERROR);
	}

	/**
	 * [AC] This method gets the error label when does not put a subject
	 * @return boolean
	 */
	public boolean isErrorSubjectDisplayed() {
		return getAnyErrorMessageLbl(MEETING_SUBJECT_REQUIERED);
	}

	/**
	 * [AC] This method gets the error label when does not put a organizer
	 * @return boolean
	 */
	public boolean isErrorOrganizerDisplayed() {
		return getAnyErrorMessageLbl(MEETING_ORGANIZER_REQUIRED);
	}

	/**
	 * [AC] This method gets the error label when does not put attendees
	 * @return boolean
	 */
	public boolean isErrorAttendeeLblDisplayed() {
		return getAnyErrorMessageLbl(MEETING_ATTENDEES_REQUIRED);
	}

	/**
	 * [AC] This method gets the error label when put invalid attendees
	 * @return boolean
	 */
	public boolean isErrorAttendeeInvalidLblDisplayed() {
		return getAnyErrorMessageLbl(MEETING_ATTENDEES_INVALID);
	}

	/**
	 * [AC]This method clears and set the new value to user name textBox
	 * @param name 
	 * @return SchedulePage
	 */
	public SchedulePage setUserNameTxtBox(String name) {
		userNameTxt.clear();
		userNameTxt.sendKeys(name);
		return this;
	}

	/**
	 * [AC] This method clears and set the new value to password textBox
	 * @param password
	 * @return SchedulePage
	 */
	public SchedulePage setPasswordTxtBox(String password) {
		wait.until(ExpectedConditions.visibilityOf(passwordTxt));
		passwordTxt.clear();
		passwordTxt.sendKeys(password);
		return this;
	}

	/**
	 * [AC] This method click on OK button
	 * @return SchedulePage
	 */
	public SchedulePage clickOkButton() {
		okBtn.click();
		okBtn.sendKeys(Keys.ESCAPE);
		UIMethods.waitForMaskDisappearsAndClickElement(timeLine);
		return this;
	}

	/**
	 * [AC] This method click on Cancel button
	 * @return SchedulePage
	 */
	public SchedulePage clickCancelButton() {
		cancelBtn.click();
		cancelBtn.sendKeys(Keys.ESCAPE);
		UIMethods.waitForMaskDisappearsAndClickElement(timeLine);
		return this;
	}

	/**
	 * [EN] This method confirm the credentials inserted by the user
	 * @param name
	 * @param password
	 * @return SchedulePage
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
		setAttendeeTxtBoxPressingEnter(attendees);
		setBodyTxtBox(bodyMeeting);
		return clickCreateBtn();
	}

	/**
	 * [JC] This method verify if the label scheduler is displayed
	 * @return boolean
	 */
	public boolean isSchedulerLblDisplayed() {
		return schedulerLbl.isDisplayed();
	}

	/**
	 * [JC] This method verify return the current date
	 * @return String
	 */
	public String getTimeLineDate() {
		String time = currentTimeLine.getAttribute("title").replace("th","").replace("st","")
				.replace("nd","").replace("rd","").replace("Current time: ","");
		return time;
	}

	/**
	 * [AC] This method clicks over TimeLine
	 * @return SchedulePage
	 */
	public SchedulePage clickOverTimeline() {
		timeLine.click();
		return this;
	}

	/**
	 * [YA]This method verifies if Meeting Box is present
	 * @param nameMeeting
	 * @return boolean
	 */
	public boolean isMeetingBoxDisplayed(String nameMeeting) {
		By meetingBoxLocator = By.xpath("//span[contains(text(),'" + nameMeeting + "')]");
		return UIMethods.isElementPresent(meetingBoxLocator);
	}

	/** 
	 * [YA]This method verifies Out Of Order is displayed in Scheduler's Timeline
	 * @param title: Out Of Order's Title
	 * @return boolean
	 */
	public boolean isOutOfOrderBoxDisplayed(String title) {
		return isMeetingBoxDisplayed(title);	
	}

	/**
	 * [YA]This method creates a meeting with required information adding minutes to current time
	 * @param organizer
	 * @param subject
	 * @param starTimeMinutes
	 * @param endTimeMinutes
	 * @param attendee
	 * @param password
	 * @return SchedulePage
	 */
	public SchedulePage createMeetingRequiredInformation(String organizer, String subject, String starTimeMinutes,
			String endTimeMinutes, String attendee, String password) {
		String startTime = TimeManager.getTime(Integer.parseInt(starTimeMinutes), "hh:mm a");
		String endTime = TimeManager.getTime(Integer.parseInt(endTimeMinutes), "hh:mm a");
		setOrganizerTxtBox(organizer);
		setSubjectTxtBox(subject);
		setStartTimeDate(startTime);
		setEndTimeDate(endTime);
		setAttendeeTxtBoxPressingEnter(attendee);	
		clickCreateBtn();
		confirmCredentials(password).isMessageMeetingCreatedDisplayed();
		return this;
	}

	/**
	 * [YA]This method verifies if UpdateBtn is present
	 * @return boolean
	 */
	public boolean isUpdateBtnPresent() {
		return updateBtn.isDisplayed();
	} 

	/**
	 * [AC] This method delete a meeting
	 * @param nameMeeting
	 * @return  SchedulePage
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
	 * @return  SchedulePage
	 */
	public SchedulePage createMeeting(String organizer, String subject, int minutesFrom, 
			int minutesTo, String attendee, String password) {

		String startTime = TimeManager.getTime(minutesFrom, "hh:mm a");
		String endTime = TimeManager.getTime(minutesTo, "hh:mm a");

		setOrganizerTxtBox(organizer);
		setSubjectTxtBox(subject);
		setStartTimeDate(startTime);
		setEndTimeDate(endTime);
		setAttendeeTxtBoxPressingEnter(attendee);
		clickCreateBtn();	
		confirmCredentials(password);
		isMessageMeetingCreatedDisplayed();
		return this;
	}

	/**
	 * [EN] This method checks that a error message is displayed 
	 * when a meeting is created with past time values.
	 * @return boolean
	 */
	public boolean isErrorMessageOfPastMeetingDisplayed() {
		return getAnyErrorMessageLbl(MEETING_PAST_CREATED_ERROR);
	}

	/**
	 * [AC] This method gets the default duration of a meeting
	 * @return int
	 */
	public int getDurationOfMeetingByDefault() {
		int resp = 0;
		String start = getStartTimeTxtBoxValue();
		String end = getEndTimeTxtBoxValue();
		int minStart = Integer.parseInt(getTimeElement(start, "minutes"));
		int minEnd = Integer.parseInt(getTimeElement(end, "minutes"));
		if(minStart > minEnd) {
			while(minEnd < minStart) {
				minEnd++;
				resp++;
			}
		} else if(minStart < minEnd) {
			while(minStart < minEnd) {
				minStart++;
				resp++;
			}
		}
		return resp;
	}

	/**
	 * [JC] This method move the Time Line left or right(depend of the value)
	 * i.e. if the value is 5000 is move to left, -5000 is move to right
	 * @param nameMeeting
	 * @return SchedulePage
	 */
	public SchedulePage moveTimeLine(int value) {
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
	 * i.e. if the value is -5000 is move to left, 5000 is move to right
	 * @param nameMeeting
	 * @return SchedulePage
	 */
	public SchedulePage moveMeeting(int value) {
		Actions builder = new Actions(driver);
		WebElement elem = driver.findElement(By.xpath("//div[contains(@class,'item range meeting selected')]"));
		builder.clickAndHold(elem)
		.moveByOffset(value, 0)
		.release().perform();
		return this;
	}

	/**
	 * [JC] This method search a meeting and click over Left Side of this meeting
	 * i.e. if the value is -5000 is move to left, 5000 is move to right
	 * @param nameMeeting
	 * @return SchedulePage
	 */
	public SchedulePage resizeMeetingLeft(String nameMeeting) {
		Actions builder = new Actions(driver);
		WebElement elem = driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + 
				"')]/parent::div/following-sibling::div[@class='drag-left']"));
		builder.clickAndHold(elem)
		.moveByOffset(-800, 0)
		.release().perform();
		return this;
	}

	/**
	 * [JC] This method search a meeting and click over Right Side of this meeting
	 * i.e. if the value is -5000 is move to left, 5000 is move to right
	 * @param nameMeeting
	 * @return SchedulePage
	 */
	public SchedulePage resizeMeetingRight(String nameMeeting) {
		Actions builder = new Actions(driver);
		WebElement elem = driver.findElement(By.xpath("//span[contains(text(),'" + nameMeeting + 
				"')]/parent::div/following-sibling::div[@class='drag-right']"));
		builder.clickAndHold(elem)
		.moveByOffset(800, 0)
		.release().perform();
		return this;
	}

	/**
	 * [EN]
	 * @param textMinorTime
	 * @return
	 */
	public boolean isTextMinorDisplayed(String textMinorTime) {
		return driver.findElement(By.xpath("//div[contains(text(),'" + textMinorTime + "')]"))
				.isDisplayed();
	}	
}