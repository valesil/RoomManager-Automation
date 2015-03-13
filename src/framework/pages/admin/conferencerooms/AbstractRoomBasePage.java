package framework.pages.admin.conferencerooms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.selenium.SeleniumDriverManager;

/**
 * This class represents the Menu for conference rooms, Save and Cancel button
 * @author Yesica Acha
 *
 */
public abstract class AbstractRoomBasePage {
	protected WebDriver driver;	
	protected WebDriverWait wait;

	@FindBy(linkText = "Room Info") 
	WebElement roomInfoLink;

	@FindBy(linkText = "Resource Associations") 
	WebElement resourceAssociationsLink;

	@FindBy(linkText = "Out of Order Planning") 
	WebElement outOfOrderPlanningLink;

	@FindBy(xpath = "//button[@ng-click='save()']") 
	WebElement saveBtn;

	@FindBy(xpath = "//button[@ng-click='cancel()']") 
	WebElement cancelBtn;
	
	@FindBy(xpath = "//div[@class='toast-message']")
	WebElement messagePopUp;

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
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='toast-message']")));
		return new ConferenceRoomPage();
	}
	
	public Object clickSaveWithErrorBtn(){
		saveBtn.click();
		return this;
	}

	public ConferenceRoomPage clickCancelBtn(){
		cancelBtn.click();
		return new ConferenceRoomPage();
	}



}