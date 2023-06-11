package reports;
//package utilities.reports;
//
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import com.aventstack.extentreports.reporter.configuration.Theme;
//
//public class ExtentManager {
//	public static ExtentReports extent;
//    public static ExtentHtmlReporter htmlReporter;
//    public static ExtentTest test;
//  
//    public static void setExtent() {
//    	htmlReporter = new ExtentHtmlReporter("Reports/report.html");
//    	htmlReporter.config().setDocumentTitle("KabulCS");
//    	htmlReporter.config().setReportName("Automation Report");
//    	htmlReporter.config().setTheme(Theme.DARK);
//    	
//    	extent = new ExtentReports();
//    	extent.attachReporter(htmlReporter);
//    	
//    	extent.setSystemInfo("HostName", "MyHost");
//    	extent.setSystemInfo("Project Name", "MyProject");
//    	extent.setSystemInfo("Test", "MyTester");
//    	extent.setSystemInfo("OS", "MyOS");
//    	extent.setSystemInfo("Brwoser", "MyBrowser");
//    }
//    
//    public static void endReport() {
//    	extent.flush();
//    }
//}
//
