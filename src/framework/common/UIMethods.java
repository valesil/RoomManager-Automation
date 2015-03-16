package framework.common;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

	public boolean isElementPresent(By element) {
		boolean present;
		try {			
			driver.findElement(element);
			present=true;
		} catch (NoSuchElementException ex) {
			present = false;
		}
		return present;
	}
	
	public boolean waitForMaskDesappears() {
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@ng-class='{in: animate}']")));		
	}
}
