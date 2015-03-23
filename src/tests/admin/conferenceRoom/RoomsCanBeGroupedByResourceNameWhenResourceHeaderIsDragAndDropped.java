package tests.admin.conferenceRoomSuite;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

public class RoomsCanBeGroupedByResourceNameWhenResourceHeaderIsDragAndDropped {
	
	
	
	@Test
	public void testRoomsCanBeGroupedByResourceNameWhenResourceHeaderIsDragAndDropped() {
		
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Resources");
		String resourceColumnName = testData.get(0).get("ResourceDisplayName");
		
		HomeAdminPage home = new HomeAdminPage();
		home.getAdminHome();
		RoomsPage room = home.clickConferenceRoomsLink();
		room.dragAndDropColumn(resourceColumnName);
		Assert.assertTrue(room.IsGroupedByRoom());
	}
}
