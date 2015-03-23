package tests.admin.suits;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

public class IsNotAllowedInsertNonNumberInputsIntoCapacityFieldOfRooms {
	
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	String displayName =testData1.get(0).get("DisplayName");	  	  
	String capacity = testData1.get(0).get("invalidCapacity");
	
	@Test
	public void testIsNotAllowedInsertNonNumberInputsIntoCapacityFieldOfRooms() {
		
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf.setRoomCapacity(capacity);
		String newCapacity = roomInf.getRoomCapacity();
		roomInf.clickCancelBtn();
		Assert.assertNotEquals(capacity,newCapacity);
	}
}
