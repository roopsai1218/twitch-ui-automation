package base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitUtils;

public class BasePage {

protected WebDriver driver;

public BasePage(WebDriver driver) {
this.driver = driver;
}

public void click(By locator) {

WaitUtils.waitForElementVisible(driver, locator);
WaitUtils.waitForElementClickable(driver, locator).click();

}

public void type(By locator, String text) {
WaitUtils.waitForElementVisible(driver, locator).sendKeys(text);
}

public void scrollDown() {

JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("window.scrollBy(0,1000)");

}

public void handlePopups() {

try {

List<WebElement> popup =
driver.findElements(By.xpath("//button[@data-test-selector='muted-segments-alert-overlay-presentation__dismiss-button']"));

if(!popup.isEmpty()) {
popup.get(0).click();
}

} catch(Exception e) {}

}

}