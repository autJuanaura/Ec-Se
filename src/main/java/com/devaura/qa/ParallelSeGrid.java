package com.devaura.qa;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ParallelSeGrid implements Runnable {

	private String browser;

	public ParallelSeGrid(String browser) {
		this.browser = browser;
	}

	public static void main(String[] args) {
		String[] browsers = { "chrome", "firefox" };

		for (String browser : browsers) {
			Thread thread = new Thread(new EcParallelDifBrowsers(browser));
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
				driver.quit();
			}
		} else {
			System.out.println("Driver not found for the specified browser.");
		}
	}

	private static WebDriver getDriver(String browser) {
		WebDriver driver = null;

		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			switch (browser.toLowerCase()) {
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;

			case "firefox":
				capabilities.setBrowserName("firefox");
				break;

			default:
				System.err.println("Browser not supported: " + browser);
				break;
			}

			String gridUrl = "http://localhost:4444/wd/hub";
			driver = new RemoteWebDriver(new URL(gridUrl), capabilities);

		} catch (MalformedURLException e) {
			System.err.println("Invalid Grid URL: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Error initializing WebDriver: " + e.getMessage());
		}

		return driver;
	}
}