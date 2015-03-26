package tests.tablet.homeUI;

/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.utils.TimeManager;

public class TheRedLineInTheTimelineIsInTheCurrentTime {
	
	@Test(groups = "UI")
	public void testRedLineInTheTimelineIsInTheCurrentTime () throws IOException{
		HomeTabletPage home = new HomeTabletPage();
		String currentDate = TimeManager.getCurrentDate("MMMM d YYY, HH:mm");
		Assert.assertTrue(home.getTimeLineDate().contains(currentDate));
	}

}
