package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestBase.BaseClass;

public class ExtentReportManager implements ITestListener {

	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String repName;
	
	//Populate the time and date in report
	
	/*
	 * SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); Date dt =
	 * new Date(); String currentdatetimestamp = df.format(dt);
	 */	
	
	
	public void onStart(ITestContext testContext)
	{
		//generate the current time stamp
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		//attach timestamp to test report name
		
		repName = "Test-Report"+ timeStamp+ ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName); //Location of File
		
		sparkReporter.config().setDocumentTitle("OpenCart Automation Report");
		sparkReporter.config().setReportName("OpenCart Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		//Project Specific Detail
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("SubModule", "Customer");
		
		//Return current user of the system
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		
		extent.setSystemInfo("Enviorment", "QA");
		
		
		//Getting OS Name from XML File
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("OS", os);
		
		//Getting Browser Name from XML File
				String browser = testContext.getCurrentXmlTest().getParameter("browser");
				extent.setSystemInfo("Browser", browser);
		
				
			List<String>includedGroup = testContext.getCurrentXmlTest().getIncludedGroups();
			if(!includedGroup.isEmpty())
				{
					extent.setSystemInfo("Groups", includedGroup.toString());	
				}

	}
	
public void onTestSuccess(ITestResult result)	
{
	
	test = extent.createTest(result.getTestClass().getName());
	test.assignCategory(result.getMethod().getGroups());
	test.log(Status.PASS, result.getName()+"Successful Execution");
	
}
	
public void onTestFailure(ITestResult result)	
{
	
	test = extent.createTest(result.getTestClass().getName());
	test.assignCategory(result.getMethod().getGroups());
	test.log(Status.FAIL, result.getName()+"Test Failed");
	test.log(Status.INFO, result.getThrowable()+"Reason");
	try {
		String imgPath = new BaseClass().captureScreen(result.getName());
		test.addScreenCaptureFromPath(imgPath);	
	} catch (IOException e1) {
		e1.printStackTrace();
	}

 }	

public void onTestSkipped(ITestResult result) {
	test = extent.createTest(result.getTestClass().getName());
	test.assignCategory(result.getMethod().getGroups());
	test.log(Status.SKIP, result.getName()+" got skipped");
	test.log(Status.INFO, result.getThrowable().getMessage());
}

public void onFinish(ITestContext testContext)
	
	
	{
		
		extent.flush();
		
		//Capturing path of report
		String pathofExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		
		//Creating File
		File extentReport = new File(pathofExtentReport);
		try {
			
			//Open the Report in the Browser Automatically
			Desktop.getDesktop().browse(extentReport.toURI());	
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
		
		
		

		
	}
	
	
}	

