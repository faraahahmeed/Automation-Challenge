package Utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;


public class TestUtils {

    public WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    public static Properties properties;
    public static WebDriver driver;

    /*
    URL and browser type are read from config file
     */
    public static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            try {
                FileInputStream fis = new FileInputStream("src/main/java/Utils/Config.properties");
                properties.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load config.properties");
            }
        }
        return properties;
    }
    public static void initialization(){
        properties = getProperties();
        String browserName = properties.getProperty("browser");

        if(browserName.equalsIgnoreCase("Chrome")){
            driver = new ChromeDriver();
        }
        else if(browserName.equalsIgnoreCase("Edge")){
            driver = new EdgeDriver();
        }
        else if(browserName.equalsIgnoreCase("FireFox")){
            driver = new FirefoxDriver();
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(properties.getProperty("URL"));

    }
    public void WaitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    public void ScrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public void RandomPause(){
        try {
            Thread.sleep(200 + new Random().nextInt(300));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void TakeScreenshotAtEndOfTest() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
    }


}
