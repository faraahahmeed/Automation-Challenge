package Locators;

import Utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/*
    Separating locators from page code for easier future changes
 */
public class SearchPageLocators extends TestUtils {
    protected WebElement searchBox = driver.findElement(By.id("sb_form_q"));
    protected By searchResults = By.xpath("//li[@class='b_algo']");
    protected By secondPageLocator = By.xpath("//a[@aria-label='Page 2']");
    protected By thirdPageLocator = By.xpath("//a[@aria-label='Page 3']");
    protected By pagesList = By.xpath("//li[@class='b_pag']");
}
