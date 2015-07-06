package com.indiewire.automation.common;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Wrapper class for driver
 * 
 * @author Ajay Kumar
 *
 */
public class WebDriverWrapper {

  static WebDriver webDriver = null;


  public enum Driver {
    ChromeDriver, FirefoxDriver, InternetExplorerDriver, HtmlUnitDriver
  }

  public static WebDriver getWebDriver(Driver driver) {
    Properties properties = Config.INSTANCE.getProperties();

    String driverLocation = properties.getProperty("DRIVERS_LOCATION");

    try {

      if (driver.equals(Driver.ChromeDriver)) {
        System.setProperty("webdriver.chrome.driver", driverLocation + "chromedriver.exe");
        webDriver = new ChromeDriver();
      } else if (driver.equals(Driver.FirefoxDriver)) {
        webDriver = new FirefoxDriver();
      } else if (driver.equals(Driver.InternetExplorerDriver)) {
        System.setProperty("webdriver.ie.driver", driverLocation + "IEDriverServer.exe");
        webDriver = new InternetExplorerDriver();
      } else if (driver.equals(Driver.HtmlUnitDriver)) {
        webDriver = new HtmlUnitDriver();
      }
    } catch (Throwable e) {
      System.out.println("Failed to load web driver :: " + e.getMessage());

    }

    return webDriver;
  }


}
