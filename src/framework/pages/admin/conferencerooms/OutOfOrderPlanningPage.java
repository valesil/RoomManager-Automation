package framework.pages.admin.conferencerooms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

/**
 * 
 * @author Yesica Acha
 *
 */
public class OutOfOrderPlanningPage extends AbstractRoomBasePage {

	private Actions action = new Actions(driver);
	
	@FindBy(xpath = "//date-picker[@model='form.from.value']//div/span/button")
	WebElement fromCalendarBtn;

	@FindBy(xpath = "//date-picker[@model='form.from.value']//th/button[@ng-click='toggleMode()']")
	WebElement fromDatePickerBtn;

	@FindBy(xpath = "//date-picker[@model='form.from.value']//th/button[@ng-click='toggleMode()']/ancestor::th/preceding-sibling::th//button")
	WebElement fromPreviousDateBtn;

	@FindBy(xpath = "//date-picker[@model='form.from.value']//th/button[@ng-click='toggleMode()']/ancestor::th/following-sibling::th//button")
	WebElement fromNextDateBtn;

	@FindBy(xpath = "//date-picker[@model='form.from.value']//button[contains(text(),'Today')]")
	WebElement fromCalendarTodayBtn;

	@FindBy(xpath = "//date-picker[@model='form.from.value']//button[contains(text(),'Clear')]")
	WebElement fromCalendarClearBtn;

	@FindBy(xpath ="//date-picker[@model='form.from.value']//button[contains(text(),'Close')]" )
	WebElement	fromCalendarCloseBtn;

	@FindBy(xpath = "//table[@ng-model='form.from.value']//a[@ng-click='incrementHours()']")
	WebElement fromIncrementHoursBtn;

	@FindBy(xpath = "//table[@ng-model='form.from.value']//a[@ng-click='decrementHours()']")
	WebElement fromDecrementHoursBtn;

	@FindBy(xpath = "//table[@ng-model='form.from.value']//a[@ng-click='incrementMinutes()']")
	WebElement fromIncrementMinutesBtn;

	@FindBy(xpath = "//table[@ng-model='form.from.value']//a[@ng-click='decrementMinutes()']")
	WebElement fromDecrementMinutesBtn;

	@FindBy(xpath = "//date-picker[@model='form.to.value']//div/span/button")
	WebElement toCalendarBtn;

	@FindBy(xpath = "//date-picker[@model='form.to.value']//th/button[@ng-click='toggleMode()']")
	WebElement toDatePickerBtn;

	@FindBy(xpath = "//date-picker[@model='form.to.value']//th/button[@ng-click='toggleMode()']/ancestor::th/preceding-sibling::th//button")
	WebElement toPreviousDateBtn;

	@FindBy(xpath = "//date-picker[@model='form.to.value']//th/button[@ng-click='toggleMode()']/ancestor::th/following-sibling::th//button")
	WebElement toNextDateBtn;

	@FindBy(xpath = "//date-picker[@model='form.to.value']//button[contains(text(),'Today')]")
	WebElement toCalendarTodayBtn;

	@FindBy(xpath = "//date-picker[@model='form.to.value']//button[contains(text(),'Clear')]")
	WebElement toCalendarClearBtn;

	@FindBy(xpath ="//date-picker[@model='form.to.value']//button[contains(text(),'Close')]" )
	WebElement	toCalendarCloseBtn;

	@FindBy(xpath = "//table[@ng-model='form.to.value']//a[@ng-click='incrementHours()']")
	WebElement toIncrementHoursBtn;

	@FindBy(xpath = "//table[@ng-model='form.to.value']//a[@ng-click='decrementHours()']")
	WebElement toDecrementHoursBtn;

	@FindBy(xpath = "//table[@ng-model='form.to.value']//a[@ng-click='incrementMinutes()']")
	WebElement toIncrementMinutesBtn;

	@FindBy(xpath = "//table[@ng-model='form.to.value']//a[@ng-click='decrementMinutes()']")
	WebElement toDecrementMinutesBtn;

	@FindBy(xpath = "//div[@class='check-button']/label")
	WebElement activationBtn;

	@FindBy(xpath = "//label[@for='title-dropdown']")
	WebElement titleCmbBox;

	@FindBy(xpath = "//input[@ng-model='form.title.value']")
	WebElement titleTxtBox;

	@FindBy(xpath = "//textarea[@ng-model='form.description.value']")
	WebElement descriptionTxtBox;

	@FindBy(xpath = "//input[@ng-model='form.emailNotification.value']")
	WebElement emailNotificationChkBox;

	public OutOfOrderPlanningPage setStartingDateWithCalendar(String date) throws ParseException {
		setDateWithCalendar(date, "from");
		return this;
	}
	
	public OutOfOrderPlanningPage setFinishingDateWithCalendar(String date) throws ParseException {
		setDateWithCalendar(date, "to");
		return this;
	}
	
