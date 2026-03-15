package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

public static void capture(WebDriver driver, String name) {

File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

try {

FileUtils.copyFile(src, new File("screenshots/" + name + ".png"));

}catch(IOException e) {
e.printStackTrace();
}

}

}