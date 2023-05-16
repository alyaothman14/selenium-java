package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import io.qameta.allure.Allure;

public class SearchResultsPage extends BasePage {

    WebDriver driver;

    @FindBy(how = How.XPATH, using = "//button/*[text()='Filter']")
    public WebElement filterButton;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    // Navigate to search page with default queries if no queries are passed
    public SearchResultsPage navigateToSearchPage(String... queries) {
        Allure.step("Navigate to Search page with queries: " + queries);

        if (queries == null) {
            this.driver.get(this.baseUrl + "search?numberOfGuests=1");
        } else {
            StringBuilder urlBuilder = new StringBuilder("search?");
            for (String query : queries) {
                urlBuilder.append(query).append("&");
            }
            String url = urlBuilder.toString();
            Allure.step("Navigate to url " + this.baseUrl + url);
            driver.get(this.baseUrl + url);
        }
        return this;
    }

    public FiltersModalComponent clickFilter() {
        Allure.step("Click on Filter button on Search result page");

        click(filterButton);
        return new FiltersModalComponent(driver);
    }

}
