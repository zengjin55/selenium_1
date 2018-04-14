package com.essa.pageObject;  
  
import org.openqa.selenium.WebDriver;  
import org.openqa.selenium.WebElement;  
import org.openqa.selenium.support.FindBy;
import com.essa.framework.BasePage;  
  
public class HomePage extends BasePage{  
    public HomePage(WebDriver driver) {  
        super(driver);  
          
    }  
    
    /*
     * 元素定位
     */
    
    //退出
    @FindBy (xpath="//*[text()='退出']")
    WebElement logout;
    
    //供应商管理
    @FindBy (xpath="//*[text()='供应商管理']")
    WebElement supplier;
    
    //平台运营跟进管理
    @FindBy (xpath="//*/a[contains(text(),'平台运营跟进管理')]")
    WebElement operationsTrack;
    
    //供应商查询
    @FindBy (xpath="//*[text()='供应商查询']")
    WebElement searchSuppliers;
    
    /*
     * 方法
     */
    
    //进入运营跟进管理页面
    public SupplierOperationsTrackPage goToSupplierOperationsTrack() {
    	
    	//点击 供应商管理
    	click(supplier);
    	
    	//点击 平台运营跟进管理
    	click(operationsTrack);
    	
    	//此时，系统会加载一个平台运营跟进管理页面，故在此初始化该页面并将driver传递过去
    	return new SupplierOperationsTrackPage(driver);
    	
    }
    
    //判断是否存在退出按钮
    public boolean isSucceed() {
    	
    	//判断退出按钮是否存在，存在则表示成功进入首页
    	return isThisPage("退出", logout);
    	
    }
    
    //判断是否选中“供应商管理”
    public boolean isSearchSuppliers() {
    	
    	return isElementExist(searchSuppliers);
    	
    }
    
    //退出登录
    public void logout() {
    	
    	click(logout);
    	
    }
}  