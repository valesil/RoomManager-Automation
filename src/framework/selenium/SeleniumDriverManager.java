package framework.selenium;

import static framework.common.AppConfigConstants.BROWSER;
import static framework.common.AppConfigConstants.CHROMEDRIVER_PATH;
import static framework.common.AppConfigConstants.IEDRIVER_PATH;
import static framework.common.AppConfigConstants.EXPLICIT_WAIT;
import static framework.common.AppConfigConstants.IMPLICIT_WAIT;
import static framework.common.AppConfigConstants.WEBDRIVERWAIT_SLEEP;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

/**
 * Manages the web browser
 * @author Room Manager Team
 */
public class SeleniumDriverManager {
	private static SeleniumDriverManager manager = null;
	private WebDriver driver;
	private WebDriverWait wait;

	protected SeleniumDriverManager() {
		initializeDriver();
	}

	/**
	 * Select a browser
	 */
	private void initializeDriver() {
		if (BROWSER.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (BROWSER.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + IEDRIVER_PATH);
			driver = new InternetExplorerDriver();
		} else if (BROWSER.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + CHROMEDRIVER_PATH);
			driver = new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(IMPLICIT_WAIT, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, EXPLICIT_WAIT, WEBDRIVERWAIT_SLEEP);
	}

	public static SeleniumDriverManager getManager() {
		if (manager == null) {
			manager = new SeleniumDriverManager();
		}
		return manager;
	}

	/**
	 * Get the Web driver
	 * 
	 * @return
	 */
	public WebDriver getDriver() {
		return driver;
	}

	public WebDriverWait getWait() {
		return wait;
	}

	/**
	 * Set to null the webdriver
	 */
	public void quitDriver() {
		try {
			driver.quit();
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Unable to quit the webdriver", e);
		}
		driver = null;
	}
}