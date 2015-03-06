package framework.pages.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.selenium.SeleniumDriverManager;

/**
 * 
 * @author Administrator
 *
 */
public class LoginPage {

	@FindBy(xpath = "//button") 
	WebElement signinBtn;

	private WebDriver driver;	
	private WebDriverWait wait;	

	/**
	 * This constructor init the driver and wait
	 */
	public LoginPage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}

	/**
	 * This method is to click on signin link
	 * @return
	 */
	public HomePage clickSignonLink() {		
		// wait for signin link
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("SIGN-ON")));
		signinBtn.click();
		return new HomePage();
	}

}
