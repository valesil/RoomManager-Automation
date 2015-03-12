/**
 * 
 */
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
public class DeleteResourcePage extends AbstractResourceBasePage {
	UIMethods uiMethod = new UIMethods();

	@FindBy(css = "button.info")
	WebElement confirmRemoveBtn;


	public DeleteResourcePage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}

	public ResourcesPage clickConfirmRemoveBtn() {
		wait.until(ExpectedConditions.elementToBeClickable(confirmRemoveBtn));
		confirmRemoveBtn.click();
		return new ResourcesPage();
	}
}
