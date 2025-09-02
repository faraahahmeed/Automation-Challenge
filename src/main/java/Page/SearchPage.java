package Page;

import Utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchPage extends TestUtils {

    static int secondPageCount;
    static List <WebElement> results;

    WebElement searchBox = driver.findElement(By.id("sb_form_q"));
    By searchResults = By.xpath("//li[@class='b_algo']");
    By secondPageLocator = By.xpath("//a[@aria-label='Page 2']");
    By thirdPageLocator = By.xpath("//a[@aria-label='Page 3']");
    By pagesList = By.xpath("//li[@class='b_pag']");


    public void SetSecondPageCount(int count){
        secondPageCount = count;
    }

    public int GetSecondPageCount() {
        if(secondPageCount == 0){
            RandomPause();
            GoToPage2();
        }
        return secondPageCount;
    }

    public WebElement GetFirstSearchResult (){
        return results.get(0);
    }
    public WebElement GetSecondSearchResult (){
        return results.get(1);
    }

    public void SearchForKeyword (){
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        String query = properties.getProperty("searchKeyWord");
        for (char c : query.toCharArray()) {
            searchBox.sendKeys(Character.toString(c));
            RandomPause();
        }
        searchBox.sendKeys(Keys.ENTER);
        WaitForPageLoad();

        results = driver.findElements(searchResults);
    }

    public void GoToPage2(){

        ScrollToElement(driver.findElement(pagesList));

        WebElement secondPage = wait.until(
                ExpectedConditions.elementToBeClickable(secondPageLocator)
        );
        secondPage.click();
        SetSecondPageCount(results.size());
    }

    public void GoToPage3(){
//        if(results.size()==0){
//            SearchForKeyword();
//        }
        RandomPause();
        ScrollToElement(driver.findElement(pagesList));

        WebElement thirdPage = wait.until(
                ExpectedConditions.elementToBeClickable(thirdPageLocator)
        );
        thirdPage.click();
        WaitForPageLoad();
    }

    public int CountOfPageElements(){
        return results.size();
    }
}
