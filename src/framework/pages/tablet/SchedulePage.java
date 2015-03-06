package framework.pages.tablet;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SchedulePage {
	@FindBy(id = "txtOrganizer")
	WebElement organizerTxtBox;
	
	@FindBy(id = "txtSubject")
	WebElement subjectTxtBox;
	
	@FindBy(id = "txtBody")
	WebElement bodyTxtBox;
	
	@FindBy(id = "xpath=(//input[@type='text'])[3]")
	WebElement attendeesTxtBox;
	
	@FindBy(xpath = "//input[@type='time']")
	WebElement startTimeTxtBox;
	
	@FindBy(xpath = "xpath=(//input[@type='time'])[2]")
	WebElement endTimeTxtBox;
	
	@FindBy(xpath = "//button[4]")
	WebElement createBtn;
	
	@FindBy(xpath = "//button")
	WebElement backBtn;
	
	@FindBy(css = "css=div.currenttime")
	WebElement currentTimeLine;
	
	public void setOrganizerTxtBox(String organizer) {
		organizerTxtBox.sendKeys(organizer);
	}
	
	public void setSubjectTxtBox(String subject) {
		subjectTxtBox.sendKeys(subject);
	}
	
	public void setAttendeesTxtBox(String attendiee) {
		attendeesTxtBox.sendKeys(attendiee);
	}
	
	public void setBodyTxtBox(String textBody) {
		bodyTxtBox.sendKeys(textBody);
	}
	
	public void setStartTimeDate(String startTime) {
		startTimeTxtBox.sendKeys(startTime);
	}
	
	public void setEndTimeDate(String endTime) {
		startTimeTxtBox.sendKeys(endTime);
	}
	
	public void ClickCreateBtn() {
		createBtn.click();
	}
	
	public void ClickBackButton() {
		backBtn.click();
	}
}
