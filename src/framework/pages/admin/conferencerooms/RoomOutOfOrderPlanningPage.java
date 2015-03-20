package framework.pages.admin.conferencerooms;

import static framework.common.MessageConstants.OUT_OF_ORDER_IN_THE_PAST;
import static framework.common.MessageConstants.TO_GRATER_THAN_FROM;
import static framework.common.MessageConstants.OUT_OF_ORDER_SHOULD_HAVE_A_TITLE;

import java.text.ParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import framework.common.UIMethods;
import framework.utils.TimeManager;

/**
 * This class represent Conference Room's Our Of Order Planning page in Admin.
 * @author Yesica Acha
 *
 */
public class RoomOutOfOrderPlanningPage extends RoomBaseAbstractPage {

	TimeManager timeManager = new TimeManager();

	@FindBy(xpath = "//div[@class='check-button']/label")
	WebElement activationBtn;

	@FindBy(xpath = "//label[@for='title-dropdown']")
	WebElement titleCmbBox;

	@FindBy(xpath = "//input[@ng-model='form.title.value']")
	WebElement titleTxtBox;

	@FindBy(xpath = "//textarea[@ng-model='form.description.value']")
	WebElement descriptionTxtBox;

	@FindBy(xpath = "//span[contains(text(),'Send email notification')]")
	WebElement emailNotificationChkBox;

	@FindBy(xpath = "//small[contains(text(),' ')]")
	WebElement errorMessageLbl;

	/**
	 *[YA]This method set the title for the Out Of Order Planning Period
	 * @param title
	 * @return
	 */
	public RoomOutOfOrderPlanningPage setTitleTxtBox (String title) {
		titleTxtBox.clear();
		titleTxtBox.sendKeys(title);
		return this;
	}

	/**
	 *[YA]This method set the description for the Out Of Order Planning Period
	 * @param description
	 * @return
	 */
	public RoomOutOfOrderPlanningPage setDescriptionTxtBox (String description) {
		descriptionTxtBox.clear();
		descriptionTxtBox.sendKeys(description);
		return this;
	} 

	/**
	 *[YA]This method select a checkBox to send an email notification to attendees
	 *  that have a meeting scheduled when an out of order period is established
	 * @return
	 */
	public RoomOutOfOrderPlanningPage selectEmailNotificationChkBox() {
		emailNotificationChkBox.click();
		return this;
	}

	/**
	 *[YA]This method clicks the activation button (calendar or clock) to confirm an out of order period
	 * @return
	 */
	private RoomOutOfOrderPlanningPage clickActivationBtn() {
		activationBtn.click();
		return this;
	}

	/**
	 *[YA]This method sets a start date (DD-MM-YYYY)
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public RoomOutOfOrderPlanningPage setStartDateWithCalendar(String date) {
		setDateWithCalendar(date, "from");
		return this;
	}

	/**
	 *[YA]This method sets an end date (DD-MM-YYYY)
	 * @param date
	 * @return
	 */
	public RoomOutOfOrderPlanningPage setEndDateWithCalendar(String date) {
		setDateWithCalendar(date, "to");
		return this;
	}

	/**
	 *[YA]This method sets a date (start or end) in the application using the calendar (DD-MM-YYYY)
	 * @param date
	 * @param dateSelector
	 */
	private void setDateWithCalendar(String date, String dateSelector) {

		//to click the calendar button next to date textBox
		clickCalendarElementBtn("calendarBtn", dateSelector);

		//to clicks date-picker button (month) as a precondition
		clickCalendarElementBtn("datePickerBtn", dateSelector);

		//to click date=picker button (year) as a precondition 
		clickCalendarElementBtn("datePickerBtn", dateSelector);

		//to click date's year button
		clickCalendarElementBtn(TimeManager.getDateElement(date, "year"), dateSelector);

		//to click date's month button
		clickCalendarElementBtn(TimeManager.getDateElement(date, "month"), dateSelector);

		//to click date's day button
		clickCalendarElementBtn(TimeManager.getDateElement(date, "day"), dateSelector);

	}

