package com.essa.pageObject;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.essa.framework.BrowserEngine;
import com.essa.pageObject.LoginPage;

public class BaseTest {
	public WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}
	
	// 调用浏览器，打开要测试的网页
	public void initsetUp() throws IOException {
		
		BrowserEngine browserEngine = new BrowserEngine();
		
		browserEngine.initConfigData();
		
		driver = browserEngine.getBrowser();
		
	}

	//初始化登录页面，登录
	public void loginValid() {
		
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		
		loginPage.login("admin", "essa123");
		
	}
}

