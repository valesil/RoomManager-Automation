package framework.pages.admin;

import static framework.common.AppConfigConstants.URL_ADMIN_HOME;

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
		return this;
	}
}
