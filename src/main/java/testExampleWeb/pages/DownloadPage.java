package testExampleWeb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DownloadPage {
    private WebDriver driver;

   // Instance the browser
    public DownloadPage(WebDriver driver) {
        this.driver = driver;
    }

    // Find the link to download and click on it
    public void downloadFile(String fileName) {
        WebElement fileLink = driver.findElement(By.linkText(fileName));
        fileLink.click();
    }
}