package framework.pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.selenium.SeleniumDriverManager;

/**
 * 
 * @author Administrator
 *
 */
public class LoginPage {

	@FindBy(xpath = "//button") 
	WebElement signinBtn;

	private String baseUrl = "http://172.20.208.177:4046/admin/#/login";
	private WebDriver driver;

	/**
	 * This constructor initialize the driver and wait
	 */
	public LoginPage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		driver.get(baseUrl);
		PageFactory.initElements(driver, this);
	}

	/**
	 * This method is to click on signin link
	 * @return
	 */
	public HomeAdminPage clickSigninLink() {		
		signinBtn.click();
		return new HomeAdminPage();
	}

}
