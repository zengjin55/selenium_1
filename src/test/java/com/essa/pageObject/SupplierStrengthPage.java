package com.essa.pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.essa.framework.BasePage;

public class SupplierStrengthPage extends BasePage{
	public SupplierStrengthPage(WebDriver driver) {  
        super(driver);  
	 }
       
	 
		/*
         * 元素定位
        */
	
        //配合度
        @FindBy (xpath="//*[contains(text(),'配合度')]/../div[1]/div/select")
        WebElement cooperateDegree;
        
        //主打产品定位
        @FindBy (xpath="//*[@dict-check-list='supplier_main_product_location']/select")
        WebElement mainProduct;
        
        //是否有研发能力:否
        @FindBy (xpath = "//*[contains(text(),'是否有研发能力')]/../div/label[2]")
        WebElement notDev;
        
        //是否研发能力：是
        @FindBy (xpath = "//*[contains(text(),'是否有研发能力')]/../div/label[1]")
        WebElement isDev;
        
        //每个季度有新品推出
        @FindBy (xpath = ".//*[@class='col-md-20']/div/label[1]/input")
        WebElement newGoodsEveryQuarter;
        
        //有配备工程师
        @FindBy (xpath =".//*[@class='col-md-20']/div/label[2]/input")
        WebElement existsEngineer;
        
        //有产品研发团队
        @FindBy (xpath = ".//*[@class='col-md-20']/div/label[3]/input")
        WebElement existsProductDev;
        
        //其他
        @FindBy (xpath = ".//*[@class='col-md-20']/div/label[4]/input")
        WebElement others;
        
        //其他文本输入框
        @FindBy (xpath = ".//*[@ng-model='ctrl.model.otherDevelopmentAbilityValue']")
        WebElement otherDevAbilValue;
        
        //是否有证书：是
        @FindBy (xpath = ".//*[contains(text(),'是否有证书')]/../div/label[1]")
        WebElement isCertificate;
        
        //是否有证书：否
        @FindBy (xpath =".//*[contains(text(),'是否有证书')]/../div/label[2]")
        WebElement notCertificate;
        
        //添加证书的“+”
        @FindBy (xpath = ".//*[@list='ctrl.model.supplierCertificationList']/div[1]/div/button[1]")
        WebElement addCertificate;
        
        /*
         * 由于这个是动态表格，表格行数会变化，必须自己写方法来定位元素
         * 证书类型选择项，value值：0~8	 0:BSCI,1:SA8000,2:ICTT……
         */
        public WebElement itemType(int i) {
        	String xpath = ".//*[@list='ctrl.model.supplierCertificationList']/div[1]/table/tbody/tr["+i+"]/td[2]/div/select";
        	return driver.findElement(By.xpath(xpath));
        }
        
        //证书编号文本框
        public WebElement itemCode(int i) {
        	String xpath = ".//*[@list='ctrl.model.supplierCertificationList']/div[1]/table/tbody/tr["+i+"]/td[3]/input";
        	return driver.findElement(By.xpath(xpath));
        }
        
        //证书说明文本框
        public WebElement itemDec(int i) {
        	String xpath = ".//*[@list='ctrl.model.supplierCertificationList']/div[1]/table/tbody/tr["+i+"]/td[4]/input";
        	return driver.findElement(By.xpath(xpath));
        }
        
        //证书图片，用于判断图片是否上传成功
        public WebElement imge(int i) {
        	String xpath = ".//*[@list='ctrl.model.supplierCertificationList']/div[2]/div[2]/div/div["+i+"]/div[1]/img";
        	return driver.findElement(By.xpath(xpath));
        }
        
        //添加证书文件
        public WebElement itemFile(int i) {
        	String xpath = ".//*[@list='ctrl.model.supplierCertificationList']/div[2]/div[2]/div/div["+i+"]/div[4]";
        	return driver.findElement(By.xpath(xpath));
        }
        
        //已添加证书下的证书集合
        @FindBy (xpath = ".//*[@list='ctrl.model.supplierCertificationList']/div[1]/table/tbody/tr")
        List<WebElement> checkBoxes;
        
        //是否服务过大客户：是
        @FindBy (xpath = ".//*[contains(text(),'是否服务过大客户')]/../div[1]/label[1]")
        WebElement isServLargeCus;
        
        //是否服务过大客户：否
        @FindBy (xpath = ".//*[contains(text(),'是否服务过大客户')]/../div[1]/label[2]")
        WebElement notServLargeCus;
        
        //服务过的大客户数
        @FindBy (xpath = ".//*[@list='ctrl.model.serviceLargeCustomerList']/div/table/tbody/tr")
        List<WebElement> customers;
        
        //大客户的“+”
        @FindBy (xpath = ".//*[@list='ctrl.model.serviceLargeCustomerList']/div/div/button[1]")
        WebElement addCustomer;
        
        //大客户名称文本框
        public WebElement largeCusName(int i) {
        	String xpath = ".//*[@list='ctrl.model.serviceLargeCustomerList']/div/table/tbody/tr["+i+"]/td[2]/input";
        	return driver.findElement(By.xpath(xpath));
        }
        
