package com.devaura.qa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GeckoTest {
	public static void main(String[] args) {
		// Set the path for GeckoDriver
		System.setProperty("webdriver.gecko.driver", "src/test/java/com/devaura/qa/drivers/firefox/geckodriver");

		// Create a new instance of the Firefox driver
		WebDriver driver = new FirefoxDriver();

		try {
			// Navigate to Google's website
			driver.get("https://www.google.com");

			// Print the title of the page
			System.out.println("Title of the page is: " + driver.getTitle());
		} finally {
			// Close the browser
			driver.quit();
		}
	}
}