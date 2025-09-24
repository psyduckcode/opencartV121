package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import TestBase.BaseClass;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC002_AccountLogin extends BaseClass
{
	
	@Test(groups={"Sanity","Master"})
	public void verifyLogin()
	{
		
	logger.info("****Starting Login Test******");	
	
	HomePage hp = new HomePage(driver);
	hp.clickMyAccount();
	hp.clickLogin();
	
	LoginPage lp = new LoginPage(driver);
	lp.setmail(p.getProperty("email"));	
	lp.setpassword(p.getProperty("password"));
	
	lp.clickLogin();
	
	
	MyAccountPage myAcc = new MyAccountPage(driver);
	Boolean check = myAcc.ismyAccExist();
	Assert.assertEquals(check, true, "Login Failed");
	
	logger.info("**** Login Finished*******");
	
	}
	
	
	
	

}
