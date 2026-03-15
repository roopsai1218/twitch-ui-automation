package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class SearchPage extends BasePage {

public SearchPage(WebDriver driver) {
super(driver);
}

By searchInput = By.xpath("//input[@type='search']");
By firstSuggestion =By.xpath("(//img[contains(@class,'tw-image')]/ancestor::a)[1]");


public void searchGame(String game) {

type(searchInput, game);

}


public void selectStreamer() {

click(firstSuggestion);

}

}