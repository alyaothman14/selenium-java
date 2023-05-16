package com.example.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Allure;

public class BasePage {
    WebDriver driver;
    WebDriverWait wait;
    String baseUrl;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            Properties prop = new Properties();
            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir") + "/src/test/resources/application.properties");
            prop.load(ip);
            baseUrl = prop.getProperty("url");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PageFactory.initElements(driver, this);

    }

    public void click(WebElement element) {
        Allure.step("Clicking on " + element.toString().split("->")[1]);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void fillText(WebElement element, String text) {
        Allure.step("Filling element " + element.toString().split("->")[1] + " with text " + text);

        wait.until(ExpectedConditions.elementToBeClickable(element));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        element.sendKeys(text);
    }

    public String getText(WebElement element) {
        Allure.step("Get text value of " + element.toString().split("->")[1]);

        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    public static void scrollIntoView(WebDriver driver, WebElement element) {
        Allure.step("Scroll Into view for " + element.toString().split("->")[1]);

        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public static void scrollToBottom(WebDriver driver) {
        Allure.step("Scroll till all dynamic elements are loaded ");

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        long lastHeight = (long) jsExecutor.executeScript("return document.body.scrollHeight");

        while (true) {
            jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long newHeight = (long) jsExecutor.executeScript("return document.body.scrollHeight");

            if (newHeight == lastHeight) {
                break; // Reached the bottom of the page, exit the loop
            }

            lastHeight = newHeight;
        }
    }

}
