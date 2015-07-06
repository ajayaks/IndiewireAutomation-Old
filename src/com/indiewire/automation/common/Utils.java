package com.indiewire.automation.common;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indiewire.automation.testcases.IWMainPage;

/**
 * Utility class having common utility methods
 * 
 * @author Ajay Kumar
 *
 */

public class Utils {
  static Properties properties = null;
  private static final Logger LOG = LoggerFactory.getLogger(IWMainPage.class);

 /**
  * Method for capturing screenshot.
  * @param driver
  * @param fileName
  */

  public static void captureScreenShot(WebDriver driver,String fileName) {

    try {
      properties = Config.INSTANCE.getProperties();
      File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      // The below method will save the screen shot in d drive with name "screenshot.png"
      String screenShotLocation = properties.getProperty("SCREENSHOTS_LOCATION");
      System.out.println("Source " + scrFile + ":::: "+driver.getCurrentUrl());
      FileUtils.copyFile(scrFile, new File(screenShotLocation +fileName + ".png"));
    } catch (Exception e) {

      // LOG.error("Failed to take capture Screen:: " + e.getMessage());
      System.out.println("Failed to capture Screenshot:: " + e.getMessage());
    }

  }

  /**
   * Method for capturing exception from site page.
   * 
   * @param driver
   * @return
   */
  public static String getExcpetionFromPage(WebDriver driver) {
    String exceptionMessage = "";
    try{
    WebDriverWait wait = new WebDriverWait(driver, 5);
    // WebElement exceptionIFrame =
    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By
        .xpath(".//iframe[contains(@src,'/_resource/dari/alert.html?id=')]")));

    // driver.switchTo().frame(exceptionIFrame);

    exceptionMessage = driver.findElement(By.className("alert-error")).getText();
    }catch(Exception ex){
      //No action required if there is no exception(iframe) on page.
    }
    return exceptionMessage;
  }

  /**
   * Check if page is loaded or not
   * 
   * @param driver
   */

  public static void checkPageIsReady(WebDriver driver) {

    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Check for ready state of page
    if (js.executeScript("return document.readyState").toString().equals("complete")) {
      System.out.println("Page Is loaded.");
      return;
    }
    // Would check for page ready state after 1 second.
    for (int i = 0; i < 25; i++) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
      }
      // To check page ready state.
      if (js.executeScript("return document.readyState").toString().equals("complete")) {
        System.out.println("Page Is loaded1111.");
        break;
      }
    }
  }

}
