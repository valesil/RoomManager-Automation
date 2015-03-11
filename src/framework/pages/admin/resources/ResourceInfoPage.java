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
public class ResourcesInfoPage extends AbstractResourceBasePage {	
	UIMethods uiMethod = new UIMethods();
	
	public ResourcesInfoPage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}
	
	//Main method to create or edit a resource
	public ResourcesPage createEditResource(String resourceName, String resourceDisplayName, String resourceDescription,
											String iconTitle) {
		setResourceName(resourceName);
		setResourceDisplayName(resourceDisplayName);
		setResourceDescription(resourceDescription);
		clickResourceIcon();
		setResourceIcon(iconTitle);		
		return clickSaveResourceBtn();
	}
	
	/**
	 * Below methods for assertions, The first verify if a resource name exist in resourceInfoPage
	 */
	public String getResourceNameFromResourceInfoPage() {
		wait.until(ExpectedConditions.visibilityOf(resourceNameTxtBox));
		return resourceNameTxtBox.getText();
	}
	
	//This method return the text from resource display name from resourceInfoPage
	public String getResourceDisplayName() {
		return resourceDisplayNameTxtBox.getAttribute("value");
	}
	
	//This method return the text from resource description from resourceInfoPage
	public String getResourceDescription() {
		return resourceDescriptionTxtBox.getAttribute("value");
	}
	
	//This method return the resource icon from resourceInfoPage
	public boolean getResourceIcon(String iconTitle) {
		By resourceIcon = By.xpath(".//*[@id='resourcesGrid']/descendant::*/span[@class='fa " +
				iconTitle + "']");
		return uiMethod.isElementPresent(resourceIcon);
	}
}
