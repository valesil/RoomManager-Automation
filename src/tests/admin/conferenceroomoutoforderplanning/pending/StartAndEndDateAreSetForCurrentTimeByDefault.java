package tests.admin.conferenceroomoutoforderplanning.pending;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.TimeManager;
import framework.utils.readers.ExcelReader;

/**
 * TC: Verify that start date is set in current time when the room has no 
 * Out Of Order Period established
 * TC: Verify that end date are set in the current time when the room has no 
 * Out Of Order Period established
 * @author Yesica Acha
 *
 */
public class StartAndEndDateAreSetForCurrentTimeByDefault {
	HomeAdminPage homeAdminPage = new HomeAdminPage(); 
	RoomOutOfOrderPlanningPage outOfOrder;
	RoomsPage rooms;
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(0).get("Room Name");
	
	
	@Test
	public void testStartAndEndDateIsSetForCurrentTimeByDefault() {
		String currentDate = TimeManager.getCurrentDate("MMM dd YYYY");
		rooms = homeAdminPage.clickConferenceRoomsLink();
		outOfOrder = rooms
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();	
		
		//Assert for TC:
		Assert.assertEquals(outOfOrder.getStartDateValue(), currentDate);
		
		//Assert for TC:
		Assert.assertEquals(outOfOrder.getEndDateValue(), currentDate);
	}
	
	@AfterClass
	public void closeOutOfOrderPage() {
		outOfOrder.clickCancelBtn();
	}
}
