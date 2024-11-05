package com.devaura.qa;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class EBrowserSt {

	public static void main(String[] args) throws InterruptedException {
		String username = "YOUR_BROWSERSTACK_USERNAME";
		String accessKey = "YOUR_BROWSERSTACK_ACCESS_KEY";

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", "Safari");
		capabilities.setCapability("browserstack.local", "false");
		capabilities.setCapability("os", "OS X");
		capabilities.setCapability("os_version", "Big Sur");
		capabilities.setCapability("name", "Ecommerce Flow Test on Safari");
		WebDriver driver = null;
		try {
			driver = new RemoteWebDriver(
					new URL("https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub"),
					capabilities);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
			driver.get("https://www.saucedemo.com/");
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
			WebElement addCardToButton = driver.findElement(By.id("shopping_cart_link"));
			addCardToButton.click();
			Thread.sleep(2000);
			WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
			cartIcon.click();
			Thread.sleep(2000);
			WebElement cartItem = driver.findElement(By.cssSelector(".cart_item .inventory_item_name"));
			System.out.println("Item in cart: " + cartItem.getText());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}
}