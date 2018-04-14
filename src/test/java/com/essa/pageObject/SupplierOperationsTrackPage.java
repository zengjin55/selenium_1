package com.essa.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.essa.framework.BasePage;

public class SupplierOperationsTrackPage extends BasePage {
	public SupplierOperationsTrackPage(WebDriver driver) {
		super(driver);
	}

	/*
	 * 元素定位
	 */
	
	// 查询输入框
	@FindBy(xpath = "//*[contains(text(),'关键字查询')]/../div/div/input")
	WebElement query;

	// 查询按钮
	@FindBy(xpath = "//*[text()='查询']")
	WebElement search;

	// 综合实力更新
	@FindBy(xpath = "//*/button[contains(text(),'综合实力更新')]")
	WebElement supplierStrength;

	// 第一条查询结果（用于确定是否查询完毕）
	@FindBy(xpath = "//*[@id='content-table']/tbody/tr[1]")
	WebElement firstResult;

	// 检查点：自主发布商品数
	@FindBy(xpath = "//*[@id='content-table']/thead/tr/th[10]")
	WebElement checkPoint;

	/*
	 * 页面方法
	 */
	
	// 输入要查询的文本
	public void searchText(String text) throws InterruptedException {
		
		// 输入要查询的供应商或者编号等,点击查询按钮
		
		sendKeys(query, text);
		
		click(search);
		
	}

	// 检查是否进入平台运营跟进管理
	public boolean isSucceed() {
		
		return isThisPage("自主发布商品数", checkPoint);
		
	}

	// 跳转到综合实力更新
	public SupplierStrengthPage goToSupplierStrengthPage(String supplierName) throws InterruptedException {
		/*
		 * 查询供应商,点击 综合实力更新,将driver传递至综合实力更新页面
		 */
		searchText(supplierName);
		
		click(supplierStrength);
		
		
		return new SupplierStrengthPage(driver);
		
	}

	// 列表是否有查询结果,没有则等待
	public void waitResult() throws InterruptedException {
		while(!(firstResult.isDisplayed()))
			Thread.sleep(1000);
	}
}
