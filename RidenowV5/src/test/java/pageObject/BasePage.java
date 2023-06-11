package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ReadConfig;

public class BasePage {
	protected WebDriver driver;
	ReadConfig rc = ReadConfig.getInstance();
	private int timeOut = Integer.parseInt(
			rc.getProperty("timeOut"));
	
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo(String url) {
        driver.get(url);
    }
    public WebElement waitForVisibility(WebElement element) {
    	return waitForVisibility(element, timeOut);
    }
    public WebElement waitForVisibility(WebElement element, int timeoutInSeconds) {
    	WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    public WebElement waitForClickable(WebElement element) {
    	return waitForClickable(element, timeOut);
    }
    
    public WebElement waitForClickable(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}

