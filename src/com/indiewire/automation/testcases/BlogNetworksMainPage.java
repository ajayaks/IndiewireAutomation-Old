package com.indiewire.automation.testcases;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indiewire.automation.common.Config;
import com.indiewire.automation.common.Utils;
import com.indiewire.automation.common.WebDriverWrapper;

/**
 * Test case for all blog networks main page verification.
 * 
 * @author Ajay Kumar
 *
 */

public class BlogNetworksMainPage {
  private WebDriver driver;
  private String baseUrl;

  private StringBuffer verificationErrors = new StringBuffer();
  private static String baseElement;
  private static final Logger LOG = LoggerFactory.getLogger(BlogNetworksMainPage.class);

  @Before
  public void setUp() throws Exception {

    driver = WebDriverWrapper.getWebDriver(WebDriverWrapper.Driver.ChromeDriver);
    Properties properties = Config.INSTANCE.getProperties();

    baseUrl = properties.getProperty("MAINSITE_URL");
    baseElement = ".//*[@id='main-nav']/div[@class='nav-parent']/a[@href='/blogs']";
    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    System.out.println("Starting testBlogNetworksMainPageLink() test case::");

  }

  @Test
  public void testBlogNetworksMainPageLink() throws Exception {

    try {
      driver.get(baseUrl);

      WebDriverWait wait = new WebDriverWait(driver, 60);
      Actions actions = new Actions(driver);
      WebElement triggerDropDown =
          wait.until(ExpectedConditions.elementToBeClickable(By.xpath(baseElement)));
      actions.moveToElement(triggerDropDown).build().perform();

      List<WebElement> webElementsList =
          driver.findElements(By.xpath(baseElement + "/following-sibling::div/a"));
      System.out.println("List size " + webElementsList.size());


      for (int index = 1; index <= webElementsList.size(); index++) {
        
        WebElement webElement =
            getWebElement(driver, baseElement + "/following-sibling::div/a[" + index + "]", true);

        String networkName = webElement.getText();
        webElement.click();
        Utils.checkPageIsReady(driver);
        String exceptionMessage = Utils.getExcpetionFromPage(driver);
        if (exceptionMessage.isEmpty()) {

          LOG.info(networkName+" main page loaded successfully :: " + driver.getTitle());

        } else {
          LOG.error("Exception found in "+networkName+" page :: " + exceptionMessage);
          Utils.captureScreenShot(driver,networkName.replace(" ", "_"));

        }

        driver.navigate().back();
        // driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      }

    } catch (Exception ex) {
      verificationErrors.append("Failed in testBlogNetworksMainPageLink() :: " + ex.getMessage());

    }

  }

  /**
   * Return web element based on xpath.
   * 
   * @param driver
   * @param text
   * @param retry
   * @return
   */

  private WebElement getWebElement(WebDriver driver, String text, boolean retry) {
    WebElement myDynamicElement = null;
    try {
      WebDriverWait wait = new WebDriverWait(driver, 60);
      Actions actions = new Actions(driver);
      WebElement triggerDropDown =
          wait.until(ExpectedConditions.elementToBeClickable(By.xpath(baseElement)));

      actions.moveToElement(triggerDropDown).build().perform();
      myDynamicElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(text)));
      actions.moveToElement(triggerDropDown).build().perform();

      myDynamicElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(text)));
      actions.moveToElement(triggerDropDown).build().perform();


      return myDynamicElement;
    } catch (Exception ex) {
      //verificationErrors.append("Failed in testIWMainPageLink() :: " + ex.getMessage());      
      if (retry) {
        System.out.println("One more try, to overcome slow network speed issue::");
        getWebElement(driver, text, false);
      }
      throw ex;
      //return null;

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
