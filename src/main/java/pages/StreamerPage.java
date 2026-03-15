package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.BasePage;
import utils.WaitUtils;
import utils.ScreenshotUtil;

public class StreamerPage extends BasePage {

    public StreamerPage(WebDriver driver) {
        super(driver);
    }

    By firstVideo =
    By.xpath("(//div[@role='list']//article//button[contains(@aria-label,'Live')])[1]");

    By mutedPopupDismiss =
    By.xpath("//button[@data-test-selector='muted-segments-alert-overlay-presentation__dismiss-button']");

    By videoPlayer =
    By.xpath("//div[@data-a-target='video-player']");

    By videoElement =
    By.xpath("//video[@aria-label='Twitch video player']");

    public void openFirstVideo() {

        WebElement element = WaitUtils.waitForElementClickable(driver, firstVideo);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // scroll to stream card
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);


        try {

            element.click();

        } catch (Exception e) {

            // fallback JS click
            js.executeScript("arguments[0].click();", element);

        }

    }

    public void waitForStream() {

        WaitUtils.waitForPageLoad(driver);

        WaitUtils.waitForElementVisible(driver, videoPlayer);

        WaitUtils.waitForElementVisible(driver, videoElement);

    }

    public void closePopupIfPresent() {

        try {

            List<WebElement> popups = driver.findElements(mutedPopupDismiss);

            if (!popups.isEmpty()) {

                WaitUtils.waitForElementClickable(driver, mutedPopupDismiss).click();

                System.out.println("Muted audio popup dismissed");

            }

        } catch (Exception e) {

            System.out.println("No muted popup displayed");

        }

    }

    public void takeScreenshot() {

        ScreenshotUtil.capture(driver, "streamer_page");

    }

}