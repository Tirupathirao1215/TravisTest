
package com.automation.accelerators;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.support.ExcelReader;

public class ActionEngine_Delete extends TestEngineWeb_Delete {
	private static final Logger LOG = Logger.getLogger(ActionEngine.class);
	public static ExcelReader excelreader=new ExcelReader(System.getProperty("user.dir")+"/TestData/TestData.xls", "Test_Data");	
	private final String msgClickSuccess = "Successfully Clicked On ";
	private final String msgClickFailure = "Unable To Click On ";
	private final String msgTypeSuccess = "Successfully Typed On ";
	private final String msgTypeFailure = "Unable To Type On ";
	private final String msgIsElementFoundSuccess = "Successfully Found Element ";
	private final String msgIsElementFoundFailure = "Unable To Found Element ";
	
	/**
	 * 
	 * @param locator
	 * @param locatorName
	 * @return
	 * @throws Throwable
	 */
	public boolean selectByIndex(By locator, int index,
			String locatorName) throws Throwable {
		boolean flag = false;
		try {
			Select s = new Select(Driver.findElement(locator));
			s.selectByIndex(index);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				this.reporter.failureReport("Select", "Option at index " + index
						+ " is Not Select from the DropDown" + locatorName, appiumDriver);
				

			} else if (flag) {
				this.reporter.SuccessReport("Select", "Option at index " + index
						+ "is Selected from the DropDown" + locatorName);
				
			}
		}
	}
	public boolean click(By locator, String locatorName) throws Throwable
	{
		boolean status = false;
		try
		{
		this.Driver.findElement(locator).click();
		this.reporter.SuccessReport("Click" , this.msgClickSuccess + locatorName);
		status = true;
		}
		catch(Exception e)
		{
			status = false;
			LOG.info(e.getMessage());
			//this.reporter.failureReport("Click", this.msgClickFailure + locatorName, this.Driver);
			
		}
		return status;
		
	}
	
	public boolean isElementPresent(By by, String locatorName,boolean expected) throws Throwable
	{
		boolean status = false;
		try
		{
			this.Driver.findElement(by);
			this.reporter.SuccessReport("isElementPresent" , this.msgIsElementFoundSuccess + locatorName);
			status = true;
		}
		catch(Exception e)
		{
			status = false;
			LOG.info(e.getMessage());
			if(expected == status)
			{
				this.reporter.SuccessReport("isElementPresent", "isElementPresent");
			}
			else
			{
				//this.reporter.failureReport("isElementPresent", this.msgIsElementFoundFailure + locatorName, this.Driver);
			}
		}
		return status;
	}
	public boolean VerifyElementPresent(By by, String locatorName,boolean expected) throws Throwable
	{
		boolean status = false;
		try
		{
			this.Driver.findElement(by);
			this.reporter.SuccessReport("VerifyElementPresent" , this.msgIsElementFoundSuccess + locatorName);
			status = true;
		}
		catch(Exception e)
		{
			status = false;
			LOG.info(e.getMessage());
			/*if(expected == status)
			{
				this.reporter.SuccessReport("isElementPresent", "isElementPresent");
			}
			else
			{
				this.reporter.failureReport("isElementPresent", this.msgIsElementFoundFailure + locatorName, this.Driver);
			}*/
		}
		return status;
	}
	// Sending Test data to Test field
	public boolean type(By locator, String testdata, String locatorName) throws Throwable
	{
		boolean status = false;
		try
		{
			this.Driver.findElement(locator).clear();
			this.Driver.findElement(locator).sendKeys(testdata);
			this.reporter.SuccessReport("type" , this.msgTypeSuccess + locatorName);
			status = true;
		}
		catch(Exception e)
		{
			status = false;
			LOG.info(e.getMessage());
		//	this.reporter.failureReport("type", this.msgTypeFailure + locatorName, this.Driver);
		}
		
		return status;
	}
	
	/**
	 * Moves the mouse to the middle of the element. The element is scrolled
	 * into view and its location is calculated using getBoundingClientRect.
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:link,menus etc..)
	 * 
	 */
	public boolean mouseover(By locator, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			WebElement mo = this.Driver.findElement(locator);
			new Actions(this.Driver).moveToElement(mo).build().perform();
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag == false) {
//				this.reporter.failureReport("MouseOver",
//						"MouseOver action is not perform on" + locatorName,this.Driver);

			} else if (flag == true) {

				this.reporter.SuccessReport("MouseOver",
						"MouserOver Action is Done on" + locatorName);
			}
		}
	}

	public  boolean JSClick(By locator, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			WebElement element = this.Driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) this.Driver;
			executor.executeScript("arguments[0].click();", element);
			//driver.executeAsyncScript("arguments[0].click();", element);

			flag = true;

		}

		catch (Exception e) {


		} finally {
			if (flag == false) {
				this.reporter.failureReport("MouseOver",
						"MouseOver action is not perform on" + locatorName, appiumDriver);
				return flag;
			} else if (flag == true) {
				this.reporter.SuccessReport("MouseOver",
						"MouserOver Action is Done on" + locatorName);
				return flag;
			}
		}
		return flag;
	}
	
	public boolean waitForElementPresent(By by, String locator, int secs)
			throws Throwable {
		boolean status = false;
		
		try {
			
			WebDriverWait wait = new WebDriverWait(this.Driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			for (int i = 0; i < secs/2; i++)
			{
				List<WebElement> elements = this.Driver.findElements(by);
				if (elements.size()>0)
				{
					status = true;
					return status;

				} 
				else
				{
					this.Driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
				}
			}
	       
		
		} 
		catch (Exception e) {
			
			return status;
		} 
	
		return status;
		
	}
}