	public OutOfOrderPlanningPage setStartingTime (String startingTime) {
		setTime(startingTime, "from");
		return this;
	}
	
	public OutOfOrderPlanningPage setFinishingTime (String finishingTime) {
		setTime(finishingTime, "to");
		return this;
	}
	
	//Falta AM y PM
	private void setTime (String time, String timeSelector) {
		String[] splittedTimeMeridian = time.split(" ");
		String meridian = splittedTimeMeridian[1];
		String[] splittedTime = splittedTimeMeridian[0].split(":");
		String hours = Integer.parseInt(splittedTime[0])+"";
		String minutes = splittedTime [1];
		WebElement hoursTxtBox = driver.findElement(By.xpath("//table[@ng-model='form." 
				+ timeSelector +".value']//input[@ng-model='hours']"));
		WebElement minutesTxtBox = driver.findElement(By.xpath("//table[@ng-model='form." 
				+ timeSelector + ".value']//input[@ng-model='minutes']"));
		hoursTxtBox.click();
		doubleClick(hoursTxtBox);
		hoursTxtBox.sendKeys(hours);
		minutesTxtBox.click();
		doubleClick(minutesTxtBox);
		minutesTxtBox.sendKeys(minutes);
		WebElement meridianBtn = driver.findElement(By.xpath("//table[@ng-model='form."
				+ timeSelector + ".value']//button[@ng-click='toggleMeridian()']"));
		String actualMeridian = meridianBtn.getText();
		System.out.println(hours);
		System.out.println(minutes);
		if(!actualMeridian.equalsIgnoreCase(meridian)) {
			meridianBtn.click();
		}
		
	}
			
	public OutOfOrderPlanningPage clickActivationBtn () {
		activationBtn.click();
		return this;
	}
	
	public OutOfOrderPlanningPage setTitleTxtBox (String title) {
		titleTxtBox.clear();
		titleTxtBox.sendKeys(title);
		return this;
	}
	
	public OutOfOrderPlanningPage setDescriptionTxtBox (String description) {
		descriptionTxtBox.clear();
		descriptionTxtBox.sendKeys(description);
		return this;
	} 
	
	public OutOfOrderPlanningPage selectEmailNotificationChkBox() {
		emailNotificationChkBox.click();
		return this;
	}

	public OutOfOrderPlanningPage setOutOfOrderPeriod (String startingDate, String finishingDate, 
			String startingTime, String finishingTime, String title, String description) throws ParseException {
		setStartingDateWithCalendar(startingDate);
		setFinishingDateWithCalendar(finishingDate);
		setTitleTxtBox(title);
		setDescriptionTxtBox(description);
		setFinishingTime(finishingTime);
		setStartingTime(startingTime);
		selectEmailNotificationChkBox();
		clickSaveBtn();
		return this;
	}
	
	private void setDateWithCalendar(String date, String dateSelector) throws ParseException {
		if(dateSelector=="from") {
			clickFromCalendarBtn();
			clickFromDatePickerBtn();
			clickFromDatePickerBtn();
		} else {
			clickToCalendarBtn();
			clickToDatePickerBtn();
			clickToDatePickerBtn();
		}
		String[] splittedDate =  splitDate(date);
					
		clickYearBtn(splittedDate[2],dateSelector);
		clickMonthBtn(splittedDate[1],dateSelector);
		clickDayBtn(splittedDate[0],dateSelector);

	}
	
	private void clickDayBtn(String day, String dateSelector) {
		clickDateComponentBtn(day,dateSelector);	
	}

	private void clickMonthBtn(String month, String dateSelector) {
		clickDateComponentBtn(month,dateSelector);	
	}

	private void clickYearBtn(String year, String dateSelector) {
		clickDateComponentBtn(year,dateSelector);	
	}

	private void clickFromCalendarBtn() {
		fromCalendarBtn.click();
	}

	private void clickToCalendarBtn() {
		toCalendarBtn.click();
	}

	private void clickFromDatePickerBtn(){
		fromDatePickerBtn.click();
	}

	private void clickToDatePickerBtn(){
		toDatePickerBtn.click();
	}

	//Buscar metodo para que reciba cualquier fecha
	private String[] splitDate(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Date newFormatDate = formatter.parse(date);
		String[] dateArray = formatter.format(newFormatDate).split("-");
		return dateArray;
	}
	
	private void clickDateComponentBtn(String dateComponent, String dateSelector) {
		WebElement dateBtn = driver.findElement(By.xpath("//date-picker[@model='form." + dateSelector + ".value']"
				+ "//span[contains(text(),'" + dateComponent + "')]/ancestor::td"));
		dateBtn.click();
	}
	
	private void doubleClick(WebElement webElement) {
		action.doubleClick();
		action.perform();
	}
}
