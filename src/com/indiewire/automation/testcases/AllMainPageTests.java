package com.indiewire.automation.testcases;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test Suite for main pages.
 * @author Ajay Kumar
 *
 */
@RunWith(Suite.class)
@SuiteClasses({IWMainPage.class, BlogNetworksMainPage.class})
public class AllMainPageTests {

  @BeforeClass
  public static void setUp() {
    System.out.println("Starting AllMainPageTests test suite::");
  }

  @AfterClass
  public static void tearDown() {
    System.out.println("Tearing down AllMainPageTests test suite::");
  }

}
