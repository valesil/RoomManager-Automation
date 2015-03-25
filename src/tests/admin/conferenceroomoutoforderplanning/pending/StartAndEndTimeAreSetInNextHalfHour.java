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
import framework.utils.readers.ExcelReader;

/**
 * TC: 
 * TC: 
 * @author Yesica Acha
 *
 */
public class StartAndEndTimeAreSetInNextHalfHour {
	HomeAdminPage homeAdminPage = new HomeAdminPage(); 
	RoomOutOfOrderPlanningPage outOfOrder;
	RoomsPage rooms;
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(0).get("Room Name");
	
	
	@Test
	public void testStartAndEndTimeAreSetInNextHalfHour() {
		rooms = homeAdminPage.clickConferenceRoomsLink();
		outOfOrder = rooms
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();	
		
		//Assert for TC:
		Assert.assertEquals(outOfOrder.getStartTimeValue(), outOfOrder.getDefaultStartTimeValue());
		
		//Assert for TC:
		Assert.assertEquals(outOfOrder.getEndTimeValue(), outOfOrder.getDefaultEndTimeValue());
	}
	
	@AfterClass
	public void closeOutOfOrderPage() {
		outOfOrder.clickCancelBtn();
	}
}
