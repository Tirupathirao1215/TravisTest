package com.appium.project.businesslogic;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.appium.samsclub.page.SamsClubHomePage;
import com.automation.accelerators.ActionEngine;
import com.automation.support.ExcelReader;

public class Samsclub extends ActionEngine{
	public static final int LOWWAITTIME = 2000;
	public static final int WAITTIME = 50000;
	public static final int LONGWAITTIME = 200000;
	public String gErrMsg = "";
	public String dayscreen;
	public ExcelReader excelreader = new ExcelReader(
			System.getProperty("user.dir") + "/TestData/TestData.xls",
			"Test_Data");
	public ITestContext testcontext;

	public void setTestContext(ITestContext testContext) {
		this.testcontext = testContext;
	}

	public String getTitle() {
		return this.Driver.getTitle();
	}
	
	//@Test
public boolean selectItem() throws Throwable{
	try{
		SamsClubHomePage.Users_Page(this.testcontext);
	String selectItem=appiumDriver.findElement(SamsClubHomePage.Buy_Now_product).getText();
	click(SamsClubHomePage.Buy_Now_product, "select product");
	click(SamsClubHomePage.ship_this_Item, "Ship this Item");
	waitForElementPresent(SamsClubHomePage.Procced_To_cart, "", 5000);
	click(SamsClubHomePage.Procced_To_cart, "Proceed to cart");
	
	if(appiumDriver.findElement(SamsClubHomePage.verify_cart_item).getText().equals(selectItem)){
		reporter.successwithscreenshot("Verifying Day screen",
				"verifying whether the screeen is navigated ",
				appiumDriver);
		
	}
	waitForElementPresent(SamsClubHomePage.removeItem, "", 5000);
	click(SamsClubHomePage.removeItem, "remove seleted Item");
	 click(SamsClubHomePage.removeBtn, "remove button");
	 return true;
}
	 catch (Exception e) {
			e.printStackTrace();
			gErrMsg = "Unable to select the Iteams";
			return false;
	 }
	
}
	@Test
	public boolean setStore() throws Throwable{
		try{
		SamsClubHomePage.Users_Page(this.testcontext);
		type(SamsClubHomePage.searchEditbox,"coke","Search Edit box");
		
		click(SamsClubHomePage.searchBtn, "Search Button");
		waitForElementPresent(SamsClubHomePage.selectClublink, "", 5000);
		
		click(SamsClubHomePage.selectClublink, "Select store button");
		waitForElementPresent(SamsClubHomePage.searchStoreEditbox, "", 5000);
		type(SamsClubHomePage.searchStoreEditbox,"01851","SearchStore Edit box");
		click(SamsClubHomePage.searchBtnGo, "store button GO");
		click(SamsClubHomePage.selectClub, "Select button of club");
		
		click(SamsClubHomePage.pickupBtn,"Pick up Button");
		if(isElementPresent(SamsClubHomePage.qtyErrortxt, "Low inventory warning", false)){
			System.out.println("Inventory is low for the item in respectivce club ");
			
		}else{
			waitForElementPresent(SamsClubHomePage.Procced_To_cart, "", 5000);
			click(SamsClubHomePage.Procced_To_cart, "Proceed to cart");
			
			if(appiumDriver.findElement(SamsClubHomePage.verify_cart_item).getText().contains("coke")){
				reporter.successwithscreenshot("Verifying Day screen",
						"verifying whether the screeen is navigated ",
						appiumDriver);
				
			}
			waitForElementPresent(SamsClubHomePage.removeItem, "", 5000);
			click(SamsClubHomePage.removeItem, "remove seleted Item");
			 click(SamsClubHomePage.removeBtn, "remove button");
		}
		
		
		
		return true;
		}catch (Exception e) {
			
			e.printStackTrace();
			gErrMsg = "Unable to sign in";
			return false;
		}
		
	}

}
