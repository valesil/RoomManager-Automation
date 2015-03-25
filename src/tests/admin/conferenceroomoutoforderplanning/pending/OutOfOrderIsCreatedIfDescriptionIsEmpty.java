package tests.admin.conferenceroomoutoforderplanning.pending;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC22: Verify an Out Of Order can be created if description text box is empty
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsCreatedIfDescriptionIsEmpty {
	
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(8).get("Room Name");
	String title = testData.get(8).get("Title");
	String description = testData.get(8).get("Description");
	String expectedMessage = testData.get(8).get("Message");
	
	@Test
	public void testOutOfOrderIsCreatedIfDescriptionIsEmpty() {
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage rooms = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = rooms
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		rooms = outOfOrder.setTitleTxtBox(title)
				.setDescriptionTxtBox(description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();
		Assert.assertTrue(rooms.isOutOfOrderSuccessfullyCreatedMessageDisplayed());
	}
	
	@AfterMethod
	public void postCondition() {
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}

}
