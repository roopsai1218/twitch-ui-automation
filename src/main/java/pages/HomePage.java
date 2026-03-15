package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;
import utils.WaitUtils;

public class HomePage extends BasePage {

public HomePage(WebDriver driver) {
super(driver);
}

By searchIcon = By.xpath("//a[@href='/directory' and .//div[text()='Browse']]");

public void openSite() {

driver.get("https://m.twitch.tv/");

WaitUtils.waitForElementVisible(driver, searchIcon);

}

public void clickSearch() {
click(searchIcon);
}

}