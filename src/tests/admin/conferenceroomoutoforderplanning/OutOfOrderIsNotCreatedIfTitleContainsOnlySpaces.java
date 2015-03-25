package tests.admin.conferenceroomoutoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC19: Verify an Out Of Order cannot be created if title has only spaces and no characters
 * TC30: Verify that when an Out Of Order title has only spaces and no characters the message 
 * "Out of order should have a title" is displayed
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsNotCreatedIfTitleContainsOnlySpaces {
	RoomOutOfOrderPlanningPage outOfOrderPage; 
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(7).get("Room Name");
	String title = testData.get(7).get("Title");
	
	@Test(groups = {"FUNCTIONAL", "UI"})
	public void testOutOfOrderIsNotCreatedIfTitleContainsOnlySpaces() throws JSONException, MalformedURLException, IOException {
		
		//Out Of Order creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		outOfOrderPage = roomsPage
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		outOfOrderPage = outOfOrderPage.setTitleTxtBox(title)
		.clickSaveWithErrorBtn();
		
		//Assertion for TC19
		Assert.assertFalse(RootRestMethods.isOutOfOrderCreated(roomName, title));
		
		//Assertion for TC30
		Assert.assertTrue(outOfOrderPage.isErrorMessagePresent());
		Assert.assertTrue(outOfOrderPage.isOutOfOrderShouldHaveTitleErrorDisplayed());
	}
	
	@AfterMethod
	public void closeOutOfOrderPage() {
		outOfOrderPage.clickCancelBtn();
	}
}
