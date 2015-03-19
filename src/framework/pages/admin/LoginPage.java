package framework.pages.admin;

import static framework.common.AppConfigConstants.URL_ADMIN;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import framework.selenium.SeleniumDriverManager;

/**
 * 
 * @author Ruben Blanco
 *
 */
public class LoginPage {

	@FindBy(xpath = "//button") 
	WebElement signinBtn;

	private WebDriver driver;

	/**
	 * This constructor initialize the driver and waits
	 */
	public LoginPage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		driver.get(URL_ADMIN);
		PageFactory.initElements(driver, this);
	}

	/**
	 * This method clicks on signin link
	 * @return
	 */
	public HomeAdminPage clickSigninLink() {		
		signinBtn.click();
		return new HomeAdminPage();
	}

	/**
	 * [ML]This method close browser
	 * @return
	 */
	public void CloseWindow() {
		driver.quit();
	}
}
