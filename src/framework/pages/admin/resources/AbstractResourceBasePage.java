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
 * @author Marco Llano
 *
 */
public class AbstractResourceBasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;

	@FindBy(xpath = "//div[@class='input-control text']/input[@ng-model='resource.name']") 
	WebElement resourceNameTxtBox;
	
	@FindBy(xpath = "//div[@class='input-control text']/input[@ng-model='resource.customName']") 
	WebElement resourceDisplayNameTxtBox;
	
	@FindBy(xpath = "//button[@ng-click='save()']") 
	WebElement saveResourceBtn;
	
	@FindBy(xpath = "//div[@class='input-control text']/textarea[@ng-model='resource.description']") 
	WebElement resourceDescriptionTxtBox;
	
	@FindBy(id = "convert") 
	WebElement resourceOpenIconBtn;
	
	public AbstractResourceBasePage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * Required methods to create or edit a resource
	 */
	public ResourceInfoPage setResourceName(String resourceName) {
		wait.until(ExpectedConditions.visibilityOf(resourceNameTxtBox));
		resourceNameTxtBox.clear();
		resourceNameTxtBox.sendKeys(resourceName);
		return new ResourceInfoPage();
	}
	
	public ResourceInfoPage setResourceDisplayName(String resourceDisplayName) {
		resourceDisplayNameTxtBox.clear();
		resourceDisplayNameTxtBox.sendKeys(resourceDisplayName);
		return new ResourceInfoPage();
	}
	
	public ResourceInfoPage setResourceDescription(String resourceDescription) {
		resourceDescriptionTxtBox.clear();
		resourceDescriptionTxtBox.sendKeys(resourceDescription);
		return new ResourceInfoPage();
	}
	
	public ResourceInfoPage selectResourceIcon(String iconTitle) {
		By icon = By.xpath("//button[@value='" + iconTitle +"']"); 
		wait.until(ExpectedConditions.elementToBeClickable(icon));
		driver.findElement(icon).click();
		return new ResourceInfoPage();
	}
	
	public ResourceInfoPage clickPreviusNextIconPageBtn(String direction) {
		By iconButton = By.xpath("//button[@class='btn btn-primary btn-" + direction + "']");
		driver.findElement(iconButton).click();
		return new ResourceInfoPage();
	}
	
	public ResourceInfoPage clickResourceIcon() {
		wait.until(ExpectedConditions.elementToBeClickable(resourceOpenIconBtn));
		resourceOpenIconBtn.click();
		return new ResourceInfoPage();
	}
	
	public ResourcesPage clickSaveResourceBtn() {
		saveResourceBtn.click();
		return new ResourcesPage();
	}
}
