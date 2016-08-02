package com.automation.accelerators;

import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.automation.report.CReporter;
import com.automation.report.ReporterConstants;
import com.automation.support.ExcelReader;
import com.automation.support.MyListener;
/**
 * 
 * @author in01518
 *
 */
/**
 * 
 * @author in01518
 *
 */
public class TestEngineWeb_Delete {
	public static final Logger LOG = Logger.getLogger(TestEngineWeb_Delete.class);
	protected AppiumDriver appiumDriver = null;
	protected WebDriver WebDriver = null;
	public EventFiringWebDriver Driver=null;
	protected CReporter reporter = null;
	
	/*cloud platform*/
	public String browser = null;
    public String version = null;
    public String platform = null;
    public String environment = null;
    public String localBaseUrl = null;
    public String cloudBaseUrl = null;
    public String userName = null;
    public String accessKey = null;
    public String cloudImplicitWait = null;
    public String cloudPageLoadTimeOut = null;
    public String updateJira = null;
    public String buildNumber = "";
    public String jobName = "";
    public String executedFrom = null;
	private String platformName=null;  
    //public static ExcelReader xlsrdr = new ExcelReader("C:\\Automationpractice\\ZYNX-Mobile-Parallel\\TestData\\TestData.xls","Test_Data");
    //public static ExcelReader excelreader=new ExcelReader(System.getProperty("user.dir")+"/TestData/TestData.xls", "Test_Data");
	/**/
 
	//private DesiredCapabilities capabilitiesForAppium = new DesiredCapabilities();
		
	@BeforeTest
	@Parameters({"automationName","browser","browserVersion","environment","platformName"})
	public void beforeTest(String automationName, String browser, String browserVersion,String environment,String platformName) throws IOException, InterruptedException
	{
		/*PropertyConfigurator.configure(System.getProperty("user.dir")+"\\Log.properties");
		   System.out.println(System.getProperty("user.dir")+"\\Log.properties");*/
			
			/*get configuration */
		this.browser = browser;
        this.version = browserVersion;
        this.platformName = platformName;
        this.environment = environment;
        this.localBaseUrl = ReporterConstants.APP_BASE_URL;
        this.userName = ReporterConstants.SAUCELAB_USERNAME;
        this.accessKey = ReporterConstants.SAUCELAB_ACCESSKEY;
        this.executedFrom = System.getenv("COMPUTERNAME");
        this.cloudImplicitWait = ReporterConstants.CLOUD_IMPLICIT_WAIT;
        this.cloudPageLoadTimeOut = ReporterConstants.CLOUD_PAGELOAD_TIMEOUT;        
        this.updateJira = "";
		
			
		
			if(environment.equalsIgnoreCase("local"))
			{
				this.setWebDriverForLocal(browser);
			}
			if(environment.equalsIgnoreCase("cloudSauceLabs"))
			{
				
	            this.setRemoteWebDriverForCloudSauceLabs();	           
			}
			if(environment.equalsIgnoreCase("cloudSauceLabsJenkins"))
			{
				this.updateConfigurationForCloudSauceLabsJenkins();
				/*set remoteWebDriver for cloudsaucelabs*/        
	            
	            this.setRemoteWebDriverForCloudSauceLabs();	           
			}
			
			
			if (environment.equalsIgnoreCase("cloudBrowserStackJenkins"))
            {
                /*TBD: Not Implemented For Running Using Jenkins*/
                this.updateConfigurationForCloudBrowserStackJenkins();
            }
			reporter = CReporter.getCReporter(browser, browserVersion , environment, true);
			
			this.Driver = new EventFiringWebDriver(this.WebDriver);
			MyListener myListener = new MyListener();
			this.Driver.register(myListener);
			Driver.get(ReporterConstants.APP_BASE_URL);
			Driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			//Driver.manage().window().maximize();
			reporter.calculateSuiteStartTime();
	}
		

	
	@AfterTest
	
	public void afterTest() throws Exception
	{
	
	
			Driver.close();
			Driver.quit();
			reporter.calculateSuiteExecutionTime();
			//reporter.createHtmlSummaryReport(ReporterConstants.APP_BASE_URL,true);
			reporter.createHtmlSummaryReport(true);
			reporter.closeSummaryReport();
	
		
	}
	
