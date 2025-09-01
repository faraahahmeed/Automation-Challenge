package Page;

import Utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;

public class SearchPage extends TestUtils {

    WebElement searchBox = driver.findElement(By.id("sb_form_q"));
    By searchResults = By.xpath("//li[@class='b_algo']");
    static List <WebElement> results;
//    By secondPageLocator = By.xpath("//a[@aria-label='Page 2']");
//    WebElement secondPage = driver.findElement(secondPageLocator);


    public WebElement FirstSearchResult (){
        return results.get(0);
    }
    public WebElement SecondSearchResult (){
        return results.get(1);
    }

    public void SearchForKeyword (){
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        String query = properties.getProperty("searchKeyWord");
        for (char c : query.toCharArray()) {
            searchBox.sendKeys(Character.toString(c));
            try {
                Thread.sleep(200 + new Random().nextInt(300));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        searchBox.sendKeys(Keys.ENTER);
        waitForPageLoad();

        results = driver.findElements(searchResults);
    }

//    public void GoToPage2(){
//        if(results.size()==0){
//            SearchForKeyword();
//        }
//        wait.until(ExpectedConditions.presenceOfElementLocated(secondPageLocator));
//        ScrollToElement(secondPage);
//
//        wait.until(ExpectedConditions.elementToBeClickable(secondPageLocator));
//        secondPage.click();
//    }

    public int CountOfPageElements(){
        return results.size();
    }
}
