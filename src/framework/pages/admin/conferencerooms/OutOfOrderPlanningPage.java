package framework.pages.admin.conferencerooms;

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
public class OutOfOrderPlanningPage extends AbstractRoomBasePage {

	TimeManager timeManager = new TimeManager();
	UIMethods uiMethods = new UIMethods();

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

	/**
	 * This method set the title for the Out Of Order Planning Period
	 * @param title
	 * @return
	 */
	public OutOfOrderPlanningPage setTitleTxtBox (String title) {
		titleTxtBox.clear();
		titleTxtBox.sendKeys(title);
		return this;
	}

	/**
	 * This method set the description for the Out Of Order Planning Period
	 * @param description
	 * @return
	 */
	public OutOfOrderPlanningPage setDescriptionTxtBox (String description) {
		descriptionTxtBox.clear();
		descriptionTxtBox.sendKeys(description);
		return this;
	} 

	/**
	 * This method select a checkBox to send an email notification to attendees
	 *  that have a meeting scheduled when an out of order period is established
	 * @return
	 */
	public OutOfOrderPlanningPage selectEmailNotificationChkBox() {
		emailNotificationChkBox.click();
		return this;
	}

	/**
	 * This method clicks the activation button (calendar or clock) to confirm an out of order period
	 * @return
	 */
	public OutOfOrderPlanningPage clickActivationBtn() {
		activationBtn.click();
		return this;
	}

	/**
	 * This method sets a start date (DD-MM-YYYY)
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public OutOfOrderPlanningPage setStartDateWithCalendar(String date) {
		setDateWithCalendar(date, "from");
		return this;
	}

	/**
	 * This method sets an end date (DD-MM-YYYY)
	 * @param date
	 * @return
	 */
	public OutOfOrderPlanningPage setEndDateWithCalendar(String date) {
		setDateWithCalendar(date, "to");
		return this;
	}

	/**
	 * This method sets a date (start or end) in the application using the calendar (DD-MM-YYYY)
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
		clickCalendarElementBtn(timeManager.getYear(date), dateSelector);

		//to click date's month button
		clickCalendarElementBtn(timeManager.getMonth(date), dateSelector);

		//to click date's day button
		clickCalendarElementBtn(timeManager.getDay(date), dateSelector);
	}

	/**
	 * This method clicks on an element of the calendar (any day, month, year, todayBtn, 
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
	 * This method set a star time (HH:MM AM/PM)for out of order planning period
	 * @param startTime
	 * @return
	 */
	public OutOfOrderPlanningPage setStartTime (String startTime) {
		setTime(startTime, "from");
		return this;
	}

	/**
	 * This method set an end time (HH:MM AM/PM) for out of order planning period
	 * @param finishingTime
	 * @return
	 */
	public OutOfOrderPlanningPage setEndTime (String finishingTime) {
		setTime(finishingTime, "to");
		return this;
	}

	/**
	 * This method sets a time (start or end) for out of order planning period
	 * @param time
	 * @param timeSelector
	 */
	private void setTime (String time, String timeSelector) {
		String hours = timeManager.getHour(time);
		String minute = timeManager.getMinute(time);
		String meridian = timeManager.getMeridian(time);
		setTimeTxtBox("hours", hours, timeSelector);
		setTimeTxtBox("minutes", minute, timeSelector);
		clickMeridianBtn(meridian, timeSelector);		
	}

	/**
	 * This method set the time (hours or minutes) in the corresponding textBox
	 * @param selector
	 * @param time
	 * @param timeSelector
	 */
	private void setTimeTxtBox (String selector, String time, String timeSelector) {
		WebElement timeTxtBox = findDateElement(selector, timeSelector);
		timeTxtBox.click();
		uiMethods.doubleClick(timeTxtBox);
		timeTxtBox.sendKeys(time);
	}

	/**
	 * This method sets the meridian (AM or PM)for the out of order time
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
	 * This method finds hoursTxtBox, minutesTxtBox, or meridianTxtBox based on the parameters
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
	 * This method clicks the arrow to increment hours in start time
	 * @return
	 */
	public OutOfOrderPlanningPage clickFromIncrementHoursBtn() {
		clickArrowBtn("from", "incrementHours");
		return this;
	}

	/**
	 * This method clicks the arrow to decrement hours in start time
	 * @return
	 */
	public OutOfOrderPlanningPage clickFromDecrementHoursBtn() {
		clickArrowBtn("from", "decrementHours");
		return this;
	}

	/**
	 * This method clicks the arrow to increment minutes in start time
	 * @return
	 */
	public OutOfOrderPlanningPage clickFromIncrementMinutesBtn() {
		clickArrowBtn("from", "incrementMinutes");
		return this;
	}

	/**
	 * This method clicks the arrow to decrement minutes in start time
	 */
	public OutOfOrderPlanningPage clickFromDecrementMinutesBtn() {
		clickArrowBtn("from", "decrementMinutes");
		return this;
	}

	/**
	 * This method clicks the arrow to increment hours in end time
	 * @return
	 */
	public OutOfOrderPlanningPage clickToIncrementHoursBtn() {
		clickArrowBtn("to", "incrementHours");
		return this;
	}

	/**
	 * This method clicks the arrow to increment hours in end time
	 * @return
	 */
	public OutOfOrderPlanningPage clickToDecrementHoursBtn() {
		clickArrowBtn("to", "decrementHours");
		return this;
	}

	/**
	 * This method clicks the arrow to increment minutes in end time
	 * @return
	 */
	public OutOfOrderPlanningPage clickToIncrementMinutesBtn() {
		clickArrowBtn("to", "incrementMinutes");
		return this;
	}

	/**
	 * This method clicks the arrow to decrement minutes in end time
	 */
	public OutOfOrderPlanningPage clickToDecrementMinutesBtn() {
		clickArrowBtn("to", "decrementMinutes");
		return this;
	}

	/**
	 * This method clicks an arrow to change hours or minutes 
	 * @param timeSelector
	 * @param action
	 * @return
	 */
	private OutOfOrderPlanningPage clickArrowBtn(String timeSelector, String action) {
		WebElement calendarElementBtn = driver.findElement(By.xpath("//table[@ng-model='form." 
				+ timeSelector + ".value']//a[@ng-click='" + action + "()']/span"));
		calendarElementBtn.click();
		return this;
	}

	/**
	 * This method sets all the information for an Out Of Order Period to be created
	 * @param startingDate
	 * @param finishingDate
	 * @param startingTime
	 * @param finishingTime
	 * @param title
	 * @param description
	 * @return
	 * @throws ParseException
	 */
	public OutOfOrderPlanningPage setOutOfOrderPeriodInformation (String startingDate, String finishingDate, 
			String startingTime, String finishingTime, String title, String description) throws ParseException {
		setStartDateWithCalendar(startingDate);
		setEndDateWithCalendar(finishingDate);
		setTitleTxtBox(title);
		setDescriptionTxtBox(description);
		setEndTime(finishingTime);
		setStartTime(startingTime);
		selectEmailNotificationChkBox();
		return this;
	}

}