	/**
	 *[YA]This method clicks on an element of the calendar (any day, month, year, todayBtn, 
	 * cancelBtn, clearBtn, etc)
	 * @param calendarElement
	 * @param dateSelector
	 */
	private void clickCalendarElementBtn(String calendarElement, String dateSelector) {
		String locator;
		switch (calendarElement) {
		case "calendarBtn":  locator = "div/span/button";
		break;
		case "datePickerBtn":  locator = "th/button[@ng-click='toggleMode()']";
		break;		
		case "previousBtn":  locator = "th/button[@ng-click='toggleMode()']/ancestor::"
				+ "th/preceding-sibling::th//button";
		break;
		case "nextBtn":  locator = "th/button[@ng-click='toggleMode()']/ancestor::"
				+ "th/following-sibling::th//button";
		break;
		case "todayBtn":  locator = "button[contains(text(),'Today')]";
		break;
		case "clearBtn":  locator = "button[contains(text(),'Clear')]";
		break;
		case "closeBtn":  locator = "button[contains(text(),'Close')]";
		break;
		default: locator = "span[contains(text(),'" + calendarElement + "')]/ancestor::td/button";
		break;
		}
		WebElement calendarElementBtn = driver.findElement(By.xpath("//date-picker[@model='form." 
				+ dateSelector + ".value']//" + locator));
		calendarElementBtn.click();
	}

	/**
	 *[YA]This method set a star time (HH:MM AM/PM)for out of order planning period
	 * @param startTime
	 * @return
	 */
	public RoomOutOfOrderPlanningPage setStartTime (String startTime) {
		setTime(startTime, "from");
		return this;
	}

	/**
	 *[YA]This method set an end time (HH:MM AM/PM) for out of order planning period
	 * @param endTime
	 * @return
	 */
	public RoomOutOfOrderPlanningPage setEndTime (String endTime) {
		setTime(endTime, "to");
		return this;
	}

	/**
	 *[YA]This method sets a time (start or end) for out of order planning period
	 * @param time
	 * @param timeSelector
	 */
	private void setTime (String modifierValue, String timeSelector) {
		int minutesToAdd = Integer.parseInt(modifierValue);
		String time = TimeManager.getTime(minutesToAdd, "hh:mm a");
		String hours = TimeManager.getTimeElement(time,"hours");
		String minutes = TimeManager.getTimeElement(time, "minutes");
		String meridian = TimeManager.getTimeElement(time,"meridian");
		setTimeTxtBox("hours", hours, timeSelector);
		setTimeTxtBox("minutes", minutes, timeSelector);
		clickMeridianBtn(meridian, timeSelector);		
	}

	/**
	 *[YA]This method set the time (hours or minutes) in the corresponding textBox
	 * @param selector
	 * @param time
	 * @param timeSelector
	 */
	private void setTimeTxtBox (String selector, String time, String timeSelector) {
		WebElement timeTxtBox = findDateElement(selector, timeSelector);
		UIMethods.doubleClick(timeTxtBox);
		timeTxtBox.sendKeys(time);
	}

	/**
	 *[YA]This method sets the meridian (AM or PM)for the out of order time
	 * @param meridian
	 * @param timeSelector
	 */
	private void clickMeridianBtn (String meridian, String timeSelector) {
		WebElement meridianBtn = findDateElement("meridian", timeSelector);
		if(!meridianBtn.getText().equalsIgnoreCase(meridian)) {
			meridianBtn.click();
		}
	}

	/**
	 *[YA]This method finds hoursTxtBox, minutesTxtBox, or meridianTxtBox based on the parameters
	 * @param element
	 * @param timeSelector
	 * @return
	 */
	private WebElement findDateElement(String element, String timeSelector) {
		String locator;
		if(element == "meridian") {
			locator = "button[@ng-click='toggleMeridian()']";
		} else {
			locator = "input[@ng-model='" + element + "']";
		}
		WebElement timeElement = driver.findElement(By.xpath("//table[@ng-model='form." 
				+ timeSelector + ".value']//" + locator));
		return timeElement;
	}

	/**
	 *[YA]This method clicks the arrow to increment hours in start time
	 * @return
	 */
	public RoomOutOfOrderPlanningPage clickFromIncrementHoursBtn() {
		clickArrowBtn("from", "incrementHours");
		return this;
	}

	/**
	 *[YA]This method clicks the arrow to decrement hours in start time
	 * @return
	 */
	public RoomOutOfOrderPlanningPage clickFromDecrementHoursBtn() {
		clickArrowBtn("from", "decrementHours");
		return this;
	}

	/**
	 *[YA]This method clicks the arrow to increment minutes in start time
	 * @return
	 */
	public RoomOutOfOrderPlanningPage clickFromIncrementMinutesBtn() {
		clickArrowBtn("from", "incrementMinutes");
		return this;
	}

