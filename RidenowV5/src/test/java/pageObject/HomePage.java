package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='undefined container']/div/span[text()='Endless adventures']")
	WebElement title;
	
	
	public String getHomeTitle() {
		return title.getText();
	}
	public HomePage goToHomePage() {
		driver.get(rc.getProperty("baseULR"));
		Assert.assertEquals(getHomeTitle(), "Endless adventures");
		return this;
	}
	
	@FindBy(xpath ="//input[@id='search']")
	WebElement searchBox;
	
	public HomePage searchVehicals(String text) {
		searchBox.sendKeys(text);
		return this;
	}
	
	@FindBy(xpath ="//input[@id='search']/following-sibling::button")
	WebElement searchButton;
	public HomePage clicOnSearchButton() {
		searchButton.click();
		return this;
	}
	
	@FindBy(xpath="//div[@class='container']//span[text()='honda']")
	WebElement rearchResult;
	public String getSearchResult() {
		return rearchResult.getText();
	}
}
