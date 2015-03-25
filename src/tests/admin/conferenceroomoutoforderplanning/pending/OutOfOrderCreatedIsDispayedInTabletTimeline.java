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
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SearchPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC02: Verify that an Out Of Order created is displayed in the scheduler timeline in Tablet
 * TC03: Verify that an Out Of Order created is displayed in Search page in Tablet
 * @author Yesica Acha
 *
 */
public class OutOfOrderCreatedIsDispayedInTabletTimeline {
	HomeAdminPage homeAdminPage = new HomeAdminPage(); 
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
	public void testOutOfOrderCreatedIsDispayedInTabletTimeline () throws InterruptedException {
				
		//Out Of Order Creation in Admin
		
		RoomsPage conferenceRoom = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = conferenceRoom
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		conferenceRoom = outOfOrder.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title, description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();
		
		//Openning Tablet for assertions
		HomePage home = new HomePage();
		
		SchedulePage scheduler = home
				.getHome()
				.clickScheduleBtn();
		
		//Assertion for TC02
		Assert.assertTrue(scheduler.isOutOfOrderBoxDisplayed(title));
		
		//Assertion for TC03
		SearchPage search = scheduler.clickSearchBtn();
		Assert.assertTrue(search.isOutOfOrderBoxDisplayed(title));
	}
	
	@AfterClass
	public void postCondition(){
		RootRestMethods.deleteOutOfOrder(roomName, title);
		homeAdminPage.getAdminHome();
	}
}
