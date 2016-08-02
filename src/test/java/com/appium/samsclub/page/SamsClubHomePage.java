package com.appium.samsclub.page;

import org.openqa.selenium.By;
import org.testng.ITestContext;

import com.automation.accelerators.ActionEngine;

public class SamsClubHomePage extends ActionEngine{
	public static By Verifyscreen;
	
 public static By Buy_Now_product;
 public static By ship_this_Item;
 public static By Procced_To_cart;
 public static By verify_cart_item;
 public static By removeItem;
 public static By removeBtn;
 public static By selectClublink;
 public static By searchEditbox;
 public static By searchBtn;
 public static By searchStoreEditbox;
 public static By searchBtnGo,selectBtn;
 public static By selectClub,pickupBtn;
 public static By qtyErrortxt;
 
 
 private ITestContext testcontext;
 public void setTestContext(ITestContext testContext) {
		this.testcontext = testContext;
	}

	public String getTitle() {
		return this.Driver.getTitle();
	}
 

	public static void Users_Page(ITestContext testContext) throws Throwable

	{
		/*selectName2=excelreader.getData("TC_001", "SelectMedication2");
		selectName1=excelreader.getData("TC_001", "SelectMedication1");*/

		String platformName = testContext.getCurrentXmlTest().getParameter("platformName");
		//String platformName ="Android";
		if (platformName.equalsIgnoreCase("Android")) {
			Verifyscreen = By.name("Sam'sClub");
			Buy_Now_product=By.xpath("//*[@id="+'"'+"TRM_Content"+'"'+"]/div/div[1]/a[2]/img");
			ship_this_Item=By.xpath("//*[@id='content']/product-page/div[1]/div/add-to-cart/div/money-box/div[1]/div/div/div/money-box-cta-buttons/div/div/money-box-cart-buttons/div/div[1]/quantity-button/button");
			Procced_To_cart=By.xpath("//*[@id='content']/product-page/div[1]/div/add-to-cart/div/money-box/div[2]/div/div/money-box-cart-messages/button");
			verify_cart_item=By.xpath("/html/body/div[3]/main/div[3]/form/div[2]/div/section[1]/div/div/div[2]/div[1]/div[2]/a/h2");
			removeItem	=By.xpath("/html/body/div[3]/main/div[3]/form/div[2]/div/section[1]/div/div/div[3]/div[2]/div/button");
			removeBtn=By.xpath("/html/body/div[5]/div/div/div/div[3]/div/div[2]/button");
			selectClublink=By.xpath("//a[@class='select-club-text'][1]");
			searchEditbox = By.xpath("//input[@placeholder='Search']");
			searchBtn = By.id("submit-search");
			searchStoreEditbox = By.xpath("//input[@placeholder='City, State or ZIP']");
			searchBtnGo=By.xpath("//button[contains(text(),'Go')]");
			selectBtn= By.xpath("//button[contains(text(),'Select')]");
			pickupBtn = By.xpath("//button[contains(text(),'Pick up')]");
			qtyErrortxt = By.xpath("//div[@class='quantity-error-message']");
		
			
			
		} else if (platformName.equalsIgnoreCase("iOS")) {
			Verifyscreen = By.xpath("//*[@id="+'"'+"app"+'"'+"]/app-layout/app-header/header/div/a/img");
			Buy_Now_product=By.xpath("//*[@id="+'"'+"TRM_Content"+'"'+"]/div/div[1]/a[2]/img");
			
		} 
		else
		{

		}
	}
}
