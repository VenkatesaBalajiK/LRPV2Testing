package tests;

import java.time.Duration;
import java.util.List;
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
import org.testng.annotations.Test;

public class CanelPort {
	WebDriver driver;
	WebDriverWait wait;
	Actions action;
	
	private static String search = "//div[contains(@class,'dynmastertwocolsearchpanel')]//button[starts-with(@id,'CNL-j_id')]";

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
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		action = new Actions(driver);
	}

	@Test(priority = 1)
	public void LaunchApp() {
		driver.get("https://lrpv2.solverminds.net/main");
		System.out.println("Application Launched");
		WebElement Username = driver.findElement(By.id("nfr_login_authname"));
		Username.sendKeys("superuser");
		WebElement Password = driver.findElement(By.id("nfr_login_authid"));
		Password.sendKeys("P@ssw0rd");
		WebElement Login = driver.findElement(By.id("nfr_login_btnlogin"));
		Login.click();
		System.out.println("Login Successfull");
	}

	@Test(priority = 2)
	public void SearchModule() throws InterruptedException {
		WebElement searchfield = driver.findElement(By.xpath("//input[@id='nfr_topbar_autocomp_input']"));
		searchfield.sendKeys("Canal Ports");

		Thread.sleep(5000);

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement Drpdown = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@data-item-label='Canal Ports']")));
		Drpdown.click();
	}

	@Test(priority = 3)
	public void CRUD() throws InterruptedException {

		// Creating the port
		WebElement searchbtn = driver.findElement(By.xpath(search));
		searchbtn.click();

		WebElement portsearch = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[@data-ref='eInput' and contains(@aria-label,'Port Code')]")));
		portsearch.click();
		portsearch.sendKeys("ASDSR");

		WebElement portvalue = driver.findElement(By.xpath("//div[contains(text(),'ASDSR')]"));
		action.doubleClick(portvalue).perform();

		List<WebElement> modalPopup = driver.findElements(By.id("nfr_sch_twocolumn_modal"));
		if (modalPopup.size() != 0) {
			WebElement selectbtn = driver.findElement(By.xpath("//button[@id='nfr-twocol-select-button']"));
			selectbtn.click();
		}

		WebElement savebtn = driver.findElement(By.id("CNL-CNL_toolbar-btnsave"));
		savebtn.click();
		
//		WebElement notificationPopup = wait.until(ExpectedConditions.presenceOfElementLocated(
//				By.xpath("//div[@class=ui-growl-message]")));
//		
//		String msg1 = driver.findElement(By.xpath("//div[@class=ui-growl-message]/span")).getText();
//		String msg2 = driver.findElement(By.xpath("//div[@class=ui-growl-message]/p")).getText();
//
//		System.out.println("Notification " + msg1 + msg2);
		
		System.out.println("Saved Successfully ");
	}

	@Test(priority = 4)
	public void read() {
		// Read the created port
		WebElement searchbtn = driver.findElement(By.xpath(search));
		searchbtn.click();
		WebElement portsearch = driver.findElement(By.xpath(
				"//input[@class='ag-input-field-input ag-text-field-input' and contains(@aria-label,'Port Code')]"));
		portsearch.sendKeys("ASDSR");
		WebElement portvalue = driver.findElement(By.xpath("//div[contains(text(),'ASDSR')]"));
		action.doubleClick(portvalue).perform();

		List<WebElement> modalPopup = driver.findElements(By.id("nfr_sch_twocolumn_modal"));
		if (modalPopup.size() != 0) {
			WebElement selectbtn = driver.findElement(By.xpath("//button[@id='nfr-twocol-select-button']"));
			selectbtn.click();
		}

		WebElement topsearchbtn = driver.findElement(By.id("CNL-CNL_toolbar-btnTblDefaultSearch"));
		topsearchbtn.click();

		/*
		 * WebElement Notification = driver.findElement(By.
		 * xpath("//div[@class=ui-growl-message] and contains(text(),'Functions not Supported')"
		 * )); Notification.getText();
		 */
	}

	@AfterTest
	public void BrowserClose() {
		driver.quit();
	}
}
