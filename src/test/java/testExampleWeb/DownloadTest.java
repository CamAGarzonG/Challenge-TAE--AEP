package testExampleWeb;

import testExampleWeb.pages.DownloadPage;
import testExampleWeb.utils.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.nio.file.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DownloadTest {

    private WebDriver driver;
    private String downloadDir = System.getProperty("user.home") + "/Downloads";
    private String fileName = "some-file.txt"; // *** If you change it, content validation should change too *** //

    @BeforeAll
    void setup() {
        // Optain automatically the updated chromium version
        WebDriverManager.chromedriver().setup();
        // Chrome configuration
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("download.default_directory=" + downloadDir);
        // Go to expected page
        driver = new ChromeDriver(options);
        driver.get("https://the-internet.herokuapp.com/download");
    }

    @Test
    void testDownloadFile() throws IOException, InterruptedException {
        DownloadPage downloadPage = new DownloadPage(driver);

        // Download file (Wait for 10 seconds)
        downloadPage.downloadFile(fileName);
        Path filePath = Paths.get(downloadDir, fileName);
        int waitSeconds = 10;
        int waited = 0;
        while (!Files.exists(filePath) && waited < waitSeconds) {
            Thread.sleep(1000);
            waited++;
        }
        // Verify file exist
        if (!Files.exists(filePath)) {
            throw new IllegalStateException("The file " + filePath + " was not downloaded after 20 seconds in expected route.");
        }
        System.out.println("File was downloaded in" + filePath);
        String content = FileUtils.readFileContent(filePath);
        Assertions.assertThat(content).isNotEmpty();
        System.out.println("File "+ fileName + " is not empty");
        // Validate file content
        try {
            Assertions.assertThat(content).isEqualTo("blah blah blah\n");
            System.out.println("Text contains blah blah blah");
        } catch (AssertionError e) {
            System.err.println("¡ALERT! File text is not the expected. \nExpected: blah blah blah\nCurrent:"+content);
            throw e;
        }
    }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        // Delete download files
        Path filePath = Paths.get(downloadDir, fileName);
        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
                System.out.println("File deleted: " + filePath);
            } catch (IOException e) {
                System.err.println("Error in delete process: " + filePath);
            }
        }
    }
}