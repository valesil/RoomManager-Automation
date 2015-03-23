package tests.tablet.homeUI;

/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomePage;
import framework.utils.TimeManager;

public class TheRedLineInTheTimelineIsInTheCurrentTime {
	public HomePage home = new HomePage();

	@Test(groups = {"UI"})
	public void testRedLineInTheTimelineIsInTheCurrentTime (){
		String currentDate = TimeManager.getCurrentDate("MMMM d YYY, HH:mm");
		Assert.assertTrue(home.getTimeLineDate().contains(currentDate));
	}

}