	@BeforeMethod
	
	public void beforeMethod(Method method)
	{
		//get browser info		
		
		//reporter = CReporter.getCReporter(deviceName, platformName, platformVersion, true);	
		reporter.initTestCase(this.getClass().getName().substring(0,this.getClass().getName().lastIndexOf(".")), method.getName(), null, true);
	}
	
	@AfterMethod
	
	public void afterMethod() throws IOException
	{
		//get browser info
				
		//reporter = CReporter.getCReporter(deviceName, platformName, platformVersion, true);				
		reporter.calculateTestCaseExecutionTime();		
		reporter.closeDetailedReport();		
		reporter.updateTestCaseStatus();
	}
	
	private void setWebDriverForLocal(String browser) throws IOException, InterruptedException
	{
		switch(browser)
		{
		case "firefox":
			this.WebDriver = new FirefoxDriver();
			break;
		case "ie":
			
			DesiredCapabilities capab = DesiredCapabilities.internetExplorer();
	        capab.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
	        capab.internetExplorer().setCapability("ignoreProtectedModeSettings", true);
	        //capab.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, INIT_PAGE);
	        
	        File file = new File("Drivers\\IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver",file.getAbsolutePath());
			capab.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	        capab.setJavascriptEnabled(true);
	        capab.setCapability("requireWindowFocus", true);
	        capab.setCapability("enablePersistentHover", false);
	        
			this.WebDriver = new InternetExplorerDriver(capab);
			/* Process p = Runtime
						.getRuntime()
						.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
				p.waitFor();*/
				Thread.sleep(1000);
		case "chrome":
			System.setProperty("webdriver.chrome.driver",
					"Drivers\\chromedriver.exe");
			
		    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		    ChromeOptions options = new ChromeOptions();
		    options.addArguments("test-type");
		    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		    this.WebDriver = new ChromeDriver(capabilities);
			
			break;
		}
		
	
	
		
	}
	
	private void setRemoteWebDriverForCloudSauceLabs() throws MalformedURLException
    {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, this.browser);
        desiredCapabilities.setCapability(CapabilityType.VERSION, this.version);
        desiredCapabilities.setCapability(CapabilityType.PLATFORM, this.platform);
        desiredCapabilities.setCapability("username", this.userName);
        desiredCapabilities.setCapability("accessKey", this.accessKey);
        desiredCapabilities.setCapability("name", this.executedFrom + " - " + this.jobName + " - " + this.buildNumber + " - ");
        URL commandExecutorUri = new URL("http://ondemand.saucelabs.com/wd/hub");
        this.WebDriver = new RemoteWebDriver(commandExecutorUri, desiredCapabilities);
    }
	
	// Integration with Jenkins and SauceLabs
	private void updateConfigurationForCloudSauceLabsJenkins()
    {
        this.browser = System.getenv("SELENIUM_BROWSER");
        this.version = System.getenv("SELENIUM_VERSION");
        this.platform = System.getenv("SELENIUM_PLATFORM");
        this.userName = System.getenv("SAUCE_USER_NAME");
        this.accessKey = System.getenv("SAUCE_API_KEY");
        this.buildNumber = System.getenv("BUILD_NUMBER");
        this.jobName = System.getenv("JOB_NAME");

        /*For Debug Purpose*/
        LOG.info("Debug: browser = " + this.browser);
        LOG.info("Debug: version = " + this.version);
        LOG.info("Debug: platform = " + this.platform);
        LOG.info("Debug: userName = " + this.userName);
        LOG.info("Debug: accessKey = " + this.accessKey);
        LOG.info("Debug: executedFrom = " + this.executedFrom);
        LOG.info("Debug: BUILD_NUMBER = " + this.buildNumber);
        LOG.info("Debug: jobName = " + this.jobName);
    }
	
	/*TBD: Not Implemented For Running Using Jenkins*/
    private void updateConfigurationForCloudBrowserStackJenkins()
    {

    }
	
}
