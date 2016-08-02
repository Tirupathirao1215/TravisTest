package com.appium.project.scripts;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.appium.project.businesslogic.Samsclub;

public class Add_SamsclubItems extends Samsclub{
	private final Logger LOG = Logger.getLogger(Add_SamsclubItems.class);
	@Test
	public void selectIteam(ITestContext testContext) throws Throwable {
		try {

			this.setTestContext(testContext);
			this.reporter.initTestCaseDescription("User should be able to view and order products");
			if (selectItem()) {
				this.reporter.SuccessReport("select Items", "seleted items sucessfully");

			} else
				this.reporter.failureReport("User unable to select Items", this.gErrMsg, this.appiumDriver);
			
			if (setStore()) {
				this.reporter.SuccessReport("store Iteams", "user store items sucessfully");

			} else
				this.reporter.failureReport("User unable to store Iteams", this.gErrMsg, this.appiumDriver);
	}catch (Exception e) {
		e.printStackTrace();
	}

}
}