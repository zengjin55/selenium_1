package com.essa.framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserEngine {  
    
    private String browserName;  
    private String serverURL;  
    private WebDriver driver;  
      
    public void initConfigData() throws IOException{  
          
        Properties p = new Properties();  
        // 加载配置文件  
        InputStream ips = new FileInputStream(".//src//main//resources//TestConfig//config.properties");  
        p.load(ips);  
          
        Logger.Output(LogType.LogTypeName.INFO, "开始从配置文件中选择浏览器");  
        browserName=p.getProperty("browserName");  
        Logger.Output(LogType.LogTypeName.INFO, "所选择的浏览器类型为: "+ browserName);  
        serverURL = p.getProperty("URL");  
        Logger.Output(LogType.LogTypeName.INFO, "所测试的URL地址为: "+ serverURL);  
        ips.close();  
    }  
      
	public WebDriver getBrowser() throws IOException{  
        
    	if(browserName.equalsIgnoreCase("Firefox")){  
        	
            System.setProperty("webdriver.gecko.driver", "\\src\\main\\resources\\geckodriver.exe");    
            driver = createFireFoxDriver();  
            Logger.Output(LogType.LogTypeName.INFO, "正在启动FireFox浏览器");  
              
        }
    	else if(browserName.equals("Chrome")){ 
           /*
    		System.setProperty("webdriver.chrome.driver", ".//src//main//resources//chromedriver");  
            driver= new ChromeDriver();  
            Logger.Output(LogType.LogTypeName.INFO, "正在启动Chrome浏览器");  
            */
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            driver = new ChromeDriver(options);
        }
    	 /*
    	driver.manage().window().maximize();  
        Logger.Output(LogType.LogTypeName.INFO, "窗口最大化");
        */
        driver.get(serverURL);  
        Logger.Output(LogType.LogTypeName.INFO, "打开URL: "+ serverURL);  
        callWait(25);  
        return driver;  
    }  
      
    /* 
     * 关闭浏览器并退出方法 
     */  
      
    public void tearDown() throws InterruptedException{  
          
        Logger.Output(LogType.LogTypeName.INFO, "关闭浏览器");  
        driver.quit();  
        Thread.sleep(3000);  
    }  
      
    /* 
     * 隐式时间等待方法 
     */  
    public void callWait(int time){  
          
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);  
        Logger.Output(LogType.LogTypeName.INFO, "设置隐性等待"+time+" 秒");  
    }  
      
      
    /* 
     * createFireFox Driver 
     * @Param: null 
     * @return: WebDriver 
     */  
      
    private WebDriver createFireFoxDriver() {  
          
        WebDriver driver = null;  
        FirefoxProfile firefoxProfile = new FirefoxProfile();  
  
        firefoxProfile.setPreference("prefs.converted-to-utf8", true);  
        //set download folder to default folder: TestDownload  
        firefoxProfile.setPreference("browser.download.folderList", 2);  
        firefoxProfile.setPreference("browser.download.dir", ".\\TestDownload");  
          
        try {  
                driver = new FirefoxDriver();  
        } catch (Exception e) {  
            Logger.Output(LogType.LogTypeName.ERROR, e.getMessage());  
            Logger.Output(LogType.LogTypeName.ERROR, "Failed to initilize the Firefox driver");  
        }  
        return driver;  
    }  
    public  void anQuan() {
    	 // 创建DesiredCapabilities类的一个对象实例  
        DesiredCapabilities cap=DesiredCapabilities.chrome();  
        // 设置变量ACCEPT_SSL_CERTS的值为True  
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        Logger.Output(LogType.LogTypeName.INFO, "设置浏览器可以打开不安全链接");
    }
}

