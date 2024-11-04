package com.devaura.qa;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

public class EcommerceFlow4 implements Runnable {

    private String browser;

    public EcommerceFlow4(String browser) {
        this.browser = browser;
    }

    public static void main(String[] args) {
        // Specify the number of parallel threads you want to run
        int numberOfThreads = 50; // Change this as needed
        String browser = "chrome"; // Change to "safari" if needed

        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(new EcommerceFlow4(browser));
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

                WebElement firstProduct = driver.findElement(By.cssSelector(".inventory_item:first-child .inventory_item_name"));
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
        switch (browser.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "/home/autmarisella/eclipse-workspace/eCommerce-flow/src/test/java/com/devaura/qa/drivers/chrome/chromedriver");
                driver = new ChromeDriver();
                break;
            case "safari":
                if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                    driver = new SafariDriver();
                } else {
                    System.out.println("Safari is only supported on macOS.");
                }
                break;
            default:
                System.out.println("Browser not supported: " + browser);
                break;
        }
        return driver;
    }
}