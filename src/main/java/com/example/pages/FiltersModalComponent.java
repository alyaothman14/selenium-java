package com.example.pages;

import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import io.qameta.allure.Allure;

public class FiltersModalComponent extends BasePage {

    WebDriver driver;
    public ArrayList<String> roomsAndBedsType = new ArrayList<>(Arrays.asList("Beds", "Bedrooms", "Bathrooms"));
    public ArrayList<String> amenities = new ArrayList<>(
            Arrays.asList("Beach front", "Free WiFi", "Air conditioning", "Pets allowed", "Street parking",
                    "Swimming pool", "Kitchen", "Washing Machine", "Hot tub", "Suitable for children"));

    @FindBy(how = How.XPATH, using = "//input[@placeholder='From']")
    public WebElement priceFromInput;

    @FindBy(how = How.XPATH, using = "//input[@placeholder='To']")
    public WebElement priceToInput;

    @FindBy(how = How.XPATH, using = "//*[text()='Clear all']")
    public WebElement clearAllButton;

    public FiltersModalComponent(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public FiltersModalComponent fillFromPrice(String value) {
        Allure.step("Fill from price with value " + value);
        if (priceFromInput.isEnabled()) {
            fillText(priceFromInput, value);
        }
        return this;

    }

    public FiltersModalComponent fillToPrice(String value) {
        Allure.step("Fill to price with value " + value);

        if (priceToInput.isEnabled()) {
            fillText(priceToInput, value);
        }
        return this;

    }

    public WebElement getIncreaseRoomsAndBedsElement(String type) {
        Allure.step("Get increase element for Rooms and Beds filter with type: " + type);

        if (roomsAndBedsType.contains(type)) {
            WebElement increaseButton = this.driver
                    .findElement(By.xpath("//*[text()='" + type + "']/parent::div//button[2]"));
            return increaseButton;
        } else {
            throw new IllegalArgumentException(
                    "Invalid type provided: " + type + " Allowed types are" + roomsAndBedsType);
        }
    }

    // Type accepts Beds, Bedrooms and Bathrooms
    public FiltersModalComponent increaseRoomsAndBeds(String type) {
        Allure.step("Increase number by 1 for Rooms and Beds for type:  " + type);

        if (roomsAndBedsType.contains(type)) {
            click(getIncreaseRoomsAndBedsElement(type));
        } else {
            throw new IllegalArgumentException(
                    "Invalid type provided: " + type + " Allowed types are" + roomsAndBedsType);
        }
        return this;
    }

    // Type accepts Beds, Bedrooms and Bathrooms
    public WebElement getDecreaseRoomsAndBedsElement(String type) {
        Allure.step("Get decrease button for Rooms and Beds filter with type: " + type);
        if (roomsAndBedsType.contains(type)) {
            WebElement decreaseButton = this.driver
                    .findElement(By.xpath("//*[text()='" + type + "']/parent::div//button[1]"));
            return decreaseButton;
        } else {
            throw new IllegalArgumentException(
                    "Invalid type provided: " + type + " Allowed types are" + roomsAndBedsType);
        }
    }

    // Type accepts Beds, Bedrooms and Bathrooms
    public FiltersModalComponent decreaseRoomsAndBeds(String type) {
        Allure.step("Decrease number by 1 for Rooms and Beds for type:  " + type);

        if (roomsAndBedsType.contains(type)) {
            click(getDecreaseRoomsAndBedsElement(type));
        } else {
            throw new IllegalArgumentException(
                    "Invalid type provided: " + type + " Allowed types are" + roomsAndBedsType);
        }
        return this;
    }

    // Type accepts Beds, Bedrooms and Bathrooms
    public String getValueForRoomsAndBeds(String type) {
        Allure.step("Get value of Rooms and Beds with type: " + type);

        if (roomsAndBedsType.contains(type)) {
            WebElement value = this.driver.findElement(By.xpath("//*[text()='" + type + "']/parent::div//span"));
            return getText(value);
        } else {
            throw new IllegalArgumentException(
                    "Invalid type provided: " + type + " Allowed types are" + roomsAndBedsType);
        }
    }

    public FiltersModalComponent checkAnAmenity(String amenityName) {
        Allure.step("Check Amenity with amenity name: " + amenityName);

        if (amenities.contains(amenityName)) {
            WebElement amenitySpan = this.driver
                    .findElement(By.xpath("//input[@id='id']/parent::label/*[text()='" + amenityName + "']"));

            scrollIntoView(this.driver, amenitySpan);

            click(amenitySpan);
        } else {
            throw new IllegalArgumentException(
                    "Invalid type provided: " + amenityName + " Allowed types are" + amenities);
        }
        return this;
    }

    public WebElement getAmenityCheckBoxElement(String amenityName) {
        Allure.step("Get Amenity checkbox element with amenity name: " + amenityName);

        if (amenities.contains(amenityName)) {

            WebElement amenityCheckbox = this.driver.findElement(By.xpath("//input[@id='id']/parent::label/*[text()='"
                    + amenityName + "']/preceding-sibling::input[@type='checkbox']"));

            return amenityCheckbox;
        } else {
            throw new IllegalArgumentException(
                    "Invalid type provided: " + amenityName + " Allowed types are" + amenities);
        }
    }

    public FiltersModalComponent clickClearAll() {
        Allure.step("Click clear all button");

        click(clearAllButton);
        return this;
    }

}
