/**
 * This page is the result of click on "+Add" button in resources page
 */
package framework.pages.admin.reources;

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
public class NewResourcesPage {
	private WebDriver driver;
	private WebDriverWait wait;
	
	@FindBy(xpath = "(//input[@type='text'])[3]") //cambiar
	WebElement resourceNameTextbox;
	
	@FindBy(xpath = "(//input[@type='text'])[4]") //cambiar
	WebElement resourceDisplayNameTextbox;
	
	@FindBy(xpath = "//div[3]/div[2]/button") //cambiar
	WebElement saveResourceBtn;

	public NewResourcesPage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * Required methods to create a resource
	 * @param resourceName
	 * @param resourceDisplayName
	 */
	public ResourcesPage createNewResource(String resourceName, String resourceDisplayName) {
		setResourceName(resourceName);
		setResourceDisplayName(resourceDisplayName);
		clickSaveResourceBtn();
		return new ResourcesPage();
	}
	
	public void setResourceName(String resourceName) {
		wait.until(ExpectedConditions.visibilityOf(resourceNameTextbox));
		resourceNameTextbox.sendKeys(resourceName);
	}
	
	public void setResourceDisplayName(String resourceDisplayName) {
		resourceDisplayNameTextbox.sendKeys(resourceDisplayName);
	}
	
	public void clickSaveResourceBtn() {
		saveResourceBtn.click();
	}
	
	
	
}
