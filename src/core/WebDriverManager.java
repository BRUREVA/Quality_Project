package core;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.Semaphore;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


//tem menu de contexto

public class WebDriverManager {
	
private static final Semaphore semaphore = new Semaphore(4);
	
	private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>() {
		@Override
		protected synchronized WebDriver initialValue(){
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			try {
				return initDriver();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return null;
		}
	};
 
	public static WebDriver driver() {
		return threadDriver.get();
	}
 
	public static WebDriverWait driverWait() {
		return new WebDriverWait(driver(), Duration.ofSeconds(30));
	}
 
	public static WebDriver initDriver() throws MalformedURLException {
		WebDriver driver = null;
		try {
			System.out.print("Setando driver");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			options.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(options);
			System.out.print("driver iniciado");
		} catch (Exception e) {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--start-maximized");
			chromeOptions.addArguments("--remote-allow-origins=*");
			driver = new RemoteWebDriver(new URL("http://localhost:4444/"), chromeOptions);
			e.printStackTrace();
		}
		return driver;
	}
 
	public static void closeAllTabs() {
		WebDriver driver = driver();
		if (driver != null) {
			driver.manage().deleteAllCookies();
			driver.quit();
			driver = null;
		}
		semaphore.release();
		if (threadDriver != null) {
			threadDriver.remove();
		}
	}

	
	
	
	

}
