package framework.pages.admin.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import framework.common.UIMethods;
import framework.selenium.SeleniumDriverManager;

/**
 * @author Marco Llano
 *
 */
public class ResourceInfoPage extends ResourceBaseAbstractPage {	
	UIMethods uiMethod = new UIMethods();

	public ResourceInfoPage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}

	/**
	 * [ML]Return the text of the resource name field in resourceInfoPage
	 * @return
	 */
	public String getResourceNameFromResourceInfoPage() {
		wait.until(ExpectedConditions.visibilityOf(resourceNameTxtBox));
		return resourceNameTxtBox.getAttribute("value");
	}
	
	/**
	 * [ML]Return the text of the resource display name field in resourceInfoPage
	 * @return
	 */
	public String getResourceDisplayName() {
		return resourceDisplayNameTxtBox.getAttribute("value");
	}

	/**
	 * [ML]Return the text of the resource description field in resourceInfoPage
	 * @return
	 */
	public String getResourceDescription() {
		return resourceDescriptionTxtBox.getAttribute("value");
	}

	/**
	 * [ML]Return the resource icon name from in resourceInfoPage if is present
	 * @param iconTitle
	 * @return
	 */
	public boolean getResourceIcon(String iconTitle) {
		By resourceIcon = By.xpath(".//*[@id='resourcesGrid']/descendant::*/span[@class='fa " +
				iconTitle + "']");
		return uiMethod.isElementPresent(resourceIcon);
	}
}
