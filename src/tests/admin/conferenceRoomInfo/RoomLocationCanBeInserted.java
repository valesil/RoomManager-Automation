package suits;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.junit.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

public class RoomLocationCanBeInserted {
	@Test
	public void testRoomLocationCanBeInserted() throws InterruptedException, BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
		String displayName =testData1.get(0).get("DisplayName");// "room01 changed";	  	  
		String location = testData1.get(0).get("Location");
		
		//LoginPage loginPage = new LoginPage();
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf.setLocation(location)
				.clickSaveBtn();
		String roomLocation = confRoomPage.doubleClickOverRoomName(displayName)
				.getRoomLocation();  
		roomInf.clickCancelBtn();
		Assert.assertEquals(location, roomLocation);
	}
}
