package framework.common;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
	static WebDriver driver = SeleniumDriverManager.getManager().getDriver();	

	public static void doubleClick(WebElement webElement) {
		Actions action = new Actions(driver);
		action.doubleClick(webElement);
		action.perform();
	}

	public static boolean isElementPresent(By element) {
		boolean present;
		try {			
			driver.findElement(element);
			present=true;
		} catch (NoSuchElementException ex) {
			present = false;
		}
		return present;
	}
	
	public static void refresh() {
		driver.navigate().refresh();
	}
	
	public static void waitForMaskDisappearsAndClickElement(WebElement webElement) {
        boolean value = false;
        while (value == false) {
              try {
                    webElement.click();
                    value = true;
              }
              catch (Exception e) {
                    value = false;
              }
        }
  }

}
