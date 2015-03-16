package framework.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import framework.selenium.SeleniumDriverManager;

public class ReportNGListener implements ITestListener {
	WebDriver driver = SeleniumDriverManager.getManager().getDriver();

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			String filePath = System.getProperty("user.dir") + "/screenshots/";
			String failureImageFileName =  new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss")
				.format(new GregorianCalendar().getTime()) + ".png"; 
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(filePath + failureImageFileName)); 
			String userDirector = System.getProperty("user.dir") + "/screenshots/"; 
			Reporter.log("<a href=\"" + userDirector + failureImageFileName 
					+ "\"><img src=\"file:///" + userDirector + failureImageFileName 
					+ "\" alt=\"\"" + "height='100' width='100'/> " + "<br />"); 
			Reporter.setCurrentTestResult(null); 
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
	}
}
