package framework.selenium;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Manages the web browser
 */
public class SeleniumDriverManager {
	private static SeleniumDriverManager manager = null;
	private WebDriver driver;
	private WebDriverWait wait;

	protected SeleniumDriverManager()
	{
		initializeDriver();
	}

	/**
	 * Select a browser
	 */
	private void initializeDriver()
	{
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 30, 100);
	}

	public static SeleniumDriverManager getManager()
	{
		if(manager == null)
		{
			manager = new SeleniumDriverManager();
		}
		return manager;
	}

	/**
	 * Get the Web driver
	 * @return
	 */
	public WebDriver getDriver()
	{
		return driver;
	}

	public WebDriverWait getWait()
	{
		return wait;
	}

	/**
	 * Set to null the webdriver
	 */
	public void quitDriver()
	{
		try
		{
			driver.quit();
		}
		catch (Exception e)
		{
			//Logger.getLogger(getClass()).error("Unable to quit the webdriver" , e);
		}
		driver = null;
	}
}