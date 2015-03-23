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
 * TC15: Verify an Out Of Order cannot be created if title has only spaces and no characters
 * TC48: Verify that when an Out Of Order title has only spaces and no characters the message 
 * "Out of order should have a title" is displayed
 * @author administrator
 *
 */
public class OutOfOrderIsNotCreatedIfTitleContainsOnlySpaces {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(7).get("Room Name");
	String title = testData.get(7).get("Title");
	
	@Test
	public void testOutOfOrderIsNotCreatedIfTitleContainsOnlySpaces() {
		
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage conferenceRoom = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = conferenceRoom
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		outOfOrder.setTitleTxtBox(title)
		.clickSaveWithErrorBtn();
		
		//Assertion for TC15
		Assert.assertTrue(outOfOrder.isErrorMessagePresent());
		
		//Assertion for TC48
		Assert.assertTrue(outOfOrder.isOutOfOrderShouldHaveTitleErrorDisplayed());
		outOfOrder.clickCancelBtn();
	}
}
