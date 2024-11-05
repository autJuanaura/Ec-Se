package com.devaura.qa;

import java.nio.file.Paths;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariDriver;

public class AttachFile implements Runnable {

    private String browser;

    public AttachFile(String browser) {
        this.browser = browser;
    }

    public static void main(String[] args) {
        int numberOfThreads = 1; 
        String browser = "chrome";

        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(new AttachFile(browser));
            thread.start();
        }
    }

    @Override
    public void run() {
        WebDriver driver = getDriver(browser);
        if (driver != null) {
            try {
                driver.get("https://demoqa.com/upload-download");
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
                WebElement uploadElement = driver.findElement(By.id("uploadFile"));
                String filePath = Paths.get("src/test/java/com/devaura/qa/testfiles/HellomAR24.pdf").toAbsolutePath().toString();
                uploadElement.sendKeys(filePath);
                Thread.sleep(2000); 
                WebElement uploadedFileName = driver.findElement(By.id("uploadedFilePath"));
                System.out.println("Uploaded file: " + uploadedFileName.getText());
            } catch (Exception e) {
                e.printStackTrace(); 
            } finally {
                driver.quit();
            }
        } else {
            System.err.println("Driver not found for the specified browser.");
        }
    }

    private static WebDriver getDriver(String browser) {
        WebDriver driver = null;
        String driverPath = Paths.get("src/test/java/com/devaura/qa/drivers/chrome/chromedriver").toAbsolutePath().toString();

        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", driverPath);
                    ChromeOptions options = new ChromeOptions();
                    driver = new ChromeDriver(options);
                    break;

                case "safari":
                    if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                        driver = new SafariDriver();
                    } else {
                        System.err.println("Safari is only supported on macOS.");
                    }
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