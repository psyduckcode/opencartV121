package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage
{
	public HomePage(WebDriver driver)
	{
		  super(driver);
	}
	
	//Click My Account
	@FindBy(xpath="//span[normalize-space()='My Account']")     
	WebElement lnkMyaccount;
	
	//Click Registration
	@FindBy(xpath="//a[normalize-space()='Register']")    
	WebElement lnkRegister;
	
	//Click Login
	@FindBy(xpath="//ul//a[normalize-space()='Login']")
	WebElement login;
	


	public void clickMyAccount()
	{
		lnkMyaccount.click();
	}

	public void clickRegister()
	{
		lnkRegister.click();
	}
	
	public void clickLogin()
	{
		login.click();
	}

}

	

