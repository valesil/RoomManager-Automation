package framework.pages.admin.conferencerooms;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CRResourceAssociationsPage extends AbstractRoomBasePage {
	@FindBy(xpath = "//div[1]/div[3]/button")//cambiar
	WebElement clickAddAvailableResourceBtn;

	@FindBy(xpath = "//div[2]/div/div/div[2]/div/div/div/div/div[2]/span")//cambiar
	WebElement firstTextResource;

	public void clickAddAvailableResourceBtn() {
		clickAddAvailableResourceBtn.click();
	}
	
	public String getResourceName(){
		return firstTextResource.getText();
	}
}
	
