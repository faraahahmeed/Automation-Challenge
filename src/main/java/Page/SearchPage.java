package Page;

import Locators.SearchPageLocators;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


/*
    The class's main focus is interactions with the search page logic
 */
public class SearchPage extends SearchPageLocators {

    static int secondPageCount;
    static List <WebElement> results;


/*
    Returns number of elements on the second page of results
 */
    public void SetSecondPageCount(int count){
        secondPageCount = count;
    }


/*
    If no count os found for the second page, the scripts moves to the page and retrieve the number of items in it
 */
    public int GetSecondPageCount() {
        if(secondPageCount == 0){
            RandomPause();
            GoToPage2();
        }
        return secondPageCount;
    }

/*
    Returns the text of the first search result
 */
    public String GetFirstSearchResult (){
        return results.get(0).getText();
    }

/*
    Returns the text of the second search result
 */
    public String GetSecondSearchResult (){
        return results.get(1).getText();
    }

/*
    Retrieves the specified keyword from the config file and search for it then saving the search results
    in a list for later use
 */
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

/*
    Goes to the second page of the search results, count the displayed results and saving it for assertions
 */
    public void GoToPage2(){

        ScrollToElement(driver.findElement(pagesList));

        WebElement secondPage = wait.until(
                ExpectedConditions.elementToBeClickable(secondPageLocator)
        );
        secondPage.click();
        SetSecondPageCount(results.size());
    }

/*
    Goes to the third page of the search results
 */
    public void GoToPage3(){

        RandomPause();
        ScrollToElement(driver.findElement(pagesList));

        WebElement thirdPage = wait.until(
                ExpectedConditions.elementToBeClickable(thirdPageLocator)
        );
        thirdPage.click();
        WaitForPageLoad();
    }

/*
    Returns the number of results found on the page
 */
    public int CountOfPageElements(){
        return results.size();
    }
}
