package TestCases;

import Page.SearchPage;
import Utils.TestUtils;
import org.junit.*;

public class SearchResultsTest extends TestUtils {
    static SearchPage searchPage;

    @BeforeClass
    public static void SetUp(){
        initialization();
        searchPage = new SearchPage();
    }

    @Before
    public void Search(){
        WaitForPageLoad();
        searchPage.SearchForKeyword();
    }

    @AfterClass
    public static void TearDown(){
        driver.quit();
    }

    @Test
    public void VerifyFirstResults() {

        String keyword = properties.getProperty("searchKeyWord");
        WaitForPageLoad();

        String firstResult = searchPage.GetFirstSearchResult().getText();
        String secondResult = searchPage.GetSecondSearchResult().getText();

        Assert.assertTrue("Keyword is not found in either result",
                firstResult.contains(keyword) && secondResult.contains(keyword));
    }

    @Test
    public void VerifySecondPageResults(){
        searchPage.GoToPage2();
        int actualPageCount = searchPage.CountOfPageElements();
        searchPage.SetSecondPageCount(actualPageCount);
        int expectedCount = Integer.parseInt(properties.getProperty("itemsPerPage"));

        Assert.assertEquals("Results count is not as expected", expectedCount, actualPageCount);

    }

    @Test
    public void VerifyThirdPageResults(){
        searchPage.GoToPage3();

        int actualPageCount = searchPage.CountOfPageElements();
        int expectedCount = searchPage.GetSecondPageCount();

        Assert.assertEquals("Results count is not as expected", expectedCount, actualPageCount);

    }

}
