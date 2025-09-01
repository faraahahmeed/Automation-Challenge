package TestCases;

import Page.SearchPage;
import Utils.TestUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SearchResultsTest extends TestUtils {
    static SearchPage searchPage;

    @BeforeClass
    public static void SetUp(){
        initialization();
        searchPage = new SearchPage();
        searchPage.SearchForKeyword();
    }

    @AfterClass
    public static void TearDown(){
        driver.quit();
    }
    @Test
    public void VerifyFirstResults() {
        String keyword = properties.getProperty("searchKeyWord");
        waitForPageLoad();

        String firstResult = searchPage.FirstSearchResult().getText();
        String secondResult = searchPage.SecondSearchResult().getText();

        Assert.assertTrue("Keyword is not found in either result",
                firstResult.contains(keyword) && secondResult.contains(keyword));
    }

//    @Test
//    public void VerifySecondPageResults(){
//        searchPage.GoToPage2();
//        int actualPageCount = searchPage.CountOfPageElements();
//        int expectedCount = Integer.parseInt(properties.getProperty("itemsPerPage"));
//
//        Assert.assertEquals("Results count is less than the expected", expectedCount, actualPageCount);
//
//    }

}
