package testCase;


import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import reports.Reporter;

@Listeners(Reporter.class)
public class HomeTest extends BaseTest{
	
	@Test(priority=1)
	public void verifyHomePage() {
		String temp = homePage.getHomeTitle();
		//System.out.println(">>>>"+temp);
//		Assert.assertEquals(temp, "Endless adventures123");
		if (temp.equalsIgnoreCase("Endless adventures123")) {
			Reporter.test.pass("Home Page title was verified.");
			return;
		}
		
		Reporter.test.fail("Home Page Title was not verify due: expected: Endless adventures123 but found" + temp);
		Assert.fail();
	}
	

	@Test (priority=3)
	public void searchVehicle() {
//		String temp = homePage.searchVehicals("honda")
//		.clicOnSearchButton()
//		.getSearchResult();
		
		homePage.searchVehicals("honda");
		sleep(1);
		homePage.clicOnSearchButton();
		sleep(1);
		String result = homePage.getSearchResult();
		sleep(1);
		
		if (result.equalsIgnoreCase("honda")) {
			Reporter.test.pass("Search functionality is working as expected.");
			return;
		}
		
		//Fail test case on the report
		Reporter.test.fail("Search functionality is not working as expected.");
		
		//fail on console
		Assert.fail();
		
		//Fail or Pass
		
	}
	
	@Test (priority=2)
	public void logOut() {
		logger.error("ERRRRRRRRRRRRRRRRRRR");
		Assert.assertEquals(true, true);
		
	}
	
	@Test (priority=4)
	public void test1() {
		logger.error("ERRRRRRRRRRRRRRRRRRR");
		Assert.assertEquals(true, true);
		
	}
	
	@Test (priority=2)
	public void test2() {
		logger.error("ERRRRRRRRRRRRRRRRRRR");
		Assert.assertEquals(true, true);
		
	}
	@Test (priority=2)
	public void test3() {
		logger.error("ERRRRRRRRRRRRRRRRRRR");
		Assert.assertEquals(true, true);
		
	}
	@Test
	public void goInverntory() {
		System.out.println("Testing.....");
		Assert.fail();
	}
}
