package com.example.tests;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

/**
 * Base class for TestNG-based test classes
 */
public class BaseTest {

  protected static String baseUrl;
  protected static Capabilities capabilities;

  protected WebDriver driver;
  public static Properties prop;

  @BeforeSuite
  public void initTestSuite() throws IOException {
    try {
      prop = new Properties();
      FileInputStream ip = new FileInputStream(
          System.getProperty("user.dir") + "/src/test/resources/application.properties");
      prop.load(ip);
      baseUrl = prop.getProperty("url");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (prop.getProperty("browser").equals("chrome")) {
      WebDriverManager.chromedriver().setup();
    }
    if (prop.getProperty("browser").equals("firefox")) {
      WebDriverManager.firefoxdriver().setup();
    }
  }

  @BeforeMethod
  public void initWebDriver() throws IOException {
    Allure.label("browser", prop.getProperty("browser"));
    if (prop.getProperty("browser").contains("chrome")) {
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--headless");
      options.addArguments("--disable-gpu"); // Disable GPU acceleration
      options.addArguments("--window-size=1920,1080"); // Set the window size
      options.addArguments("--no-sandbox"); // Disable the sandbox mode
      options.addArguments("--disable-dev-shm-usage"); // Disable the /dev/shm usage
      options.addArguments("--ignore-certificate-errors"); // Ignore certificate errors
      driver = new ChromeDriver(options);

    }

    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
  }

  @AfterMethod(alwaysRun = true)
  @Attachment(value = "Screenshot", type = "image/png")
  public void tearDown() {
    Allure.addAttachment("ScreenShot",
        new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    driver.quit();
  }
}
