package tests.admin.outoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.common.MeetingMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC07: Verify a created Out Of Order's information is not displayed in the Scheduler 
 * by clicking on it in Scheduler page in the Tablet
 * TC08: Verify a created Out Of Order's information cannot be changed in the Scheduler 
 * Page in the Tablet
 * @author Yesica Acha
 *
 */
public class OutOfOrderInformationIsNotDisplayedOrUpdatedInScheduler {
	
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(10).get("Room Name");
	String startDate = testData.get(10).get("Start date");
	String endDate = testData.get(10).get("End date");
	String startTime = testData.get(10).get("Start time (minutes to add)");
	String endTime = testData.get(10).get("End time (minutes to add)");
	String title = testData.get(10).get("Title");
	String description = testData.get(10).get("Description");
	
	@Test
	public void testOutOfOrderInformationIsNotDisplayedInScheduler () {
				
		//Out Of Order Creation in Admin
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage conferenceRoom = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = conferenceRoom
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		conferenceRoom = outOfOrder.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title, description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();
		
		//Openning Tablet for assertions
		MeetingMethods meetingMethods = new MeetingMethods();
		HomePage home = meetingMethods.getHomeForSpecificRoom(roomName);
		SchedulePage scheduler = home
				.clickScheduleBtn()
				.clickOverMeetingCreated(title);
		
		//Assert for TC
		Assert.assertTrue(scheduler.getMeetingOrganizerValue().isEmpty());
		Assert.assertTrue(scheduler.getMeetingSubjectValue().isEmpty());
		
		//Assert for TC
		Assert.assertFalse(scheduler.isUpdateBtnPresent());
	}
	
	@AfterClass
	public void postCondition(){
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RootRestMethods.deleteOutOfOrder(roomName, title);
		homeAdminPage.getAdminHome();
	}
}
