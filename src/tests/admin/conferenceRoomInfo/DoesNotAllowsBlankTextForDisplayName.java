package suits;package tests.admin.suits;
import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

public class DoesNotAllowsBlankTextForDisplayName {

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	String displayName =testData1.get(0).get("DisplayName");// "room01 changed";	  	  

	@Test
	public void testDoesNotAllowsBlankTextForDisplayName() {

		HomeAdminPage home = new HomeAdminPage();	    
		RoomsPage rooms = home.clickConferenceRoomsLink();
		
		RoomInfoPage roomInf = rooms.doubleClickOverRoomName(displayName);
		roomInf.setDisplayName("")
		.clickSaveBtn();
		Assert.assertTrue(roomInf.isDisplaynameErrorMessageDisplayed());
	}
	
	@AfterClass
	public void postcondition() {
		RoomInfoPage roomInfo = new RoomInfoPage();
		roomInfo.clickCancelBtn();
	}
}
