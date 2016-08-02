package com.automation.accelerators;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.automation.report.CReporter;
import com.automation.report.ReporterConstants;
import com.automation.support.ExcelReader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

/**
 * 
 * @author in01518
 *
 */
public class TestEngine {
	public static final Logger LOG = Logger.getLogger(TestEngine.class);
	protected AppiumDriver appiumDriver = null;
	protected WebDriver Driver = null;
	protected CReporter reporter = null;
	protected int ExecutionID;
	protected int TestcaseID;
	public DesiredCapabilities capabilitiesForAppium = new DesiredCapabilities();

	@BeforeTest
	@Parameters({ "automationName", "rotatable", "safariAllowPopups",
			"safariIgnoreFraudWarning", "apppackage", "appactivity", "app","browserName",
			"platformName", "platformVersion", "deviceName", "deviceID",
			"appiumUrl" })
	public void beforeTest(@Optional String automationName,
			@Optional String rotatable, @Optional String safariAllowPopups,
			@Optional String safariIgnoreFraudWarning,
			@Optional String apppackage, @Optional String appactivity,
			@Optional String app,@Optional String browserName, String platformName, String platformVersion,
			String deviceName, @Optional String deviceID, String appiumUrl)
			throws MalformedURLException {
		System.out.println("Testing started");
		capabilitiesForAppium.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
		capabilitiesForAppium.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300000");
		
		//Define iOS Driver using Appium
		if (platformName.equals("iOS")) {
			capabilitiesForAppium.setCapability(MobileCapabilityType.ROTATABLE,rotatable.equalsIgnoreCase("true") == true ? true : false);
			capabilitiesForAppium.setCapability("safariAllowPopups",
					safariAllowPopups.equalsIgnoreCase("true") == true ? true: false);
			capabilitiesForAppium.setCapability("safariIgnoreFraudWarning",safariIgnoreFraudWarning.equalsIgnoreCase("true") == true ? true: false);
			capabilitiesForAppium.setCapability("rotatable", true);
			capabilitiesForAppium.setCapability(MobileCapabilityType.APP, app);
			capabilitiesForAppium.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
			capabilitiesForAppium.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			capabilitiesForAppium.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			capabilitiesForAppium.setCapability("udid", deviceID);
			appiumDriver = new IOSDriver(new URL(appiumUrl),capabilitiesForAppium);
			reporter = CReporter.getCReporter(deviceName, platformName,platformVersion, true);

		}
		
		//Define Android Driver using Appium
		if (platformName.equalsIgnoreCase("android")) {
			
			//capabilitiesForAppium.setCapability(MobileCapabilityType.APP_PACKAGE, apppackage);
			//capabilitiesForAppium.setCapability(MobileCapabilityType.APP_ACTIVITY, appactivity);
			capabilitiesForAppium.setCapability(MobileCapabilityType.APP, app);
			capabilitiesForAppium.setBrowserName(browserName);
			capabilitiesForAppium.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
			capabilitiesForAppium.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			capabilitiesForAppium.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			reporter = CReporter.getCReporter(deviceName, platformName,platformVersion, true);
			capabilitiesForAppium.setCapability("udid", deviceID);
			appiumDriver = new AndroidDriver(new URL(appiumUrl),capabilitiesForAppium);
		}

		appiumDriver.get(ReporterConstants.APP_BASE_URL);
		appiumDriver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
		reporter.calculateSuiteStartTime();

	}

	@AfterTest
	@Parameters({ "deviceName", "platformName", "platformVersion" })
	public void afterTest(String deviceName, String platformName,
			String platformVersion) throws Exception {

		// get browser info

		// reporter = CReporter.getCReporter(deviceName, platformName,
		// platformVersion, true);
		/*
		 * appiumDriver.close(); 
		 */
		reporter.calculateSuiteExecutionTime();
		reporter.createHtmlSummaryReport(true);
		reporter.closeSummaryReport();

	}

	@BeforeMethod
	@Parameters({ "deviceName", "platformName", "platformVersion" })
	public void beforeMethod(Method method, String deviceName,
			String platformName, String platformVersion) {
		// get browser info

		// reporter = CReporter.getCReporter(deviceName, platformName,
		// platformVersion, true);
		reporter.initTestCase(
				this.getClass()
						.getName()
						.substring(0,
								this.getClass().getName().lastIndexOf(".")),
				method.getName(), null, true);
	}

	@AfterMethod
	@Parameters({ "deviceName", "platformName", "platformVersion" })
	public void afterMethod(Method method, String deviceName,
			String platformName, String platformVersion) throws IOException {
		// get browser info

		// reporter = CReporter.getCReporter(deviceName, platformName,
		// platformVersion, true);
		reporter.calculateTestCaseExecutionTime();
		reporter.closeDetailedReport();
		reporter.updateTestCaseStatus();
	}

}
