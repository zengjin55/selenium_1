package com.essa.framework;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.essa.framework.BasePage;
import com.essa.framework.LogType;
import com.essa.framework.Logger;

public class BasePage {

	public static WebDriver driver;
	public static String pageTitle;
	public static String pageUrl;
	public static String OutputFileName = getDateTimeByFormat(new Date(), "yyyyMMdd_HHmmss");  
	/*
	 * 构造方法
	 */
	public BasePage(WebDriver driver) {
		BasePage.driver = driver;
	}

	/*
	 * 在文本框内输入字符
	 */
	protected void sendKeys(WebElement element, String text) {
		try {
			if (element.isEnabled()) {
				element.clear();
				Logger.Output(LogType.LogTypeName.INFO, "清除文本框中已有字符：" + partialStr(element.toString(), "xpath:"));
				element.sendKeys(text);
				Logger.Output(LogType.LogTypeName.INFO, "输入的字符是：" + text);
			}
		} catch (Exception e) {
			Logger.Output(LogType.LogTypeName.ERROR, element.toString()+"元素不存在");
		}

	}

	/*
	 * 点击元素，这里指点击鼠标左键
	 */
	protected void click(WebElement element) {

		try {
			if (element.isEnabled()) {
				Logger.Output(LogType.LogTypeName.INFO, "点击元素：" + partialStr(element.toString(), "xpath:"));
				element.click();
			}
		} catch (Exception e) {
			Logger.Output(LogType.LogTypeName.ERROR, e.getMessage() + ".");
		}

	}

	/*
	 * 在文本输入框执行清除操作
	 */
	protected void clear(WebElement element) {

		try {
			if (element.isEnabled()) {
				element.clear();
				Logger.Output(LogType.LogTypeName.INFO, "清除输入框中字符：" + partialStr(element.toString(), "xpath:") );
			}
		} catch (Exception e) {
			Logger.Output(LogType.LogTypeName.ERROR, e.getMessage() + ".");
		}

	}

	/*
	 * 判断一个页面元素是否显示在当前页面
	 */
	protected void verifyElementIsPresent(WebElement element) {

		try {
			if (element.isDisplayed()) {
				Logger.Output(LogType.LogTypeName.INFO, "元素存在：" + partialStr(element.toString(), "xpath:").trim());

			}
		} catch (Exception e) {
			Logger.Output(LogType.LogTypeName.ERROR, e.getMessage() + ".");
		}
	}

	/*
	 * 获取页面的标题
	 */
	protected String getCurrentPageTitle() {

		pageTitle = driver.getTitle();
		Logger.Output(LogType.LogTypeName.INFO, "当前页面的标题为：" + pageTitle);
		return pageTitle;
	}

	/*
	 * 获取页面的url
	 */
	protected String getCurrentPageUrl() {

		pageUrl = driver.getCurrentUrl();
		Logger.Output(LogType.LogTypeName.INFO, "当前页面的URL为：" + pageUrl);
		return pageUrl;
	}

	/*
	 * 处理多窗口之间切换
	 */
	protected void switchWindow() {

		String currentWindow = driver.getWindowHandle();// 获取当前窗口句柄
		Set<String> handles = driver.getWindowHandles();// 获取所有窗口句柄
		Logger.Output(LogType.LogTypeName.INFO, "当前窗口数量： " + handles.size());
		Iterator<String> it = handles.iterator();
		while (it.hasNext()) {
			if (currentWindow == it.next()) {
				continue;
			}
			try {
				// driver.close();// 关闭旧窗口
				WebDriver window = driver.switchTo().window(it.next());// 切换到新窗口
				Logger.Output(LogType.LogTypeName.INFO, "新窗口的标题为：" + window.getTitle());
			} catch (Exception e) {
				Logger.Output(LogType.LogTypeName.ERROR, "无法切换到新打开窗口" + e.getMessage());

			}
			// driver.close();//关闭当前焦点所在的窗口
		}
		// driver.switchTo().window(currentWindow);//回到原来页面
	}
	
	/*
	 * 浏览器弹框操作，true确认弹框，false取消弹框
	 */
	protected void alert(boolean isAccept) {
		Alert alert = driver.switchTo().alert();
		if (isAccept) {
			Logger.Output(LogType.LogTypeName.INFO, "提示框内容为：" + alert.getText());
			alert.accept();
			Logger.Output(LogType.LogTypeName.INFO, "确认弹框");
		} else {
			Logger.Output(LogType.LogTypeName.INFO, "提示框内容为：" + alert.getText());
			alert.dismiss();
			Logger.Output(LogType.LogTypeName.INFO, "取消弹框");
		}
	}
	
