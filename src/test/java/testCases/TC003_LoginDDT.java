package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import TestBase.BaseClass;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups="DataDriven") //getting data provider from different class
	public void verify_loginDDT(String email, String pwd, String exp) throws InterruptedException
	{
		
		logger.info("***** Stating Login**********");
		
		try
		{
			
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp = new LoginPage(driver);
		lp.setmail(email);	
		lp.setpassword(pwd);
		
		lp.clickLogin();
		
		
		MyAccountPage myAcc = new MyAccountPage(driver);
		boolean check = myAcc.ismyAccExist();
		
		
		
		
		
		if(exp.equalsIgnoreCase("Valid"))
		{
			if(check==true)
			{
				myAcc.clickLogOut();
				Assert.assertTrue(true);
				
			}
			
			else 
			{
				Assert.assertTrue(false);
			}
		}
		
		
		
		if(exp.equalsIgnoreCase("Invalid"))
		{
			if(check==true)
			{
				myAcc.clickLogOut();
				Assert.assertTrue(false);
				
			}
			
			else
			{
				Assert.assertTrue(true);
			}
			
		}
	}
		catch(Exception e)
		{
			Assert.fail();
		}

		logger.info("*****Login Finished**********");	
		
		
	}
	
	
	
	
	
	
	
	
	
}
