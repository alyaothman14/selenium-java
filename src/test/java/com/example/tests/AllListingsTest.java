package com.example.tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.example.pages.AllListingsPage;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;

public class AllListingsTest extends BaseTest {

  private AllListingsPage allListingsPage;

  @BeforeMethod
  public void initPageObjects() {
    allListingsPage = PageFactory.initElements(driver, AllListingsPage.class);

  }

  @Test
  @Epic("All Listing")
  @Description("This test to verify that the listing loaded is the same as on the All Button")
  public void testTotalLoadedListEqualAllButton() {
    allListingsPage.navigateToAllListing();
    Allure.step("Assert that total loaded listing is equal to number beside all button");
    Assert.assertEquals(allListingsPage.getTotalNumberOfListingAfterLoadingAllListing(),
        allListingsPage.getTotalNumberOfListingAllButton());

  }

}
