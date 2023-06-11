package reports;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import testCase.BaseTest;
import utilities.TestInfo;

public class Reporter extends TestListenerAdapter {
	public static ExtentTest test; //Logic test
	public static ExtentReports extent; //Logic report
	public static ExtentSparkReporter htmlReporter; //UI Report
	
	public static void setExtent() {
		createHtmlReport();
		createExtentReports();
	}

	private static void createExtentReports() {
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("HostName", getHostName());
		extent.setSystemInfo("Project Name", "MyProject");
		extent.setSystemInfo("Test", getUserName());
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		Capabilities capabilities = ((RemoteWebDriver) BaseTest.driver).getCapabilities();
		String browserName = capabilities.getBrowserName();
		String browserVersion = capabilities.getVersion();

		extent.setSystemInfo("Browser", browserName + " " + browserVersion);
	}

	private static void createHtmlReport() {
		htmlReporter = new ExtentSparkReporter("Reports/report.html");
		try {
			htmlReporter.loadJSONConfig(new File(System.getProperty("user.dir") + "\\Configurations\\spark-config.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getHostName() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            
            return localhost.getCanonicalHostName();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
		return null;
    }
	public static String getUserName() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return localhost.getHostName().toString();
            

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
		return null;
    }
	public static void endReport() {
		extent.flush();
	}

	@Override 
	public void onTestStart(ITestResult result) {
		String testName = result.getName(); 
//		System.out.println("onTestStart");
//		System.out.println(testName);
		String testInfo = "Test Steps:<br>"+
						TestInfo.getTestExpectedResult(testName) +
						"<br>Expected Result<br>"+
						TestInfo.getTestExpectedResult(testName);
		test = extent.createTest(testName, TestInfo.getTestDescription(testName))
				.createNode("Test Details")
				.info(testInfo);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
//		System.out.println("onTestSuccess");
		// test = extent.createTest(result.getTestName());
		handleTestResult(result, Status.PASS, ExtentColor.LIME);
	}

	@Override
	public void onTestFailure(ITestResult result) {
//		System.out.println("onTestFailure");
		// test = extent.createTest(result.getName());
		handleTestResult(result, Status.FAIL, ExtentColor.RED);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
//		System.out.println("onTestSkipped");
		// test = extent.createTest(result.getTestName());
		handleTestResult(result, Status.PASS, ExtentColor.BLUE);
	}

	private void handleTestResult(ITestResult result, Status status, ExtentColor color) {

		test.log(status, MarkupHelper.createLabel(result.getName(), color));
		Throwable throwable = result.getThrowable();
		if (throwable != null) {
			test.log(status, MarkupHelper.createLabel(throwable.getMessage(), color));
		}
		
	}
}
