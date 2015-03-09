/**
 * 
 */
package framework.pages.admin.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.selenium.SeleniumDriverManager;

/**
 * @author administrator
 *
 */
public class AbstractResourceBasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;

	@FindBy(xpath = "(//input[@type='text'])[3]") 
	WebElement resourceNameTextbox;
	
	@FindBy(xpath = "(//input[@type='text'])[4]") 
	WebElement resourceDisplayNameTextbox;
	
	@FindBy(xpath = "//div[3]/div[2]/button") 
	WebElement saveResourceBtn;
	
	@FindBy(xpath = "//textarea") 
	WebElement resourceDescriptionTextbox;
	
	@FindBy(xpath = ".//*[@id='convert']") 
	WebElement resourceOpenIconBtn;
	
	public AbstractResourceBasePage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}
	
	public void setResourceName(String resourceName) {
		wait.until(ExpectedConditions.visibilityOf(resourceNameTextbox));
		resourceNameTextbox.sendKeys(resourceName);
	}
	
	public void setResourceDisplayName(String resourceDisplayName) {
		resourceDisplayNameTextbox.sendKeys(resourceDisplayName);
	}
	
	public void setResourceDescription(String resourceDescription) {
		resourceDescriptionTextbox.sendKeys(resourceDescription);
	}
	
	public void setResourceIcon(String iconTitle) {
		driver.findElement(By.xpath("//button[@value='" + iconTitle +"']")).click();;
	}
	
	public void clickResourceIcon() {
		resourceOpenIconBtn.click();
	}
	
	public ResourcesPage clickSaveResourceBtn() {
		saveResourceBtn.click();
		return new ResourcesPage();
	}
}
