package tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class CanelPort extends BaseTest {

	private static String search = "//div[contains(@class,'dynmastertwocolsearchpanel')]//button[starts-with(@id,'CNL-j_id')]";
	private static String searchfieldXpath = "//input[@id='nfr_topbar_autocomp_input']";
	private static String dropdownValueXpath = "//li[@data-item-label='Canal Ports']";
	private static String portSearchXpath = "//input[@data-ref='eInput' and contains(@aria-label,'Port Code')]";
	private static String portValueXpath = "//div[contains(text(),'ASDSR')]";
	private static String selectButtonXpath = "//button[@id='nfr-twocol-select-button']";
	private static String topSearchIdValue = "CNL-CNL_toolbar-btnTblDefaultSearch";
	private static String modalPopupIdValue = "nfr_sch_twocolumn_modal";

	@Test(priority = 1)
	public void SearchModule() throws InterruptedException {
		login();
		Thread.sleep(5000);
		WebElement searchfield = driver.findElement(By.xpath(searchfieldXpath));
		sendKeysToElement(searchfield, "Canal Ports");
		Thread.sleep(5000);
		clickElement(driver.findElement(By.xpath(dropdownValueXpath)));
	}

	@Test(priority = 2)
	public void CRUD() throws InterruptedException {

		// Creating the port
		Thread.sleep(5000);
		WebElement searchbtn = driver.findElement(By.xpath(search));
		clickElement(searchbtn);

		WebElement portsearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(portSearchXpath)));
		clickElement(portsearch);
		sendKeysToElement(portsearch, "ASDSR");

		WebElement portvalue = driver.findElement(By.xpath(portValueXpath));
		doubleClickElement(portvalue);

		List<WebElement> modalPopup = driver.findElements(By.id(modalPopupIdValue));
		if (modalPopup.size() != 0) {
			WebElement selectbtn = driver.findElement(By.xpath(selectButtonXpath));
			clickElement(selectbtn);
		}

		WebElement savebtn = driver.findElement(By.id("CNL-CNL_toolbar-btnsave"));
		clickElement(savebtn);

		System.out.println("Saved Successfully ");
		Thread.sleep(2000);
	}

	@Test(priority = 3)
	public void read() throws InterruptedException {
		// Read the created port
		WebElement searchbtn = driver.findElement(By.xpath(search));
		clickElement(searchbtn);
		
		Thread.sleep(2000);
		WebElement portsearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(portSearchXpath)));
		clickElement(portsearch);
		sendKeysToElement(portsearch, "ASDSR");

		WebElement portvalue = driver.findElement(By.xpath(portValueXpath));
		doubleClickElement(portvalue);

		List<WebElement> modalPopup = driver.findElements(By.id(modalPopupIdValue));
		if (modalPopup.size() != 0) {
			WebElement selectbtn = driver.findElement(By.xpath(selectButtonXpath));
			selectbtn.click();
		}

		WebElement topsearchbtn = driver.findElement(By.id(topSearchIdValue));
		topsearchbtn.click();
	}

//	public void getNotificationMessage() {
//		WebElement notificationPopup = wait.until(ExpectedConditions.presenceOfElementLocated(
//		By.xpath("//div[@class=ui-growl-message]")));
//		String msg1 = driver.findElement(By.xpath("//div[@class=ui-growl-message]/span")).getText();
//		String msg2 = driver.findElement(By.xpath("//div[@class=ui-growl-message]/p")).getText();
//		System.out.println("Notification " + msg1 + msg2);
//	}
}
