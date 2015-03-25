package tests.admin.conferenceroomoutoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC36: Verify that start time  (hours and minutes) is set for the beginning of the next half hour 
 * to current time when the room has no Out Of Order Period
 * TC37: Verify that end time  (hours and minutes) is set for the end of the next half hour period 
 * to current time when the room has no Out Of Order Period established
 * @author Yesica Acha
 *
 */
public class StartAndEndTimeAreSetForTheNextHalfHourPeriod {
	HomeAdminPage homeAdminPage = new HomeAdminPage(); 
	RoomOutOfOrderPlanningPage outOfOrderPage;
	RoomsPage roomsPage;
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(0).get("Room Name");
	
	
	@Test (groups = "UI")
	public void testStartAndEndTimeAreSetForTheNextHalfHourPeriod() {
		roomsPage = homeAdminPage.clickConferenceRoomsLink();
		outOfOrderPage = roomsPage
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();	
		
		//Assert for TC36:
		Assert.assertEquals(outOfOrderPage.getStartTimeValue(), outOfOrderPage.getDefaultStartTimeValue());
		
		//Assert for TC37:
		Assert.assertEquals(outOfOrderPage.getEndTimeValue(), outOfOrderPage.getDefaultEndTimeValue());
	}
	
	@AfterMethod
	public void closeOutOfOrderPage() {
		outOfOrderPage.clickCancelBtn();
	}
}
