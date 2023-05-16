package com.example.tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.example.pages.FiltersModalComponent;
import com.example.pages.SearchResultsPage;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;

public class FiltersFormTest extends BaseTest {

  private SearchResultsPage searchResultPage;
  private FiltersModalComponent filterModalComponent;
  private int MAX_ALLOWED_NUMBER = 10;
  private int MIN_ALLOWED_NUMBER = 0;
  LocalDate today = LocalDate.now();

  // Add one day to today's date
  LocalDate tomorrow = today.plusDays(1);

  // Define the desired date format
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  // Format the dates
  String formattedToday = today.format(formatter);
  String formattedTomorrow = tomorrow.format(formatter);

  @BeforeMethod
  public void initPageObjects() {
    searchResultPage = PageFactory.initElements(driver, SearchResultsPage.class);

  }

  @Test
  @Epic("Filter Form")
  @Description("Test that price input on filter modal is disabled if check in and out is not provided")
  public void testPriceDisabled() {
    filterModalComponent = searchResultPage.navigateToSearchPage((String[]) null).clickFilter();
    Allure.step("Assert that from price is disabled");
    Assert.assertFalse(filterModalComponent.priceFromInput.isEnabled());
    Allure.step("Assert that to price is disabled");
    Assert.assertFalse(filterModalComponent.priceToInput.isEnabled());

  }

  @Test
  @Description("Test that price input on filter modal is enabled if date is provided")
  @Epic("Filter Form")

  public void testPriceEnabledWhenDatesAreProvided() {
    filterModalComponent = searchResultPage.navigateToSearchPage("start=" + formattedToday, "end=" + formattedTomorrow)
        .clickFilter();
    Allure.step("Assert that from price is enabled");
    Assert.assertTrue(filterModalComponent.priceFromInput.isEnabled());
    Allure.step("Assert that to price is enabled");
    Assert.assertTrue(filterModalComponent.priceToInput.isEnabled());
    filterModalComponent.fillFromPrice("1").fillToPrice("200");
    Allure.step("Assert that from price value is correct");
    Assert.assertEquals(filterModalComponent.priceFromInput.getAttribute("value"), "1");
    Allure.step("Assert that to price value is correct");
    Assert.assertEquals(filterModalComponent.priceToInput.getAttribute("value"), "200");

  }

  @Test
  @Description("Test that price input on filter modal can not be zero")
  @Epic("Filter Form")

  public void testPriceCannotBeZero() {

    filterModalComponent = searchResultPage.navigateToSearchPage("start=" + formattedToday, "end=" + formattedTomorrow)
        .clickFilter();
    Allure.step("Assert that from price value is enabled");
    Assert.assertTrue(filterModalComponent.priceFromInput.isEnabled());
    Allure.step("Assert that to price value is enabled");
    Assert.assertTrue(filterModalComponent.priceToInput.isEnabled());

    filterModalComponent.fillFromPrice("0").fillToPrice("0");
    Allure.step("Assert that from price value is 0");
    Assert.assertEquals(filterModalComponent.priceFromInput.getAttribute("value"), "");
    Allure.step("Assert that to price value is 0");
    Assert.assertEquals(filterModalComponent.priceToInput.getAttribute("value"), "");

  }

  @Test
  @Description("Test that the max allowed numbers for Rooms and Beds input is 10 and increase button is disabled after")
  @Epic("Filter Form")
  public void testSelectingMaxNumberForRoomsAndBeds() {
    filterModalComponent = searchResultPage.navigateToSearchPage((String[]) null).clickFilter();

    for (String type : filterModalComponent.roomsAndBedsType) {
      for (int i = 0; i < MAX_ALLOWED_NUMBER; i++) {
        filterModalComponent.increaseRoomsAndBeds(type);
      }
      Allure.step("Assert that increase button is disabled for type: "+type);
      Assert.assertFalse(filterModalComponent.getIncreaseRoomsAndBedsElement(type).isEnabled());
      Allure.step("Assert that the max value is "+MAX_ALLOWED_NUMBER);
      Assert.assertEquals(filterModalComponent.getValueForRoomsAndBeds(type), Integer.toString(MAX_ALLOWED_NUMBER));
    }

  }

  @Test
  @Description("Test that the min allowed numbers for Rooms and Beds input is 0 and decrease button is disabled after")
  @Epic("Filter Form")
  public void testSelectingMinNumberForRoomsAndBeds() {
    filterModalComponent = searchResultPage.navigateToSearchPage((String[]) null).clickFilter();

    for (String type : filterModalComponent.roomsAndBedsType) {

      Assert.assertEquals(filterModalComponent.increaseRoomsAndBeds(type).getValueForRoomsAndBeds(type), "1");
      Allure.step("Assert that all Room and beds of type "+ type+" decrease button is disabled");

      Assert.assertFalse(
          filterModalComponent.decreaseRoomsAndBeds(type).getDecreaseRoomsAndBedsElement(type).isEnabled());
          Allure.step("Assert that all Room and beds of type "+ type+" is of min value 0");
  
        Assert.assertEquals(filterModalComponent.getValueForRoomsAndBeds(type), Integer.toString(MIN_ALLOWED_NUMBER));

    }

  }

  @Test
  @Description("Test that all Amenities can be selected")
  @Epic("Filter Form")

  public void testSelectAllAmenities() {
    filterModalComponent = searchResultPage.navigateToSearchPage((String[]) null).clickFilter();
    for (String type : filterModalComponent.amenities) {
      Allure.step("Assert that all amenity of type "+ type+" is selected");
      Assert.assertTrue(filterModalComponent.checkAnAmenity(type).getAmenityCheckBoxElement(type).isSelected());

    }

  }

  @Test
  @Description("Test Clear All resets all applied filters")
  @Epic("Filter Form")

  public void testClearAll() {
    filterModalComponent = searchResultPage.navigateToSearchPage("start=" + formattedToday, "end=" + formattedTomorrow)
        .clickFilter();
    filterModalComponent.fillFromPrice("1").fillToPrice("200");

    for (String type : filterModalComponent.roomsAndBedsType) {
      for (int i = 0; i < MAX_ALLOWED_NUMBER; i++) {
        filterModalComponent.increaseRoomsAndBeds(type);
      }
    }
    for (String amenityType : filterModalComponent.amenities) {
      filterModalComponent.checkAnAmenity(amenityType);
    }
    filterModalComponent.clickClearAll();
    for (String type : filterModalComponent.amenities) {
      Allure.step("Assert that all amenities are not checked");

      Assert.assertFalse(filterModalComponent.getAmenityCheckBoxElement(type).isSelected());
    }
    for (String typeRoom : filterModalComponent.roomsAndBedsType) {
      Allure.step("Assert that rooms and beds values are reset to 0");
      Assert.assertTrue(filterModalComponent.getIncreaseRoomsAndBedsElement(typeRoom).isEnabled());
      Assert.assertEquals(filterModalComponent.getValueForRoomsAndBeds(typeRoom), Integer.toString(MIN_ALLOWED_NUMBER));
    }
      Allure.step("Assert that from and to price is reset to empty value");
      Assert.assertEquals(filterModalComponent.priceFromInput.getAttribute("value"), "");
      Assert.assertEquals(filterModalComponent.priceToInput.getAttribute("value"), "");
    

  }
}
