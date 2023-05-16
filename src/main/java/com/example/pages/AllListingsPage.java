package com.example.pages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import io.qameta.allure.Allure;

public class AllListingsPage extends BasePage {

    WebDriver driver;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'All')]/span")
    public WebElement allListingTotal;

    public AllListingsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public AllListingsPage navigateToAllListing() {
        driver.get(this.baseUrl + "all-listings");
        return this;
    }

    public String getTotalNumberOfListingAllButton() {
        Allure.step("Get total number represented beside All button");
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(allListingTotal.getText());
        if (matcher.find()) {
            String number = matcher.group();
            return number;
        } else {
            throw new Error("All list does not have a value to represent the total listing");
        }
    }

    public String getTotalNumberOfListingAfterLoadingAllListing() {
        Allure.step("Get total number of all loaded listing on all listing page");
        scrollToBottom(this.driver);
        return Integer.toString((driver.findElements(By.xpath("//a[contains(@href,'/listings')]"))).size());
    }
}
