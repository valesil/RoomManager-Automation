package framework.pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.selenium.SeleniumDriverManager;

/**
 * This class contains the main menu
 * @author Administrator
 *
 */
public class MainMenu {

	WebDriver driver;
	
	@FindBy(linkText = "Room Manager") 
	WebElement HomeLink;

	@FindBy(linkText = "Email Servers") 
	WebElement emailServersLink;
	
	@FindBy(linkText = "Impersonation") 
	WebElement impersonationLink;
	
	@FindBy(linkText = "Conference Rooms") 
	WebElement conferenceRoomsLink;
	
	@FindBy(linkText = "Resources") 
	WebElement resourcesLink;
	
	@FindBy(linkText = "Issues") 
	WebElement issuesLink;
	
	@FindBy(linkText = "Locations") 
	WebElement locationsLink;
	
	@FindBy(linkText = "Tablets") 
	WebElement tabletsLink;
	
	
	public MainMenu() {
		driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
	}
	
	public HomePage emailServersLink() {
		HomeLink.click();
		return new HomePage();
	}
	
	public HomePage impersonationLink() {
		HomeLink.click();
		return new HomePage();
	}
	
	public ConferenceRoomPage conferenceRoomsLink() {
		HomeLink.click();
		return new ConferenceRoomPage();
	}
	
	public HomePage resourcesLink() {
		HomeLink.click();
		return new HomePage();
	}
	
	public HomePage clickHomeLink() {
		HomeLink.click();
		return new HomePage();
	}
	
	public HomePage issuesLink() {
		HomeLink.click();
		return new HomePage();
	}
	
	public HomePage locationsLink() {
		HomeLink.click();
		return new HomePage();
	}
	
	public HomePage tabletsLink() {
		HomeLink.click();
		return new HomePage();
	}
	
}
