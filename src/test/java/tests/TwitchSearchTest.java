package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.SearchPage;
import pages.StreamerPage;
import utils.DriverFactory;

public class TwitchSearchTest {

	WebDriver driver;

	@Test
	public void searchStarcraftStreamer() throws InterruptedException {

		driver = DriverFactory.getDriver();

		HomePage home = new HomePage(driver);
		SearchPage search = new SearchPage(driver);
		StreamerPage streamer = new StreamerPage(driver);

		home.openSite();

		home.clickSearch();

		search.searchGame("StarCraftII");

		search.selectStreamer();

		streamer.openFirstVideo();

		streamer.closePopupIfPresent();

		streamer.waitForStream();
		
		Thread.sleep(5000); // waiting for stream to stabilize before taking screenshot

		streamer.takeScreenshot();

	}

	@AfterTest
	public void closeBrowser() {

		DriverFactory.quitDriver();

	}

}