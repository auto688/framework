package testCase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import pageObject.HomePage;
import reports.Reporter;
import utilities.Driver;
import utilities.Log;
import utilities.TestInfo;

public class BaseTest{
	public static WebDriver driver; // null
    HomePage homePage;
    Log logger;
    
	public static void captureScreenshot(WebDriver driver, String screenshotName) {
        // Convert WebDriver object to TakesScreenshot
        TakesScreenshot ts = (TakesScreenshot) driver;

        // Capture screenshot as File object
        File screenshotFile = ts.getScreenshotAs(OutputType.FILE);

        try {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());
        	// Specify the destination path for the screenshot
            String destinationPath = "Screenshots/" + screenshotName +"_" +timestamp+ ".png";

            // Copy the screenshot file to the destination path
            FileUtils.copyFile(screenshotFile, new File(destinationPath));
            System.out.println("Screenshot captured: " + destinationPath);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
	}
	
	@BeforeSuite
	public void setUp() {
		driver = Driver.getDriver();
		logger = Log.getInstance();
		homePage = new HomePage(driver);
		Reporter.setExtent();
		TestInfo.readTestData();
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
			logger.info("Driver closed");
			
		}
		
		Reporter.endReport();
	}
	
	public String getCurrentMethodName() {
	    return StackWalker.getInstance()
	                      .walk(s -> s.skip(1).findFirst())
	                      .get()
	                      .getMethodName();
	}

	public String getCallerMethodName() {
	    return StackWalker.getInstance()
	                      .walk(s -> s.skip(2).findFirst())
	                      .get()
	                      .getMethodName();
	}

	public void sleep(int time) {
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
