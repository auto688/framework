package utilities;



import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Driver {
	private static ReadConfig rc = ReadConfig.getInstance();

	public static WebDriver getDriver() {
		String browser = rc.getProperty("browser");
		String browserVersion = rc.getProperty("browserVersion");
		String url = rc.getProperty("baseULR");
		String ipAddress = rc.getProperty("LocalMachineIPAddress");
		InetAddress localhost = null;
		WebDriver driver = null;
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			
			e1.printStackTrace();
		} 
		String localIP = localhost.getHostAddress().trim();
		driver = localIP.contentEquals(ipAddress) ?
			startLocalBrowser(browser, browserVersion):
			startRemoteBrowser(rc.getProperty("RemoteMachineIPAddress"), browserVersion);
		
		if (driver != null) {
			driver.get(url);
			windowManagement(driver);
		}
		return driver;
	}

	public static WebDriver startRemoteBrowser(String hubURL, String browserVersion) {
		return null;
	}

	public static WebDriver startLocalBrowser(String browserName, String browserVersion) {
		switch (browserName.toLowerCase()) {
		case "ie":
			return createIE(browserVersion);
		case "chrome":
			return createChrome(browserVersion);

		case "firefox":
			return createFirefox(browserVersion);

		default:
			return createChrome(browserVersion);
		}

	}


	private static WebDriver createFirefox(String browserVersion)  {
		if (browserVersion != null) 
			WebDriverManager.firefoxdriver().browserVersion(browserVersion).setup();
		else
			WebDriverManager.firefoxdriver().setup();
		
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--start-maximized");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--ignore-certificate-errors");
		options.setCapability("acceptInsecureCerts", true);
		return new FirefoxDriver(options);
	}

	private static WebDriver createChrome(String browserVersion) {

		if (browserVersion != null) 
			WebDriverManager.chromedriver().browserVersion(browserVersion).setup();
		else
			WebDriverManager.chromedriver().setup();
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--ignore-certificate-errors");
		options.setCapability("acceptInsecureCerts", true);

		return new ChromeDriver(options);
	}



	private static WebDriver createIE(String browserVersion) {
		if (browserVersion != null) 
			WebDriverManager.iedriver().arch32().browserVersion(browserVersion).setup();
		else
			WebDriverManager.iedriver().arch32().setup();
		
		DesiredCapabilities capabilities = null;
		WebDriver driver = new InternetExplorerDriver();
		capabilities = new DesiredCapabilities();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilities.setCapability("requireWindowFocus", true);
		capabilities.setCapability("nativeEvents", false);
		capabilities.setCapability("unexpectedAlertBehaviour", "accept");
		capabilities.setCapability("ignoreProtectedModeSetting", true);
		capabilities.setCapability("disable-popup-blocking", true);
		capabilities.setCapability("enablePersistentHover", true);
		capabilities.setCapability("ignoreZoomSetting", true);
		capabilities.setCapability("EnableNativeEvents", false);
		capabilities.setCapability("ie.ensureCleanSession", true);
		capabilities.setCapability("allow-blocked-content", true);
		capabilities.setCapability("-ignore-certificate-errors", true);
		capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
		return driver;
	}

	
	public WebDriver startRemoteBrowser(String browswerVersion) {

		DesiredCapabilities capabilities = null;
		WebDriver driver = null;
		try {
			if (System.getProperty("browserType").equalsIgnoreCase("Chrome")) {
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_settings.popups", 0);
				prefs.put("download.default_directory", rc.getProperty("downloadFolderPath"));
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability("applicationCacheEnabled", false);

				// this is used to start the test to specific chrome browser in the selenium hub
				capabilities.setCapability("applicationName",
						rc.getProperty("ChromeBrowserNameInSeleniumServer"));
				LoggingPreferences loggingPreferences = new LoggingPreferences();
				loggingPreferences.enable(LogType.BROWSER, Level.SEVERE);
				capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
				System.out.println("Starting 'Remote Chrome' browser !");
			}else if (System.getProperty("browserType").equalsIgnoreCase("IE")) {
				capabilities = DesiredCapabilities.internetExplorer();
				// this is used to start the test to specific IE browser in the selenium hub
				capabilities.setCapability("applicationName",
						rc.getProperty("IEBrowserNameInSeleniumServer"));
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				capabilities.setCapability("requireWindowFocus", true);
				capabilities.setCapability("nativeEvents", false);
				capabilities.setCapability("unexpectedAlertBehaviour", "accept");
				capabilities.setCapability("ignoreProtectedModeSetting", true);
				capabilities.setCapability("disable-popup-blocking", true);
				capabilities.setCapability("enablePersistentHover", true);
				capabilities.setCapability("ignoreZoomSetting", true);
				capabilities.setCapability("EnableNativeEvents", false);
				capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
				String path = "\\\\rft01\\C$\\Users\\ajalal\\Desktop\\downloadedFoderForTemporaryFiles\\";
				String[] cmd1 = {"REG ADD \"HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\Main\" /F /V \"Default Download Directory\" /T REG_SZ /D "
						+ path};
				try {
					Runtime.getRuntime().exec(cmd1);//exec(cmd1);
				} catch (Exception e) {
					System.out.println("Coulnd't change the registry for default directory for IE");
				}
				System.out.println("Starting 'Remote ie' browser !");

			} else if (System.getProperty("browserType").equalsIgnoreCase("Firefox")) {
				capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability("applicationName",
						rc.getProperty("FirefoxBrowserNameInSeleniumServer"));// this is start the test to
				System.out.println("Starting 'Remote firefox' browser !");
			} else {
				capabilities = DesiredCapabilities.chrome();
				LoggingPreferences loggingPreferences = new LoggingPreferences();
				capabilities.setCapability("applicationName", "chromeInSeleniumServerRTF01");// this is start the test
																								// to specific chrome in
																								// the selenium hub
				loggingPreferences.enable(LogType.BROWSER, Level.SEVERE);
				capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
				capabilities.setCapability("applicationCacheEnabled", false);
				System.out.println(
						"User selected remote browser: " + System.getProperty("browserType").equalsIgnoreCase("Chrome")
								+ ", Starting default remote browser - Chrome!");

			}
			driver = new RemoteWebDriver(capabilities);

			// this code the local directory to remote directory
			((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		
		}
		return driver;
	}


	private static void windowManagement(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
}
