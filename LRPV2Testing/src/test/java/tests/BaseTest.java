package tests;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
	WebDriver driver;
	WebDriverWait wait;
	Actions action;

	@BeforeTest
	public void BrowserSetup() {

		ChromeOptions options = new ChromeOptions();

		// Disable Chrome's password manager
		options.addArguments("--disable-features=PasswordManager");
		options.addArguments("--disable-save-password-bubble");
		options.setExperimentalOption("prefs",
				Map.of("credentials_enable_service", false, "profile.password_manager_enabled", false));
		options.setExperimentalOption("prefs",
				java.util.Collections.singletonMap("profile.password_manager_leak_detection", false));

		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		action = new Actions(driver);
	}

	public void clickElement(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
		} catch (Exception e) {
			System.err.println("Failed to click on the element: " + e.getMessage());
		}
	}

	public void doubleClickElement(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));

			Actions actions = new Actions(driver);
			actions.doubleClick(element).perform();

			System.out.println("Double-clicked on the element successfully.");
		} catch (Exception e) {
			System.err.println("Failed to double-click on the element: " + e.getMessage());
		}
	}

	public void sendKeysToElement(WebElement element, String text) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			element.sendKeys(text);
			System.out.println("Text entered successfully: " + text);
		} catch (Exception e) {
			System.err.println("Failed to send keys to the element: " + e.getMessage());
		}
	}

	public void login() throws InterruptedException {
		driver.get("https://lrpv2.solverminds.net/main");
		System.out.println("Application Launched");
		WebElement Username = driver.findElement(By.id("nfr_login_authname"));
		Username.sendKeys("superuser");
		WebElement Password = driver.findElement(By.id("nfr_login_authid"));
		Password.sendKeys("P@ssw0rd");
		WebElement Login = driver.findElement(By.id("nfr_login_btnlogin"));
		clickElement(Login);
		System.out.println("Login Successfull");
	}

	public void SearchModule(String searchValue) throws InterruptedException {
		Thread.sleep(5000);
		WebElement searchfield = driver.findElement(By.xpath("//input[@id='nfr_topbar_autocomp_input']"));
		sendKeysToElement(searchfield, searchValue);
		Thread.sleep(5000);
		clickElement(driver.findElement(By.xpath("//li[@data-item-label='" + searchValue + "']")));
	}

	@AfterTest
	public void BrowserClose() {
		driver.quit();
	}
}
