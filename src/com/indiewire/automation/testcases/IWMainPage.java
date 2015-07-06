package com.indiewire.automation.testcases;

import static org.junit.Assert.fail;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indiewire.automation.common.Config;
import com.indiewire.automation.common.Utils;
import com.indiewire.automation.common.WebDriverWrapper;

/**
 * Test case for IW main page verification.
 * 
 * @author Ajay Kumar
 *
 */

public class IWMainPage {
  private WebDriver driver;
  private String baseUrl;

  private StringBuffer verificationErrors = new StringBuffer();

  private static final Logger LOG = LoggerFactory.getLogger(IWMainPage.class);

  @Before
  public void setUp() throws Exception {

    driver = WebDriverWrapper.getWebDriver(WebDriverWrapper.Driver.ChromeDriver);

    Properties properties = Config.INSTANCE.getProperties();

    baseUrl = properties.getProperty("MAINSITE_URL");

    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    System.out.println("Starting testIWMainPageLink() test case::");
  }

  @Test
  public void testIWMainPageLink() {
    try {
      driver.get(baseUrl);

      String exceptionMessage = Utils.getExcpetionFromPage(driver);
      if (exceptionMessage.isEmpty()) {

        LOG.info("Indiewire Main page loaded successfully :: " + driver.getTitle());

      } else {
        LOG.error("Exception found in IWMainPage :: " + exceptionMessage);
        Utils.captureScreenShot(driver,"IWMainPage");

      }

    } catch (Exception e) {
      verificationErrors.append("Failed in testIWMainPageLink() :: " + e.getMessage());

    }


  }


  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

}
