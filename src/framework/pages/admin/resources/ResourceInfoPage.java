package framework.pages.admin.resources;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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
	@FindBy(linkText = "Resource Associations") 
	WebElement resourceAssociationsLink;
	
	public ResourceInfoPage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}

	/**
	 * [ML]Return the text of the resource name field in resourceInfoPage
	 * @return
	 */
	public String getResourceName() {
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
	 * [ML]Click in resourceAssociationLink
	 * @return ResourceAssociationPage
	 */
	public ResourceAssociationsPage clickResourceAssociationLink() {
		resourceAssociationsLink.click();
		return new ResourceAssociationsPage();
	}
}
