package com.devaura.qa;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ParallelGhGrid {

	private static final String GRID_URL = "http://localhost:4444/wd/hub";

	public void runTests() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(() -> runTest("chrome"));
		executor.submit(() -> runTest("firefox"));
		executor.shutdown();
	}

	private void runTest(String browser) {
		WebDriver driver = null;

		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName(browser);
			driver = new RemoteWebDriver(new URL(GRID_URL), capabilities);
			driver.get("http://example.com");
			System.out.println("Title of the page in " + browser + ": " + driver.getTitle());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}

	public static void main(String[] args) {
		ParallelGhGrid test = new ParallelGhGrid();
		test.runTests();
	}
}