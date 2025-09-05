package Utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;


public class TestUtils {

    public static Properties properties;
    public static WebDriver driver;
    public static WebDriverWait wait ;
    static ExtentReports extent;
    static ExtentTest test;

    public static ExtentReports getExtent() {
        return extent;
    }
    public static void setTest(ExtentTest test) {
        TestUtils.test = test;
    }
    public static ExtentTest getTest() {
        return test;
    }


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
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(properties.getProperty("URL"));

    }

/*
    Configured to wait for page's ready state to ensure page is fully loaded before interactions
 */
    public static void WaitForPageLoad() {
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
    public static void TakeScreenshotAtEndOfTest(String methodName) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String screenshotPath = currentDir + "/screenshots/" + methodName + "_" + timestamp + ".png";
        FileUtils.copyFile(scrFile, new File(screenshotPath));
    }


    public static void startReport() {
        ExtentSparkReporter report = new ExtentSparkReporter("Test-Report.html");
        extent = new ExtentReports();
        extent.attachReporter(report);
    }

    public static void endReport() {
        if (extent != null) {
            extent.flush();
        }
    }

}
