package com.essa.testSuite;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.essa.pageObject.HomePage;
import com.essa.pageObject.SupplierOperationsTrackPage;
import com.essa.pageObject.SupplierStrengthPage;
import com.essa.pageObject.BaseTest;

public class Test_Development_Ability extends BaseTest {

	WebDriver driver;

	// 登录
	@BeforeClass
	public void setUp() throws IOException {

		initsetUp();

		loginValid();

	}

	// 切换到“供应商管理”菜单
	@Test
	public void toSupplierOperationsTrack() {

		/*
		 * 由于我们只能对一个driver进行操作,要先将driver获取 我们要操作的页面为HomePage，所以要初始化一个HomePage对象
		 * 调用进入平台运营管理,断言
		 */
		this.driver = getDriver();

		HomePage homePage = PageFactory.initElements(driver, HomePage.class);

		homePage.goToSupplierOperationsTrack();

		assertEquals(homePage.isSearchSuppliers(), true, "切换到“供应商管理”菜单失败！");

	}

	// 进入“平台运营跟进管理” 选择供应商，进入其综合实力评估页
	@Test(dataProvider = "suppliers", dependsOnMethods = { "toSupplierOperationsTrack" })
	public void editDevelopmentAbility(String supplier) throws Exception {

		SupplierOperationsTrackPage sotp = PageFactory.initElements(driver, SupplierOperationsTrackPage.class);

		// 断言是否进入页面

		SoftAssert softAssert = new SoftAssert();

		softAssert.assertEquals(sotp.isSucceed(), true, "进入平台运营跟进管理失败！");


		// 在列表中查找出要编辑的供应商（excel中的）进入实力评估页面

		sotp.goToSupplierStrengthPage(supplier);

		SupplierStrengthPage strengthPage = PageFactory.initElements(driver, SupplierStrengthPage.class);

		softAssert.assertEquals(strengthPage.isSucceed(), true, "进入实力评估页面失败！");

		/*
		 * 配合度选择“高”，主打产品：中高
		 * 是否研发能力选择“是”，勾选：每个季度有新品推出，有配备工程师，产品研发团队,其他
		 * 添加证书，服务过大客户
		 * 点击提交
		 */

		strengthPage.selectCooperateDegree("高");
		Thread.sleep(500);
		
		strengthPage.selMainProduct("中高");
		Thread.sleep(500);
		
		strengthPage.isDevAblity("是");
		strengthPage.newProduct();
		strengthPage.haveEngineer();
		strengthPage.haveTeam();
		strengthPage.other();
		
		strengthPage.addCertification();
		strengthPage.addSerLargeCus();

		strengthPage.submit();
		Thread.sleep(1000);
		
		softAssert.assertEquals(strengthPage.isSubmit(), true, "提交失败！");

		softAssert.assertAll();
		
		strengthPage.alertClosed();

	}
	

	// 读取excel表格 表格位置在data文件夹下 表格名称为“供应商”，读取的sheet名称为“供应商”
	@DataProvider(name = "suppliers")
	public Object[][] suppliersData() {

		try {

			return com.essa.framework.BasePage.readExcel(".\\src\\main\\resources", "data.xlsx", "供应商");

		} catch (Exception e) {

			e.printStackTrace();

			return null;

		}
	}

	// 测试完要清理，不占用资源

	@AfterClass
	public void tearDown() {

		driver.quit();

	}
}
