package framework.pages.admin;

import static framework.common.AppConfigConstants.URL_ADMIN_HOME;

import org.openqa.selenium.By;

import framework.common.UIMethods;

/**
 * 
 * @author Ruben Blanco
 *
 */
public class HomeAdminPage extends AbstractMainMenu {
	/**
	 * [YA]This method returns to HomeAdminPage from Tablet
	 * @return
	 */
	public HomeAdminPage getAdminHome() {
		driver.get(URL_ADMIN_HOME);
		By signInLocator = By.xpath("//button[@ng-click='signIn()']");
		if(UIMethods.isElementPresent(signInLocator)) {
			driver.findElement(signInLocator).click();
		}
		return this;
	}
}
