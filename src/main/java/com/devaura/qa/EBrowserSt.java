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
        // BrowserStack credentials
        String username = "YOUR_BROWSERSTACK_USERNAME"; // Replace with your BrowserStack username
        String accessKey = "YOUR_BROWSERSTACK_ACCESS_KEY"; // Replace with your BrowserStack access key

        // Desired capabilities for Safari on BrowserStack
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("browserstack.local", "false");
        capabilities.setCapability("os", "OS X");
        capabilities.setCapability("os_version", "Big Sur"); // You can specify the OS version if needed
        capabilities.setCapability("name", "Ecommerce Flow Test on Safari"); // Name of the test

        WebDriver driver = null;

        try {
            // Create a RemoteWebDriver instance
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub"), capabilities);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

            // Navigate to the website
            driver.get("https://www.saucedemo.com/");

            // Interact with the web elements
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            // Close the driver
            if (driver != null) {
                driver.quit();
            }
        }
    }
}