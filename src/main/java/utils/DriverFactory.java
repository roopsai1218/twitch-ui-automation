package utils;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

private static WebDriver driver;

public static WebDriver getDriver() {

if(driver == null) {

WebDriverManager.chromedriver().setup();

Map<String, String> mobileEmulation = new HashMap<>();
mobileEmulation.put("deviceName", "iPhone X");

ChromeOptions options = new ChromeOptions();
options.setExperimentalOption("mobileEmulation", mobileEmulation);

driver = new ChromeDriver(options);

driver.manage().window().maximize();

}

return driver;
}

public static void quitDriver() {

if(driver != null) {
driver.quit();
driver = null;
}

}

}