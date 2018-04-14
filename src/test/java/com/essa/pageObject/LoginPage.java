package com.essa.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.essa.framework.BasePage;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/*
	 * 元素定位
	 */
	
	//账号输入框
	@FindBy (xpath="//*[@id='username']")
	WebElement login_account;
	
	//密码输入框
	@FindBy (xpath="//*[@id='password']")
	WebElement login_password;
	
	//登录按钮
	@FindBy (xpath="//*[@id='subBtn']")
	WebElement login_submit;
	
	/*
	 * 页面方法
	 */
	
	/*
	 * 1.输入帐号
	 * 2.输入密码
	 * 3.点击登录
	 * 4.将driver返回
	 */
	public HomePage login(String account,String password) {
	
		sendKeys(login_account, account);
		
		sendKeys(login_password, password);
		
		click(login_submit);
		
		return new HomePage(driver);
		
	}
}
