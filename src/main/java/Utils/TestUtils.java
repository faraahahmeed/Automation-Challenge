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
    This function parses the Config.properties file to easily read its values within the tests
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
/*
    1. Initializing the driver with the stated browser type form config file
       a. equalsIgnoreCase is used to avoid input error as much as possible
       b. all 3 browsers (Google Chrome, Microsoft Edge and Mozilla FireFox) are supported
   2. Driver has been set to clear cookies and maximize the browser window
 */
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

/*
    Configured to wait for page's ready state to ensure page is fully loaded before interactions
 */
    public void WaitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }


/*
    Scrolling to a desired element in the page
 */
    public void ScrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

/*
    Used to add random breaks/pauses during the test run to avoid triggering captcha checks
 */
    public void RandomPause(){
        try {
            Thread.sleep(200 + new Random().nextInt(300));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


/*
    Take screenshots after failed tests for reporting
 */
    public static void TakeScreenshotAtEndOfTest() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
    }


}
