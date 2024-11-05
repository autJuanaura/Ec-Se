package com.devaura.qa;

import java.nio.file.Paths;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class EcParallelDifBrowsers implements Runnable {

	private String browser;

	public EcParallelDifBrowsers(String browser) {
		this.browser = browser;
	}

	public static void main(String[] args) {
		String[] browsers = { "chrome", "firefox" };

		for (String browser : browsers) {
			Thread thread = new Thread(new EcommerceParallel(browser));
			thread.start();
		}
	}

	@Override
	public void run() {
		WebDriver driver = getDriver(browser);
		if (driver != null) {
			try {
				driver.get("https://www.saucedemo.com/");
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

				WebElement userNameField = driver.findElement(By.id("user-name"));
				WebElement psswdField = driver.findElement(By.id("password"));
				WebElement loginButton = driver.findElement(By.id("login-button"));

				userNameField.sendKeys("standard_user");
				psswdField.sendKeys("secret_sauce");
				loginButton.click();
				Thread.sleep(2000);

				WebElement firstProduct = driver
						.findElement(By.cssSelector(".inventory_item:first-child .inventory_item_name"));
				firstProduct.click();
				Thread.sleep(2000);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// Close the driver
				driver.quit();
			}
		} else {
			System.out.println("Driver not found for the specified browser.");
		}
	}

	private static WebDriver getDriver(String browser) {
		WebDriver driver = null;

		try {
			switch (browser.toLowerCase()) {
			case "chrome":
				String chromeDriverPath = Paths.get("src/test/java/com/devaura/qa/drivers/chrome/chromedriver")
						.toAbsolutePath().toString();
				System.setProperty("webdriver.chrome.driver", chromeDriverPath);
				ChromeOptions chromeOptions = new ChromeOptions();
				driver = new ChromeDriver(chromeOptions);
				break;

			case "firefox":
				String geckoDriverPath = Paths.get("src/test/java/com/devaura/qa/drivers/firefox/geckodriver")
						.toAbsolutePath().toString();
				System.setProperty("webdriver.gecko.driver", geckoDriverPath);
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.setCapability("marionette", true); 

				driver = new FirefoxDriver(firefoxOptions);
				break;

			default:
				System.err.println("Browser not supported: " + browser);
				break;
			}
		} catch (Exception e) {
			System.err.println("Error initializing WebDriver: " + e.getMessage());
		}

		return driver;
	}
}