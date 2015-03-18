package framework.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import framework.selenium.SeleniumDriverManager;

/**
 * 
 * @author Ruben Blanco
 *
 */
public class UIMethods {
	private WebDriver driver;	
	private WebDriverWait wait;
	private Actions action;

	public UIMethods() {
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		action = new Actions(driver);
	}

	public void doubleClick(WebElement webElement) {
		action.doubleClick(webElement);
		action.perform();
	}
}
