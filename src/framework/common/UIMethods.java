package framework.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import framework.selenium.SeleniumDriverManager;

/**
 * 
 * @author Ruben Blanco
 *
 */
public class UIMethods {
	private WebDriver driver;	
	private Actions action;

	public UIMethods() {
		driver = SeleniumDriverManager.getManager().getDriver();
		action = new Actions(driver);
	}
	
	public void doubleClick(WebElement webElement) {
		action.doubleClick(webElement);
		action.perform();
	}
}
