package com.indiewire.automation.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * For loading automation configurations.
 * @author Ajay Kumar
 *
 */
public enum Config {

  INSTANCE;
  public Properties getProperties() {
    Properties properties = new Properties();
    InputStream in = this.getClass().getClassLoader().getResourceAsStream("config.properties");
    try {
      properties.load(in);
       in.close();
    } catch (IOException e) {
      System.out.println("Failed to load config :: " + e.getMessage());
    }
    return properties;

  }



}
