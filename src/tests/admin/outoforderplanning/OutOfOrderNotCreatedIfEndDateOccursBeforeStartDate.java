package tests.admin.outoforderplanning;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.conferencerooms.ConferenceRoomPage;
import framework.pages.admin.conferencerooms.OutOfOrderPlanningPage;
import framework.utils.readers.ExcelReader;
import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

public class OutOfOrderNotCreatedIfEndDateOccursBeforeStartDate {

	@Test
	public void testOutOfOrderNotCreatedIfEndDateOccursBeforeStartDate() throws Exception, IOException {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
		String startDate = testData.get(0).get("Start date");
		String endDate = testData.get(0).get("End date");
		ConferenceRoomPage conferenceRoom = new ConferenceRoomPage();
		OutOfOrderPlanningPage outOfOrder = conferenceRoom
				.doubleClickOverRoomName(testData.get(0).get("Room Name"))
				.clickOutOfOrderPlanningLink();
		outOfOrder.setStartDateWithCalendar(startDate)
		.setEndDateWithCalendar(endDate)
		.clickSaveWithErrorBtn();
		Assert.assertTrue(outOfOrder.errorMessageIsPresent());
	}
}