	/*
	 * 下拉框选择选项
	 */
	protected void selectElement(WebElement element, String optionText) {
		Select select = new Select(element);
		select.selectByVisibleText(optionText);
		Logger.Output(LogType.LogTypeName.INFO, "选择选项：" + optionText);
	}

	/*
	 * 判断元素在页面中是否存在
	 */
	protected boolean isElementExist(WebElement element) {
		try {
			Boolean bool = element.isDisplayed();
			Logger.Output(LogType.LogTypeName.INFO, "检查元素是否存在：" + bool);
			return bool;
		} catch (NoSuchElementException e) {
			takeScreenShot();
			Logger.Output(LogType.LogTypeName.ERROR, "无法确认当前元素是否存在：" + e.getMessage());
			return false;
		}
	}

	/*
	 * 获取元素的文本值
	 */
	protected void getText(WebElement element) {

		try {
			if (element.isEnabled()) {
				element.getText();
				Logger.Output(LogType.LogTypeName.INFO, "获取当前元素的文本值：" + element.getText());
			}
		} catch (Exception e) {
			Logger.Output(LogType.LogTypeName.ERROR, e.getMessage() + ".");
		}
	}

	/*
	 * js的点击操作
	 */
	protected void jsExecutorClick(WebElement element) {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].click();", element);
			Logger.Output(LogType.LogTypeName.INFO, "调用JavaScript点击元素：" + element.getText());
		} catch (Exception e) {
			Logger.Output(LogType.LogTypeName.ERROR, e.getMessage() + ".");
		}

	}

	/*
	 * js的删除操作
	 */
	protected void jsExecutorRemoveAttribute(WebElement webElement, String attribute) {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].removeAttribute('" + attribute + "');", webElement);
			Logger.Output(LogType.LogTypeName.INFO, "调用JavaScript删除元素属性：" + attribute);
		} catch (Exception e) {
			Logger.Output(LogType.LogTypeName.ERROR, e.getMessage() + ".");
		}

	}

	/*
	 * 获取js返回的值
	 */
	protected String jsExecutorGetAttributeValue(WebDriver driver, WebElement webElement) {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			Logger.Output(LogType.LogTypeName.INFO, "调用JavaScript返回元素属性值");
			return (String) jsExecutor.executeScript("return arguments[0].id;", webElement);
		} catch (Exception e) {
			Logger.Output(LogType.LogTypeName.ERROR, e.getMessage() + ".");
			return null;
		}
	}

	/*
	 * 读取excel中的数据
	 * 第一个参数为excel的路径地址
	 * 第二个参数为excel的文件名
	 * 第三个参数为excel的worksheet名
	 */
	public static Object[][] readExcel(String filepath, String filename, String SheetName) throws Exception {
		File file = new File(filepath + "\\" + filename);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook Workbook = null;
		// 获取文件扩展名
		String fileExtensionName = filename.substring(filename.indexOf("."));
		Logger.Output(LogType.LogTypeName.INFO, "获取所要读取的文件");
		// 判断是.xlsx还是.xls的文件并进行实例化
		if (fileExtensionName.equals(".xlsx")) {
			Workbook = new XSSFWorkbook(inputStream);
			Logger.Output(LogType.LogTypeName.INFO, "文件为：.xlsx格式");
		} else if (fileExtensionName.equals(".xls")) {
			Workbook = new HSSFWorkbook(inputStream);
			Logger.Output(LogType.LogTypeName.INFO, "文件为：.xls格式");
		}
		// 通过sheetName生成Sheet对象
		Sheet Sheet = Workbook.getSheet(SheetName);
		int rowCount = Sheet.getLastRowNum() - Sheet.getFirstRowNum();
		List<Object[]> records = new ArrayList<Object[]>();
		for (int i = 0; i < rowCount + 1; i++) {
			Row row = Sheet.getRow(i);
			String fields[] = new String[row.getLastCellNum()];
			for (int j = 0; j < row.getLastCellNum(); j++) {
				if (row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC) {
					row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
				}
				// 判断数据的类型
				switch (row.getCell(j).getCellType()) {
				case Cell.CELL_TYPE_NUMERIC: // 数字
					fields[j] = String.valueOf(row.getCell(j).getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING: // 字符串
					fields[j] = String.valueOf(row.getCell(j).getStringCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN: // Boolean
					fields[j] = String.valueOf(row.getCell(j).getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA: // 公式
					fields[j] = String.valueOf(row.getCell(j).getCellFormula());
					break;
				case Cell.CELL_TYPE_BLANK: // 空值
					fields[j] = "";
					break;
				case Cell.CELL_TYPE_ERROR: // 故障
					fields[j] = "非法字符";
					break;
				default:
					fields[j] = "未知类型";
					break;
				}
			}

			records.add(fields);
		}
		Object[][] results = new Object[records.size()][];
		for (int i = 0; i < records.size(); i++) {
			results[i] = records.get(i);
		}
		Logger.Output(LogType.LogTypeName.INFO, "读取文件成功");
		return results;
	}

	/*
	 * 上传文件
	 */
	protected void uploadFile(String filePath) throws Exception {
		Logger.Output(LogType.LogTypeName.INFO, "开始上传文件");
		StringSelection sel = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
		// 新建一个Robot类的对象
		Robot robot = new Robot();
		Thread.sleep(1000);

		// 按下回车
		robot.keyPress(KeyEvent.VK_ENTER);

		// 释放回车
		robot.keyRelease(KeyEvent.VK_ENTER);

		// 按下 CTRL+V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		// 释放 CTRL+V
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		Thread.sleep(1000);

		// 点击回车 Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Logger.Output(LogType.LogTypeName.INFO, "上传文件成功");
	}

	/*
	 * 字符串切片
	 * 第一个参数：需要被操作的元素
	 * 第二个参数：从这个字符开始切
	 * 第三个参数：到这个字符结尾
	 * 例子：某个元素的文本值为：广州市天河区猎德
	 * 只要“天河区”
	 * 第二个参数：天  第三个参数：区
	 */
	protected String partialStr(WebElement element, String begin, String end) {
		String result_string = element.getText();
		Logger.Output(LogType.LogTypeName.INFO, "获取所需切片的字符串");
		// 根据词切片，取第二片字符串
		String st1 = result_string.split(begin)[1];
		Logger.Output(LogType.LogTypeName.INFO, "切除" + begin + "之前的字符串");
		// 再切一次结尾，得到我们想要的结果
		String search_need = st1.split(end)[0];
		Logger.Output(LogType.LogTypeName.INFO, "切除" + end + "之后的字符串");
		Logger.Output(LogType.LogTypeName.INFO, "返回切片后的字符串");
		return search_need;
	}
	
	//复写切片
	protected String partialStr(String string,String begin) {
		String st1 = string.split(begin)[1];
		return st1;
	}
	
	/*
	 * 每隔1秒查找一次我们要的元素是否存在
	 */
	protected WebElement  mywait(WebElement element) throws InterruptedException {
		while(!(isElementExist(element)))
			Thread.sleep(1000);
		return element;
	}
	
	/*
	 * 设立检查点，判断页面是否是我们要的
	 */
	protected boolean isThisPage(String checkPoint,WebElement element) {
		boolean bool1=checkPoint.equals(element.getText());
		Logger.Output(LogType.LogTypeName.INFO, "判断检查点是否存在");
		return bool1;
	}

	/*
	 * 截图当前页面
	 */
	protected void takeScreenShot() {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			// 拷贝截图文件到我们项目./Screenshots
			FileUtils.copyFile(src, new File(".\\Log\\Screenshots\\"+OutputFileName+"截图.png"));
			Logger.Output(LogType.LogTypeName.INFO, "截图当前页面成功！");
		}

		catch (IOException e) {
			System.out.println(e.getMessage());
			Logger.Output(LogType.LogTypeName.INFO, "截图当前页面失败！");
		}

	}
	
	/*
	 * 上下移动滚动条，这里使用js操作
	 * percent值：0：最下方  100：最上方
	 */
	protected void moveHeightScroll(String percent) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("scrollBy(0, 0-document.body.scrollHeight *"+percent+"/100)");
		Logger.Output(LogType.LogTypeName.INFO, "上下拖动滚动条");
	}
	
	//左右移动滚动条，0：最左  100：最右
	protected void moveWidthScroll(String percent) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("scrollBy(0, 0-document.body.scrollWidth *"+percent+"/100)");
		Logger.Output(LogType.LogTypeName.INFO, "左右拖动滚动条");
	}
	
	//获取当前系统时间，得到格式化时间字符串  
	protected static String getDateTimeByFormat(Date date, String format) {  
  
        SimpleDateFormat df = new SimpleDateFormat(format);  
  
        return df.format(date);  
  
    } 
}