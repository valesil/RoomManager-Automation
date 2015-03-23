package tests.admin.outoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC11: Verify an Out Of Order cannot be created if title is empty
 * TC47: Verify that when title is empty the message "Out of order should have a title" is displayed
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsNotCreatedIfTitleIsEmpty {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(6).get("Room Name");
	String title = testData.get(6).get("Title");
	
	@Test
	public void testOutOfOrderIsNotCreatedIfTitleIsEmpty() {
		
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage conferenceRoom = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = conferenceRoom
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		outOfOrder.setTitleTxtBox(title)
		.clickSaveWithErrorBtn();
		
		//Assertion for TC11
		Assert.assertTrue(outOfOrder.isErrorMessagePresent());
		
		//Assertion for TC47
		Assert.assertTrue(outOfOrder.isOutOfOrderShouldHaveTitleErrorDisplayed());
		outOfOrder.clickCancelBtn();
	}
}
