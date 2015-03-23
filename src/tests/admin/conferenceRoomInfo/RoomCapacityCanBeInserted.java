package tests.admin.suits;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.junit.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.utils.readers.ExcelReader;

public class RoomCapacityCanBeInserted {
	@Test
	public void testRoomCapacityCanBeInserted() throws InterruptedException, BiffException, IOException {
		
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
		String displayName =testData1.get(0).get("DisplayName");// "room01 changed";	  	  
		String capacity = testData1.get(0).get("Capacity");
		
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf.setRoomCapacity(capacity)
		.clickSaveBtn();

		String roomCapacity = confRoomPage.doubleClickOverRoomName(displayName)
				.getRoomCapacity();
		roomInf.clickCancelBtn();
		Assert.assertEquals(capacity, roomCapacity);
	}

}