	/**
	 * [YA]This method clicks the arrow to decrement minutes in start time
	 */
	public RoomOutOfOrderPlanningPage clickFromDecrementMinutesBtn() {
		clickArrowBtn("from", "decrementMinutes");
		return this;
	}

	/**
	 * [YA]This method clicks the arrow to increment hours in end time
	 * @return
	 */
	public RoomOutOfOrderPlanningPage clickToIncrementHoursBtn() {
		clickArrowBtn("to", "incrementHours");
		return this;
	}

	/**
	 * [YA]This method clicks the arrow to increment hours in end time
	 * @return
	 */
	public RoomOutOfOrderPlanningPage clickToDecrementHoursBtn() {
		clickArrowBtn("to", "decrementHours");
		return this;
	}

	/**
	 * [YA]This method clicks the arrow to increment minutes in end time
	 * @return
	 */
	public RoomOutOfOrderPlanningPage clickToIncrementMinutesBtn() {
		clickArrowBtn("to", "incrementMinutes");
		return this;
	}

	/**
	 * [YA]This method clicks the arrow to decrement minutes in end time
	 */
	public RoomOutOfOrderPlanningPage clickToDecrementMinutesBtn() {
		clickArrowBtn("to", "decrementMinutes");
		return this;
	}

	/**
	 * [YA]This method clicks an arrow to change hours or minutes 
	 * @param timeSelector
	 * @param action
	 * @return
	 */
	private RoomOutOfOrderPlanningPage clickArrowBtn(String timeSelector, String action) {
		WebElement calendarElementBtn = driver.findElement(By.xpath("//table[@ng-model='form." 
				+ timeSelector + ".value']//a[@ng-click='" + action + "()']/span"));
		calendarElementBtn.click();
		return this;
	}

	/**
	 * [YA]This method sets all the information for an Out Of Order Period to be created
	 * @param startingDate
	 * @param finishingDate
	 * @param startingTime
	 * @param finishingTime
	 * @param title
	 * @param description
	 * @return
	 * @throws ParseException
	 */
	public RoomOutOfOrderPlanningPage setOutOfOrderPeriodInformation (String startDate, String endDate, 
			String startTime, String endTime, String title, String description) {
		setStartDateWithCalendar(startDate);
		setEndDateWithCalendar(endDate);
		setTitleTxtBox(title);
		setDescriptionTxtBox(description);
		setEndTime(endTime);
		setStartTime(startTime);
		selectEmailNotificationChkBox();
		return this;
	}

	/**
	 * [YA]This method activates an Out Of Order if it is deactivated 
	 * @return
	 */
	public RoomOutOfOrderPlanningPage activateOutOfOrder(){
		if(emailNotificationChkBox.getAttribute("class").contains("text-disabled-color")) {
			clickActivationBtn();
		}
		return this;
	}

	/**
	 * [YA]This method deactivates an Out Of Order if it is activated
	 * @return 
	 */
	public RoomOutOfOrderPlanningPage deactivateOutOfOrder(){
		if(!emailNotificationChkBox.getAttribute("class").contains("text-disabled-color")) {
			clickActivationBtn();
		}
		return this;
	}

	/**
	 * [YA]This method verifies if an error message is correct
	 * @return boolean
	 */
	private boolean isErrorMessageCorrect(String errorMessage) {
		return driver.findElement(By.xpath("//small[contains(text(),'" 
				+ errorMessage + "')]")).isDisplayed();
	}
	
	/**
	 * [YA]This method verifies that a message that says: "'To' field must be greater than 'From' field" 
	 * is displayed
	 * @return boolean
	 */
	public boolean isToGreaterThanFromErrorDisplayed() {
		return isErrorMessageCorrect(TO_GRATER_THAN_FROM);
	}
	
	/**
	 * [YA]This method verifies that a message that says: "Cannot establish out of order as a past event"
	 * is displayed
	 * @return boolean
	 */
	public boolean isOutOfOrderInThePastErrorDisplayed() {
		return isErrorMessageCorrect(OUT_OF_ORDER_IN_THE_PAST);
	}
	
	/**
	 * [YA]This method verifies that a message that says: "Cannot establish out of order as a past event"
	 * is displayed
	 * @return boolean
	 */
	public boolean isOutOfOrderShouldHaveTitleErrorDisplayed() {
		return isErrorMessageCorrect(OUT_OF_ORDER_SHOULD_HAVE_A_TITLE);
	}
	

}