package com.essa.testSuite;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.essa.framework.BrowserEngine;
import com.essa.pageObject.HomePage;
import com.essa.pageObject.LoginPage;

public class Test_Login {
	WebDriver driver;
	
	/*
	 * 调用浏览器，打开网页
	 * 执行任何测试之前都应执行这个步骤
	 * 后面我会把这个方法封装在pageObject.function里面
	 */
	
	@BeforeClass
	public void setUp() throws IOException {
		
		BrowserEngine browserEngine = new BrowserEngine();
		
		browserEngine.initConfigData();
		
		driver = browserEngine.getBrowser();
		
	}
	
	/*
	 * 初始化（实例化）一个登录页面
	 * 做登录操作
	 * 登录完后会跳转到后台首页
	 * 故要初始化一个后台首页用于断言是否登录成功
	 * 这里登录成功的标识是有“退出”按钮
	 */
	
	@Test(dataProvider="users")
	public void login(String account,String password) {
		
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		
		loginPage.login(account, password);
		
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		
		/*
		 * 软断言，即就算此用例失败，依然会继续执行
		 * 断言后，做退出登录操作
		 */
		
		SoftAssert softAssert = new SoftAssert();
		
		softAssert.assertEquals(homePage.isSucceed(), true, "登录失败！");
		
		softAssert.assertAll();
		
		homePage.logout();
		
	}
	
	/*
	 * 提供数据对象
	 * 此方法读取当前项目根目录下的data\\data.xlsx文件中的“users”工作表
	 */
	
	@DataProvider(name="users")
	public Object[][] suppliersData() {
		
		try {
			
			return com.essa.framework.BasePage.readExcel(".\\src\\main\\resources", "data.xlsx", "users");
		
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return null;
			
		}
	}
	
	/*
	 * 最后要做清理工作，避免占用资源
	 */
	
	@AfterClass
	public void tearDown() {
		
		driver.quit();
		
	}
}