        //备注文本框
        public WebElement note(int i) {
        	String xpath = ".//*[@list='ctrl.model.serviceLargeCustomerList']/div/table/tbody/tr["+i+"]/td[3]/input";
        	return driver.findElement(By.xpath(xpath));
        }
        
        //确定按钮
        @FindBy (xpath = "//*[text()='确定']")
        WebElement submit;
        
        //标签页名称
        @FindBy (xpath ="//*/a[contains(text(),'综合实力评估')]")
        WebElement labelName;
        
        //提交完成后，操作成功的弹框关闭按钮
        @FindBy (xpath="//*[@class='close']")
        WebElement alertClosed;
        
        //关闭页面
        @FindBy(xpath = "//*[text()='综合实力评估']/../span")
        WebElement closed;
	
        
        /*
         * 方法
         */
        
        //选择配合度 
        public void selectCooperateDegree(String cooperateGrade) {
        	
        	//选择配合度：高、中、低
        	selectElement(cooperateDegree, cooperateGrade);
        	
        }
        
        //是否有研发能力,参数可选 是/否
        public void isDevAblity(String ablity) {
        	if("是".equals(ablity))
        		click(isDev);
        	else {
        		click(notDev);
			}
        }
        
        //
        
        //提交编辑
        public void submit() {
        	moveHeightScroll("0");
        	click(submit);
        	
        }
        
        //勾选每季度有新品推出
        public void newProduct() {
        	if(newGoodsEveryQuarter.isSelected());//判断是否已勾选
        	else {
        	isElementExist(newGoodsEveryQuarter);
        	click(newGoodsEveryQuarter);
        	}
        }
        
        //勾选有配备工程师
        public void haveEngineer() {
        	if(existsEngineer.isSelected());
        	else {
        	isElementExist(existsEngineer);
        	click(existsEngineer);
        	}
        }
        
        //勾选有研发团队
        public void haveTeam() {
        	if (existsProductDev.isSelected());
        	else {
        	isElementExist(existsProductDev);
        	click(existsProductDev);
        	}
        }
        
        //勾选“其他”
        public void other() {
        	if(others.isSelected());
        	else {
        		click(others);
				isElementExist(otherDevAbilValue);
				sendKeys(otherDevAbilValue, "我们每天都有新品推出！");
			}
        }
        
        
        //拖动滚动条，因为本页面如果数据量多，就看不到头部的关闭本页面的X
        public void moveScroll() {
        	
        	moveHeightScroll("100");
        	
        }
        
        //检查是否列表加载完成
        public boolean isSucceed() throws InterruptedException {
        	while(!(labelName.isDisplayed())) {
        		Thread.sleep(1000);
        	}
        	return isElementExist(labelName);
        	
        }
        
        //判断是否提交成功
        public boolean isSubmit() {
        	
        	return isElementExist(alertClosed);
        	
        }
        
        //关闭“操作成功”提示
        public void alertClosed() {
        	click(alertClosed);
        }
        
        /*
         * 添加证书逻辑：
         * 1.点击是否有证书：是
         * 2.点击“+”
         * 3.统计已有x个证书
         * 4.i=x
         * 5.选择证书类型WRAP，证书编号，证书说明，传证书图片
         * 6.判断证书是否上传成功，不成功则等待
         */
        public void addCertification() throws Exception {
        	click(isCertificate);
        	isElementExist(addCertificate);
        	click(addCertificate);
        	
        	java.util.List<WebElement> list= checkBoxes;
        	int x = list.size();
        	
        	selectElement(itemType(x), "WRAP");
        	sendKeys(itemCode(x), "20180331:"+x);
        	sendKeys(itemDec(x), "证书说明：这是第"+x+"个证书");
        	click(itemFile(x));
        	uploadFile("E:\\pic\\证书.jpg");

        	//如果图片是系统默认的图片，则等待1秒
        	while("/img/nopic.jpg".equals(imge(x).getAttribute("ng-src"))) {
        		Thread.sleep(1000);
        	}
        }
        
        /*
         * 添加服务大客户记录
         * 先把滚动条拉下来，不然找不到元素
         * 1.是否服务过大客户，点击：是
         * 2.点击“+”
         * 3.判断已有服务过的次数x，i=x
         * 4.写入大客户名称，备注
         */
        public void addSerLargeCus() {
        	moveHeightScroll("0");
        	click(isServLargeCus);
        	isElementExist(addCustomer);
        	click(addCustomer);
        	List<WebElement> list = customers;
        	int x = list.size();
        	sendKeys(largeCusName(x), "第"+x+"大客户");
        	sendKeys(note(x), "这个是备注信息"+x);
        }
        
        //选择主打产品
        public void selMainProduct(String degree) {
        	selectElement(mainProduct, degree);
        }
        
        //关闭本页面
        public void closed() {
        	
        	click(closed);
        	
        }
}
