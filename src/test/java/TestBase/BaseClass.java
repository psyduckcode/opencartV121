package TestBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass 

{

	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	
	@SuppressWarnings("deprecation")
	@BeforeClass(groups = {"Sanity","Regression","Master",})
	@Parameters({"os","browser"})
	public void setup(String os , String br) throws IOException
	{
		
		FileReader fr = new FileReader("./src//test//resources//config.properties");
		p = new Properties();		
		p.load(fr);
		
		
		
	logger = LogManager.getLogger(this.getClass());
		
	
	if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
	{
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		//os
		if(os.equalsIgnoreCase("windows"))
		{
			capabilities.setPlatform(Platform.WIN10);
		}
		
		else if(os.equalsIgnoreCase("linux"))
		{
			capabilities.setPlatform(Platform.LINUX);
		}
		
		else
		{
			System.out.println("No Matching os");
			return ;
		}
		//browser
		switch(br.toLowerCase())
		{
		case "chrome" : capabilities.setBrowserName("chrome");
		break;
		case "edge" : capabilities.setBrowserName("MicrosoftEdge");
		break;
		default : System.out.println("No Matching Browser");
		}
		
		driver = new  RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);	
	}

	
	
	if(p.getProperty("execution_env").equalsIgnoreCase("local"))
	{
		switch(br.toLowerCase())
		{
		case "chrome" : driver = new ChromeDriver();
		break;
		
		case "edge" : System.setProperty("webdriver.edge.driver", "C:\\driver\\msedgedriver.exe");
		driver =  new EdgeDriver();
		break;
		default : System.out.println("Invalid Browser");
		return; //Return will exit from Execution 	
	}
	
}
	
	
		//driver=new ChromeDriver();
	
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("appurl"));  // reading url from properties file
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups = {"Sanity","Regression","Master",})
	public void tearDown()
	{
		   if (driver != null) {
		        driver.quit(); // closes all windows & ends WebDriver session
		    }
	}
	

	public String randomeString()
	{
		@SuppressWarnings("deprecation")
		String generatedString=RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	public String randomeNumber()
	{
		@SuppressWarnings("deprecation")
		String generatedString=RandomStringUtils.randomNumeric(10);
		return generatedString;
	}
	
	public String randomAlphaNumeric()
	{
		@SuppressWarnings("deprecation")
		String str=RandomStringUtils.randomAlphabetic(3);
		@SuppressWarnings("deprecation")
		String num=RandomStringUtils.randomNumeric(3);
		
		return (str+"@"+num);
	}
	
	
	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
			
		return targetFilePath;

	
	
	
	}
	
	
}
