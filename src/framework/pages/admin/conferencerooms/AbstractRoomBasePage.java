 package framework.pages.admin.conferencerooms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.selenium.SeleniumDriverManager;

public abstract class AbstractRoomBasePage {
	protected WebDriver driver;	
	protected WebDriverWait wait;
	
	@FindBy(linkText="Room Info") 
	WebElement roomInfoLink;
	
	@FindBy(linkText="Resource Associations") 
	WebElement resourceAssociationsLink;
	
	@FindBy(linkText="Out of Order Planning") 
	WebElement outOfOrderPlanningLink;
	
	@FindBy(xpath="//button[@ng-click='save()']") 
	WebElement saveBtn;
	
	@FindBy(xpath="//span[contains(text(),'Cancel')]") 
	WebElement cancelBtn;
	
	public AbstractRoomBasePage() {
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}
	
	public RoomInfoPage clickRoomInfoLink(){
		roomInfoLink.click();
		return new RoomInfoPage();
	}
	
	public CRResourceAssociationsPage clickResourceAssociationsLink(){
		resourceAssociationsLink.click();
		return new CRResourceAssociationsPage();
	}
	
	public OutOfOrderPlanningPage clickOutOfOrderPlanningLink(){
		outOfOrderPlanningLink.click();
		return new OutOfOrderPlanningPage();
	}
	
	public ConferenceRoomPage clickSaveBtn(){
		saveBtn.click();
		return new ConferenceRoomPage();
	}
	
	public ConferenceRoomPage clickCancelBtn(){
		cancelBtn.click();
		return new ConferenceRoomPage();
	}
		
}